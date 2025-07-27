import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class DataSet {

    // Variables
    private String filename;
    private int numberOfMinima;
    private String calculationMethod;

    // Constructor
    public DataSet(String filename, int numberOfMinima, String calculationMethod) {

        this.filename = filename;
        this.numberOfMinima = numberOfMinima;
        this.calculationMethod = calculationMethod;

        System.out.println("\tGenerating " + this.filename + " for " + this.numberOfMinima + " minima calculated by " + this.calculationMethod);

        Data[][][][] data = new Data[52][52][52][52];

        // Set the position of the minima
        ArrayList<Minima> minima = new ArrayList<>();
        for (int i = 0; i < numberOfMinima; i++) {
            Minima aMinimum = new Minima();
            minima.add(aMinimum);
        }

        // Generate the data array
        for (int w = 0; w < 52; w++) {
            for (int x = 0; x < 52; x++) {
                for (int y = 0; y < 52; y++) {
                    for (int z = 0; z < 52; z++) {
                        if (calculationMethod.equals("Gauss")) {
                            data[w][x][y][z] = new Data(w, x, y, z, "-----", calculateGauss(w, x, y, z, minima), -1.00);
                        }
                        if (calculationMethod.equals("Random")) {
                            data[w][x][y][z] = new Data(w, x, y, x, "-----", calculateRandom(), -1.00);
                        }
                    }
                }
            }
        }

        // Flag all the minima in the set as 'LOCAL'; the lowest after a later sort will be flagged 'GLOBAL'
        int minimaCount = 0;
        for (int w = 1; w < 51; w++) {
            for (int x = 1; x < 51; x++) {
                for (int y = 1; y< 51; y++) {
                    for (int z = 1; z < 51; z++) {

                        boolean isMinimum = false;
                        if (    data[w][x][y][z].getValue() < data[w-1][x][y][z].getValue() &&
                                data[w][x][y][z].getValue() < data[w+1][x][y][z].getValue() &&
                                data[w][x][y][z].getValue() < data[w][x-1][y][z].getValue() &&
                                data[w][x][y][z].getValue() < data[w][x+1][y][z].getValue() &&
                                data[w][x][y][z].getValue() < data[w][x][y-1][z].getValue() &&
                                data[w][x][y][z].getValue() < data[w][x][y+1][z].getValue() &&
                                data[w][x][y][z].getValue() < data[w][x][y][z-1].getValue() &&
                                data[w][x][y][z].getValue() < data[w][x][y][z+1].getValue()

                        ) { isMinimum = true; }

                        if (isMinimum) {
                            data[w][x][y][z].setType("LOCAL");
                            minimaCount++;
                        }
                    }
                }
            }
        }

        // Easiest way to sort for a ranking is with an anraylist... so let's make one
        ArrayList<Data> sortData = new ArrayList<>();
        for (int w = 1; w < 51; w++) {
            for (int x = 1; x < 51; x++) {
                for (int y = 1; y < 51; y++) {
                    for (int z = 1; z < 51; z++) {
                        Data newData = new Data( data[w][x][y][z].getW(),
                                data[w][x][y][z].getX(),
                                data[w][x][y][z].getY(),
                                data[w][x][y][z].getZ(),
                                data[w][x][y][z].getType(),
                                data[w][x][y][z].getValue(),
                                data[w][x][y][z].getRank());
                        sortData.add(newData);
                    }
                }
            }
        }

        // Sort the array by value
        Collections.sort(sortData, Comparator.comparingDouble(Data::getValue));

        // DEBUG : Print out the first and last five entries to check for gremlins
        /*
        System.out.println("\n");
        for (int i = 0; i < 5; i++) {
            System.out.println(i + " : " + sortData.get(i));
        }
        System.out.println("\n");
        for (int i = sortData.size()-5; i < sortData.size(); i++) {
            System.out.println(i + " : " + sortData.get(i));
        }
        */

        // Now calculate the rank for each element
        for (int i = 0; i < sortData.size(); i++) {
            double rank = (double) 100.00 * i / sortData.size();
            sortData.get(i).setRank(rank);
        }

        // Set the rank 0 entry to the global minimum
        sortData.get(0).setType("GLOBAL");

        // Now put all that rank and type data back in the original array structure
        for (int i = 0; i < sortData.size(); i++) {
            int w = sortData.get(i).getW();
            int x = sortData.get(i).getX();
            int y = sortData.get(i).getY();
            int z = sortData.get(i).getZ();
            double rank = sortData.get(i).getRank();
            String type = sortData.get(i).getType();
            data[w][x][y][z].setRank(rank);
            data[w][x][y][z].setType(type);
        }

        // Now make the actual file
        try (FileWriter outputF = new FileWriter(this.filename)) {
            outputF.write("Method           : " + this.calculationMethod + "\n");
            outputF.write("Attempted minima : " + this.numberOfMinima + "\n");
            outputF.write("Minima found     :" + minimaCount + "\n");
            for (int i= 0; i < minima.size(); i++) {
                outputF.write(minima.get(i) + "\n");
            }
            outputF.write("\n");
            outputF.write("Data:\n");

            for (int w = 1; w < 51; w++) {
                for (int x = 1; x < 51; x++) {
                    for (int y = 1; y < 51; y++) {
                        for (int z = 1; z < 51; z++) {
                            String aS = "[" + w + "," + x + "," + y + "," + z + "]," +
                                         data[w][x][y][z].getValue() + "," +
                                         data[w][x][y][z].getRank() + "," +
                                         data[w][x][y][z].getType();
                            outputF.write(aS + "\n");
                        }
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Methods

    public static double calculateGauss(int w, int x, int y, int z, ArrayList<Minima> minima) {

        double answer = 0.00;

        for (int n = 0; n < minima.size(); n++) {

            Minima aMin = minima.get(n);
            int minw = aMin.getW();
            int minx = aMin.getX();
            int miny = aMin.getY();
            int minz = aMin.getZ();
            double width = aMin.getWidth();
            double depth = aMin.getDepth();

            double v = 0.00;
            v += (w - minw) * (w - minw);
            v += (x - minx) * (x - minx);
            v += (y - miny) * (y - miny);
            v += (z - minz) * (z - minz);

            v = depth * Math.exp(-v / width / width);

            answer += v;
        }

        return 1 - answer; // we want minima not maxima, and most of the values will be 1.00
    }

    public static double calculateRandom() {

        return RNG.GLOBAL.nextDouble();
    }
}

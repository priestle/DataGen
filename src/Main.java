import java.util.*;

public class Main {
    public static void main(String[] args) {

        /*
            (c) Nigel D. Priestley, 2025 -

            A program to generate a function in Z4 with independent data in the range [1,50]
            by using Gaussian functions... basically this in latex form...

            \[
            f(w, x, y, z) = \sum_{j=1}^{N} A_j \cdot \exp\left(
                -\frac{
                    (w - \mu_{j,w})^2 +
                    (x - \mu_{j,x})^2 +
                    (y - \mu_{j,y})^2 +
                    (z - \mu_{j,z})^2
                }{2\sigma_j^2}
            \right)
            \]
         */

        System.out.println("Starting data generation...");

        String outputFileName = "D:/output.csv";
        int numberOfMinima = 4;

        Data[][][][] data = new Data[52][52][52][52];
        Random random = new Random();

        // Set the position of the minima
        System.out.println("Generating minima for data set :");
        ArrayList<Minima> minima = new ArrayList<>();
        for (int i = 0; i < numberOfMinima; i++) {
            Minima aMinimum = new Minima();
            minima.add(aMinimum);
            System.out.println(aMinimum);
        }

        // Generate the data array
        for (int w = 0; w < 52; w++) {
            for (int x = 0; x < 52; x++) {
                for (int y = 0; y < 52; y++) {
                    for (int z = 0; z < 52; z++) {
                        data[w][x][y][z] = new Data(w, x, y, z, "-----", calculateGauss(w, x, y, z, minima), -1.00);
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
        System.out.println("Number of minima found : " + minimaCount);

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

        // Print out the first and last five entries to check for gremlins
        System.out.println("\n");
        for (int i = 0; i < 5; i++) {
            System.out.println(i + " : " + sortData.get(i));
        }
        System.out.println("\n");
        for (int i = sortData.size()-5; i < sortData.size(); i++) {
            System.out.println(i + " : " + sortData.get(i));
        }

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

        // And finally dump it all to a csv file

        System.out.println("DONE.");
    }

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
}

































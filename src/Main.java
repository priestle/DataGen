import java.io.FileWriter;
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

        int[] numberOfMinimaPerDataset = {1, 3, 10, 30 , 100, 300, 1000};
        int dataSetCountPerGroup = 3;

        for (int minimaIndex = 0; minimaIndex < numberOfMinimaPerDataset.length; minimaIndex++) {
            int numberOfMinima = numberOfMinimaPerDataset[minimaIndex];

            for (int dataSetNumber = 0; dataSetNumber < dataSetCountPerGroup; dataSetNumber++) {
                String filename = "D:/canDump2/data4Dmin" + numberOfMinima + "-" + dataSetNumber + "-Gauss.csv";
                DataSet d = new DataSet(filename, numberOfMinima, "Gauss");
                // Everything that needs to be done with 'd' is done in that constructor...
            }
        }

        for (int numberOfRandomSets = 0; numberOfRandomSets < 4; numberOfRandomSets++) {
            DataSet d = new DataSet("D:/candump2/data4Drandom-" + numberOfRandomSets + ".csv", 0, "Random");
        }


        System.out.println("DONE.");
    }


}

































import java.util.Random;

public class RNG {


    /**
     * Inspired by Sir Neil "Global Mutable State and Re-roll RNGs in Constructors" Ferguson of Imperial College.
     * This RNG provides a reproducible, shared random source across GADFLI.
     */

    public static final Random GLOBAL = new Random(12345);

}

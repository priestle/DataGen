import java.util.Random;

public class Minima {

    // Variables
    private int w;
    private int x;
    private int y;
    private int z;
    private double width;
    private double depth;

    // Constructor
    public Minima() {
        Random random = new Random();
        this.w = random.nextInt(50) + 1;
        this.x = random.nextInt(50) + 1;
        this.y = random.nextInt(50) + 1;
        this.z = random.nextInt(50) + 1;
        this.width = random.nextDouble() * 10;
        this.depth = random.nextDouble();
    }

    // Gets
    public int getW() { return this.w; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getZ() { return this.z; }
    public double getWidth() { return this.width; }
    public double getDepth() { return this.depth; }

    // Sets


    // Methods
    @Override
    public String toString() {
        return  String.format("Minima at [%d,%d,%d,%d] : width %.2f, depth %.2f)",
                this.w, this.x, this.y, this.z, this.width, this.depth);
    }
}

public class Data {

    // Variables
    private int w;
    private int x;
    private int y;
    private int z;
    private String type;  // GLOBAL/LOCAL/-----
    private double value;
    private double rank;

    // Constructor
    public Data(int w, int x, int y, int z, String type, double value, double rank) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.value = value;
        this.rank = rank;
    }

    // Gets
    public int getW()        { return this.w; }
    public int getX()        { return this.x; }
    public int getY()        { return this.y; }
    public int getZ()        { return this.z; }
    public String getType()  { return this.type; }
    public double getValue() { return this.value; }
    public double getRank()  { return this.rank; }

    // Sets
    public void setValue(double value) { this.value = value; }
    public void setRank(double rank) { this.rank = rank; }
    public void setType(String type) { this.type = type; }

    // Methods
    @Override
    public String toString() {
        return "[" + this.w + "," + this.x + "," + this.y + "," + this.z + "] " +
                this.value + " (" + this.rank + ") " + this.type;
    }
}

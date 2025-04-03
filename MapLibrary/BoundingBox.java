package MapLibrary;

public class BoundingBox {

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    
    public BoundingBox(double minX, double minY, double maxX, double maxY)
    {

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

    }

    public double getMinX()
    {
        return this.minX;
    }

    public double getMinY()
    {
        return this.minY;
    }

    public double getMaxX()
    {
        return this.maxX;
    }

    public double getMaxY()
    {
        return this.maxY;
    }

}

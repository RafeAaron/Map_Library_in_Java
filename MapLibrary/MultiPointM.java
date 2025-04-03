package MapLibrary;

public class MultiPointM {
    
    int id;
    int numberOfPoints;
    Point[] points;
    private double measure;

    BoundingBox multiLineBoundingBox;

    public MultiPointM(int[] data)
    {

        

    }

    public Point[] getPoints()
    {

        return this.points;
    }

    public BoundingBox getBoundingBox()
    {

        return this.multiLineBoundingBox;

    }

}

package MapLibrary;

public class MultiPointZ {
    
    int id;
    int numberOfPoints;
    Point[] points;
    private double measure;

    BoundingBox multiLineBoundingBox;

    public MultiPointZ(int[] data)
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

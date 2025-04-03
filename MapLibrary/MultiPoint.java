package MapLibrary;

public class MultiPoint {
    
    int id;
    int numberOfPoints;
    Point[] points;

    BoundingBox multiLineBoundingBox;

    public MultiPoint(int[] data)
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

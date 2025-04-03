package MapLibrary;

public class PolyLine {

    private BoundingBox polylineBoundingBox;
    MultiPoint[] points;
    
    public PolyLine(int[] data)
    {

        

    }

    public BoundingBox getBoundingBox()
    {

        return this.polylineBoundingBox;

    }

    public MultiPoint[] getlines()
    {
        return this.points;
    }

}

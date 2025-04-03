package MapLibrary;

public class PolyLineM {

    private BoundingBox polylineBoundingBox;
    MultiPoint[] points;
    private double measure;
    
    public PolyLineM(int[] data)
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

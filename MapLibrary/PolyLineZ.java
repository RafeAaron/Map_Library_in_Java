package MapLibrary;

public class PolyLineZ {

    private BoundingBox polylineBoundingBox;
    MultiPoint[] points;
    private double measure;
    
    public PolyLineZ(int[] data)
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

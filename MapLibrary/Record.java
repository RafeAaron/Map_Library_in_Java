package MapLibrary;

import java.nio.ByteBuffer;

public class Record {

    int content[];
    int lengthOfContent;
    int id;
    String recordType;
    int shapeType;


    public Record(int[] bytes, int id, int contentLength)
    {

        this.id = id;
        this.lengthOfContent = contentLength;
        this.content = bytes;

        this.getShapeInformation();

    }

    public boolean getShapeInformation()
    {
        boolean status = false;

        this.shapeType = this.content[3];
        this.shapeType = this.shapeType << 8 | this.content[2];
        this.shapeType = this.shapeType << 8 | this.content[1];
        this.shapeType = this.shapeType << 8 | this.content[0];

        if( this.shapeType == 1) System.out.println("Point");

        else if(this.shapeType == 3) System.out.println("Polyline");

        else if(this.shapeType == 5) System.out.println("Polygon");

        else if(this.shapeType == 8) System.out.println("MultiPoint");

        else if(this.shapeType == 11) System.out.println("PointZ");

        else if(this.shapeType == 13) System.out.println("PolylineZ");

        else if(this.shapeType == 15) System.out.println("PolygonZ");

        else if(this.shapeType == 18) System.out.println("MultiPointZ");

        else if(this.shapeType == 21) System.out.println("PointM");

        else if(this.shapeType == 23) System.out.println("PolylineM");

        else if(this.shapeType == 25) System.out.println("PolygonM");

        else if(this.shapeType == 28) System.out.println("MultiPointM");

        else if(this.shapeType == 31) System.out.println("MultiPatch");

        status = true;

        return status;
    }

    public Point createPoint()
    {

        if(this.shapeType != 1)
        {
            System.out.println("This is not a point");
            return null;
        }

        this.recordType = "Point";

        int dataForPoint[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPoint[i] = this.content[i + 4];
        }

        return new Point(dataForPoint);
    }

    public Polygon createPolygon()
    {
        if(this.shapeType != 5)
        {
            System.out.println("This is not a polygon");
            return null;
        }

        this.recordType = "Polygon";

        int dataForPolygon[] = new int[this.lengthOfContent - 4];


        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPolygon[i] = this.content[i + 4];
        }

        return new Polygon(dataForPolygon);
    }

    public PolyLine createPolyLine()
    {
        if(this.shapeType != 3)
        {
            System.out.println("This is not a polyline");
            return null;
        }

        this.recordType = "PolyLine";

        int dataForPolyLine[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPolyLine[i] = this.content[i + 4];
        }

        return new PolyLine(dataForPolyLine);
    }

    public MultiPoint createMultiPoint()
    {
        if(this.shapeType != 3)
        {
            System.out.println("This is not a multiline");
            return null;
        }

        this.recordType = "MultiPoint";

        int dataForMultiPoint[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForMultiPoint[i] = this.content[i + 4];
        }

        return new MultiPoint(dataForMultiPoint);
    }

    public PointZ createPointZ()
    {

        if(this.shapeType != 11)
        {
            System.out.println("This is not a pointZ");
            return null;
        }

        this.recordType = "PointZ";

        int dataForPointZ[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPointZ[i] = this.content[i + 4];
        }

        return new PointZ(dataForPointZ);
    }

    public PolygonZ createPolygonZ()
    {
        if(this.shapeType != 15)
        {
            System.out.println("This is not a polygonZ");
            return null;
        }

        this.recordType = "PolygonZ";

        int dataForPolygonZ[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPolygonZ[i] = this.content[i + 4];
        }

        return new PolygonZ(dataForPolygonZ);
    }

    public PolyLineZ createPolyLineZ()
    {
        if(this.shapeType != 13)
        {
            System.out.println("This is not a polylineZ");
            return null;
        }

        this.recordType = "PolyLineZ";

        int dataForPolyLineZ[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPolyLineZ[i] = this.content[i + 4];
        }

        return new PolyLineZ(dataForPolyLineZ);
    }

    public MultiPointZ createMultiPointZ()
    {
        if(this.shapeType != 18)
        {
            System.out.println("This is not a multilineZ");
            return null;
        }

        this.recordType = "MultiPointZ";

        int dataForMultiPointZ[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForMultiPointZ[i] = this.content[i + 4];
        }

        return new MultiPointZ(dataForMultiPointZ);
    }

    public PointM createPointM()
    {

        if(this.shapeType != 21)
        {
            System.out.println("This is not a pointM");
            return null;
        }

        this.recordType = "PointM";

        int dataForPointM[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPointM[i] = this.content[i + 4];
        }

        return new PointM(dataForPointM);
    }

    public PolygonM createPolygonM()
    {
        if(this.shapeType != 25)
        {
            System.out.println("This is not a polygonM");
            return null;
        }

        this.recordType = "PolygonM";

        int dataForPolygonM[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPolygonM[i] = this.content[i + 4];
        }

        return new PolygonM(dataForPolygonM);
    }

    public PolyLineM createPolyLineM()
    {
        if(this.shapeType != 23)
        {
            System.out.println("This is not a polylineM");
            return null;
        }

        this.recordType = "PolyLineM";

        int dataForPolyLineM[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForPolyLineM[i] = this.content[i + 4];
        }

        return new PolyLineM(dataForPolyLineM);
    }

    public MultiPointM createMultiPointM()
    {
        if(this.shapeType != 28)
        {
            System.out.println("This is not a multilineM");
            return null;
        }

        this.recordType = "MultiPointM";

        int dataForMultiPointM[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForMultiPointM[i] = this.content[i + 4];
        }

        return new MultiPointM(dataForMultiPointM);
    }

    public MultiPatch createMultiPatch()
    {
        if(this.shapeType != 31)
        {
            System.out.println("This is not a multipatch");
            return null;
        }

        this.recordType = "MultiPatch";

        int dataForMultiPatch[] = new int[this.lengthOfContent - 4];

        for(int i = 0; i < this.lengthOfContent - 4; i++)
        {
            dataForMultiPatch[i] = this.content[i + 4];
        }

        return new MultiPatch(dataForMultiPatch);
    }
}

package MapLibrary;

import java.nio.ByteBuffer;

public class Polygon{

    BoundingBox polygonBoundingBox;

    MultiPoint[] polygons;
    int numberOfPoints;
    int numberOfParts;
    int[] parts;

    public Polygon(int[] data)
    {

        System.out.println("Hey I'm a polygon"); 
        
        byte[] minXArray = new byte[8];
        byte[] minYArray = new byte[8];
        byte[] maxXArray = new byte[8];
        byte[] maxYArray = new byte[8];
        
        for(int i = 0; i < 36; i++)
        {
            if(i < 8)
            {

                minXArray[i] = (byte)data[i];

            }else if(i < 16 && i >=8)
            {

                minYArray[i - 8] = (byte)data[i];

            }else if(i < 24 && i >=16)
            {

                maxXArray[i - 16] = (byte)data[i];

            }
            else{

                maxYArray[i - 24] = (byte)data[i];

            }
        }

        double minX = ByteBuffer.wrap(minXArray).getDouble();
        double minY = ByteBuffer.wrap(minYArray).getDouble();
        double maxX = ByteBuffer.wrap(maxXArray).getDouble();
        double maxY = ByteBuffer.wrap(maxYArray).getDouble();

        this.polygonBoundingBox = new BoundingBox(minX, minY, maxX, maxY);
        
        int numberOfPartsInPolygon = data[39];
        numberOfPartsInPolygon = numberOfPartsInPolygon << 8 | data[38];
        numberOfPartsInPolygon = numberOfPartsInPolygon << 8 | data[37];
        numberOfPartsInPolygon = numberOfPartsInPolygon << 8 | data[36];

        this.numberOfParts = numberOfPartsInPolygon;

        int numberOfPointsInPolygon = data[43];
        numberOfPointsInPolygon = numberOfPointsInPolygon << 8 | data[42];
        numberOfPointsInPolygon = numberOfPointsInPolygon << 8 | data[41];
        numberOfPointsInPolygon = numberOfPointsInPolygon << 8 | data[40];

        this.numberOfPoints = numberOfPointsInPolygon;

        this.parts = new int[this.numberOfParts];

        for(int i = 1; i <= numberOfPartsInPolygon; i++)
        {
            int a = data[43 + (4 * i)];
            a = a << 8 | data[43 + (4 * i) + 1];
            a = a << 8 | data[43 + (4 * i) + 2];
            a = a << 8 | data[43 + (4 * i) + 3];

            this.parts[i - 1] = a;
        }

        int partNumber = 0;

        this.polygons = new MultiPoint[this.numberOfParts];

        int byteOffset = 44 + (4 * this.numberOfParts);

        MultiPoint pointMulti = null;

        for(int i = 0; i < numberOfPointsInPolygon; i++)
        {

            Point currentPoint;

            if(i == this.parts[partNumber])
            {
                pointMulti = new MultiPoint();
                this.polygons[partNumber] = pointMulti;
                partNumber++;
            }

            int[] arrayOfBytes = new int[16];

            for(int a = 0; a < 16; a++)
            {
                arrayOfBytes[a] = data[byteOffset + (i * 16) + a];
            }

            currentPoint = new Point(arrayOfBytes);
            pointMulti.addPoint(currentPoint);

        }
    }

    public int getNumberOfPolygons()
    {
        return this.polygons.length;
    }

    public MultiPoint[] getPolygons()
    {
        return this.polygons;
    }

    public String toString()
    {

        String endString = "";

        for(int i = 0; i < this.polygons.length; i++)
        {
            endString += this.polygons.toString() + "\n";
        }

        return endString;
    }

}
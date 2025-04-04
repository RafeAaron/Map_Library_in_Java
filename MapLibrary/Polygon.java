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
        
        byte[] minXArray = new byte[8];
        byte[] minYArray = new byte[8];
        byte[] maxXArray = new byte[8];
        byte[] maxYArray = new byte[8];
        
        for(int i = 0; i < 32; i++)
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
        
        int numberOfPartsInPolygon = data[35];
        numberOfPartsInPolygon = numberOfPartsInPolygon << 8 | data[34];
        numberOfPartsInPolygon = numberOfPartsInPolygon << 8 | data[33];
        numberOfPartsInPolygon = numberOfPartsInPolygon << 8 | data[32];

        this.numberOfParts = numberOfPartsInPolygon;

        int numberOfPointsInPolygon = data[39];
        numberOfPointsInPolygon = numberOfPointsInPolygon << 8 | data[38];
        numberOfPointsInPolygon = numberOfPointsInPolygon << 8 | data[37];
        numberOfPointsInPolygon = numberOfPointsInPolygon << 8 | data[36];

        this.numberOfPoints = numberOfPointsInPolygon;
        
        this.parts = new int[this.numberOfParts];

        for(int i = 0; i < numberOfPartsInPolygon; i++)
        {
            int a = data[40 + (4 * i) + 3];
            a = a << 8 | data[40 + (4 * i) + 2];
            a = a << 8 | data[40 + (4 * i) + 1];
            a = a << 8 | data[40 + (4 * i) + 0];

            this.parts[i] = a;

        }

        this.polygons = new MultiPoint[this.numberOfParts];

        int byteOffset = 40 + (4 * this.numberOfParts);

        for(int i = 0; i < numberOfPartsInPolygon; i++)
        {
            MultiPoint pointMulti = new MultiPoint();
            this.polygons[i] = pointMulti;

            if(numberOfPartsInPolygon == 1){

                for(int a = 0; a < this.numberOfPoints;a++)
                {
                    int[] arrayOfBytes = new int[16];

                    Point currentPoint;

                    for(int b = 0; b < 16; b++)
                    {
                        arrayOfBytes[b] = data[byteOffset + (a * 16) + b];
                    }

                    currentPoint = new Point(arrayOfBytes);
                    pointMulti.addPoint(currentPoint);
                }

            }else{

                if(i == numberOfPartsInPolygon - 1)
                {

                    int numberOfPointsRemaining = this.numberOfPoints - this.parts[i];
                    int startingpoint = this.parts[i];

                    for(int c = 0; c < numberOfPointsRemaining; c++)
                    {
                        int[] arrayOfBytes = new int[16];
                        Point currentPoint;

                        for(int d = 0; d < 16; d++)
                    {
                        arrayOfBytes[d] = data[byteOffset + (startingpoint * 16) + d];
                    }

                    currentPoint = new Point(arrayOfBytes);
                    pointMulti.addPoint(currentPoint);
                    startingpoint++;

                    }

                }else{

                    int numberOfPointsRemaining = this.parts[i + 1] - this.parts[i];
                    int startingpoint = this.parts[i];

                    for(int c = 0; c < numberOfPointsRemaining; c++)
                    {

                        int[] arrayOfBytes = new int[16];
                        Point currentPoint;

                        for(int d = 0; d < 16; d++)
                        {
                            arrayOfBytes[d] = data[byteOffset + (startingpoint * 16) + d];
                        }

                    currentPoint = new Point(arrayOfBytes);
                    pointMulti.addPoint(currentPoint);
                    startingpoint++;

                    }

                }

            }
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
            endString += this.polygons[i].toString() + "\n";
        }

        return endString;
    }

}
package MapLibrary;

import java.nio.ByteBuffer;

public class MultiPoint {
    
    int id;
    int numberOfPoints;
    Point[] points;

    BoundingBox multiPointBoundingBox;

    public MultiPoint(int[] data)
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

        this.multiPointBoundingBox = new BoundingBox(minX, minY, maxX, maxY);

        int numberOfPoints = data[35];
        numberOfPoints = numberOfPoints << 8 | data[34];
        numberOfPoints = numberOfPoints << 8 | data[33];
        numberOfPoints = numberOfPoints << 8 | data[32];

        this.numberOfPoints = numberOfPoints;

        int byteOffset = 36;

        for(int a = 0; a < this.numberOfPoints;a++)
                {
                    int[] arrayOfBytes = new int[16];

                    Point currentPoint;

                    for(int b = 0; b < 16; b++)
                    {
                        arrayOfBytes[b] = data[byteOffset + (a * 16) + b];
                    }

                    currentPoint = new Point(arrayOfBytes);
                    this.addPoint(currentPoint);
                }

    }

    public MultiPoint(){
        this.id = -1;

        this.points = new Point[2];
        this.numberOfPoints = 0;

    }

    public boolean addPoint(Point pointToAdd)
    {

        boolean status = false;

        if(this.numberOfPoints == points.length)
        {
            Point[] pointsNewArray = new Point[this.numberOfPoints * 2];

            for(int i = 0; i < this.numberOfPoints; i++)
            {
                pointsNewArray[i] = this.points[i];
            }

            this.points = pointsNewArray;
            this.points[this.numberOfPoints] = pointToAdd;

            this.numberOfPoints++;

            status = true;
        }else{
            this.points[this.numberOfPoints] = pointToAdd;
            this.numberOfPoints++;
            status = true;
        }

        return status;

    }

    public Point[] getPoints()
    {

        return this.points;
    }

    public BoundingBox getBoundingBox()
    {

        return this.multiPointBoundingBox;

    }

    public String toString()
    {

        String endResult = "";

        for(int a = 0; a < this.points.length; a++)
        {
            if(a == this.numberOfPoints) break;
            endResult += this.points[a].toString() + "\n";
        }

        return endResult;

    }

}

package MapLibrary;

import java.nio.ByteBuffer;

public class Point {

    private double X;
    private double Y;
    
    public Point(int[] data)
    {

        byte pointXBytes[] = new byte[8];
        byte pointYBytes[] = new byte[8];

        for(int i = 0; i < 8; i++)
        {
            pointXBytes[i] = (byte)data[i];
        }

        for(int i = 8; i < 16; i++)
        {
            pointYBytes[i] = (byte)data[i];
        }

        ByteBuffer bufferForPointX = ByteBuffer.wrap(pointXBytes);
        ByteBuffer bufferForPointY = ByteBuffer.wrap(pointYBytes);

        this.X = bufferForPointX.getDouble();
        this.Y = bufferForPointY.getDouble();
    }

    public double getX()
    {
        return this.X;
    }

    public double getY()
    {
        return this.Y;
    }

}

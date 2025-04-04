package MapLibrary;

public class MultiPoint {
    
    int id;
    int numberOfPoints;
    Point[] points;

    BoundingBox multiLineBoundingBox;

    public MultiPoint(int[] data)
    {
        
        

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

        return this.multiLineBoundingBox;

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

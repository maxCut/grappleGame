public class Helpers
{
    public static double getDistance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }
    public static LegSide negateLegSide(LegSide side)
    {
        if(side == LegSide.Right)
        {
            return LegSide.Left;
        }
        return LegSide.Right;
    }

    //finds the smaller angle between two angles
    public static double angleDif(double a, double b)
    {
        double diff = (a - b + 2*Math.PI)%(2*Math.PI);
        if(diff>Math.PI)
        {
            diff = 2*Math.PI - diff;
        }
        return diff;
    }

    public static double getAngle(double x1,double y1, double x2, double y2)
    {
        return (Math.atan2(x1-x2,y2-y1) + 2*Math.PI)%(2*Math.PI);
    }
}

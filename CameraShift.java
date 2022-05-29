public class CameraShift
{
    private static Movement playerMovement;
    private static double frameHeight = 0;
    private static double frameWidth = 0;
    public static void setPlayer(Movement m)
    {
        playerMovement = m; 
    }
    public static void setFrameDims(double w, double h)
    {
        frameHeight = h;
        frameWidth= w;
    }

    public static double getXShift()
    {
        if((playerMovement==null)||(frameWidth==0)||(frameHeight == 0))
        {
            return 0;
        }
        return frameWidth/2 - playerMovement.getCenterX();
    }

    public static double getYShift()
    {   
        if((playerMovement==null)||(frameWidth==0)||(frameHeight == 0))
        {
            return 0;
        }
        return frameHeight/2 - playerMovement.getCenterY();
    }
}

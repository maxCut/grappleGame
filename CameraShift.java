public class CameraShift
{
    private static Movement playerMovement;
    private static GrapplingHook grapplingHook;
    private static double frameHeight = 0;
    private static double frameWidth = 0;
    private static double cameraX = 0;
    private static double cameraY = 0;
    private static double targetX = 0;
    private static double targetY = 0;
    private static final double CAMERAVELOCITY = 3.2;
    public static void setPlayer(Movement m)
    {
        playerMovement = m; 
        cameraX = m.getCenterX();
        cameraY = m.getCenterY();
    }
    public static void setGrapple(GrapplingHook g)
    {
        grapplingHook = g;
    }
    public static void setFrameDims(double w, double h)
    {
        frameHeight = h;
        frameWidth= w;
    }

    public static void update()
    {
        targetX = (playerMovement.getCenterX() + grapplingHook.getHookX())/2;
        targetY = (playerMovement.getCenterY() + grapplingHook.getHookY())/2;
        double xDiff = targetX -cameraX;
        double yDiff = targetY - cameraY;
        
        double r = CAMERAVELOCITY/Math.sqrt(Math.pow(xDiff,2)+Math.pow(yDiff,2));
        double xVelocity = r*xDiff;
        double yVelocity = r*yDiff;
        
       if(Math.abs(xDiff)<CAMERAVELOCITY)
       {
        cameraX = targetX;
       }
       else
       {
        cameraX+=xVelocity;
       }
        
       if(Math.abs(yDiff)<CAMERAVELOCITY)
       {
        cameraY = targetY;
       }
       else{
        cameraY += yVelocity;
       }
    }
    public static double getXShift()
    {
        if((playerMovement==null)||(frameWidth==0)||(frameHeight == 0))
        {
            return 0;
        }
        return frameWidth/2 - cameraX;
    }

    public static double getYShift()
    {   
        if((playerMovement==null)||(frameWidth==0)||(frameHeight == 0))
        {
            return 0;
        }
        return frameHeight/2 - cameraY;
    }
    
    public static int xShift(double x)
    {
        return (int)((x+getXShift())*Scale.WORLDSCALE);
    }
    public static int yShift(double y)
    {
        return (int)((y+getYShift())*Scale.WORLDSCALE);
    }
}

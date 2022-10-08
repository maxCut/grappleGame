public class Scale
{
    public static final double SCALE = 2.0;
    public static double getScale()
    {
        return SCALE/CameraShift.getZoomEffect();
    }
    public static final double WORLDSCALE = 1000.0; //This allows us to draw the world really big then shrink to avoid jitter (like from int rounding)
}

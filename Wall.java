import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Rectangle;
public abstract class Wall implements Collidable
{
    public double xCord;
    public double yCord;
    public double width;
    public double height;
    public BufferedImage sprite;
    public void draw(Graphics g)
    {
        g.drawImage(sprite,CameraShift.xShift(xCord),CameraShift.yShift(yCord),(int)(width*Scale.WORLDSCALE),(int)(height*Scale.WORLDSCALE),null);
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)xCord,(int)yCord,(int)width,(int)height);
    }
    public void onIntersection(Collidable c)
    {
    }
}

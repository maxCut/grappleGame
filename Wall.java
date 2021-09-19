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
        g.drawImage(sprite,(int)(xCord*Scale.SCALE),(int)(yCord*Scale.SCALE),(int)(width*Scale.SCALE),(int)(height*Scale.SCALE),null);
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)xCord,(int)yCord,(int)width,(int)height);
    }
    public void onIntersection(Collidable c)
    {
    }
}

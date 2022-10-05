import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
public abstract class Monster implements Collidable
{
    protected double xCord;
    protected double yCord;
    protected int width;
    protected int height;
    protected boolean alive = true;
    protected BufferedImage sprite;

    public abstract void update();

    public void draw(Graphics g)
    {
        g.drawImage(sprite,CameraShift.xShift(xCord),CameraShift.yShift(yCord),(int)(Scale.WORLDSCALE*width),(int)(Scale.WORLDSCALE*height),null);
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)xCord,(int)yCord,width,height);
    }

    public boolean isAlive()
    {
        return alive;
    }

    public double getX()
    {
        return xCord;
    }

    public double getY()
    {
        return yCord;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void onIntersection(Collidable c)
    {
        if(c instanceof Sword)
        {
            if(c.getBoundries() instanceof Polygon)
            {
                if(((Polygon)c.getBoundries()).intersects(xCord,yCord,width,height))
                {
                    alive = false;
                } 
            }
        }
    }
}

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.awt.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player implements Collidable
{
    private Movement location;
    private Direction facing;
    private final double BOUNCE = .2;
    private final int width;
    private final int height;
    private SpiderBody spiderBody;
    public Player(CollisionDetector c)
    {
        width = 20;
        height = 36;
        spiderBody = new SpiderBody(width,height,260,260);
        location = new Movement(260,260,width,height,spiderBody);
        facing = Direction.Up;
        c.addCollidable(this);
    }
    
    public Movement getMovement()
    {
        return location;
    }

    public void update()
    {
        location.update();
        spiderBody.update();
    }

    public void draw(Graphics g)
    {
        spiderBody.draw(g);
    }
    public Shape getBoundries()
    {
        int elipseRadius = 12;
        return new Ellipse2D.Double(location.getX()-elipseRadius,location.getY()-elipseRadius,2*elipseRadius,2*elipseRadius);
    }
    public void onIntersection(Collidable c)
    {
        if(c instanceof Wall)
        {
            location.collision(c);
        }
    }
}

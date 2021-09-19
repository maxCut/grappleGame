import java.util.ArrayList;
import java.awt.image.BufferedImage;
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
    public Player(CollisionDetector c)
    {
        width = 20;
        height = 36;
        location = new Movement(260,260,width,height);
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
    }

    public void draw(Graphics g)
    {
        g.fillRect((int)(location.getX()*Scale.SCALE),
                (int)(location.getY()*Scale.SCALE),
                (int)(width*Scale.SCALE),
                (int)(height*Scale.SCALE));
        
    }
    public Shape getBoundries()
    {
        return new Rectangle(location.getX(),location.getY(),20,36);
    }
    public void onIntersection(Collidable c)
    {
        if(c instanceof Wall)
        {
            location.collision(c);
        }
    }
}

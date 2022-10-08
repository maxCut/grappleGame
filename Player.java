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
    private Sword sword;
    private final double BOUNCE = .2;
    private final int width;
    private final int height;
    private int health = 3;
    private int iframes = 0;
    private SpiderBody spiderBody;
    private SpiderLegMovement spiderLegs;
    public Player(CollisionDetector c)
    {
        width = 20;
        height = 36;
        spiderBody = new SpiderBody(width,height,260,260);
        location = new Movement(260,260,width,height,spiderBody);
        sword = new Sword(location);
        spiderLegs = new SpiderLegMovement(.7*height/2, .6*width/2, height, width, location);
        c.addCollidable(this);
        c.addCollidable(sword);
    }
    
    public Movement getMovement()
    {
        return location;
    }

    public void update()
    {
        location.update();
        spiderBody.update();
        sword.update();
        spiderLegs.update();
        if(iframes>0)
        {
            iframes--;
        }
    }

    public void swingSword()
    {
        sword.swing();
    }

    public void draw(Graphics g)
    {
        spiderLegs.drawLeftLegs(g);
        spiderLegs.drawRightLegs(g);
        spiderBody.draw(g);
        sword.draw(g);
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
        else if(c instanceof Monster)
        {
            if(iframes>0)
            {
                return;
            }
            if(health>0)
                health--;//TODO add some 
                iframes = 120;
        }
    }
    public boolean isDead()
    {
        return health <=0;
    }
}

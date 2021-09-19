import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Graphics;
import java.lang.Math.*;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class GrapplingHook implements Collidable
{
    private Movement playerMovement;
    private double hookX; 
    private double hookY;
    private double xVelocity;
    private double yVelocity;
    private double xAcceleration;
    private double yAcceleration;
    private boolean launchedImune = false;
    private boolean launched = false;
    private boolean latched = false;
    private boolean pulling = false;
    private final double ACCELFORCE = .03;
    private final double LAUNCHFORCE = 6.9;
    private final double elipseRadius = 10.0;
    
    public GrapplingHook(Movement m, CollisionDetector c)
    {
        c.addCollidable(this);
        playerMovement = m;
        hookX = playerMovement.getX();
        hookY = playerMovement.getY();
    }
    public void launch(MouseEvent e)
    {
        latched = false;
        if(launched)
        {
            hookX = playerMovement.getX();
            hookY = playerMovement.getY();
        }
        launched = true;
        launchedImune = true;
        double targetX = e.getX()/Scale.SCALE - playerMovement.getX();
        double targetY = e.getY()/Scale.SCALE - playerMovement.getY();
        double r = LAUNCHFORCE/Math.sqrt(Math.pow(targetX,2)+Math.pow(targetY,2));
        xVelocity = r*targetX;
        yVelocity = r*targetY;
    }
    public void pull()
    {
        launchedImune = false;
        pulling = true;
        if(latched)
        {
            if(launched)
            {
                double targetX = hookX - playerMovement.getX();
                double targetY = hookY - playerMovement.getY();
                double r = ACCELFORCE/Math.sqrt(Math.pow(targetX,2)+Math.pow(targetY,2));
                double xAccel= r*targetX;
                double yAccel= r*targetY;
                playerMovement.setAcceleration(xAccel,yAccel);
            }
        }
    }
    public void stopPull()
    {
        playerMovement.setAcceleration(0,0);
        pulling = false;
    }

    public void update()
    {
       if(!launched)
       {
           hookX = playerMovement.getX();
           hookY = playerMovement.getY();
       }
       hookX+=xVelocity;
       hookY+=yVelocity;
       xVelocity+=xAcceleration;
       yVelocity+=yAcceleration;
    }
    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton()==1)
        {
            launch(e);
        }
        else if(e.getButton()==2)
        {
            launched = false;
        }
        else if(e.getButton()==3)
        {
            pull();
        }
        else
        {

            System.out.println(e);
        } 
    }
    public void mouseReleased(MouseEvent e)
    {
        if(e.getButton()==3)
        {
            stopPull();
        }
    }
    public void draw(Graphics g)
    {
        if(launched)
        {
            g.drawOval((int)((hookX-elipseRadius)*Scale.SCALE),(int)((hookY-elipseRadius)*Scale.SCALE)
                ,(int)(2*elipseRadius*Scale.SCALE),(int)(2*elipseRadius*Scale.SCALE));
            g.drawLine((int)(playerMovement.getX()*Scale.SCALE)
                ,(int)(playerMovement.getY()*Scale.SCALE)
                ,(int)(hookX*Scale.SCALE)
                ,(int)(hookY*Scale.SCALE));
        }
    }
    
    public Ellipse2D getHitBox()
    {
        return new Ellipse2D.Double(hookX-elipseRadius,hookY-elipseRadius,2*elipseRadius,2*elipseRadius);
    }

    public Shape getBoundries()
    {
        return getHitBox();
    }

    public void onIntersection(Collidable c)
    {
        if(c instanceof Wall)
        {
            latched = true;
            xVelocity = 0; 
            yVelocity = 0;
            if(pulling)
            {
                if(launched)
                {
                    double targetX = hookX - playerMovement.getX();
                    double targetY = hookY - playerMovement.getY();
                    double r = ACCELFORCE/Math.sqrt(Math.pow(targetX,2)+Math.pow(targetY,2));
                    double xAccel= r*targetX;
                    double yAccel= r*targetY;
                    playerMovement.setAcceleration(xAccel,yAccel);
                }
            }
        }
        if(c instanceof Player)
        {
            if(!launchedImune)
            {
                launched = false;
            }
        }
    }
}

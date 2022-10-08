import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;
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
    private final double LAUNCHFORCE = 13.9;
    private final double elipseRadius = 10.0;
    
    public GrapplingHook(Movement m, CollisionDetector c)
    {
        c.addCollidable(this);
        playerMovement = m;
        hookX = playerMovement.getCenterX();
        hookY = playerMovement.getCenterY();
    }
    public double getHookX()
    {
        return hookX;
    }
    public double getHookY()
    {
        return hookY;
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


        double targetX = e.getX()/Scale.getScale() - playerMovement.getCenterX()-CameraShift.getXShift();
        double targetY = e.getY()/Scale.getScale() - playerMovement.getCenterY()-CameraShift.getYShift();


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
                double targetX = hookX - playerMovement.getCenterX();
                double targetY = hookY - playerMovement.getCenterY();
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
           hookX = playerMovement.getCenterX();
           hookY = playerMovement.getCenterY();
       }
       hookX+=xVelocity;
       hookY+=yVelocity;
       xVelocity+=xAcceleration;
       yVelocity+=yAcceleration;
       if(launched)
       {
        playerMovement.setAngle(getAngle());
       }
    }

    private double getAngle()
    {
        return Math.atan2(playerMovement.getCenterX()-hookX,hookY-playerMovement.getCenterY());
        //since we go from top left of screen with pixels we have to reverse that here. So we use hookY - player (everything is flipped along y axis)
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
            /*
            g.setColor(Color.GREEN);
            g.fillOval(CameraShift.xShift((playerMovement.getCenterX()-elipseRadius)),CameraShift.yShift((playerMovement.getCenterY()-elipseRadius))
                ,(int)(2*elipseRadius*Scale.WORLDSCALE),(int)(2*elipseRadius*Scale.WORLDSCALE));
             */
            g.setColor(Color.BLACK);
            g.fillOval(CameraShift.xShift((hookX-elipseRadius)),CameraShift.yShift((hookY-elipseRadius))
                ,(int)(2*elipseRadius*Scale.WORLDSCALE),(int)(2*elipseRadius*Scale.WORLDSCALE));
            g.drawLine(CameraShift.xShift(playerMovement.getCenterX())
                ,CameraShift.yShift(playerMovement.getCenterY())
                ,CameraShift.xShift(hookX)
                ,CameraShift.yShift(hookY));
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

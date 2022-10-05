import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
public class BallBeam extends Monster
{
    double xVelocity;
    double yVelocity;
    double elipseRadius;
    final double LAUNCHFORCE = 1.0;

    public BallBeam(double x, double y, Movement playerMovement)
    {
        xCord = x;
        yCord = y;
        elipseRadius = 15;

        double targetX = playerMovement.getCenterX() - xCord;
        double targetY = playerMovement.getCenterY() - yCord;
        double r = LAUNCHFORCE/Math.sqrt(Math.pow(targetX,2)+Math.pow(targetY,2));
        xVelocity = r*targetX;
        yVelocity = r*targetY;
    }
    
    public void update()
    {
        xCord += xVelocity;
        yCord += yVelocity;

    }
    public void draw(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(CameraShift.xShift((xCord-elipseRadius)*Scale.WORLDSCALE),CameraShift.yShift((yCord-elipseRadius)*Scale.WORLDSCALE)
                ,(int)(2*elipseRadius*Scale.WORLDSCALE),(int)(2*elipseRadius*Scale.WORLDSCALE));
        g.setColor(Color.BLACK);

    }
    public void onIntersection(Collidable c)
    {
        if(c instanceof Wall)
        {
            alive = false;
        }
    }    
    
    public Shape getBoundries()
    {
        return new Ellipse2D.Double(xCord-elipseRadius,yCord-elipseRadius,2*elipseRadius,2*elipseRadius);
    }
}

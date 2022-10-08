import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;

public class Movement
{
    private double xCord;
    private double yCord;
    private double width;
    private double height;

    private double xVelocity =0;
    private double yVelocity = 0;
    private double xAcceleration = 0;
    private double yAcceleration = 0;

    private SpiderBody spiderBody;
    
    private final double BOUNCE = .2;
    private final double MAX_VELOCITY = 2.8;

    public Movement(int x, int y, int w, int h,SpiderBody s)
    {
        width = w;
        height = h;
        xCord = x;
        yCord = y;
        spiderBody = s; 
    }

    public double getWidth()
    {
        return width;
    }
    public double getHeight()
    {
        return height;
    }
    public double getCenterX()
    {
        return spiderBody.getCenterX();
    }

    public double getCenterY()
    {
        return spiderBody.getCenterY();
    }
    
    public int getX()
    {
        return (int)xCord;
    }
    
    public int getY()
    {
        return (int)yCord;
    }

    public void setAcceleration(double x, double y)
    {
        xAcceleration = x;
        yAcceleration = y;
    }

    private void setVelocityUpdate(double xAccel, double yAccel)
    {
        if(xAccel == 0 && yAccel == 0)
        {
            return;
        }
        double targetX = xVelocity+xAccel; 
        double targetY = yVelocity+yAccel;
        
        double r = Math.sqrt(Math.pow(targetX,2)+Math.pow(targetY,2));
        double velSum = Math.min(r,MAX_VELOCITY);

        xVelocity = targetX*(velSum/r);
        yVelocity = targetY*(velSum/r);
    }

    public double getVelocityX()
    {
        return xVelocity;
    }

    public double getVelocityY()
    {
        return yVelocity;
    }

    public void update()
    {
        xCord += xVelocity;
        yCord += yVelocity;
        setVelocityUpdate( xAcceleration, yAcceleration);
        spiderBody.moveTo(xCord,yCord);
    }

    public void setAngle(double a)
    {
        spiderBody.setAngle(a);
    }

    public double getAngle()
    {
        return spiderBody.getAngle();
    }

    public void collision(Collidable c)
    {
            setAcceleration(0,0);
            if(c instanceof TopWall || c instanceof BottomWall 
                    || c instanceof TopLeftWall || c instanceof TopRightWall)
            {
                xCord-=xVelocity;
                yCord-=yVelocity;
                yVelocity = -1.0*yVelocity*BOUNCE;
            }
            else if(c instanceof LeftWall || c instanceof RightWall)
            {
                xCord-=xVelocity;
                yCord-=yVelocity;
                xVelocity = -1.0*xVelocity*BOUNCE;
            }
            else if(c instanceof Wall)
            {
                xCord-=xVelocity;
                yCord-=yVelocity;
                xVelocity = -1.0*xVelocity*BOUNCE;
                yVelocity = -1.0*yVelocity*BOUNCE;
            }
    }
}

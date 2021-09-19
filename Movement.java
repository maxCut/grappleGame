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
    
    private final double BOUNCE = .2;
    private final int MOVEDISTANCE = 1;

    public Movement(int x, int y, int w, int h)
    {
        width = w;
        height = h;
        xCord = x;
        yCord = y;
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

    public void update()
    {
        xCord += xVelocity;
        yCord += yVelocity;
        xVelocity += xAcceleration;
        yVelocity += yAcceleration;
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
    }
}

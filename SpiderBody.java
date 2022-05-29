import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.lang.Math;
public class SpiderBody
{
    private double angle;
    private double goalAngle;
    private SpiderLegSet leftLegs; //not really left and right its a pair of 2 on the left one on the right and vice versa
    private SpiderLegSet rightLegs;
    private double xCord = 0;
    private double yCord = 0;
    private final int width;
    private final int height;
    private final double ROTSPEED = .03;
    public SpiderBody(int w, int h, double x, double y)
    {
        width = w;
        height = h;
        xCord = x;
        yCord = y;
        angle = 0;
    }
    public void update()
    {
        double anglep = (angle-goalAngle)%(2*Math.PI);
        double anglen = (goalAngle-angle)%(2*Math.PI);
        if(anglep<0)
            anglep+=2*Math.PI;
        if(anglen<0)
            anglen+=2*Math.PI;
        if(angle==goalAngle)
        {
            return;
        }
        else if(anglep<ROTSPEED||anglen<ROTSPEED)
        {
            angle = goalAngle;
        }
        else if(anglep<anglen)
        {
            angle-=ROTSPEED;
        }
        else
        {
            angle+=ROTSPEED;
        }
        angle%=2*Math.PI;
    }
    public void moveTo(double x,double y)
    {
        xCord = x;
        yCord = y;
    }
    public void setAngle(double a)
    {
        goalAngle = a;
    }

    public double getAngle()
    {
        return angle;
    }

    public double getCenterX()
    {
        double xShift = Math.cos(angle+Math.PI/2)*height/2;
        return xCord+xShift;
    }
    public double getCenterY()
    {
        double yShift = Math.sin(angle+Math.PI/2)*height/2;
        return yCord+yShift;
    }
    private double getTopShift()
    {
        return Math.cos(angle)*-3;
    }

    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate((int)(xCord*Scale.SCALE),(int)(yCord*Scale.SCALE));
        g2d.rotate(angle);
        g2d.translate((int)(-width/2*Scale.SCALE),(int)(-height/2*Scale.SCALE));
        g2d.drawImage(SpriteMap.spiderBottom,0,0
                ,(int)(width*Scale.SCALE),(int)(height*Scale.SCALE),null);
        g2d.translate((int)(width/2*Scale.SCALE),(int)(height/2*Scale.SCALE));
        g2d.rotate(-angle);
        g2d.translate(0,(int)(getTopShift()*Scale.SCALE));
        g2d.rotate(angle);
        g2d.translate((int)(-width/2*Scale.SCALE),(int)(-height/2*Scale.SCALE));
        g2d.drawImage(SpriteMap.spiderTop,0,0
                ,(int)(width*Scale.SCALE),(int)(height*Scale.SCALE),null);
        g2d.translate((int)(width/2*Scale.SCALE),(int)(height/2*Scale.SCALE));
        g2d.rotate(-angle);
        g2d.translate(0,(int)(-getTopShift()*Scale.SCALE));
        g2d.translate((int)(-xCord*Scale.SCALE),(int)(-yCord*Scale.SCALE));
    }
}

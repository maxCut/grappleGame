import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.lang.Math;
public class SpiderBody
{
    private double angle;
    private double goalAngle;
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
        angle+=2*Math.PI;
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
        double xShift = Math.cos(angle+Math.PI/2)*width/2;
        //System.out.println(xShift);
        return xCord;//+xShift;
    }
    public double getCenterY()
    {
        double yShift = Math.sin(angle+Math.PI/2)*height/2;
        return yCord;//+yShift;
    }
    private double getTopShiftY()
    {
        return (Math.cos(angle)*-2.5)-1.3;
        //0 is 100 
        //90 is 50
        //180 is 0
        //270 is 50
    }
    private double getTopShiftX()
    {
        return (Math.sin(angle)*2.0);
        //0 is 0 
        //90 is -50
        //180 is 50
        //270 is 0
    }

    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(CameraShift.xShift(xCord),CameraShift.yShift(yCord));
        g2d.rotate(angle);
        g2d.translate((int)(-width/2*Scale.WORLDSCALE),(int)(-height/2*Scale.WORLDSCALE));
        g2d.drawImage(SpriteMap.spiderBottom,0,0
                ,(int)(width*Scale.WORLDSCALE),(int)(height*Scale.WORLDSCALE),null);
        g2d.translate((int)(width/2*Scale.WORLDSCALE),(int)(height/2*Scale.WORLDSCALE));
        g2d.rotate(-angle);
        g2d.translate((int)(getTopShiftX()*Scale.WORLDSCALE),(int)(getTopShiftY()*Scale.WORLDSCALE));
        g2d.rotate(angle);
        g2d.translate((int)(-width/2*Scale.WORLDSCALE),(int)(-height/2*Scale.WORLDSCALE));
        g2d.drawImage(SpriteMap.spiderTop,0,0
                ,(int)(width*Scale.WORLDSCALE),(int)(height*Scale.WORLDSCALE),null);
        g2d.translate((int)(width/2*Scale.WORLDSCALE),(int)(height/2*Scale.WORLDSCALE));
        g2d.rotate(-angle);
        g2d.translate((int)(-getTopShiftX()*Scale.WORLDSCALE),(int)(-getTopShiftY()*Scale.WORLDSCALE));
        //g2d.translate((int)(-xCord*Scale.WORLDSCALE),(int)(-yCord*Scale.WORLDSCALE));
        g2d.translate(-CameraShift.xShift(xCord),-CameraShift.yShift(yCord));
    }
}

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.lang.Math;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Color;
public class Sword implements Collidable
{
    private Movement movement;
    private double width;
    private double height;
    private double angle;
    private boolean swingingRight;
    private final double ANGLESPEED = .05;
    public Sword(Movement m)
    {
        width = 40;
        movement = m;
        height = 30;
        angle = 0;
    }
    public void update()
    {
        if(swingingRight)
        {
            if(angle<Math.PI)
            {
                angle+=ANGLESPEED;
            }
        }
        else
        {
            if(angle>0)
            {
                angle-=ANGLESPEED;
            }
        }
    }
    public void swing()
    {
        swingingRight = !swingingRight;
    }
    public void draw(Graphics g)
    {
        double xShift = height/2;
        double yShift = -height/2;
        double xCord = movement.getCenterX();
        double yCord = movement.getCenterY();
        double drawAngle = angle + movement.getAngle();
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(CameraShift.xShift(xCord),CameraShift.yShift(yCord));
        //g2d.translate((int)(xCord*Scale.WORLDSCALE),(int)(yCord*Scale.WORLDSCALE));
        g2d.rotate(drawAngle);
        g2d.translate((int)(xShift*Scale.WORLDSCALE),(int)(yShift*Scale.WORLDSCALE));

        g2d.drawImage(SpriteMap.sword,0,0
                ,(int)(width*Scale.WORLDSCALE),(int)(height*Scale.WORLDSCALE),null);

        g2d.translate((int)(-xShift*Scale.WORLDSCALE),(int)(-yShift*Scale.WORLDSCALE));
        g2d.rotate(-drawAngle);
        g2d.translate(-CameraShift.xShift(xCord),-CameraShift.yShift(yCord));

    }

    public Shape getBoundries()
    {

        Polygon p = new Polygon();

        double oxCord = movement.getCenterX();
        double oyCord = movement.getCenterY();
        double oShift = 20;
        double odrawAngle = angle + movement.getAngle();
        oxCord+=Math.cos(odrawAngle)*oShift;
        oyCord+=Math.sin(odrawAngle)*oShift;
        odrawAngle += Math.PI/2;
        oShift = 8;
        double x2Cord= oxCord + Math.cos(odrawAngle)*oShift;
        double y2Cord= oyCord + Math.sin(odrawAngle)*oShift;

        double x3Cord= oxCord - Math.cos(odrawAngle)*oShift;
        double y3Cord= oyCord - Math.sin(odrawAngle)*oShift;

        odrawAngle += Math.PI/2;
        oShift = 39;

        double x4Cord= x3Cord - Math.cos(odrawAngle)*oShift;
        double y4Cord= y3Cord - Math.sin(odrawAngle)*oShift;
        
        odrawAngle += Math.PI/2;
        oShift = 16;
        
        double x5Cord= x4Cord - Math.cos(odrawAngle)*oShift;
        double y5Cord= y4Cord - Math.sin(odrawAngle)*oShift;

        p.addPoint((int)x2Cord,(int)y2Cord);
        p.addPoint((int)x3Cord,(int)y3Cord);
        p.addPoint((int)x4Cord,(int)y4Cord);
        p.addPoint((int)x5Cord,(int)y5Cord);

        return p;

    }

    public void onIntersection(Collidable c)
    {

    }
}

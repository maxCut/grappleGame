import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.Shape;

import java.awt.Graphics2D;
public class SpiderLeg
{
    LegSide side;
    int index; //0, 1, or -1
    double bodyVerticalShift;
    double bodyHorizontalShift;
    double footVerticalShift;
    double footHorizontalShift;
    double bodyX; 
    double bodyY;
    double footX;
    double footY;
    double kneeX;
    double kneeY;
    final double SHINLENGTH = 25;
    final double THIGHLENGTH = 25;
    final double THIGHTHICKNESS = 3;
    final double SHINTHICKNESS = 3;
    
    final Color thighColor = Color.BLUE;
    final Color shinColor = Color.CYAN;
    final Color LEGBORDERCOLOR = new Color(3,41,192);

    public SpiderLeg(LegSide s, int i, double vsBody, double hsBody,double vsFoot, double hsFoot)
    {
        side = s;
        index = i;
        
        bodyVerticalShift = vsBody;
        bodyHorizontalShift = hsBody;
        footVerticalShift = vsFoot;
        footHorizontalShift = hsFoot;
    }

    public double getKneeTranslatePercent(double beta)
    {
        return Math.cos(beta);
    }
    
    final double PROJECTIONSHIFT = .4;
    public void update( double xCordBodyCenter, double yCordBodyCenter, double xCordFootCenter, double yCordFootCenter, double angle, boolean active)
    {   
        footX = getFootLocationX(xCordFootCenter, angle);
        footY = getFootLocationY(yCordFootCenter, angle);
        bodyX = getBodyLocationX(xCordBodyCenter, angle);
        bodyY = getBodyLocationY(yCordBodyCenter, angle);
        double stepLength = Helpers.getDistance(bodyX, bodyY, footX, footY);
        double alpha = getKneeAlpha(stepLength);
        double beta = getKneeBeta(bodyX, bodyY, footX,footY);
        double kneeSumAngleTop =  ((beta + alpha)  + 2*Math.PI)%(2*Math.PI);
        double kneeSumAngleBot =  ((beta - alpha)  + 2*Math.PI)%(2*Math.PI);
        double kneeXTop = getKneeLocationX(kneeSumAngleTop,THIGHLENGTH, bodyX);
        double kneeYTop = getKneeLocationY(kneeSumAngleTop,THIGHLENGTH,bodyY);
        double kneeXBot = getKneeLocationX(kneeSumAngleBot,THIGHLENGTH, bodyX);
        double kneeYBot = getKneeLocationY(kneeSumAngleBot,THIGHLENGTH,bodyY);

        double kneeLineLength = Helpers.getDistance(kneeXBot, kneeYBot, kneeXTop, kneeYTop);
        double kneeLineAngle = Helpers.getAngle(kneeXBot, kneeYBot, kneeXTop, kneeYTop);
        double translationShiftLength =  kneeLineLength/2 - getKneeTranslatePercent(kneeLineAngle)*kneeLineLength*PROJECTIONSHIFT;
        kneeX = getShiftedVerticalX(kneeXBot, translationShiftLength, beta);
        kneeY = getShiftedVerticalY(kneeYBot, translationShiftLength, beta);
        


    }

    public double getBodyLocationX(double xCord,double angle)
    {
        return getEndLocationX(xCord, side == LegSide.Right ? bodyHorizontalShift:-bodyHorizontalShift, bodyVerticalShift * index, angle);
    }
    
    public double getBodyLocationY(double yCord,double angle)
    {
        return getEndLocationY(yCord, side == LegSide.Right ? bodyHorizontalShift:-bodyHorizontalShift, bodyVerticalShift * index, angle);
    }

    public double getFootLocationX(double xCord,double angle)
    {
        return getEndLocationX(xCord, side == LegSide.Right ? footHorizontalShift:-footHorizontalShift, footVerticalShift * index, angle);
    }

    public double getFootLocationY(double yCord,double angle)
    {
        return getEndLocationY(yCord, side == LegSide.Right ? footHorizontalShift:-footHorizontalShift, footVerticalShift * index, angle);
    }

    public double getEndLocationX(double xCord, double horizontalShift, double verticalShift, double angle)
    {
        return getShiftedVerticalX(getShiftedHorizontalX(xCord, horizontalShift, angle), verticalShift, angle);
    }

    public double getEndLocationY(double yCord, double horizontalShift, double verticalShift, double angle)
    {
        return getShiftedVerticalY(getShiftedHorizontalY(yCord, horizontalShift, angle), verticalShift, angle);        
    }

    //gets an x coordinate shifted forward dist from point relative to angle of spider.
    private double getShiftedVerticalX(double xCord,double shift,double angle)
    {
        return xCord - shift * Math.sin(-angle); //because we start at top left and y is positive our angle is negative in regards to "vertical behavior" 
    }

    //gets an y coordinate shifted forward dist from point relative to angle of spider.
    private double getShiftedVerticalY(double yCord,double shift,double angle)
    {
        return yCord - shift * Math.cos(-angle);
    }

    //gets an x coordinate shifted horizontaly dist from point relative to angle of spider.
    private double getShiftedHorizontalX(double xCord,double shift,double angle)
    {
        return xCord + shift * Math.cos(angle);
    }

    //gets an y coordinate shifted horizontaly dist from point relative to angle of spider.
    private double getShiftedHorizontalY(double yCord,double shift,double angle)
    {
        return yCord + shift * Math.sin(angle);
    }

    public double getKneeLocationX(double angle, double thighLength, double bodyX)
    {
        return bodyX- thighLength*Math.cos(angle);

    }
    public double getKneeLocationY(double angle, double thighLength, double bodyY)
    {
        return bodyY - thighLength*Math.sin(angle);
    }

    public double getKneeAlpha(double stepLength)
    {
        //System.out.println("step length " + stepLength + " knee alpha " +  Math.acos((Math.pow(stepLength,2)+Math.pow(SHINLENGTH,2)-Math.pow(THIGHLENGTH,2))/(2*stepLength*SHINLENGTH)) );
        return Math.acos((Math.pow(stepLength,2)+Math.pow(SHINLENGTH,2)-Math.pow(THIGHLENGTH,2))/(2*stepLength*SHINLENGTH));
    }
    
    public double getKneeBeta(double bodyX, double bodyY, double footX, double footY)
    {
        //return Math.atan2(footX - bodyX, footY- bodyY);
        return (Helpers.getAngle(bodyX, bodyY, footX, footY) + 3* Math.PI/2)%(2*Math.PI);
    }

    public void drawThigh(Graphics g)
    {
        double angle = Helpers.getAngle(kneeX, kneeY,bodyX, bodyY);
        double length = Helpers.getDistance(kneeX, kneeY, bodyX, bodyY);

        
        g.setColor(thighColor);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.translate(CameraShift.xShift(kneeX),CameraShift.yShift(kneeY));
        g2d.rotate(angle);
        g2d.translate((int)(-THIGHTHICKNESS/2*Scale.WORLDSCALE),0);

        g.setColor(thighColor);
        g2d.fillRect(0,0,(int)(THIGHTHICKNESS*Scale.WORLDSCALE),(int)(length*Scale.WORLDSCALE));
        g.setColor(LEGBORDERCOLOR);
        g2d.drawRect(0,0,(int)(THIGHTHICKNESS*Scale.WORLDSCALE),(int)(length*Scale.WORLDSCALE));
        
        g2d.translate((int)(THIGHTHICKNESS/2*Scale.WORLDSCALE),0);
        g2d.rotate(-angle);
        g2d.translate(-CameraShift.xShift(kneeX),-CameraShift.yShift(kneeY));

        g.setColor(Color.BLACK);
        
    }


    public void drawShin(Graphics g)
    {
        
        double angle = Helpers.getAngle(kneeX, kneeY, footX, footY);
        
        double length = Helpers.getDistance(kneeX, kneeY, footX, footY);
        // create rect centred on the point we want to rotate it about
        
        
        g.setColor(shinColor);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.translate(CameraShift.xShift(kneeX),CameraShift.yShift(kneeY));
        g2d.rotate(angle);
        g2d.translate((int)(-SHINTHICKNESS/2*Scale.WORLDSCALE),0);

        g2d.fillRect(0,0,(int)(SHINTHICKNESS*Scale.WORLDSCALE),(int)(length*Scale.WORLDSCALE));
        g.setColor(LEGBORDERCOLOR);
        g2d.drawRect(0,0,(int)(SHINTHICKNESS*Scale.WORLDSCALE),(int)(length*Scale.WORLDSCALE));

        
        g2d.translate((int)(SHINTHICKNESS/2*Scale.WORLDSCALE),0);
        g2d.rotate(-angle);
        g2d.translate(-CameraShift.xShift(kneeX),-CameraShift.yShift(kneeY));

        g.setColor(Color.BLACK);


    }

    public double getScaledThigh(double angle, boolean active)
    {
        return THIGHLENGTH *.2 + THIGHLENGTH*.8*Math.tan(angle);
    }

    public void drawLeg(Graphics g)
    {
        drawShin(g);
        drawThigh(g);
    }
}
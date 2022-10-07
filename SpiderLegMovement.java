import java.awt.Graphics;
public class SpiderLegMovement
{
    final double SWITCH_DIST = 18.0;
    final double SWITCH_ANGLE = Math.PI*2*.12;
    private SpiderLegSet leftLegs; //not really left and right its a pair of 2 on the left one on the right and vice versa
    private SpiderLegSet rightLegs;
    double bodyCenterX = 0;
    double bodyCenterY = 0;
    double bodyAngle= 0;
    LegSide activeSide = LegSide.Left ;
    Movement playerLocation;

    public SpiderLegMovement(double vsBody, double hsBody,double vsFoot, double hsFoot, Movement location)
    {
        leftLegs = new SpiderLegSet(LegSide.Left, vsBody, hsBody,vsFoot,hsFoot); 
        rightLegs  = new SpiderLegSet(LegSide.Right, vsBody, hsBody,vsFoot,hsFoot); 
        rightLegs.moveTo(location.getCenterX(), location.getCenterY(),location.getAngle());
        leftLegs.moveTo(location.getCenterX(), location.getCenterY(),location.getAngle());
        playerLocation = location;
    }

    public void update()
    {
        updatePosition(playerLocation.getCenterX(), playerLocation.getCenterY(), 
        playerLocation.getVelocityX(), playerLocation.getVelocityY(), playerLocation.getAngle());
        leftLegs.update(bodyCenterX, bodyCenterY, SWITCH_ANGLE, false);
    }

    private void updatePosition(double xCord,double yCord, double xVelocity,double yVelocity, double angle)
    {
        SpiderLegSet inactive = getInactiveSide();
        switch(activeSide)
        {
            case Left:
                
                leftLegs.moveTo(bodyCenterX*2-inactive.xCord, bodyCenterY*2-inactive.yCord, ((2*bodyAngle - inactive.angleCord+ 2*Math.PI)%(2*Math.PI)));
                //angle a - theta == angle theta - b
                //angle a = 2*theta - b
                //angle b = 2*theta - a
                // x1 + x2 = cordx*2
                //
                break;
            case Right:
                rightLegs.moveTo(bodyCenterX*2-inactive.xCord, bodyCenterY*2-inactive.yCord, ((2*bodyAngle - inactive.angleCord+ 2*Math.PI)%(2*Math.PI)));
                break;
        }
        if(Helpers.getDistance(xCord, yCord, inactive.xCord, inactive.yCord)>SWITCH_DIST||Helpers.angleDif(angle, inactive.angleCord)>SWITCH_ANGLE)
        {
            //check to make sure nothing is weird
            SpiderLegSet active = getActiveSide();
            if(Helpers.getDistance(xCord, yCord, active.xCord, active.yCord)>SWITCH_DIST)
            {
                //this is weird this should not happen.
                //active.moveTo(xCord, yCord, angle);
            }
            activeSide = Helpers.negateLegSide(activeSide);
            
        }
        bodyCenterX = xCord;
        bodyCenterY = yCord;
        bodyAngle = angle;
        leftLegs.update( bodyCenterX, bodyCenterY, playerLocation.getAngle(),activeSide==LegSide.Left);
        rightLegs.update( bodyCenterX, bodyCenterY, playerLocation.getAngle(),activeSide==LegSide.Right);
    }
    private SpiderLegSet getInactiveSide()
    {
        switch(activeSide)
        {
            case Left:
                return rightLegs;
            case Right:
                return leftLegs;
        }
        return null;
    } 
    private SpiderLegSet getActiveSide()
    {
        switch(activeSide)
        {
            case Left:
                return leftLegs;
            case Right:
                return rightLegs;
        }
        return null;
    } 
    public void drawLeftLegs(Graphics g)
    {
        leftLegs.drawLegSet(g);

    }

    public void drawRightLegs(Graphics g)
    {
        rightLegs.drawLegSet(g);
    }
}

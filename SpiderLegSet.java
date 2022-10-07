import java.awt.Graphics;
public class SpiderLegSet
{
    public double xCord = 0;
    public double yCord = 0;
    public double angleCord = 0;
    LegSide side;
    SpiderLeg[] spiderLegs = new SpiderLeg[3];
    public SpiderLegSet(LegSide s, double vsBody, double hsBody,double vsFoot, double hsFoot)
    {
        side = s;
        spiderLegs[0] = new SpiderLeg(s,-1, vsBody,  hsBody, vsFoot, hsFoot);
        spiderLegs[1] = new SpiderLeg(Helpers.negateLegSide(s),0, vsBody,  hsBody, vsFoot, hsFoot+15.0);
        spiderLegs[2] = new SpiderLeg(s,1, vsBody,  hsBody, vsFoot, hsFoot);
    }
    public void moveTo(double x, double y, double angle)
    {
        xCord = x;
        yCord = y;
        angleCord = angle;
    }

    public void update(double bodyCenterX, double bodyCenterY, double angle, boolean active)
    {
        
        for (SpiderLeg spiderLeg : spiderLegs) {
            spiderLeg.update(bodyCenterX, bodyCenterY, xCord, yCord, angleCord, active);
        }

    }
    public void drawLegSet(Graphics g)
    {
        for (SpiderLeg spiderLeg : spiderLegs) {
            spiderLeg.drawLeg(g);
        }
    }

    public void drawShins(Graphics g)
    {
        for (SpiderLeg spiderLeg : spiderLegs) {
            spiderLeg.drawShin(g);
        }
    }
    public void drawThighs(Graphics g)
    {
        for (SpiderLeg spiderLeg : spiderLegs) {
            spiderLeg.drawThigh(g);
        }
    }
}

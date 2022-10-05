public class SpiderLegMovement
{
    enum ActiveLegSide
    {
        Left,
        Right;
    }
    private SpiderLegSet leftLegs; //not really left and right its a pair of 2 on the left one on the right and vice versa
    private SpiderLegSet rightLegs;
    int bodyCenterX = 0;
    int bodyCenterY = 0;
    ActiveLegSide movingSet = ActiveLegSide.Left ;
    public void updatePosition(double xCord,double yCord, double xVelocity,double yVelocity)
    {
        
    }
}

import java.awt.Rectangle;
import java.awt.Shape;
public class RightWall extends Wall
{
    public RightWall(double x, double y)
    {
        xCord = x;
        yCord = y;
        width = 40;
        height = 40;
        sprite = SpriteMap.rightWall;
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)(xCord+20),(int)yCord,(int)(width-20),(int)(height));
    }
}

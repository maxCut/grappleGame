import java.awt.Rectangle;
import java.awt.Shape;
public class LeftWall extends Wall
{
    public LeftWall(double x, double y)
    {
        xCord = x;
        yCord = y;
        width = 40;
        height = 40;
        sprite = SpriteMap.leftWall;
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)(xCord),(int)yCord,(int)(width-20),(int)(height));
    }
}

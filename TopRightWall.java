import java.awt.Rectangle;
import java.awt.Shape;
public class TopRightWall extends Wall
{
    public TopRightWall(double x, double y)
    {
        xCord = x;
        yCord = y;
        width = 40;
        height = 120;
        sprite = SpriteMap.topRightWall;
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)xCord,(int)yCord,(int)width,(int)(height-30));
    }
}

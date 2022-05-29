import java.awt.Rectangle;
import java.awt.Shape;
public class TopLeftWall extends Wall
{
    public TopLeftWall(double x, double y)
    {
        xCord = x;
        yCord = y;
        width = 40;
        height = 120;
        sprite = SpriteMap.topLeftWall;
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)xCord-20,(int)yCord,(int)width+40,(int)(height-30));
    }
}

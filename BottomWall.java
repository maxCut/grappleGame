import java.awt.Rectangle;
import java.awt.Shape;
public class BottomWall extends Wall
{
    public BottomWall(double x, double y)
    {
        xCord = x;
        yCord = y;
        height = 20;
        width = 40;
        sprite = SpriteMap.bottomWall;
    }
}

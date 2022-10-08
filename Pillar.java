import java.awt.Rectangle;
import java.awt.Shape;
public class Pillar extends Wall
{
    public Pillar(double x, double y)
    {
        xCord = x;
        yCord = y;
        width = 40;
        height = 120;
        sprite = SpriteMap.pillar;
    }
    public Shape getBoundries()
    {
        return new Rectangle((int)xCord,(int)yCord,(int)width,(int)(height-30));
    }
}

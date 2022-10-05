import java.awt.image.BufferedImage;
import java.awt.Graphics;
public class FloorTile
{
    public double xCord;
    public double yCord;
    public double width;
    public double height;
    public BufferedImage sprite;
    public FloorTile(double x, double y)
    {
        xCord = x;
        yCord = y;
        width = 40;
        height = 40;
        sprite = SpriteMap.floorTile;
    }
    public void draw(Graphics g)
    {
        g.drawImage(sprite,CameraShift.xShift(xCord),CameraShift.yShift(yCord),(int)(width*Scale.WORLDSCALE),(int)(height*Scale.WORLDSCALE),null);
    }
}


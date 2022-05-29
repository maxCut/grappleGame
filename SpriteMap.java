import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class SpriteMap
{
    public static BufferedImage floorTile; 
    public static BufferedImage leftWall; 
    public static BufferedImage rightWall; 
    public static BufferedImage topLeftWall;
    public static BufferedImage topRightWall;
    public static BufferedImage topWall;
    public static BufferedImage bottomWall;
    public static BufferedImage spiderTop;
    public static BufferedImage spiderBottom;
    public static BufferedImage sword;
    public static BufferedImage pot;
    public static void init()
    {
      try
        {
            floorTile = ImageIO.read(Pictures.load("floorTile.png"));
            leftWall = ImageIO.read(Pictures.load("leftWall.png"));
            rightWall = ImageIO.read(Pictures.load("rightWall.png"));
            topRightWall = ImageIO.read(Pictures.load("topRightWallCorner.png"));
            topLeftWall = ImageIO.read(Pictures.load("topLeftWallCorner.png"));
            topWall = ImageIO.read(Pictures.load("topWall.png"));
            bottomWall = ImageIO.read(Pictures.load("bottomWall.png"));
            spiderTop = ImageIO.read(Pictures.load("spiderTop.png"));
            spiderBottom = ImageIO.read(Pictures.load("spiderBottom.png"));
            sword = ImageIO.read(Pictures.load("sword.png"));
            pot = ImageIO.read(Pictures.load("pot.png"));
        }
        catch(Exception e)
        {
            System.out.println("File not found");
        }

    }
}

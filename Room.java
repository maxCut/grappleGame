import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
public class Room
{
    private ArrayList<Wall> walls = new ArrayList<>(); 
    private ArrayList<FloorTile> floor= new ArrayList<>(); 
    public Room(CollisionDetector c)
    {
        int height = 10;
        int width = 20;
        int xCord = 0;
        int yCord = 0;
        walls.add(new TopLeftWall(xCord,yCord));
        xCord += 40;
        for(int i = 0; i<width; i++)
        {
            walls.add(new TopWall(xCord,yCord));
            xCord += 40;
        }
        walls.add(new TopRightWall(xCord,yCord));
        xCord = 0;
        yCord+=120;
        for(int j = 0; j< height; j++)
        {
            walls.add(new LeftWall(xCord,yCord));
            xCord += 40;
            for(int i = 0; i<width; i++)
            {
                floor.add(new FloorTile(xCord,yCord));
                xCord += 40;
            }
            walls.add(new RightWall(xCord,yCord));
            xCord = 0;
            yCord+=40;
        }
        
        for(int i = 0; i<width+2; i++)
        {
            walls.add(new BottomWall(xCord,yCord));
            xCord += 40;
        }

        for(Wall wall:walls)
        {
            c.addCollidable(wall);

        }
    }

    public void draw(Graphics g)
    {
        for(Wall w: walls)
        {
            w.draw(g);
        }
        for(FloorTile f:floor)
        {
           f.draw(g); 
        }
    }
}

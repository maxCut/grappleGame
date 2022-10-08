import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
public class Room
{
    private ArrayList<Wall> walls = new ArrayList<>(); 
    private ArrayList<Wall> innerWalls = new ArrayList<>(); 
    private ArrayList<FloorTile> floor = new ArrayList<>(); 
    private ArrayList<Monster> monsters = new ArrayList<>(); 
    private CollisionDetector collisionDetector;
    private Movement playerMovement;
    public Room(CollisionDetector c, Movement mo)
    {
        int height = 20;
        int width = 40;
        int xCord = 0;
        int yCord = 0;
        collisionDetector = c;
        playerMovement = mo;

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
        
        innerWalls.add(new Pillar(500, 500));
        innerWalls.add(new Pillar(700, 500));
        innerWalls.add(new Pillar(300, 500));
        innerWalls.add(new Pillar(900, 500));
        innerWalls.add(new Pillar(1100, 500));
        innerWalls.add(new Pillar(600, 200));
        innerWalls.add(new Pillar(800, 200));
        innerWalls.add(new Pillar(1000, 200));
        
        monsters.add(new Pot(500,200));

        for(int i = 0; i<width+2; i++)
        {
            walls.add(new BottomWall(xCord,yCord));
            xCord += 40;
        }

        for(Wall wall:walls)
        {
            c.addCollidable(wall);
        }
        for(Wall wall:innerWalls)
        {
            c.addCollidable(wall);
        }

        for(Monster m: monsters)
        {
            c.addCollidable(m);
        }
    }

    public void update()
    {
        for(int i = 0; i<monsters.size();i++)
        {
            Monster m = monsters.get(i);
            m.update();
            if(!m.isAlive())
            {
                monsters.remove(m);
                collisionDetector.removeCollidable(m);
            }
            if((m instanceof Pot) && ((Pot)m).trySpawnBubble())
            {
                Monster newMonster = new BallBeam(m.getX()+m.width/2,m.getY()+m.height/2,playerMovement);
                monsters.add(newMonster);
                collisionDetector.addCollidable(newMonster);
            }
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
        for(Monster m:monsters)
        {
            m.draw(g);
        }
        for(Wall w: innerWalls)
        {
            w.draw(g);
        }
    }
}

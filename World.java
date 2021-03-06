import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Image;
import java.awt.image.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
public class World extends JComponent
{
    static World start = new World();   //this is the recursively defined object of the game itself. 
    static Map<TileType, BufferedImage> world = new HashMap<TileType, BufferedImage>();//way of storing image mem adresses
    static JFrame frame = new JFrame();
    static Room room;
    static Player player;
    static GrapplingHook grapplingHook;
    static CollisionDetector collisionDetector;

    public World()
    {
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                grapplingHook.mouseClicked(e);
            }
            public void mouseReleased(MouseEvent e)
            {
                grapplingHook.mouseReleased(e);
            }
        });
        
    }
    public static void main(String[] args) throws IOException
    {
        
        SpriteMap.init();
        collisionDetector = new CollisionDetector();
        frame.setBackground(Color.black);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //jframe settings
        frame.add(start);
        frame.setVisible(true);
        frame.setSize((int)(880*Scale.SCALE),(int)(560*Scale.SCALE));

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 120;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;
        long gameTime = 0;

        player = new Player(collisionDetector);
        room = new Room(collisionDetector,player.getMovement());
        grapplingHook = new GrapplingHook(player.getMovement(),collisionDetector);

        CameraShift.setPlayer(player.getMovement());
        CameraShift.setFrameDims(840,480);

        frame.addKeyListener(new KeyAdapter()
                {
                    boolean swordButtonReady = true;
                    public void keyPressed(KeyEvent e)
                    {
                        if(e.getKeyCode() == KeyEvent.VK_SPACE)
                        {
                            if(swordButtonReady)
                            {
                                player.swingSword();
                                swordButtonReady = false;
                            }
                        }

                    }

                    public void keyReleased(KeyEvent e)
                    {
                        if(e.getKeyCode() == KeyEvent.VK_SPACE)
                        {
                            swordButtonReady = true;
                        }
                    }


                });

        while(true)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            lastFpsTime += updateLength;
            lastFpsTime %= 1000000000;

            update();
            frame.repaint();
            try
            {
                gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;

                Thread.sleep(gameTime);//gives time aspect so that it doesnt repaint every second
            } 
            catch(Exception e)
            {}
        }
    }

    private static void update() {
        if(player.isDead())
        {
            return;
        }
        grapplingHook.update();
        player.update();
        room.update();
        collisionDetector.checkCollisions();
    }
    public void paintComponent(Graphics g)
    {
        if(player.isDead())
        {
            //g.drawText(0,0,"Game Over");

        }
        else
        {
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate((int)((CameraShift.getXShift())*Scale.SCALE),(int)((CameraShift.getYShift())*Scale.SCALE));
            room.draw(g);
            player.draw(g);
            grapplingHook.draw(g);
        }
    }
}

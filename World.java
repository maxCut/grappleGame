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
import java.awt.RenderingHints;
import java.awt.image.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
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
        frame.setSize((int)(880*Scale.WORLDSCALE),(int)(560*Scale.WORLDSCALE));

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final int TARGET_UPDATES_PER_SECOND = 120;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        long gameTime = 0;
        int updateMod = 0;

        player = new Player(collisionDetector);
        room = new Room(collisionDetector,player.getMovement());
        grapplingHook = new GrapplingHook(player.getMovement(),collisionDetector);

        CameraShift.setPlayer(player.getMovement());
        CameraShift.setGrapple(grapplingHook);
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


        
        update();
        while(true)
        {
            long now = System.nanoTime();
            lastLoopTime = now;

            frame.repaint();
            int needed_updates = (TARGET_UPDATES_PER_SECOND+updateMod)/TARGET_FPS;
            updateMod = (TARGET_UPDATES_PER_SECOND+updateMod)%TARGET_FPS;
            for(int i = 0; i<needed_updates;i++ )
            {
                update();
            }
            try
            {
                gameTime = (OPTIMAL_TIME - (lastLoopTime - System.nanoTime()))/1000000;
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
        super.paintComponent(g);
        if(player.isDead())
        {
            //g.drawText(0,0,"Game Over");

        }
        else
        {
            Graphics2D g2d = (Graphics2D)g;
    
            g2d.scale(Scale.SCALE/Scale.WORLDSCALE, Scale.SCALE/Scale.WORLDSCALE);
            //g2d.translate((int)((CameraShift.getXShift())*Scale.WORLDSCALE),(int)((CameraShift.getYShift())*Scale.WORLDSCALE));
            room.draw(g);
            grapplingHook.draw(g);
            player.draw(g);
            g2d.scale(Scale.WORLDSCALE/Scale.SCALE, Scale.WORLDSCALE/Scale.SCALE);
        }
    }
}

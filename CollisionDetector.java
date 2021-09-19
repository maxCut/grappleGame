import java.util.ArrayList;
public class CollisionDetector
{
    public ArrayList<Collidable> collidables = new ArrayList<>();
    public void addCollidable(Collidable c)
    {
        collidables.add(c);
    }
    public void checkCollisions()
    {
        for(int i = 0; i<collidables.size()-1;i++)
        {
            for(int j = i+1; j<collidables.size();j++)
            {
                if(collidables.get(i).getBoundries().getBounds2D().intersects(collidables.get(j).getBoundries().getBounds2D()))
                {
                    collidables.get(i).onIntersection(collidables.get(j));
                    collidables.get(j).onIntersection(collidables.get(i));
                }
            }
        }
    }
}

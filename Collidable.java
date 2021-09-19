import java.awt.Shape;
public interface Collidable
{
    public Shape getBoundries();
    public void onIntersection(Collidable c);
}

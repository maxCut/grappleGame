public class Pot extends Monster
{
    private final int SPAWNTIME = 500;
    private int spawnTimer = 0;
    private boolean spawnBubble = false;
    public Pot(int x, int y)
    {
        xCord = x;
        yCord = y;
        width = 30;
        height = 30;
        sprite = SpriteMap.pot;
    }
    public void update()
    {
        spawnTimer++;
        if(spawnTimer>=SPAWNTIME)
        {
            spawnTimer = 0;
            //spawnBubble = true;
        }
    }
    public boolean trySpawnBubble()
    {
        if(spawnBubble)
        {
            spawnBubble = false;
            return true;
        }
        return false;
    }
}

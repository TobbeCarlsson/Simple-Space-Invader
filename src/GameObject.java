import java.awt.Image;

public class GameObject {
    Image sprite;
    int[] pos = new int[2];
    int[] scale = new int[2];
    boolean dead = false;
    boolean destroyed = false;
    int score = 100;
    Entity ent;

    public GameObject (Image sprite, int xpos, int ypos, int width, int height) {
        this.sprite = sprite;
        this.pos[0] = xpos;
        this.pos[1] = ypos;
        this.scale[0] = width;
        this.scale[1] = height;
    }
    public GameObject (Image sprite, int xpos, int ypos, int width, int height, Entity ent) {
        this.sprite = sprite;
        this.pos[0] = xpos;
        this.pos[1] = ypos;
        this.scale[0] = width;
        this.scale[1] = height;
        this.ent = ent;
    }

    public void getHit()
    {
        if(ent != null)
        {
            ent.Hit();
        }
    }

    public Image getImage ()
    {
        return sprite;
    }

    public int[] getPosition ()
    {
        return pos;
    }

    public int[] getScale ()
    {
        return scale;
    }

    public void MoveToPosition (int[] pos)
    {
        this.pos[0] = pos[0];
        this.pos[1] = pos[1];
    }

    public void MoveRelative (int[] dpos)
    {
        this.pos[0] += dpos[0];
        this.pos[1] += dpos[1];
    }

    public boolean isDead(){
        return dead;
    }

    public void Destroy(){
        destroyed = true;
    }

    public boolean isDestroyed (){
        return destroyed;
    }
}
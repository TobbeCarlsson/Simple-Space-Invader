import java.awt.Image;

public class TextObject {

    String text;
    int[] pos = new int[2];
    int[] scale = new int[2];
    boolean dead = false;
    int score = 0;
    int fontsize;

    public TextObject (String text, int xpos, int ypos, int fontsize) {
        this.text = text;
        this.pos[0] = xpos;
        this.pos[1] = ypos;
        this.fontsize = fontsize;
    }

    public String getString ()
    {
        return text;
    }

    public int getFontSize ()
    {
        return fontsize;
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
}
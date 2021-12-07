import java.awt.*;
public class Entity {
    protected GameObject go;
    public Entity (Image sprite, int xpos, int ypos, int width, int height)
    {
        go = new GameObject(sprite, xpos, ypos, width, height);
    }
    public Entity (GameObject go)
    {
        this.go = go;
    }
    public GameObject GetGameObject()
    {
        return go;
    }
    public void Update()
    {

    }
    public void Hit()
    {
        
    }
}

public class Bullet extends Entity {
    private int speed;
    private SpaceInvaders gameManager;
    public enum BulletType {
        PLAYERBULLET,
        ENEMYBULLET
    }

    public Bullet (GameObject go, int speed)
    {
        super(go);
        this.speed = speed;
    }

    public void Update()
    {

    }
}
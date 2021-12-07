import java.awt.event.*;
import java.awt.*;


public class Spaceship extends Entity {
    private int speed = 10;
    private int currentheading = 0;
    private Board board;
    GameObject bullet;
    int[] xy;
    boolean hit = true;
    int[] hidden = new int[]{-1000000,100000};
    Enemy[][] enemies;
    

    public Spaceship (Image sprite, int xpos, int ypos, int width, int height)
    {
        
        super(sprite, xpos, ypos, width, height);
    }

    public Spaceship (GameObject go, Board board, Enemy[][] enemies)
    {
        
        super(go);
        this.board = board;
        this.enemies = enemies;
    }

    public void move (int movement)
    {
        int[] mov = new int[] {movement*speed, 0};
        go.MoveRelative(mov);
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT||key == KeyEvent.VK_A) {
            currentheading = -1;
        }

        if (key == KeyEvent.VK_RIGHT||key == KeyEvent.VK_D) {
            currentheading = 1;
        }

        if (key == KeyEvent.VK_SPACE){
            if(hit)
            {
            xy = GetGameObject().getPosition();
            bullet = board.addImage("img/Bullet.png", xy[0]+12, xy[1]-30, 9, 30);
            hit = false;
            }
            
        }

        
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT||key == KeyEvent.VK_A) {
            currentheading = 0;
        }

        if (key == KeyEvent.VK_RIGHT||key == KeyEvent.VK_D) {
            currentheading = 0;
        }
    }

    @Override
    public void Update()
    {
        move(currentheading);        
        if(!hit)
        {
            int[] mov = new int[] {0, -2*speed};
            bullet.MoveRelative(mov); 

            if(bullet.getPosition()[1] <= 0)
            {
                hit = true;
                bullet.MoveToPosition(hidden);
            }
            Hit();
        }
        
        
    }

    public void Hit()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if(enemies[i][j].CheckIfHit()){
                    continue;
                }
                int x = bullet.getPosition()[0];
                int y = bullet.getPosition()[1];
                if(enemies[i][j].GetGameObject() != null)
                {
                    int width = enemies[i][j].GetGameObject().getScale()[0];
                    int height = enemies[i][j].GetGameObject().getScale()[1];
                    int ex = enemies[i][j].GetGameObject().getPosition()[0];
                    int ey = enemies[i][j].GetGameObject().getPosition()[1];
                    if ((x <= ex+width && x >= ex) && (y <= ey+height && y >= ey))
                    {
                        bullet.Destroy();
                        enemies[i][j].Die();;
                        hit = true;
                    }
                }
            }
        }
    }

    
}
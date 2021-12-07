
import java.awt.event.*;
import java.awt.*;


public class Enemy extends Entity {
    private int ticksToMove = 10;
    private int ticksToMoveMax = 10;
    private int health = 1;
    private int row;
    private Heading heading = Heading.RIGHT;
    public enum Heading {LEFT, RIGHT};
    private int movesDown = 0;
    private int[] borderRegion = new int[] {100, 1400};
    private SpaceInvaders gameManager;
    private int score;
    private int[] mDown =  {0,30};
    private int[] mRight = {30,0};
    private int[] mLeft = {-30,0};
    int[] hidden = new int[]{900,-1000};
  
    public Enemy (GameObject go, int ticksToMove, int health, int row, int score, SpaceInvaders gameManager)
    {
        super(go);
        this.ticksToMoveMax = ticksToMove;
        this.health = health;
        this.score = score;
        this.gameManager = gameManager;
    }
      
    public void Update ()
    {
        if(go!=null)
        {
            if(CrossingBorder())
            {
                ChangeHeading();
            }
            Move();
        }
    }

    public void Move ()
    {
        if(movesDown != 0)
        {
            MoveDown();
        }else{
            MoveSide();
        }
    }

    public void MoveDown()
    {
        if(go.isDead())
        {
            return;
        }
        go.MoveRelative(mDown);
        movesDown = 0;
    }

    public void MoveSide ()
    {
        if(heading == Heading.RIGHT)
        {
            go.MoveRelative(mRight);
            ticksToMove--;
            if(ticksToMove == 0)
            {
                ChangeHeading();
                ticksToMove = ticksToMoveMax;
                movesDown = 1;
            }
        }else if (heading == Heading.LEFT)
        {
            go.MoveRelative(mLeft);
            ticksToMove--;
            if(ticksToMove == 0)
            {
                ChangeHeading();
                ticksToMove = ticksToMoveMax;
                movesDown = 1;
            }
        }
    }

    public boolean CheckIfHit ()
    {
        if(go != null)
        {
            return go.destroyed;
        }
        return false;
    }

    public void Die ()
    {
        gameManager.increaseScore(score);
        go.Destroy();
        go = null;
    }

    public void ChangeHeading ()
    {
        if(heading == Heading.LEFT)
        heading = Heading.RIGHT;
        else
        heading = Heading.LEFT;
    }

    public boolean CrossingBorder ()
    {
        if(heading == Heading.LEFT && go.getPosition()[0]<borderRegion[0])
        {
            return true;
        } else if (heading == Heading.RIGHT && go.getPosition()[0]>borderRegion[1]) {
            return true;
        }
        return false;
    }

    @Override
    public void Hit ()
    {
        health --;
        if(health<=0)
        Die();
    }
    
    public int score()
    {
        return score;
    }
}


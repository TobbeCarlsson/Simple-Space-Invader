import javax.swing.Timer;

import org.w3c.dom.Text;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;

/**
 * Runtime class for game
 * @author Tobias Carlsson, Kei Duke Bergman
 */
public class SpaceInvaders extends Game implements ActionListener {

    enum State {
        MENU,
        PLAYING,
        GAMEOVER
    }

    private int scorenumber;
    private int highscorenumber = 0;
    private int DELAY = 15;
    private Timer timer;
    private JLabel highscore;
    private TextObject score;
    private TextObject waveNr;
    private GameObject startInvader;
    private Enemy[][] enemies = new Enemy[3][10];
    private Spaceship ship;
    private boolean won = false;
    private boolean right = true;
    private int counter = 0;    
    private boolean border = false;
    public State state;
    private GameObject enemy;
    private int enemyScore;
    protected int wave = 1;
    private int waveTickNumber;
    private int gameoverToMenuTick;
    private JLabel scorelabel;
    private GameObject bullet;
    private int[] shipPos;
    private int[] shipScale;
    private int[] bulletPos;
    private int[] bulletScale;
    private Ovvelord Glenn;
    private Ovvelord Ric;

    public SpaceInvaders(int width, int height){
        super(width, height);
        addKeyListener(new TAdapter());
        timer = new Timer(DELAY, this);
        timer.start();
        setFocusable(true);
        requestFocusInWindow();
    }

    public static void main(String[] args){
        new SpaceInvaders(1500, 1000);
    }
   
    public void actionPerformed(ActionEvent e) {
        update();
    }

    public void increaseScore(int score)
    {
        scorenumber += score;
    }

    @Override
    public void run() {
        super.run();
        while (running){
        }
        stop();
    }

    public void UpdateOvvelord (Ovvelord ovve)
    {
        ovve.MoveSide();
        if(ovve.hdg == 1 && ovve.go.getPosition()[0] >= 4500)
        {
            ovve.hdg = -1;
        }
        else if(ovve.hdg == -1 && ovve.go.getPosition()[0] <= -4500)
        {
            ovve.hdg = 1;
        }
    }

    int steps = 0;
    @Override
    public void update(){
        if(state == State.PLAYING)
        {
            UpdateOvvelord(Glenn);
            UpdateOvvelord(Ric);
            counter++;
            if(counter % 10 == 0)
            {
                moveEnemies();
            }
            if(counter % 80 == 0)
            {
                enemyShooting();
            }

            if(bullet != null){
                moveBullet();
            }
            myBoard.Render();
            steps++;
            ship.Update();
            if(waveIsCleared())
            {
                wave++;
                SpawnEnemies();
            }
            if(waveTickNumber > 0)
            {
                waveTickNumber--;
                waveNr.text = "Wave " + ((Integer)wave).toString();
            }else if (waveTickNumber == 0)
            {
                waveTickNumber--;
                myBoard.RemoveTextRenderer(waveNr);
            }
            winlosscheck();
            updateScore();
        }
        if(state == State.GAMEOVER)
        {
            gameoverToMenuTick--;
            if(gameoverToMenuTick<0)
            {
                state = State.MENU;
                InitMenuUI();
            }
        }
    }

   

    public void StartGame()
    {
        state = State.PLAYING;
        InitGameUI();
        steps = 0;
    }

    public boolean waveIsCleared()
    {
        boolean cleared = true;
        for(int i = 0; i < enemies.length; i++)
        {
            for(int j = 0; j < enemies[i].length; j++)
            {
                if(enemies[i][j].GetGameObject() != null)
                {
                    cleared = false;
                }
            }
        }
        return cleared;
    }

    public void ExitGame()
    {
        running = false;
        myBoard.getFrame().dispatchEvent(new WindowEvent(myBoard.getFrame(), WindowEvent.WINDOW_CLOSING));
    }

    public void GameOver()
    {
        state = State.GAMEOVER;
        gameoverToMenuTick = 120;
        myBoard.removeAll();
        myBoard.revalidate();
        myBoard.repaint();
        if(scorenumber > highscorenumber)
        {
            highscorenumber = scorenumber;
        }
        scorelabel = myBoard.addLabel("Final score: " + scorenumber, 550, 350, 750, btnH, fontSize, Color.BLACK, Color.WHITE);
    }

    @Override
    public void InitGameUI()
    {
        myBoard.removeAll();
        myBoard.revalidate();
        myBoard.repaint();
        int fsScore = 28;
        score = myBoard.addText("score", 1400, 50, fsScore);
        SpawnEnemies();
        GameObject shipObject = myBoard.addImage("img/Shipsprite.png", 750, 900, 32, 32);
        ship = new Spaceship(shipObject, myBoard, enemies);
        setFocusable(true);
        requestFocusInWindow();
        Glenn = new Ovvelord(myBoard.addImage("img/Prev_Alien.png", -4500, 300, 32, 32), 3);
        Ric = new Ovvelord(myBoard.addImage("img/riccy_boi.png", 4500, 700, 32, 32), 10);
    }

    public void enemyShooting()
    {
        ArrayList<Enemy> aliveEnemies = new ArrayList<Enemy>();
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(enemies[i][j].GetGameObject() != null)
                {
                    aliveEnemies.add(enemies[i][j]);
                }
            }
        }
        int random = (int)(Math.random()*(aliveEnemies.size()-1));
        Enemy shooter = aliveEnemies.get(random);
        int[] position = shooter.GetGameObject().getPosition();
        int[] scale = shooter.GetGameObject().getScale();
        bullet = myBoard.addImage("img/Bullet.png", position[0]+scale[0]/2, position[1]+scale[1], 9, 30);
    }

    public void moveBullet()
    {
        int[] mov = new int[] {0, 30};
        bullet.MoveRelative(mov);
        int[] position = bullet.getPosition();
        if(position[1] >= 900)
        {
            bullet.Destroy();
            bullet = null;
        }
    }

    public void SpawnEnemies ()
    {
        waveTickNumber = 60;
        int fsWave = 40;
        waveNr = myBoard.addText("Wave", 750, 300, fsWave);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 10; j++){
                if(i == 0){
                    enemy = myBoard.addImage("img/GreenSpaceInvader.png", (j*50)+300, -150 + 50*i, 50, 50);
                    enemyScore = 30;
                }else if (i == 1){
                    enemy = myBoard.addImage("img/BlueSpaceInvader.png", (j*50)+300, -150 + 50*i, 50, 50);
                    enemyScore = 20;
                }else if (i == 2){
                    enemy = myBoard.addImage("img/PinkSpaceInvader.png", (j*50)+300, -150 + 50*i, 50, 50);
                    enemyScore = 10;
                }
                enemies[i][j] = new Enemy(enemy, clamp(16-wave, 15, 1), 1, i, enemyScore * wave, this);
            }
        }
    }

    public int clamp (int val, int max, int min)
    {
        if(val>max)
        {   
            return max;
        }
        else if (val<min)
        {
            return min;
        }
        return val;
    }

    @Override
    public void InitMenuUI()
    {
        myBoard.removeAll();
        myBoard.revalidate();
        myBoard.repaint();
        scorenumber = 0;
        startbutton = myBoard.addButton("Start game", 550, 350, btnW, btnH, fontSize, Color.BLACK, Color.WHITE);
        quitbutton = myBoard.addButton("Quit", 550, 550, btnW, btnH, fontSize, Color.BLACK, Color.WHITE);
        hiscore = myBoard.addLabel("Highscore: ", 525, 250, btnW, btnH, 40, Color.BLACK, Color.WHITE);
        highscore = myBoard.addLabel(Integer.toString(highscorenumber), 775, 250, btnW, btnH, 40, Color.BLACK, Color.WHITE);
        startInvader = myBoard.addImage("img/PinkSpaceInvaderStart.png", 600, 100, 200, 200);
        startbutton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                myBoard.RemoveRenderer(startInvader);
                startInvader = null;
                StartGame();
            }
        }
        );
        quitbutton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                ExitGame();
            }
        }
        );
    }

    public void moveEnemies()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                enemies[i][j].Update();
            }
        }
    }

    public void winlosscheck()
    {
        if(bullet != null)
        {
            shipPos = ship.GetGameObject().getPosition();
            shipScale = ship.GetGameObject().getScale();
            bulletPos = bullet.getPosition();
            bulletScale = bullet.getScale();
        
            if((bulletPos[1]+bulletScale[1]) >= shipPos[1] && (bulletPos[0] >= (shipPos[0]-9) && bulletPos[0] <= (shipPos[0]+shipScale[0])))
            {
                GameOver();
            }
        }
        for(int i = 0; i < enemies.length; i++)
        {
            for(int j = 0; j < enemies[i].length; j++)
            {
                if(enemies[i][j].GetGameObject() != null && enemies[i][j].GetGameObject().getPosition()[1] > 1020)
                {
                    GameOver();
                }
            }
        }
    }

    public void updateScore()
    {
        if(score != null)
        score.text = ((Integer)scorenumber).toString();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if(ship!= null)
            ship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(ship!= null)
            ship.keyPressed(e);
           
        }
    }

}   
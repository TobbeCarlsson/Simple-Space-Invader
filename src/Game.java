import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Setup for game
 * @author Tobias Carlsson, Kei Duke Bergman
 */
public class Game extends JFrame implements Runnable {

    protected int btnW = 300;
    protected int btnH = 100;
    protected int fontSize = 60;
    protected static Board myBoard;
    protected int width, height;

    protected boolean running = false;
    protected Thread thread;
    protected JButton startbutton;
    protected JButton quitbutton;
    protected JLabel hiscore;
    protected JLabel score;

    public Game(int width, int height){
        this.width = width;
        this.height = height;
        start();
    }

    public void init(int width, int height){
        try{
            myBoard = new Board(width, height, Color.BLACK, "Space invaders a la Kei och Tobbe", this);
            InitMenuUI();
           
        }catch(Exception e) {
            System.err.println("Cannot create the display");
        }        
    }

    public void update(){
        
    }

    public void render(){
        
    }

    //Is overridden in main game class
    public void InitGameUI()
    {
        
    }

    //Is overridden in main game class
    public void InitMenuUI()
    {

    }

    public void run(){
       
        init(width, height);
    }

    public synchronized void start(){
        if (running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start(); // Will call run method
    }

    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        
    }

}
import javax.swing.*;

import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class that creates the GUI elements for games
 * @author: Kei Duke Bergman, Tobias Carlsson
 * 
 */
public class Board extends JPanel {
  JFrame frame;
  Color backgroundColor = Color.BLACK;
  ArrayList<GameObject> renderObjects;
  ArrayList<TextObject> renderTextObjects;
  

  public Board(int width, int height, Color col, String title, JFrame frame) throws InterruptedException
  {
    this.renderObjects = new ArrayList<GameObject>();
    this.renderTextObjects = new ArrayList<TextObject>();
    this.frame = frame;
    setPreferredSize(new Dimension(width, height));
    this.backgroundColor = col;
    SetBackgroundColor(col);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack();
    frame.setVisible(true);
    this.setLayout(null);
}

public JLabel addLabel(String title, int xPos, int yPos, int width, int height, int fontSize, Color back, Color front){
  JLabel l = new JLabel(title);
  l.setBounds(xPos,yPos,width,height);
  l.setFont(new Font("Arial", Font.PLAIN, fontSize));
  l.setBorder(BorderFactory.createEmptyBorder());
  l.setBackground(back);
  l.setForeground(front);
  add(l);
  frame.setVisible(true);
  return l;
}

public GameObject addImage(String path, int xPos, int yPos,int width,int height){
  Image image = LoadImage(path).getImage();
  GameObject g = new GameObject(image,xPos,yPos,width,height);
  AddRenderer(g);
  frame.setVisible(true);
  return g;
}

public TextObject addText(String text, int xPos, int yPos,int fontsize){
  TextObject g = new TextObject(text,xPos,yPos,fontsize);
  AddTextRenderer(g);
  frame.setVisible(true);
  return g;
}

  public JFrame getFrame ()
  {
    return frame; 
  }

  public JPanel getPanel ()
  {
    return this; 
  }

 

  public void SetBackgroundColor (Color col)
  {
    setBackground(col);
  }

  public void SetTitle (String title)
  {
    frame.setTitle(title);
  }
  
  public JButton addButton (String text, int x, int y, int width, int height, int fontSize, Color back, Color front){
    JButton b = new JButton(text);
    b.setBounds(x, y, width, height);
    b.setFont(new Font("Arial", Font.PLAIN, fontSize));
    b.setBackground(back);
    b.setForeground(front);
    b.setBorder(BorderFactory.createEmptyBorder());
    this.add(b);
    frame.setVisible(true);
    return b;
 }


  public ImageIcon LoadImage(String path)
  {
    return new ImageIcon(ClassLoader.getSystemResource(path));
  }

  public void PaintObject(GameObject go, Graphics g)
  {
    Graphics2D graphics = (Graphics2D) g;
    graphics.drawImage(go.getImage(), go.pos[0], go.pos[1], this);
  }

  public void PaintText(TextObject go, Graphics g)
  {
    Graphics2D graphics = (Graphics2D) g;
    g.setColor(Color.WHITE);
    Font fontisch = new Font("Dialog", Font.PLAIN, go.getFontSize());
    g.setFont(fontisch);
    graphics.drawString(go.getString(), go.pos[0], go.pos[1]);
  }

  @Override 
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);

    for (int i = renderObjects.size()-1; i >= 0; i--)
    {
      GameObject go = renderObjects.get(i);
      if(!go.isDestroyed())
      {
        PaintObject(go, getGraphics());
      }
      else
      {
        renderObjects.remove(i);
      }
    }
    for (int i = 0; i < renderTextObjects.size(); i++)
    {
      TextObject go = renderTextObjects.get(i);
      PaintText(go, getGraphics());
    }

    //apparently, the renderer divides the work into desynched subroutines which is an IQ-DEPRIVED way to do graphics
    //luckily, we can mitigate some disgusting jittering by syncing the draw actions.
    Toolkit.getDefaultToolkit().sync();
  } 
  public void Render()
  {
    paintComponent(getGraphics());
  }

  public void RemoveRenderer(GameObject go)
  {
    renderObjects.remove(go);
  }

  public void RemoveTextRenderer(TextObject go)
  {
    renderTextObjects.remove(go);
  }

  public void AddRenderer(GameObject go)
  {
    renderObjects.add(go);
    PaintObject(go, getGraphics());
  }

  public void AddTextRenderer(TextObject go)
  {
    renderTextObjects.add(go);
    PaintText(go, getGraphics());
  }

  @Override
  public void removeAll ()
  {
    super.removeAll();
    renderObjects = new ArrayList<GameObject>();
    renderTextObjects = new ArrayList<TextObject>();
  }

}
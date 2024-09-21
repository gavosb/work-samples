import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class GameListener here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameListener implements KeyListener
{
    /**
     * Constructor for objects of class GameListener
     */
    public GameListener()
    {
        
    }
    
    /**
     * implicitly called by the listener whenever a key is pressed while the appropriate component is selected
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
    switch(key) { 
        case KeyEvent.VK_UP:
            QuiltController.getFrame().shift("up");
            break;
        case KeyEvent.VK_DOWN:
            QuiltController.getFrame().shift("down");
            break;
        case KeyEvent.VK_LEFT:
            QuiltController.getFrame().shift("left");
            break;
        case KeyEvent.VK_RIGHT :
            QuiltController.getFrame().shift("right");
            break;
     }
    }
    /**
     * Placeholders to satisfy KeyListener
     */
    public void keyTyped(KeyEvent e){
        
    }
    public void keyReleased(KeyEvent e){
        
    }
}

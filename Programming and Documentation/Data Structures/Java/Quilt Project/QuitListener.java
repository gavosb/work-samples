import java.awt.*;
import java.awt.event.*;
/**
 * QuitListener - listens for the quit event
 *
 * @author Gavin Osborn
 * @version 04.12.2021
 */
public class QuitListener implements ActionListener
{

    /**
     * Constructor for objects of class QuitListener
     */
    public QuitListener()
    {
    }
    
    /**
     * Exits the game
     * @param e ActionEvent - automatically passed
     */
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }
}

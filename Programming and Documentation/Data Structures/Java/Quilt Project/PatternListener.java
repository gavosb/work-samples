import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * PatternListener - listens for the quit event
 *
 * @author Gavin Osborn
 * @version 04.12.2021
 */
public class PatternListener implements ActionListener
{
    JFrame frame;
    /**
     * Constructor for objects of class PatternListener
     */
    public PatternListener()
    {
        frame = null;
    }
    /**
     * Sets the frame to be used for actions
     * @param f JFrame to be passed
     */
    public void setFrame(JFrame f){
        frame = f;
    }
    
    /**
     * Displays patterns when the appropriate button has been pressed
     * @param e ActionEvent - automatically passed
     */
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Maze")){
            ((QuiltFrame)frame).displayMaze();
        }else if(e.getActionCommand().equals("Traditional")){
            ((QuiltFrame)frame).displayQuilt();
        }else if(e.getActionCommand().equals("Advanced")){
            ((QuiltFrame)frame).displayAdvanced();
        }else if (e.getActionCommand().equals("Random")){
            ((QuiltFrame)frame).displayRandom();
            }else if (e.getActionCommand().equals("2048")){
            ((QuiltFrame)frame).display2048();
        }else if (e.getActionCommand().equals("Alternating")){
            ((QuiltFrame)frame).displayAlternating();
        }else if (e.getActionCommand().equals("Paint")){
            QuiltController.setTool("Paint");
        }else if (e.getActionCommand().equals("Inspect")){
            QuiltController.setTool("Inspect");
        }else if (e.getActionCommand().equals("Switch")){
            QuiltController.setTool("Switch");
        }else if (e.getActionCommand().equals("Choose Color")){
            QuiltController.setPalette("color");
            JFrame frame = new JFrame();
            frame.setPreferredSize(new Dimension(300, 300));
            Container contentPane = frame.getContentPane();
            contentPane.add(QuiltController.getCc());
            frame.pack(); //update frame layout and set to visible
            frame.setVisible(true);
        }else if (e.getActionCommand().equals("Default Block")){
            QuiltController.setPalette("default");
        }else if (e.getActionCommand().equals("Random Block")){
            QuiltController.setPalette("random");
        }else if (e.getActionCommand().equals("Recursive Block")){
            QuiltController.setPalette("recursive");
        }else if (e.getActionCommand().equals("Image Block")){
            QuiltController.setPalette("image");
        }else if (e.getActionCommand().equals("Special Block")){
            QuiltController.setPalette("special");
        }
    }
}

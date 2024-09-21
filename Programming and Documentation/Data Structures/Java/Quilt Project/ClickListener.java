import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author G.C.O
 * @version 04.12.2021
 */
public class ClickListener implements MouseListener
{
    /**
     * Constructor for objects of class QuitListener
     */
    public ClickListener()
    {
    }
    
    /**
     * implicitly called by the listener whenever the mouse is clicked on the attached component
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e){
        String action = null;
        Block c = null;
        JPanel mainPanel = QuiltController.getFrame().getMainPanel();
        try {
            c = ((Block)e.getComponent());
        }catch (ClassCastException b) {
                 return;
        }
        
        if (QuiltController.getTool().equals("Inspect")){
            c.enlargeFrame();
        }else if (QuiltController.getTool().equals("Switch")){
            if (QuiltController.getSwitch() != null){
                Block obj1 = c; //current
                Block obj2 = QuiltController.getSwitch(); //previously selected
                int obj1o = mainPanel.getComponentZOrder(obj1);
                int obj2o = mainPanel.getComponentZOrder(obj2);
                mainPanel.setComponentZOrder(obj1, obj2o);
                mainPanel.setComponentZOrder(obj2, obj1o);
                QuiltController.getFrame().revalidate();
                QuiltController.getFrame().repaint();
                QuiltController.setSwitch(null);
            }else{
                QuiltController.setSwitch(c);
            }
        }else if (QuiltController.getTool().equals("Paint")){
            if (QuiltController.getPalette().equals("color")){
                //paint block chosen color
                Color newc = QuiltController.getCcColor();
                c.setColor(newc);
            }else if (QuiltController.getPalette().equals("default")){
                paintblock(mainPanel, c);
            }else if (QuiltController.getPalette().equals("random")){
                paintblock(mainPanel, c);
            }else if (QuiltController.getPalette().equals("recursive")){
                paintblock(mainPanel, c);
            }else if (QuiltController.getPalette().equals("image")){
                paintblock(mainPanel, c);
            }else if (QuiltController.getPalette().equals("special")){
                paintblock(mainPanel, c);
            }
            
        }
    }
    
    /**
     * Placeholders that fulfill the interface requirements
     */
    public void mouseExited(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mousePressed(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
    }
    
    /**
     * Paints the provided block whatever the current Palette choice is
     * @param mainPanel JPanel where the Block is located
     * @param c Block to be modified
     */
    public void paintblock(JPanel mainPanel, Block c){
        Block b = new Block(QuiltController.getCcColor(), QuiltController.getPalette());
        int cn = mainPanel.getComponentZOrder(c);
        mainPanel.add(b);
        mainPanel.remove(c);
        mainPanel.setComponentZOrder(b, cn);
        QuiltController.getFrame().revalidate();
        QuiltController.getFrame().repaint();    
    }
}
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.Random;
import javax.imageio.*;
import java.io.*;
/**
 * Block - a basic quilt block that takes a solid color
 *
 * @author Gavin Osborn
 * @version 04.16.2021
 */
public class Block extends JPanel
{
    private Color solidcolor;
    private boolean rl = true;
    private boolean ll = true;
    private boolean tl = true;
    private boolean bl = true;
    private String pattern = "default";
    private int number = 0;
    private Random rng = new Random();
    private boolean copy = false;
    private int seed;
    private int ranum;
    private int h;
    private int w;
    
    //should we use multiple constructors, or just one or two?
    /**
     * Constructor for objects of class Block
     * @param sc Color to set as default background
     * @param rng Random class to be passed
     * @param type String for what kind of Block it is
     */
    public Block(Color sc, String type)
    {
        seed = rng.nextInt();
        rng.setSeed(seed);
        solidcolor = sc;
        this.setBackground(sc);
        addMouseListener(new ClickListener());
        pattern = type;
        w = getWidth();
        h = getHeight();
    }
    
    /**
     * Constructor that creates a new copy of the passed Block (not a reference)
     * In order to copy randomized things, their randomness must be selected in the constructor or field, not paint.
     */
    public Block(Block block){ //used for zooming in to/enlarging a Block
        //this should be able to be accessed directly because its the same class
        this.solidcolor = block.solidcolor;
        this.setBackground(block.solidcolor);
        this.pattern = block.pattern;
        this.rl = block.rl;
        this.ll = block.ll;
        this.tl = block.tl;
        this.bl = block.bl;
        this.w = block.w;
        this.h = block.h;
        this.rng = new Random(block.seed);
        this.seed = block.seed;
        addMouseListener(new ClickListener());
    }
    
    /**
     * Draws a recursive sierpinski Triangle given the initial points for a polygon
     * @param count int - initial setting determines how many times the method is called recursively
     * @param x int[] - the list of x values
     * @param y int[] - the list of y values
     * @param g Graphics - provided by paintComponent's graphics
     */
    public void drawTriangle(int count, int[] x, int[] y, Graphics g){
        if (count <= 0){return;}
        g.drawPolygon(x, y, 3);
        
        //middle point, right point, left point
        
        //you can get a triforce by shrinking the middle point as well and only doing 1 triangle
        int [] nx = {(x[0]), (x[0]+x[2])/2,(x[0]+x[1])/2};
        int [] ny = {(y[0]), (y[0]+y[1])/2,(y[0]+y[2])/2};
        drawTriangle(count-1, nx, ny, g);
        int [] nxe = {(x[0]+x[2])/2, (x[1]+x[2])/2,(x[2])};
        int [] nye = {(y[0]+y[2])/2, (y[1]+y[2])/2,(y[2])};
        drawTriangle(count-1, nxe, nye, g);
        int [] nxee = {(x[0]+x[1])/2, (x[1]),(x[2]+x[1])/2};
        int [] nyee = {(y[0]+y[1])/2, (y[1]),(y[1]+y[2])/2};
        drawTriangle(count-1, nxee, nyee, g);
    }
    
    /**
     * Overrides paintComponent;
     * Draws lines and pattern specific graphical changes
     * @param g Graphics - implicitly provided by Swing
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        rng.setSeed(seed);
        if (!copy){w = getWidth(); h = getHeight();}
        if (pattern.equals("recursive")){
            g.setColor(Color.DARK_GRAY);
            //middle point, then right point, then left
            int [] x = {getWidth()/2,getWidth()-10,10};
            int [] y = {10,getHeight()-10,getHeight()-10};
            drawTriangle(6, x, y, g);
        }if (pattern.equals("2048") && number > 0){
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("TimesRoman", 1, 30));
            g.drawString(""+number,getWidth()/2,getHeight()/2);
        }
        if (pattern.equals("image")){
            try {                
              Image image = ImageIO.read(new File("cat.png"));
              Image img = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
              g.drawImage(img, 0, 0, this);
           } catch (IOException ex) {
                System.out.println("error");
           }
        }
        if (pattern.equals("random")){
            int i = 0;
            while (i < rng.nextInt(10)){
                g.setColor(new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256)));
                int x = rng.nextInt(w);
                int y = rng.nextInt(h);
                int x2 = rng.nextInt(w);
                int y2 = rng.nextInt(h);
                g.fillRect(x, y, x2, y2);
                i++;
            }
        }
        if (pattern.equals("special")){
            int x = 0;
            int y = 0;
            for (int i=0; i < w; i++){
                g.setColor(new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256)));
                g.fillOval(x, y, x, y);
                x += 10;
                y += 10;
            }
        }
        
        g.setColor(Color.DARK_GRAY); //make sure to set this back for the maze if you have to change it

        //draws lines if enabled (maze generation enables them so far)
        if (!rl){
            g.drawLine(getWidth(),0,getWidth(),getHeight());
        }
        if (!ll){
            g.drawLine(0,0,0,getHeight());
        }
        if (!tl){
            g.drawLine(0,0,getWidth(),0);
        }
        if (!bl){
            g.drawLine(0,getHeight(),getWidth(),getHeight());
        }
    }
    
    /**
     * Sets the lines to be drawn on the sides of the Block
     * @param line String for line to be drawn
     * rl = right line
     * ll = left line
     * tl = top line
     * bl = bottom line
     */
    public void setLine(String line){
        if (line.equals("rl")){
            rl = false;
        }else if (line.equals("ll")){
            ll = false;
        }else if (line.equals("tl")){
            tl = false;
        }else if (line.equals("bl")){
            bl = false;
        }
    }
    
    /**
     * Creates a separate JFrame with a version of the Block within it
     * Called by the Inspect tool within PatternListener
     */
    public void enlargeFrame(){
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(300, 300));
        Container contentPane = frame.getContentPane();
        Block b = new Block(this);
        b.setCopy(true);
        contentPane.add(b);
        contentPane.revalidate();
        contentPane.repaint();
        frame.pack(); //update frame layout and set to visible
        frame.setVisible(true);
    }
    
    /**
     * Sets the background color of the Block
     * @param sc Color to change to
     */
    public void setColor(Color sc){
        solidcolor = sc;
        this.setBackground(sc);
        repaint();
    }
    
    /**
     * Sets the number of the Block and changes to the appropriate background Color
     * @param n Int to set as the new number
     */
    public void setNumber(int n){
        number = n;
        setColor(QuiltController.getNumberColors().get(n));
        
    }
    
    /**
     * Gets the 2048 number of the Block
     * @return int number
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * Sets the state of the copy variable to the provided boolean
     * @param c boolean
     */
    public void setCopy(boolean c){
        copy = c;
    }
    
    /**
     * Sets the seed of rng
     * @param s int s - the seed to be set
     */
    public void setSeed(int s){
        seed = s;
        rng.setSeed(s);
    }
}

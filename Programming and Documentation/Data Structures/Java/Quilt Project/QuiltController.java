import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * QuiltController - controls the QuiltFrame
 *
 * @author Gavin Osborn
 * @version 04.12.2021
 */
public class QuiltController
{
    private int rows;
    private int columns;
    private static QuiltFrame frame;
    private static Random rng = new Random(100);
    private static String selectedTool = "Inspect";
    private static JColorChooser cc = new JColorChooser();
    private static String palette_type = "default";
    private static Block previousBlock;
    private static HashMap<String, Color> customColors;
    private static HashMap<Integer, Color> numberColors;
    private static ArrayList<Block> blocktypes;
    
    /**
     * Constructor for objects of class QuiltController
     */
    public QuiltController(int r, int c)
    {
        customColors = new HashMap<>();
        customColors.put("brown", new Color(222,204,180));
        customColors.put("red", new Color(154,80,69));
        customColors.put("cream", new Color(231,217,170));
        customColors.put("blue", new Color(96,135,164));
        customColors.put("green", new Color(115,142,87));
        customColors.put("pink", new Color(236,192,165));
        customColors.put("gold", new Color(217,191,89));
        customColors.put("purple", new Color(218,138,244));
        customColors.put("teal", new Color(160,222,221));
        customColors.put("violet", new Color(102,106,159));
        customColors.put("maroon", new Color(165,71,99));
        
        numberColors = new HashMap<>();
        numberColors.put(0, customColors.get("brown"));
        numberColors.put(2, customColors.get("cream"));
        numberColors.put(4, customColors.get("pink"));
        numberColors.put(8, customColors.get("blue"));
        numberColors.put(16, customColors.get("green"));
        numberColors.put(32, customColors.get("teal"));
        numberColors.put(64, customColors.get("red"));
        numberColors.put(128, customColors.get("violet"));
        numberColors.put(256, customColors.get("maroon"));
        numberColors.put(512, customColors.get("gold"));
        numberColors.put(1024, customColors.get("purple"));
        numberColors.put(2048, Color.YELLOW);
        blocktypes = new ArrayList<>(Arrays.asList(new Block(customColors.get("red"), "recursive"),
        new Block(customColors.get("cream"), "image"),new Block(customColors.get("blue"), "random"),
        new Block(customColors.get("green"), "special"), new Block(customColors.get("pink"), "default")));
        //rng = new Random(100); //remove seed for final release
        rows = r; //rows and columns might be able to be static
        columns = c;
        makeQFrame();
    }
    
    /**
     * Creates a QuiltFrame
     */
    public void makeQFrame(){
        frame = new QuiltFrame(rows, columns);
        Container contentPane = frame.getContentPane();
        
        //create primary menu bar
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        //create "Menu" dropdown on menu bar
        JMenu fileMenu = new JMenu("Menu");
        JMenu patternMenu = new JMenu("Patterns"); //menu for patterns
        JMenu toolbar = new JMenu("Tools"); //menu for tools
        JMenu palette = new JMenu("Palette");
        menubar.add(fileMenu);
        menubar.add(patternMenu);
        menubar.add(toolbar);
        menubar.add(palette);
        //create menu dropdown buttons
        JMenuItem quitItem = new JMenuItem("Exit");
        fileMenu.add(quitItem);
        //create pattern dropdown buttons
        JMenuItem tradQuilt = new JMenuItem("Traditional");
        JMenuItem ranQuilt = new JMenuItem("Random");
        JMenuItem altQuilt = new JMenuItem("Alternating");
        JMenuItem mazeQuilt = new JMenuItem("Maze");
        JMenuItem advancedQuilt = new JMenuItem("Advanced");
        JMenuItem gameQuilt = new JMenuItem("2048");
        patternMenu.add(tradQuilt);
        patternMenu.add(advancedQuilt);
        patternMenu.add(altQuilt);
        patternMenu.add(ranQuilt);
        patternMenu.add(mazeQuilt);
        patternMenu.add(gameQuilt);
        
        //create toolbar dropdown buttons
        JRadioButton inspect = new JRadioButton("Inspect", true);
        JRadioButton switchBlocks = new JRadioButton("Switch");
        JRadioButton paintBlocks = new JRadioButton("Paint");
        toolbar.add(inspect);
        toolbar.add(switchBlocks);
        toolbar.add(paintBlocks);
        ButtonGroup bug=new ButtonGroup();
        bug.add(inspect);
        bug.add(switchBlocks);
        bug.add(paintBlocks);
        //inspect.addMouseListener(new ClickListener());
        //switchBlocks.addMouseListener(new ClickListener());
        
        //create menu buttons for Palette
        JRadioButton cdefault = new JRadioButton("Default Block");
        JRadioButton crandom = new JRadioButton("Random Block");
        JRadioButton crecursive = new JRadioButton("Recursive Block");
        JRadioButton cimage = new JRadioButton("Image Block");
        JRadioButton cspecial = new JRadioButton("Special Block");
        JRadioButton colorpicker = new JRadioButton("Choose Color");
        palette.add(cdefault);
        palette.add(crandom);
        palette.add(crecursive);
        palette.add(cimage);
        palette.add(cspecial);
        palette.add(colorpicker);
        ButtonGroup nuts=new ButtonGroup();
        nuts.add(cdefault);
        nuts.add(crandom);
        nuts.add(crecursive);
        nuts.add(cimage);
        nuts.add(cspecial);
        nuts.add(colorpicker);
        
        //add event listeners to buttons
        quitItem.addActionListener(new QuitListener());
        PatternListener p = new PatternListener();
        p.setFrame(frame); //pass the frame to the listener
        tradQuilt.addActionListener(p);
        altQuilt.addActionListener(p);
        ranQuilt.addActionListener(p);
        mazeQuilt.addActionListener(p);
        advancedQuilt.addActionListener(p);
        gameQuilt.addActionListener(p);
        
        cdefault.addActionListener(p);
        crandom.addActionListener(p);
        crecursive.addActionListener(p);
        cimage.addActionListener(p);
        cspecial.addActionListener(p);
        colorpicker.addActionListener(p);
        
        
        inspect.addActionListener(p);
        switchBlocks.addActionListener(p);
        paintBlocks.addActionListener(p);
        
        frame.addKeyListener(new GameListener());
        
        frame.pack(); //update frame layout and set to visible
        frame.setVisible(true);
    }
    
    /**
     * @return the Random object
     */
    public static Random getRNG(){
        return rng;
    }
    
    /**
     * @return the selected tool
     */
    public static String getTool(){
        return selectedTool;
    }
    
    /**
     * Sets the selected tool type
     * @param t The selected tool
     */
    public static void setTool(String t){
        selectedTool = t;
    }
    
    /**
     * Gets the global JColorChooser
     * @return JColorChooser
     */
    public static JColorChooser getCc(){
        return cc;
    }
    /**
     * Gets the global JColorChooser's Color
     * @return Color from CC
     */
    public static Color getCcColor(){
        return cc.getColor();
    }
    /**
     * Sets the global JColorChooser's Color
     * @param i Color 
     */
    public static void setCcColor(Color i){
        cc.setColor(i);
    }
    /**
     * Sets the palette type
     * @param i String
     */
    public static void setPalette(String i){
        palette_type = i;
    }
    /**
     * Gets the palette type
     * @return String palette type
     */
    public static String getPalette(){
        return palette_type;
    }
    
    /**
     * Gets the primary JFrame
     * @return String palette type
     */
    public static QuiltFrame getFrame(){
        return frame;
    }
    /**
     * Gets the previousBlock for the Switch tool
     * @return Block previousBlock
     */
    public static Block getSwitch(){
        return previousBlock;
    }
    /**
     * Sets the previousBlock for the Switch tool
     */
    public static void setSwitch(Block i){
        previousBlock = i;
    }
    /**
     * Gets the types of Blocks available
     * @return ArrayList<Block> blocktypes
     */
    public static ArrayList<Block> getTypes(){
        return blocktypes;
    }
    /**
     * Gets the HashMap of customColors, a list of Colors by name
     * brown, red, cream, blue, green, pink, gold, purple, teal, violet, maroon
     * @return HashMap<String, Color> customColors
     */
    public static HashMap<String, Color> getColors(){
        return customColors;
    }
    /**
     * Gets the HashMap of numbered Colors, a list of Colors by number - for 2048.
     * 0:brown, 2:cream, 4:pink, 8:blue, 16:green, 32:teal, 64:red, 128:violet,
     * 256:maroon, 512:gold, 1024:purple, 2048:yellow
     * @return HashMap<Integer, Color> numberColors
     */
    public static HashMap<Integer, Color> getNumberColors(){
        return numberColors;
    }
    
    /**
     * Main function for .jar files or any other use. Sets default size to 4x4.
     * Could also be modified to allow changing the size according to arguments.
     */
    public static void main(String[] args){
        QuiltController qc = new QuiltController(4,4);
        
    }
}

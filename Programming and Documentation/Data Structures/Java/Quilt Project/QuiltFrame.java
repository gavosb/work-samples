import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * QuiltFrame - a kind of JFrame
 *
 * @author Gavin Osborn
 * @version 04.12.2021
 */
public class QuiltFrame extends JFrame
{
   
    private static final int SQUARE_SIZE = 100;
    private int rows;
    private int columns;
    private Random rng;
    private Container contentPane;
    private JPanel mainPanel;
    private JPanel toolPanel;
    private Block[][] blockgrid;
    private int score = 4;
    private int swaps = 1;
    private boolean noDown = false;
    private boolean noUp = false;
    private boolean noLeft = false;
    private boolean noRight = false;
    private boolean endgame = false;
    private boolean win = false;
    /**
     * Constructor for objects of class QuiltFrame
     * @param r number of rows
     * @param c number of columns
     * @param random Random object from QuiltController
     */
    public QuiltFrame(int r, int c)
    {
        
        
        blockgrid = new Block[r][c];
        
        rng = QuiltController.getRNG(); //good practice to keep random objects with one seed
        rows = r;
        columns = c;
        
        mainPanel = new JPanel();
        toolPanel = new JPanel();
        
        contentPane = this.getContentPane();
        contentPane.add(mainPanel,BorderLayout.CENTER);
        contentPane.add(toolPanel,BorderLayout.WEST);
        
        refreshMain();
        
        //displayAdvanced();
        display2048();
        toolPanel.setLayout(new GridLayout(5,1));
        
        
    }
    
    /**
     * Gets the preferred size of the QuiltFrame
     * @return Dimension of rows*SQUARE_SIZE & columns*SQUARE_SIZE
     */
    public Dimension getPreferredSize(){
        Dimension dim = new Dimension(rows*SQUARE_SIZE, columns*SQUARE_SIZE);
        return dim;
    }
    
    /**
     * Applies default settings to the contentPane of the QuiltFrame
     */
    public void refreshMain(){
        mainPanel.removeAll();
        this.repaint();
        mainPanel.setLayout(new GridLayout(rows, columns, 5, 5)); 
        mainPanel.setBackground(Color.DARK_GRAY);
    }
    
    /**
     * Displays a maze pattern
     */
    public void displayMaze(){
        mainPanel.removeAll();
        Maze maze = new Maze(rows, mainPanel);
        this.validate();
        this.repaint(); //allows lines to be drawn because lines are set after creation
        this.pack();
    }
    
    /**
     * displays an alternating pattern of default Blocks
     */
    public void displayAlternating(){
        refreshMain();
        int color = 0;
        for (int x = 0; x < rows; x++){
            for (int y = 0; y < columns; y++){
                //alternate colors
                if (color == 1){
                    color = 0;
                }else{
                    color = 1;
                }
                Block block = null; //create a new block for each column
                //select colors with a switch (cleaner looking than if-statements)
                switch (color) {
                    case 0: //red
                        block = new Block(new Color(154,80,69), "default");
                        break;
                    case 1: //blue
                        block = new Block(new Color(96,135,164), "default");
                        break;
                    default:
                        block = new Block(Color.WHITE, "default");
                        System.out.println("Error, incorrect color code: " + color);
                        break;
                }
                //add block to contentPane with gridlayout
                mainPanel.add(block);
            }
        }
        //updates the layout with new components
        this.pack();
     }
    
    /**
     * Displays default blocks in a traditional alternating pattern starting from a random default Block color
     */
    public void displayQuilt(){
        refreshMain();
        //select starting color
        int color = rng.nextInt(5);
        for (int x = 0; x < rows; x++){
            for (int y = 0; y < columns; y++){
                Block block = null; //create a new block for each column
                //select colors with a switch (cleaner looking than if-statements)
                switch (color) {
                    case 0: //red
                        block = new Block(new Color(154,80,69), "default");
                        break;
                    case 1: //blue
                        block = new Block(new Color(96,135,164), "default");
                        break;
                    case 2: //green
                        block = new Block(new Color(115,142,87), "default");
                        break;
                    case 3: //cream
                        block = new Block(new Color(231,217,170), "default");
                        break;
                    case 4: //pink
                        block = new Block(new Color(236,192,165), "default");
                        break;
                    default:
                        block = new Block(Color.WHITE, "default");
                        System.out.println("Error, incorrect color code: " + color);
                        break;
                }
                //increment color and reset to 0 if over color code limit
                color += 1;
                if (color == 5){
                    color = 0;
                }
                //add block to contentPane with gridlayout
                mainPanel.add(block);
            }
        }
        //updates the layout with new components
        this.pack();
    }
    
    /**
     * displays an advanced quilt pattern using more than default Blocks
     */
    public void displayAdvanced(){
        refreshMain();
        int color = rng.nextInt(QuiltController.getTypes().size());
        for (int x = 0; x < rows; x++){
            for (int y = 0; y < columns; y++){
                Block block = new Block(QuiltController.getTypes().get(color));
                block.setSeed(rng.nextInt());
                block.repaint();
                //increment color and reset to 0 if over color code limit
                color += 1;
                if (color == 5){
                    color = 0;
                }
                //add block to contentPane with gridlayout
                mainPanel.add(block);
            }
        }
        //updates the layout with new components
        this.pack();
    }
    /**
     * displays a randomized pattern using more than default Blocks
     */
    public void displayRandom(){
        refreshMain();
        for (int x = 0; x < rows; x++){
            for (int y = 0; y < columns; y++){
                int color = rng.nextInt(QuiltController.getTypes().size());
                Block block = new Block(QuiltController.getTypes().get(color));
                block.setSeed(rng.nextInt());
                block.repaint();
                //add block to contentPane with gridlayout
                mainPanel.add(block);
            }
        }
        //updates the layout with new components
        this.pack();
    }
    
    /**
     * Sets the current arrangement to a new game of 2048.
     */
    public void display2048(){
        //generate here and then merely refresh every movement in listener...
        refreshMain();
        score = 4;
        
        int x1 = rng.nextInt(rows);int x2 = rng.nextInt(rows);
        int y1 = rng.nextInt(columns);int y2 = rng.nextInt(columns);
        Block block1 = new Block(QuiltController.getNumberColors().get(2), "2048");
        Block block2 = new Block(QuiltController.getNumberColors().get(2), "2048");
        blockgrid[x1][y1] = block1;
        blockgrid[x2][y2] = block2;
        for (int x = 0; x < rows; x++){
            for (int y = 0; y < columns; y++){
                if (blockgrid[x][y]!=block1 && blockgrid[x][y]!=block2){
                    blockgrid[x][y] = new Block(QuiltController.getColors().get("brown"), "2048");
                    mainPanel.add(blockgrid[x][y]);
                }else{
                    blockgrid[x][y].setNumber(2);
                    mainPanel.add(blockgrid[x][y]);
                }
            }
        }
        
        this.pack();
    }
    
    /**
     * A helper method for the 2048 game. Shifts Blocks in a given direction.
     */
    public void shift(String dir){
        swaps = 0;
        
        //maybe for left and up use a reverse 2d array?
        if (dir.equals("up") || dir.equals("left")){
            for (int x = rows-1; x > -1; x--){
                for (int y = columns-1; y > -1; y--){
                    int newx = x;
                    int newy = y;
                    //change rows-1 to rows,but then its out of bounds
                    //boolean breaker = false;
                    while (true){
                        
                        switch (dir){
                            case "up":
                                newx -= 1;
                                break;
                            case "left":
                                newy -= 1;
                                break;
                            default:
                                break;
                            }
                        if (newx >= 0 && newx < rows && newy >= 0 && newy < columns){
                            //check if cell is occupied
                            if (blockgrid[newx][newy].getNumber() != 0){ //occupied
                                    if (blockgrid[newx][newy].getNumber() == blockgrid[x][y].getNumber()){
                                        blockgrid[newx][newy].setNumber(blockgrid[newx][newy].getNumber()*2);
                                        blockgrid[x][y].setNumber(0);
                                        if (blockgrid[newx][newy].getNumber() == 2048){
                                            win = true;
                                        }
                                        swaps += 1;
                                        //call iterative function?
                                        //i think this leaves an awkward empty space - probably not
                                    }else{
                                        //stop
                                        //add some kind of check here to allow it to continue; but not just combine things that arent adjacent
                                        //if its not the same number, it just stays still because you break the loop.
                                        //you need to move it forward?
                                        //honestly this just doesnt work because its not doing the movement in the same loop - one is inverted
                                        
                                        break;
                                        //breaker = true;
                                    }
                            }else{ //allow shifting
                                swapBlocks(newx,newy,x,y);
                                swaps += 1;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        //go through each row
        //within each row, move block up if possible,else continue looking in row
        if (dir.equals("down") || dir.equals("right")){
            
            for (int x = 0; x < rows; x++){
                for (int y = 0; y < columns; y++){
                    int newx = x;
                    int newy = y;
                    //keep moving non-zero blocks ahead until they encounter resistance
                    //boolean breaker = false;
                    while (true){
                        
                        switch (dir){
                            case "down":
                                newx += 1;
                                break;
                            case "right":
                                newy += 1;
                                break;
                            default:
                                break;
                        }
                        
                        //check if out of bounds
                        if (newx >= 0 && newx < rows && newy >= 0 && newy < columns){
                            //check if cell is occupied
                            if (blockgrid[newx][newy].getNumber() != 0){ //occupied
                                    if (blockgrid[newx][newy].getNumber() == blockgrid[x][y].getNumber()){
                                        blockgrid[newx][newy].setNumber(blockgrid[newx][newy].getNumber()*2);
                                        blockgrid[x][y].setNumber(0);
                                        if (blockgrid[newx][newy].getNumber() == 2048){
                                            win = true;
                                        }
                                        swaps += 1;
                                        //call iterative function?
                                        //i think this leaves an awkward empty space
                                    }else{
                                        //breaker = true;
                                        break;
                                    }
                            }else{ //allow shifting
                                swapBlocks(newx,newy,x,y);
                                swaps += 1;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        spawnTwos();
        //check if map has no more combinations & game over
        if (dir.equals("down") && swaps == 0){
            noDown = true;
        }else if (dir.equals("down") && swaps != 0){
            noDown = false;
        }
        if (dir.equals("right") && swaps == 0){
            noRight = true;
        }else if (dir.equals("right") && swaps != 0){
            noRight = false;
        }
        if (dir.equals("up") && swaps == 0){
            noUp = true;
        }else if (dir.equals("up") && swaps != 0){
            noUp = false;
        }
        if (dir.equals("left") && swaps == 0){
            noLeft = true;
        }else if (dir.equals("left") && swaps != 0){
            noLeft = false;
        }
        checkMap();
    }
    
    /**
     * A helper method for the 2048 game. Spawns a two Block for each player movement, if possible.
     */
    public void spawnTwos(){
        int x1 = rng.nextInt(rows);
        int y1 = rng.nextInt(columns);
        int counter = 0;
        boolean spawn = true;
        while (blockgrid[x1][y1].getNumber()!=0){
            x1 = rng.nextInt(rows);y1 = rng.nextInt(columns);
            counter += 1;
            if (counter > rows*columns){spawn=false;break;}
        }
        if (spawn){
            blockgrid[x1][y1].setNumber(2);
            score += 2;
        }
    }

    /**
     * swaps the blockgrid[x][y] and mainPanel componentZOrder of Blocks A and B
     * used by Shift() to switch blocks out when they move
     * @param x1 - x position of block A
     * @param y1 - y position of block A
     * @param x2 - x position of block B
     * @param y2 - y position of block B
     */
    public void swapBlocks(int x1, int y1, int x2, int y2){
        Block obj1 = blockgrid[x1][y1];
        Block obj2 = blockgrid[x2][y2];
        blockgrid[x1][y1] = obj2;
        blockgrid[x2][y2] = obj1;
        int obj1o = mainPanel.getComponentZOrder(obj1);
        int obj2o = mainPanel.getComponentZOrder(obj2);
        mainPanel.setComponentZOrder(obj1, obj2o);
        mainPanel.setComponentZOrder(obj2, obj1o);
        revalidate();
        repaint();
    }
    
    /**
     * Determines whether the game of 2048 ends or not. Called after every player move.
     */
    public void checkMap(){
        if (noDown && noRight && noLeft && noUp){
            noDown = false; noRight = false; noLeft = false; noUp = false;
            endgame = true;
        }
        if (endgame || win){
            JLabel label = new JLabel("Game Over. Score: " + score, JLabel.CENTER);
            if (win){
                label.setText("You reached 2048. Score: " + score);
            }
            label.setBackground(QuiltController.getColors().get("red"));
            label.setOpaque(true);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(label);
            this.pack();
            revalidate();
            repaint();
            endgame = false;
            win = false;
        }
    }
    
    /**
     * Gets the main JPanel, where blocks are contained
     * @return JPanel
     */
    public JPanel getMainPanel(){
        return mainPanel;
    }
}

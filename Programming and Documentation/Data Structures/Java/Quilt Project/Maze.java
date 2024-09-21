import java.io.Serializable;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.lang.Math;
import java.awt.*;
import javax.swing.*;
/**
 * A maze full of Rooms as cells
 *
 * @author Gavin Osborn
 * @version 03.10.2021
 */
public class Maze implements Serializable
{
    
    private Block[][] maze; //x, y
    private int size;
    private HashMap<Block, Integer> sets;
    private Random random;
    private JPanel mainPanel;
    /**
     * Constructor for objects of class Maze
     * @param  size the size of the maze, eg 50 for a 50x50 maze
     * @param c JPanel to add Blocks to
     */
    public Maze(int Size, JPanel c)
    {
        // initialise instance variables
        mainPanel = c;
        random = QuiltController.getRNG();
        size = Size;
        maze = new Block[size][size];
        generateMaze();
    }
    
    /**
     * Carves out a maze path using a binary tree algorithm
     */
    public void createPaths() {
        
        for(int x=0; x<size; x++) {
            for(int y=0; y<size; y++){
               if(random.nextInt(2) >= 1 && x-1 < size && x-1 >= 0) {
                   maze[x][y].setLine("ll");
                   maze[x-1][y].setLine("rl");
               }else if (y-1 < size && y-1 >= 0){
                   maze[x][y].setLine("bl");
                   maze[x][y-1].setLine("tl");
               }else if(x-1 < size && x-1 >= 0) {
                   maze[x][y].setLine("ll");
                   maze[x-1][y].setLine("rl");
               
               }
            }
        }
        
    }
    
    /**
     * Generates a maze full of Room objects
     *
     */
    public void generateMaze(){
        mainPanel.setLayout(new GridLayout(size, size, 0, 0)); 
        mainPanel.setBackground(Color.DARK_GRAY);
        for(int x=0; x<size; x++) {
            for(int y=0; y<size; y++){
                Block cell = new Block(new Color(231,217,170), "default");
                maze[x][y] = cell;
                mainPanel.add(maze[x][y]);
            }
        }
        createPaths();
    }
    
    /**
     * Gets the maze, which is a 2D array of Blocks
     * @return Array[][] a 2D array of Blocks
     */
    public Block[][] getMaze(){
        return maze;
    }
    
    /**
     * Gets a Block from the maze
     * @param x x-axis of Block
     * @param y y-axis of Block
     * @return Block
     */
    public Block getBlock(int x, int y){
        return maze[x][y];
    }
    
}

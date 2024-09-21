

package graphalgorithms;

import java.util.Stack;

/**
 * Graph structure with traversal methods
 * @author timmermank1
 * @author Gavin Osborn
 */
public class AdjListGraph {
    public String s = "["; //used for DFS
    /**
     * Driver Method: Rather than have a whole driver class, we can place the
     * main method here to test our algorithms.
     *
     * @param args
     */
        public static void main(String[] args) {
        int[][] edgeList1 = {
            {0, 1},
            {0, 3},
            {1, 0},
            {1, 2},
            {2, 3},
            {3, 0},
            {3, 1},
            {3, 2}};
        AdjListGraph g1 = new AdjListGraph(4, edgeList1);
        System.out.println(g1 + "\n\n");
        
        System.out.println("DFT should be: [ 0 3 2 1 ] \n");
        System.out.println("       DFT is: " + g1.depthFirstTraversal() +"\n");
        System.out.println("BFT should be: [ 0 3 1 2 ] \n");
        System.out.println("       BFT is: " + g1.breadthFirstTraversal() + "\n");
        
        
        int[][] edgeList2 = {
            {0, 1},
            {0, 3},
            {1, 0},
            {1, 2},
            {2, 3},
            {3, 0},
            {3, 4},
            {4, 1},
            {4, 5},
            {5, 8},
            {5, 3},
            {5, 6},
            {6, 7},
            {7, 1},
            {7, 8},
            {7, 2},
            {8, 9},
            {8, 5},
            {8, 11},
            {9, 0},
            {9, 16},
            {9, 10},
            {10, 16},
            {10, 9},
            {11, 15},
            {10, 12},
            {12, 14},
            {13, 15},
            {13, 12},
            {14, 12},
            {15, 11},
            {15, 13},
            {16, 10}};
        AdjListGraph g2 = new AdjListGraph(17, edgeList2);
        System.out.println(g2 + "\n\n");
        
        System.out.println("DFT should be: [ 0 3 4 5 6 7 2 8 11 15 13 12 14 9 10 16 1 ]\n");
        System.out.println("       DFT is: " + g2.depthFirstTraversal() +"\n");
        System.out.println("BFT should be: [ 0 3 1 4 2 5 6 8 7 11 9 15 10 16 13 12 14 ] \n");
        System.out.println("       BFT is: " + g2.breadthFirstTraversal() + "\n");
        
    }
    /* 
    * The internal node class holding values within the adjacency list
    */
    private class Node {
        public int value;
        public Node next;
        public boolean marked = false;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    //Data Fields
    Node[] adjList;

    //Constructors
    /**
     * numVertex lists how many vertex there are. Value must be 0-26 inclusive.
     * edges is a e x 2 matrix where e is the number of edges. Each row in the
     * matrix represents an edge in the graph. A row of [0,3] would indicate an
     * edge from vertex 0 to vertex 3.
     *
     * @param numVertex
     * @param edges
     */
    public AdjListGraph(int numVertex, int[][] edges) {
        //populates adjacency list
        adjList = new Node[numVertex];
        for (int i = 0; i<numVertex; i++){
            adjList[i] = new Node(i, null);
        }
        //iterate through edges and add edge from edges[x][0] to edges[x][1]
        for (int x = 0; x<edges.length; x++){
            addEdge(edges[x][0], edges[x][1]);
        }
    }

    //Methods
    /**
     * Adds a directed edge to the graph.
     * @param vertexFrom
     * @param vertexTo 
     */
    public void addEdge(int vertexFrom, int vertexTo) {
        Node temp = new Node(vertexTo, null);
        temp.next = adjList[vertexFrom].next;
        adjList[vertexFrom].next = temp;
    }
    
    /**
     * sets all vertices data field marked to false.
     */
    private void unMarkAll(){
        for (int i = 0; i < adjList.length; i++) {
            adjList[i].marked = false;
        }
    }
    
    /**
     * Breadth First Traversal of a Graph, represented by String output
     * @return String
     */
    public String breadthFirstTraversal(){
        unMarkAll();
        MyQueue q = new MyQueue();
        String traversal = "[";
        q.add(0);
        adjList[0].marked = true;
        while (!q.isEmpty()){
            //add all unmarked children of top of queue (remove it),
            //dequeue and repeat until list is empty due to there not being any more unmarked children for any nodes
            
            Node e = null;
            try {
                e = adjList[q.remove()];
            }catch (Exception w){
                System.out.println("Error removing from queue");
            }
            traversal += " " + e.value;
            
            
            //add all unmarked children of e (top)
            e = e.next;
            while (e != null){ //goes through all connections
                if (!adjList[e.value].marked){
                    q.add(e.value);
                    adjList[e.value].marked = true;
                }
                e = e.next;
            }
        }
        return traversal + " ]";
    }
    
    /**
     * Helper method for depth first traversal - called recursively
     * @param e , node to process
     */
    public void dfsAid(Node e){
        
        adjList[e.value].marked = true;
        s += " " + e.value;
        Node z = e.next;
        
        while (z != null){ //goes through connections and finds suitable paths; falling out of the dfsAid() when no more paths available
                if (!adjList[z.value].marked){
                    dfsAid(adjList[z.value]);
                }
                z = z.next;
            }
    }
    
    /**
     * Recursive Depth First Traversal of a graph, represented by String output
     * @return String
     */
    public String depthFirstTraversal(){
        unMarkAll();
        Node e = adjList[0];
        dfsAid(e);
        return s + " ]";
        
    }
    
    /**
     * Returns a string representing vertex connections in the graph
     * @return 
     */
    public String toString(){
        String s = "";
        for (Node e : adjList){
            s += "[" + e.value + "] -> ";
            Node current = e.next;
            while (current != null){
                s += current.value + " -> ";
                current = current.next;
            }
            s += "\n";
        }
        return s;
    }
    
}

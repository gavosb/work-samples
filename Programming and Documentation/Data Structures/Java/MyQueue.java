
package graphalgorithms;

/**
 * Implementation of a circular queue of nodes holding integer values
 * @author timmermank1
 * @author Gavin Osborn
 */
public class MyQueue {
    
    /* 
    * The internal node class holding the values of each item in the list
    */
    private class Node {

        public int value;
        public Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }// end Node

    private Node tail;
    private int size;

    public MyQueue() {
        tail = null;
        size = 0;
    }
    
    /* 
    * Adds a new node to the bottom of the queue
    */
    public void add(int value) {

        if (tail == null) {
            tail = new Node(value, null);
            tail.next = tail;
        } else {
            Node tmp = new Node(value, tail.next);
            tail.next = tmp;
            tail = tmp;
        }
        size++;

    }
    /* 
    * Removes a node from the top of the queue, throws exception if unable to remove
    */
    public int remove() throws Exception {
        //int value = tail.next.value; // would this work even if size == 0?
        if (size == 0) {
            throw new Exception("Nothing to remove.");
        } else if (size == 1) {
            int value = tail.value;
            tail = null;
            size--;
            return value;
        } else {
            int value = tail.next.value;
            tail.next = tail.next.next;
            size--;
            return value;
        }

    }
    
    /* 
    * @return size of queue
    */
    public int size() {
        return size;
    }

    /* 
    * @ return boolean indicating whether queue is empty
    */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /* 
    * @return string of all node values in queue
    */
    public String toString(){
        String str = "";
        if (tail != null){
            Node e = tail.next;
            while (e != tail){
                str += e.value;
                e = e.next;
            }
        }
        return str;
    }
    
}

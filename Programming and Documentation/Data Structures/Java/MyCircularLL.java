/**
 * This class creates a circular linked list object.
 */
package sortingproject;

/**
 * @version 09/09/21
 * @author Gavin Osborn
 */
class MyCircularLL {

    private Node tail;
    
    /**
     * creates an empty list
     */
    public MyCircularLL(){
        tail = null;
    }
    
    /**
     * Adds value to the start of the list
     * O(1)
     * @param value 
     */
    public void add(int value) {
        Node new_node = new Node(value);
        
        if (tail==null){
            tail = new_node;
            tail.next = tail;
        }else{
            new_node.next = tail.next;
            tail.next = new_node;
        }
    }
    
      /**
     * Adds value to the end of the list
     * O(1)
     * @param value 
     */
    public void append(int value) {
        Node new_node = new Node(value);
        if (tail==null){
            tail = new_node;
            tail.next = tail;
        }else{
            new_node.next = tail.next;
            tail.next = new_node;
            tail = new_node;
        }
    }
    
    /**
     * removes the first occurrence of item from the list.
     * O(n)
     * @param item 
     */
    public void remove(int item){
        if (tail == null){
            return;
        }
        Node curr = tail.next, prev = null;
        
        if (curr != null && curr.value == item) { //if key is at front
            if (curr == tail){
                tail = null;
            }else{
                tail.next = curr.next;
            }
            System.out.println(item + " found and deleted");
            return;
        }

        while (curr != tail && curr.value != item) {
            prev = curr;
            curr = curr.next;
        }
        if (curr != tail) {
            if (curr == tail){
                prev.next = tail.next;
                tail = prev;
            }
            prev.next = curr.next;
            System.out.println(item + " found and deleted");
        } else {
            System.out.println(item + " not found");
        }
        
    }

    /**
     * Removes the first value in the list
     * O(1)
     */
    public void remove(){
        if (tail == null){
            System.out.println("List is empty");
            return;
        }
        System.out.println("Removed first item with value of " + tail.next.value);
        tail.next = tail.next.next;
        if (tail.next == tail){
            tail = null;
        }
        
        
    }
    
    /**
     * Unused but just leaving it in anyway
     * O(1)
     * @return 
     */
    public Node pop(){
        
        if (tail == null){
            System.out.println("List is empty");
            return null;
        }
        System.out.println("Popped first item with value of " + tail.next.value);
        
        Node r = new Node(tail.next.value);
        tail.next = tail.next.next;
        if (tail.next == tail){
            tail = null;
        }
        return r;
        
    }
    
    /**
     * Empties the list
     * O(1)
     */
    public void empty(){
        //This should work because we will no longer have an accessible reference and the garbage collector will take it from there - right?
        //if not, I suppose a de-linking loop would be needed
        tail = null;
    }
    
    /**
     * Returns the location of the first occurrence of the value in the list.
     * Returns 0 if it is the first item. return -1 if it isn't in the list
     * O(n)
     * @param value 
     */
    public int indexOf(int value){
        if (tail == null){
            System.out.println("List is empty");
            return -1;
        }
        Node curr = tail.next;
        int index = -1;
        
        while (curr != tail && curr.value != value) {
            curr = curr.next;
            index++;
        }
        if (curr.value != value){
            return -1;
        }
        if (tail.value == value){
            index++;
        }else{
            index++;
        }
        return index;
    }
    /**
     * Runs insert to find proper location in list
     * Worst case of O(n)
     * @param node 
     */
    public void Insert(Node node){
        
        Node curr = tail.next, prev = tail;
        
        while (curr != tail && curr.value < node.value){
            prev = curr;
            curr = curr.next;
        }
        
        prev.next = node;
        node.next = curr;
    }
    
    /**
     * Runs in a worst case of O(n^2) and a best case of O(N) if Insert never required
     * Runs insertion sort on the list
     */
    public void sortOne(){
        
        Node curr = tail.next, prev=curr;
        
        while (curr != tail){
            if (curr.value < prev.value){
                prev.next = curr.next;
                Insert(curr);
		curr = prev.next; //starts next iteration where it left off
            }else{
            
            prev = curr;
            curr = curr.next;
	   }
        }
        
        if (prev.value > tail.value){
            Node n = new Node(tail.value);
            prev.next = tail.next;
            tail = prev;
            
            Insert(n);
        }
        
    }
    
    
    
    /**
     * This is the method called when a MyLinkList is passed to System.out
     * It determines what is printed
     * @return 
     */
    @Override
    public String toString(){
        if(tail == null){
            return "[]";
        }
        String toPrint = "[ ";
        Node cur = tail.next;
        while(cur != tail){
            toPrint += cur.value + " ";
            cur = cur.next;
        }
        toPrint += cur.value + "]";
        return toPrint;
    }
    
    /**
     * This is a Node class to be used in your linked list.
     */
    private static class Node {
        public Node next;
        public int value;
        public Node(int value) {
            this.value = value;
            this.next = null;
        }
        public Node(int value, Node next){
            this.next = next;
            this.value = value;
        }
    }
    
}

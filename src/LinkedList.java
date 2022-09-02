public class LinkedList {

    Node head;// head of list
    int count=0;//number of elements in the linked list

    // Linked list Node.
    // This inner class is made static
    // so that main() can access it
    static class Node {

        int data;
        Node next;

        // Constructor
        Node(int d) {
            data = d;
            next = null;
        }
    }

    // Method to insert a new node
    public void insert(int data) {
        // Create a new node with given data
        Node new_node = new Node(data);


        // If the Linked List is empty,
        // then make the new node as head
        if (this.head == null) {
            this.head = new_node;
        }
        else{
            // Else traverse till the last node
            // and insert the new_node there
            Node last = this.head;
            while (last.next != null) {
                last = last.next;
            }

            // Insert the new_node at last node
            last.next = new_node;
        }
        count++;
    }
    public void removeMed(){
        if(head==null)
            return;
        Node tmp = this.head;
        int position = this.count/2;
        for (int i = 0; i < position - 1; i++)
            tmp = tmp.next;
        tmp.next = tmp.next.next;
        this.count--;
    }
    public int[] toArray(){
        int[] A = new int[this.count];
        if (this.head == null) {return null;}
        Node last = this.head;
        for(int i=0;i<count;i++){
            A[i] = last.data;
            last = last.next;
        }
        return A;
    }

}
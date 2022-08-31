public class LinkedQueue {

    private static class Node {
        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    int count;
    private Node front;
    private Node rear;

    public LinkedQueue() {
        front = rear = null;
        count = 0;
    }

    public void enqueue(int value) {
        if (rear != null) {
            rear.next = new Node(value, null);
            rear = rear.next;
        } else {
            rear = new Node(value, null);
            front = rear;
        }
        count++;
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue - Queue is empty");
        }

        int value = front.value;
        front = front.next;
        count--;

        if (front == null) {
            rear = null;
        }

        return value;
    }
    public boolean isEmpty() {
        return front == null;
    }
}
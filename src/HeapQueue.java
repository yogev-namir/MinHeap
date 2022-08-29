public class HeapQueue {
    private LinkedList<HeapNode> linkedList;
    private LinkedList<HeapNode> tail;
    protected int elements;

    public HeapQueue()
    {
        this.linkedList = null;
        this.tail= null;
        this.elements=0;

    }

    public void enqueue(HeapNode x)
    {
        LinkedList<HeapNode> node= new LinkedList<>(x);
        elements++;
        if(this.tail != null)
        {
            this.tail.setNext(node);
            node.setPrev(this.tail);
            this.tail = node;
        }
        else
        {
            this.linkedList= node;
            this.tail= node;
        }
    }

    public HeapNode dequeue()
    {
        HeapNode node;
        if(this.linkedList == null)
        {
            return null;
        }
        node = this.linkedList.getHead().getValue();
        elements--;
        this.linkedList = this.linkedList.getNext();
        if(this.linkedList == null)
        {
            this.tail =null;
        }
        else
        {
            this.linkedList.setPrev(null);
        }
        return node;
    }


}
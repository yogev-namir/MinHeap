public class HeapNode {
    int key,index;
    HeapNode parent, left, right;

    public HeapNode(int key,int index, HeapNode left, HeapNode right
                    ,HeapNode parent){
        this.key=key;
        this.index=index;
        this.left=left;
        this.right=right;
        this.parent=parent;
    }
    public HeapNode(int key,int index){
        this(key, index, null, null,null);
    }
    public HeapNode(int key,int index, HeapNode parent){
        this(key, index, null, null,parent);
    }

}

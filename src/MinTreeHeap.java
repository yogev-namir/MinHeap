import java.io.DataOutputStream;
import java.io.IOException;


public class MinTreeHeap {
    HeapNode root;
    int heapSize;

    public MinTreeHeap(HeapNode root, int heapSize) {
        this.root = root;
        this.heapSize = heapSize;
    }

    public static void BuildHeap(int[] A){
        for(int i= A.length-1;i>=0;i--)
            Heapify(A,i);
    }

    public static MinTreeHeap BuildHeapT(int[] A) {
        if(A==null){
            return new MinTreeHeap(null,0);
        }
        BuildHeap(A);
        HeapNode[] B = new HeapNode[A.length+1];
        B[0] = null;
        for (int i = 0; i < A.length; i++) {
            HeapNode x = new HeapNode(A[i], i);
            B[i+1] = x;
        }
        for (int i = 1; i < B.length; i++) {
            B[i].parent = B[i /2];
            if(2*i <= A.length)
                B[i].left = B[2*i];
            if((2*i)+1 <= A.length)
                B[i].right = B[(2*i)+1];
        }
        return new MinTreeHeap(B[1],A.length);
    }
    public void HeapInsert(int k) {
        this.heapSize++;
        int[] path = findPath();
        HeapNode child, parent = this.root;

        if(parent==null){
            this.root = new HeapNode(k, this.heapSize);
            return;
        }
        for(int i=0; i<path.length-1; i++)
            parent = (path[i]==0) ? parent.left : parent.right;
        if(parent.left==null) {
            parent.left = new HeapNode(k, this.heapSize, parent);
            child = parent.left;
        }
        else{
            parent.right = new HeapNode(k, this.heapSize, parent);
            child = parent.right;
        }
        while(parent!=null && child.key < parent.key) {
            swapT(child,parent);
            child = parent;
            parent = parent.parent;
        }
    }

    /**
     * 0 is left - 1 is right
     * @return min element in the heap(by key)
     */
    public int HeapExtractMin(){
        if(this.root==null)
            return Integer.MIN_VALUE;
        int min = this.root.key;
        int[] path = findPath();

        this.heapSize--;
        HeapNode tmp = this.root;
        for(int i=0; i<path.length; i++)
            tmp = (path[i]==0) ? tmp.left : tmp.right;
        this.root.key = tmp.key;
        if(tmp.parent.left==tmp)
            tmp.parent.left=null;
        else
            tmp.parent.right=null;
        HeapifyT(this.root);
        return min;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        if(this.root==null){
            out.writeBytes("");
            return;
        }
        int key, height = log2(this.heapSize)+1;
        LinkedQueue q= new LinkedQueue();
        for(int i=1; i<=height;i++){
            printLayer(this.root,i, q);
            while(!q.isEmpty()) {
                key = q.dequeue();
                if(q.count>0)
                    out.writeBytes("" + key + ",");
                else
                    out.writeBytes("" + key);
            }
            out.writeBytes(System.lineSeparator());
        }
    }
    public void printLayer(HeapNode node, int layer, LinkedQueue q){
        if(node == null)
            return;
        else if(layer==1) {
            q.enqueue(node.key);
        }
        else {
            printLayer(node.left, layer - 1, q);
            printLayer(node.right, layer - 1, q);
        }

    }

    public void HeapifyT(HeapNode node){
        if(node == null || (node.left == null && node.right == null))
            return;
        HeapNode smallest= node;
        if(node.left != null && node.left.key < node.key)
            smallest=node.left;
        if(node.right != null && node.right.key < smallest.key)
            smallest=node.right;
        if(smallest!=node){
            swapT(node,smallest);
            HeapifyT(smallest);
        }

    }
    public static void Heapify(int[] A, int i){
        int left=(2*i)+1;
        int right=(2*i)+2;
        int heapSize=A.length, smallest;
        if(left<=heapSize-1 && A[left]<A[i])
            smallest=left;
        else
            smallest=i;
        if(right<=heapSize-1 && A[right]<A[smallest])
            smallest=right;
        if(smallest!=i){
            swap(A,i,smallest);
            Heapify(A,smallest);
        }
    }
    public static void swap(int[] A, int i, int j){
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }
    public static void swapT(HeapNode x, HeapNode y){
        int tmp = x.key;
        x.key = y.key;
        y.key = tmp;
    }
    public int[] findPath(){
        //assume that heapSize was already incremented by one
        int[] path = new int[log2(this.heapSize)];
        for(int i=path.length-1, tmp = this.heapSize; i>=0; i--) {
            path[i] = (tmp % 2 == 0) ? 0 : 1;//0 is left - 1 is right
            tmp = (int) tmp / 2;
        }
        return path;
    }
    public static int log2(int N)
    {

        // calculate log2 N indirectly
        // using log() method

        return (int)(Math.log(N) / Math.log(2));
    }
}

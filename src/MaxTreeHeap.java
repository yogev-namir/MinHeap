import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Math.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.TreeMap;


public class MaxTreeHeap {
    HeapNode root;
    int heapSize;

    public MaxTreeHeap(HeapNode root, int heapSize) {
        this.root = root;
        this.heapSize = heapSize;
    }

    public static void BuildHeap(int[] A){
        for(int i= A.length-1;i>=0;i--)
            Heapify(A,i);
    }

    public static MaxTreeHeap BuildHeapT(int[] A) {
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
        return new MaxTreeHeap(B[1],A.length);
    }
    public void HeapInsert(int k){
        this.heapSize++;
        int[] path = findPath();
        HeapNode child, parent = this.root;


        if(parent==null) {
            this.root = new HeapNode(k, this.heapSize);
            return;
        }
        for(int i=0; i<path.length-1; i++)
            parent = (path[i]==0) ? parent.left : parent.right;
        if(this.heapSize%2 == 0) {
            parent.left = new HeapNode(k, this.heapSize, parent);
            child = parent.left;
        }
        else{
            parent.right = new HeapNode(k, this.heapSize, parent);
            child = parent.right;
        }

        while(parent!=null && child.key > parent.key) {
            swapT(child,parent);
            child = parent;
            parent = parent.parent;
        }
    }

    /**
     * 0 is left - 1 is right
     * @return min element in the heap(by key)
     */
    public int HeapExtractMax(){
        int max = this.root.key;
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
        return max;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        int height = log2(this.heapSize)+1;
        HeapQueue q= new HeapQueue();
        for(int i=1; i<=height;i++){
            printLayer(this.root,i, q);
            HeapNode node= q.dequeue();
            while(node != null) {
                if(q.elements>0){
                    out.writeBytes("" + node.key + ",");
                    node= q.dequeue();
                }
                else{
                    out.writeBytes("" + node.key);
                    node= q.dequeue();
                }
            }
            out.writeBytes(System.lineSeparator());
        }
    }
    public void printLayer(HeapNode node, int layer,
                           HeapQueue q){
        if(node == null)
            return;
        else if(layer==1) {
            q.enqueue(node);
        }
        else {
            printLayer(node.left, layer - 1, q);
            printLayer(node.right, layer - 1, q);
        }

    }
    public void HeapifyT(HeapNode node){
        if(node == null || (node.left == null && node.right == null))
            return;
        HeapNode largest= node;
        if(node.left != null && node.left.key > node.key)
            largest=node.left;
        if(node.right != null && node.right.key > largest.key)
            largest=node.right;
        if(largest!=node){
            swapT(node,largest);
            HeapifyT(largest);
        }

    }
    public static void Heapify(int[] A, int i){
        int left=(2*i)+1;
        int right=(2*i)+2;
        int heapSize=A.length, largest=i;
        if(left<=heapSize-1 && A[left]>A[i])
            largest=left;
        if(right<=heapSize-1 && A[right]>A[largest])
            largest=right;
        if(largest!=i){
            swap(A,i,largest);
            Heapify(A,largest);
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

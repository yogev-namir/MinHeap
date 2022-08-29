import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Math.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.TreeMap;


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
    public void HeapInsert(int k){
        int[] path = findPath();
        this.heapSize++;
        HeapNode tmp = this.root;
        for(int i=0; i<path.length-1; i++)
            tmp = (path[i]==0) ? tmp.left : tmp.right;
        if(this.heapSize%2 == 0)
            tmp.left = new HeapNode(k, this.heapSize);
        else
            tmp.right = new HeapNode(k, this.heapSize);
    }

    /**
     * 0 is left - 1 is right
     * @return min element in the heap(by key)
     */
    public int HeapExtractMin(){
        int min = this.root.key;
        this.heapSize--;
        int[] path = findPath();
        HeapNode tmp = this.root;
        for(int i=0; i<path.length; i++)
            tmp = (path[i]==0) ? tmp.left : tmp.right;
        this.root.key = tmp.key;
        HeapifyT(this.root);
        return min;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        int height = log2(this.heapSize);
        Queue q = new Queue();
        for(int i=1; i<=height;i++){
            printLayer(out,this.root,i,0);

            out.writeBytes(System.lineSeparator());
        }
    }
    public void printLayer(DataOutputStream out, HeapNode node, int layer, Queue q) throws IOException {
        if(node == null)
            return;
        else if(layer==1) {
            if(lastNode==0)
                out.writeBytes(""+node.key+",");
            else
                out.writeBytes(""+node.key);
        }
        else
            printLayer(out, node.left,layer-1,0);
            if(layer-1==1)
                printLayer(out, node.right,layer-1,1);
            else
                printLayer(out, node.right,layer-1,0);

    }
    public void HeapifyT(HeapNode node){
        if(node == null || (node.left == null && node.right == null))
            return;
        if(node.left != null && node.left.key < node.key){
            if(node.right != null && (node.left.key < node.right.key)){
                swapT(node, node.left);
                HeapifyT(node.left);
            }
            else{
                swapT(node, node.right);
                HeapifyT(node.right);
            }
        }
        else{
            if(node.right != null && (node.right.key < node.key)){
                swapT(node, node.right);
                HeapifyT(node.right);
            }
        }
    }
    public static void Heapify(int[] A, int i){
        int left=(2*i)+1;
        int right=(2*i)+2;
        int heapSize=A.length, smallest;
        if(left<=heapSize-1 && A[left]<A[i])
            smallest=left;
        else smallest=i;
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
        int[] path = new int[(int)log2(this.heapSize)];
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

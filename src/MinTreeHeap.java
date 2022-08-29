import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Math.*;
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
            B[i].parent = B[Math.floorDiv(i,2)];
            if(2*i <= A.length)
                B[i].left = B[2*i];
            if((2*i)+1 <= A.length)
                B[i].right = B[(2*i)+1];
        }
        return new MinTreeHeap(B[1],A.length);
    }
    public void HeapInsert(int k){

    }

    public int HeapExtractMin(){

        return 0;
    }

     public void printByLayer(DataOutputStream out){

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
     public static void swap(int[] A,int i,int smallest){
        int tmp=A[i];
        A[i]=A[smallest];
        A[smallest]=tmp;
     }
     public int[] findPath(){
        //assume that heapSize was already incremented by one
        int[] path = new int[(int)Math.log(this.heapSize)];
        for(int i=path.length-1, tmp = this.heapSize; i>=0; i--) {
            path[i] = (tmp % 2 == 0) ? 0 : 1;//1 is left - 0 is right
            tmp = (int) tmp / 2;
        }
        return path;
     }

}

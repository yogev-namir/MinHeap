public class MedianDS {
    LinkedList list = null;
    MaxTreeHeap maxTreeHeap;
    MinTreeHeap minTreeHeap;

    public MedianDS(int[] A){
        int median = select(A,A.length/2);
        /*
        int[] min, max = new int[A.length/2];
        if(A.length%2==0)
            min = new int[A.length/2];
        else min = new int[(A.length/2)+1];
        copyArray(A,max,0, max.length);
        copyArray(A,min,max.length, A.length);
         */
        LinkedList max= new LinkedList(),min= new LinkedList();
        for(int i=0;i<A.length;i++){
            if (A[i] <= median) {
                max.insert(A[i]);
            } else {
                min.insert(A[i]);
            }
        }
        maxTreeHeap = MaxTreeHeap.BuildHeapT(max.toArray());
        minTreeHeap = MinTreeHeap.BuildHeapT(min.toArray());
    }
    public void insert(int x){
        if(x<findMedian())
            maxTreeHeap.HeapInsert(x);
        else
            minTreeHeap.HeapInsert(x);
        if(minTreeHeap.heapSize == maxTreeHeap.heapSize+1){
            int tmp = minTreeHeap.HeapExtractMin();
            maxTreeHeap.HeapInsert(tmp);
        }
        else if(minTreeHeap.heapSize == maxTreeHeap.heapSize-2){
            int tmp = maxTreeHeap.HeapExtractMax();
            minTreeHeap.HeapInsert(tmp);
        }
    }

    public void delMedian(){
        maxTreeHeap.HeapExtractMax();
        if(minTreeHeap.heapSize==maxTreeHeap.heapSize+1){
            int median = minTreeHeap.HeapExtractMin();
            maxTreeHeap.HeapInsert(median);
        }
    }
    public int findMedian(){
        return maxTreeHeap.root.key;
    }
    public int partition(int[] A,int p, int r, int x){
        int n = r - p;
        int[] B = new int[n];
        int left = 0, right = n;

        for(int i=0; i<n; i++){
            if(A[i+p]>x){
                B[right-1] = A[i+p];
                right--;
            } else if (A[i+p]<x) {
                B[left] = A[i+p];
                left++;
            }
        }
        int q = left;
        B[q]=x;
        for (int i=0;i<n;i++){
            A[i+p]=B[i];
        }
        return p+q;
    }
    public int select(int[] A, int i){
        int x = recursiveSelect(A);
        int q = partition(A,0, A.length, x);
        if(i==q)
            return x;
        else if (i<q) {
            int[] B = new int[q];
            copyArray(A,B,0,q);
            select(B,i);
        }
        else {
            int[] B = new int[A.length-q-1];
            copyArray(A,B,q+1, A.length);
            select(B,i-q-1);
        }

        return 0;
    }
    public int recursiveSelect(int[] A){
        if(A.length==1)
            return A[0];
        LinkedList[] sets = divideTo5(A);
        int[] setsMedians = new int[sets.length];
        MergeSort merge = new MergeSort();
        for(int j=0; j<sets.length;j++) {
            int[] tmp = sets[j].toArray();
            setsMedians[j] = merge.median(tmp);
        }
        return recursiveSelect(setsMedians);
    }
    public void copyArray(int[] A,int[] B,int l,int r){
        for(int i=l; i<r;i++)
            B[i]=A[i];
    }
    public LinkedList[] divideTo5(int[] A){
        LinkedList[] sets;
        int fullSets = A.length/5;

        if(A.length%5==0)
            sets = new LinkedList[A.length/5];
        else
            sets = new LinkedList[(A.length/5)+1];

        for(int i=0; i< sets.length; i++)
            sets[i]=new LinkedList();
        for(int i=0; i<A.length/5; i++){
            for(int j=0; j<5 ;j++){
                sets[i].insert(A[(i*5)+j]);
            }
        }
        int residuals = A.length - (fullSets*5);
        if(residuals>0){
            for(int k=0; k<residuals; k++){
                sets[A.length/5].insert(A[(fullSets*5)+k]);
            }
        }
        return sets;
    }
}

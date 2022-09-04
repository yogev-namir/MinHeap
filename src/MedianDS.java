public class MedianDS {
    LinkedList list = null;
    MaxTreeHeap maxTreeHeap;
    MinTreeHeap minTreeHeap;
    public MedianDS(int[] A){
        int median = select(A,A.length/2);
        int[] min, max = new int[A.length/2];
        if(A.length%2==0)
            min = new int[A.length/2];
        else min = new int[(A.length/2)+1];
        copyArray(A,max,0, max.length);
        copyArray(A,min,max.length, A.length);
        maxTreeHeap = MaxTreeHeap.BuildHeapT(max);
        minTreeHeap = MinTreeHeap.BuildHeapT(min);
    }
    public void insert(int x){

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
        return p+q-1;
    }
    public int select(int[] A, int i){
        if(A.length==1)
            return A[0];
        LinkedList[] sets = divideTo5(A);
        int[] setsMedians = new int[sets.length];
        MergeSort merge = new MergeSort();
        for(int j=0; j<sets.length;j++) {
            int[] tmp = sets[j].toArray();
            setsMedians[j] = merge.median(tmp);
        }
        int x = select(setsMedians,i);
        int q = partition(A,0, A.length-1, x);
        if(i==q) return x;
        else if (i<q) {
            int[] B = new int[q-1];
            copyArray(A,B,0,q-1);
            select(B,i);
        }
        else {
            int[] B = new int[q-1];
            copyArray(A,B,q+1, A.length);
            select(B,i-q);
        }

        return 0;
    }
    public void copyArray(int[] A,int[] B,int l,int r){
        for(int i=l; i<r;i++)
            B[i]=A[i];
    }
    /*
    public int selectGivenMedian(int[] A, int i, MergeSort merge){
        int n = A.length;
        if(n==1)
            return A[0];
        int x = merge.median(A);
        int q = partition(A,0,n,x);

        if(i==q)
            return A[q];
        int[] B;
        if(i<q) {
            B = new int[q];
            for(int j=0; j<q; j++)
                B[j]=A[j];
            return selectGivenMedian(B,i,merge);
        }
        else {
            B = new int[n - q];
            for(int j=q; j<n; j++)
                B[j]=A[j];
            return selectGivenMedian(B,i-q,merge);
        }
    }

     */
    public LinkedList[] divideTo5(int[] A){
        LinkedList[] sets;

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
        int residuals = A.length - (sets.length*5);
        if(residuals>0){//there are residuals
            for(int k=0; k<residuals; k++){
                sets[A.length/5].insert(A[(sets.length*5)+k]);
            }
        }
        return sets;
    }
    /*
    public int[][] divideTo5(int[] A){
        int lastCopied=0;//the last element that was handled(to check for any residuals)
        int[][] sets;
        if(A.length%5==0)
            sets = new int[A.length/5][5];
        else
            sets = new int[(A.length/5)+1][5];
        for(int i=5; i<=A.length; i+=5){
            for(int j=i-5; j<i ;j++){
                sets[(i/5)-1][j%5] = A[((i/5)-1)*5 + (j%5)];
                lastCopied = ((i/5)-1)*5 + (j%5);
            }
        }
        int residuals = A.length - lastCopied - 1;
        if(lastCopied+1 < A.length){//there are residuals
            for(int k=0; k<residuals; k++){
                sets[list.count/5][k] = A[lastCopied + k + 1];
            }
        }
        return sets;
    }

     */

}

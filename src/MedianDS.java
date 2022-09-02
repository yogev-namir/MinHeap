public class MedianDS {
    LinkedList list = null;
    public MedianDS(int[] A){
        LinkedList[] sets = divideTo5(A);
        int[] setsMedians = new int[sets.length];
        for(int i=0; i< sets.length; i++){
            int[] tmp = sets[i].toArray();
            MergeSort merge = new MergeSort();
            merge.sort(tmp,0,tmp.length-1);
            setsMedians[i] = tmp[tmp.length/2];
        }
        //MaxTreeHeap maxHeap = new MaxTreeHeap();
        //MinTreeHeap minHeap = new MinTreeHeap();
    }
    public void insert(int x){

    }
    public void delMedian(){

    }
    public int findMedian(){

        return 0;
    }

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

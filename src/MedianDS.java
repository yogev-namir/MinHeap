public class MedianDS {
    LinkedList list = null;
    public MedianDS(int[] A){
        this.list = new LinkedList();
        for(int i=0; i<A.length; i++){
            list.insert(A[i]);
        }
    }
    public void insert(int x){

    }
    public void delMedian(){

    }
    public int findMedian(){

        return 0;
    }

    public int[][] divideTo5(){
        int lastCopied=0;//the last element that was handled(to check for any residuals)
        int[] A = this.list.toArray();
        int[][] sets;
        if(list.count%5==0)
            sets = new int[list.count/5][];
        else
            sets = new int[(list.count/5)+1][];
        for(int i=5; i<=A.length; i+=5){
            for(int j=i-5; j<i ;j++){
                sets[i/5][j%5] = A[((i/5)-1)*5 + (j%5)];
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

}

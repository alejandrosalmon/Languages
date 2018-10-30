public class Addmat implements  Runnable{
    int [][] mat1;  int [][] mat2;  int [][] mat3;
    int begin; int end; int size;

    public Addmat(int[][] m1, int[][] m2, int[][] m3, int b, int e,int s){
        //added variable size, because the parameters now being passed as end are different for add1 
        mat1 = m1;  mat2 = m2;  mat3 = m3;  begin = b;  end = e; size = s;
    }

    @Override
    public void run() {
        //we process size/2 number of rows from beggining(which could be mimddle) to end (wchich could also be middle)
        for(int i = begin; i< end; i++){
            for(int j = 0; j< size; j++){
                mat3[i][j] = mat3[i][j]+ mat1[i][j]+mat2[i][j];
            }
        }
    }
}
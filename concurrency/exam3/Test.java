public class Test {
    public static void main(String args[]) throws InterruptedException{
        // filling the matrix to test the code
        int start = 0 , end = 2;
        int[][]  matA= new int[end][end];
        int[][]  matB= new int[end][end];
        int[][]  matResult= new int[end][end];

        for(int i = 0; i< end; i++){
            for(int j = 0; j< end; j++){
                matA[i][j]= 1; matB[i][j]= 1;  matResult[i][j] = 0;
            }           
        }
        // filling the matrix to test the code
        // I modified de constructor accordingly
        // add 1 proccesses the matrixes from the beggining to the middle of it  (i.e.  4 rows processes rows 0, 1)
        Addmat add1 = new Addmat(matA, matB, matResult, 0, end/2, end);
        // add 2 proccesses the matrixes from the middle to the end of it  (i.e.  4 rows processes rows 2, 3)
        Addmat add2 = new Addmat(matA, matB, matResult, end/2, end, end);

        Thread t1 = new Thread(add1);
        Thread t2 = new Thread(add2);   

        t1.start();
        t2.start();

        // we wait for both threads to finish
        t1.join();
        t2.join();

        for(int i = 0; i< end; i++){
            for(int j = 0; j< end; j++){
                System.out.print(" " + matResult[i][j]);
            }           
            System.out.print("\n");
        }
    }
}
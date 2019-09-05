#include <stdio.h>
#define ThreadsPerBlock 10

__device__ void convolution(int conv_col, int conv_row, float *d_kernel, int k_size, float *d_matrix, int size_x, int size_y, float *d_conv, int max_row, int max_col){
    int conv_index = conv_col+ conv_row*max_col;
    d_conv[conv_index] = 0;
    for(int k_row = 0;  k_row < k_size; k_row ++){
            for(int k_col = 0;  k_col < k_size ; k_col ++){
                d_conv[conv_index] += 
                d_kernel[k_col + (k_row*k_size)] *
                d_matrix[(conv_col+k_col) + (conv_row+k_row)*size_x];
            }
        }
}

__global__ void convolute(float *d_kernel, int k_size, float *d_matrix, int size_x, int size_y, float *d_conv, int max_row, int max_col){
    int col = threadIdx.x + blockIdx.x * blockDim.x;
    int row = threadIdx.y + blockIdx.y * blockDim.y;
    //flattens matrix to be able to process it properly
    if(max_row > row && max_col > col){
        convolution(col, row, d_kernel, k_size, d_matrix, size_x, size_y, d_conv, max_row, max_col);
    }
}

void print_mat(float *mat, int n){
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            printf("%.1f\t", mat[i*n+j]);
        }
        printf("\n");
    }
    printf("\n");
}


void fill_mat(float *mat, int n){
    int c = 0;
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            //mat[i*n+j] = c++%10;
	    mat[i*n+j] = 2;
        }
    }
}

void fill_ker(float *mat){
    int size = 3;
    for (int i = 0; i < size; i++){
        for (int j = 0; j < size; j++){
            if(i==1 && j == 1){
                mat[i*size+j] = 8;
            } else {
        	   mat[i*size+j] = -1;
            }
        }
    }    
}

int main(){
    float *h_kernel, *h_matrix, *h_conv;
    float *d_kernel, *d_matrix, *d_conv;

    int k_size = 3;
    int size_x, size_y;

    printf("Please enter the size of the square matrix to convolute over: \n");
    scanf("%d", &size_x);

    size_y = size_x;


    int max_row = size_x - (k_size/2)*2;
    int max_col = size_y - (k_size/2)*2;

    int numBlocks = (ThreadsPerBlock + (size_x*size_y-1))/ThreadsPerBlock;

    h_kernel = (float *)malloc(sizeof(float)*k_size*k_size);
    h_matrix = (float *)malloc(sizeof(float)*size_x*size_y);
    h_conv = (float *)malloc(sizeof(float)*max_row*max_col);

    fill_ker(h_kernel);
    fill_mat(h_matrix, size_x);

    printf("\n\n----------- Kernel to apply: \n");
    print_mat(h_kernel, k_size);
    printf("\n\n----------- Original Matrix to convolute: \n");
    print_mat(h_matrix, size_x);

   
    cudaMalloc((void**)&d_kernel,sizeof(float)*k_size*k_size);
    cudaMalloc((void**)&d_matrix,sizeof(float)*size_x*size_y);
    cudaMalloc((void**)&d_conv,sizeof(float)*max_row*max_col);

    cudaMemcpy(d_kernel, h_kernel,sizeof(float)*k_size*k_size, cudaMemcpyHostToDevice);
    cudaMemcpy(d_matrix, h_matrix,sizeof(float)*size_x*size_y, cudaMemcpyHostToDevice);

    dim3 Blocks(numBlocks,numBlocks);
    dim3 Threads(ThreadsPerBlock,ThreadsPerBlock);
   
    //printf("Blocks %i \nThreads %i \n", numBlocks, ThreadsPerBlock);
    convolute<<<Blocks, Threads>>>(d_kernel, k_size, d_matrix, size_x, size_y, d_conv, max_row, max_col);
 
    cudaMemcpy(h_conv, d_conv,sizeof(float)*max_row*max_col, cudaMemcpyDeviceToHost);

    printf("\n\n------------ Resulting Matrix: \n");
    print_mat(h_conv, max_col);
   
    free(h_kernel);
    free(h_conv);
    free(h_matrix);
   
    cudaFree(d_kernel);
    cudaFree(d_conv);
    cudaFree(d_matrix);
}

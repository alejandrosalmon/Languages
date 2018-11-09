#include "../cuda_runtime.h"
#include "../handle_error.h"
#include <time.h>

#define N 			8
#define THREADS_PER_BLOCK	2



__global__ void add(int *a, int *b, int *c, int max){
	int index = threadIdx.x + blockIdx.x*blockDim.x;
	int id = index;
	while (id < max){
		c[id] = a[id] + b[id];
		// for the problem larger than the number of threads incrase
		// the index is increased by the total number of threads
		id = id + blockDim.x*gridDim.x;
	}
}

void fill_mat(int *mat){
	int c = 0;
	for (int i = 0; i < N; i++){
		for(int j = 0; j<N; j++){
			mat[j + i*N] = c++;
		}
	}
}

void print_mat(int *mat){
	for (int i = 0; i<N; i++){
		for(int j =0; j<N; j++){
			printf("%i\t", mat[j + i*N]);
		}
		printf("\n");
	}
}

int main(){
	int *a, *b, *c;
	int *d_a, *d_b, *d_c;
	int tam = N*N*sizeof(int);

	HANDLE_ERROR(cudaMalloc((void**)&d_a,tam));
	HANDLE_ERROR(cudaMalloc((void**)&d_b,tam));
	HANDLE_ERROR(cudaMalloc((void**)&d_c,tam));

	a = (int*)malloc(tam);
	b = (int*)malloc(tam);
	c = (int*)malloc(tam);

	fill_mat(a);
	fill_mat(b);

	print_mat(a);
	print_mat(b);
	printf("\n");


	cudaMemcpy(d_a,a,tam,cudaMemcpyHostToDevice);
	cudaMemcpy(d_b,b,tam,cudaMemcpyHostToDevice);

	add <<<N*N/THREADS_PER_BLOCK, THREADS_PER_BLOCK>>>(d_a, d_b, d_c, N*N);

	cudaMemcpy(c,d_c,tam,cudaMemcpyDeviceToHost);

	print_mat(c);


	free(a);
	free(b);
	free(c);
	
	cudaFree(d_a);
	cudaFree(d_b);
	cudaFree(d_c);
	return 0;
}





	

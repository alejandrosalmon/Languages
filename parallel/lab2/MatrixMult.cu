// Alejandro Salmon Felix Diaz
// A01201954

//incluir path a propio cuda_runtime
#include "../cuda_runtime.h"
#include "handle_error.h"
#include <stdio.h>
#include <stdlib.h>

#define BLOCK_SIZE 	16

__global__ void mat_mult(float* dmat_res, float* dmat_a, float* dmat_b, int a_row, int a_col, int b_col){
	
	int col = threadIdx.x + blockIdx.x * blockDim.x;
	int row = threadIdx.y + blockIdx.y * blockDim.y;

	if(col<b_col && row<a_row){
	float result = 0;
		for(int i=0; i<a_col; i++){
			result += dmat_a[row*a_col+i] * dmat_b[i*b_col+col];
		}
		dmat_res[row*b_col+col] = result;
	}
}

//hur dur
void print_mat(float* mat, int row, int col){
    for (int i = 0; i < row; i++){
        for (int j = 0; j < col; j++){
            printf("%.1f\t", mat[i*col+j]);
        }
        printf("\n");
    }
    printf("\n");
}

//llena matrices igual que en los casos de prueba del lab
void fill_matab(float* mata, int rowa, int cola, float* matb, int rowb, int colb){
	int c = 1;
	for(int i = 0; i<rowa; i++){
		for(int j = 0; j<cola; j++){
			mata[i*cola+j] = c++%10;
		}
	}
	c--;
	for(int i = 0; i<cola; i++){
		for(int j = 0; j<colb; j++){
			matb[i*colb+j] = c++%10;
		}
	}
}


int main(int argc, char* argv[]){
	float *hmat_a, *hmat_b, *hmat_res;
	float *dmat_a, *dmat_b, *dmat_res;

	if (argc != 5) {
		printf("usage: %s [MatrixA Rows] [MatrixA Columns] [MatrixB Rows] [MatrixB Columns]\n", argv[0]);
		return -1;
	}


	//realmente no se utiliza
	int a_row = atoi(argv[1]);
	int a_col = atoi(argv[2]);
	/*
		realmente nunca se usa b_row pero memoria de sobra thats why 
		DISCLAIMER:
		(si el uso innecesario de memora molesta al lector favor de comentar la linea siguiente)
	*/
	int b_row = atoi(argv[3]);
	int b_col = atoi(argv[4]);

	if(a_col != atoi(argv[3])){
		printf("Matrix dimensions are not correct\n");
		 	 return -1;
	}


	//genera matrices para producto punto
	hmat_a = (float *)malloc(sizeof(float)*a_row*a_col);
	hmat_b = (float *)malloc(sizeof(float)*a_col*b_col);
	hmat_res = (float *)malloc(sizeof(float)*a_row*a_row);

	fill_matab(hmat_a, a_row, a_col, hmat_b, a_col, b_col);

	print_mat(hmat_a, a_row, a_col);
	print_mat(hmat_b, a_col, b_col);

	HANDLE_ERROR(cudaMalloc((void**)&dmat_a,sizeof(float)*a_row*a_col));
	HANDLE_ERROR(cudaMalloc((void**)&dmat_b,sizeof(float)*b_row*b_col));
	HANDLE_ERROR(cudaMalloc((void**)&dmat_res,sizeof(float)*a_row*a_row));


    cudaMemcpy(dmat_a, hmat_a, sizeof(float)*a_row*a_col, cudaMemcpyHostToDevice);
    cudaMemcpy(dmat_b, hmat_b, sizeof(float)*a_col*b_col, cudaMemcpyHostToDevice);


    // Funciones que encontre en linea para calcular bloques y threads optimos dependiendo del tamano de las matrices
    dim3 dimGrid((b_col + BLOCK_SIZE - 1) / BLOCK_SIZE, (a_row + BLOCK_SIZE - 1) / BLOCK_SIZE);
    dim3 dimBlock(BLOCK_SIZE, BLOCK_SIZE);

    //manda llamar la funcion de GPU
    mat_mult<<<dimGrid, dimBlock>>>(dmat_res, dmat_a, dmat_b, a_row, a_col, b_col);

    //copia matriz resultante
    cudaMemcpy(hmat_res, dmat_res, sizeof(float)*a_row*a_row, cudaMemcpyDeviceToHost);

    //imprime resultado
    print_mat(hmat_res, a_row, a_row);


    //libera memoria de host
    free(hmat_a);
	free(hmat_b);
    free(hmat_res);
    //libera memoria de device
    cudaFree(dmat_a);
    cudaFree(dmat_b);
    cudaFree(dmat_res);

}
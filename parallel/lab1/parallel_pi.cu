/*
   Alejandro Salmon F.D.
   A01201954
   Lab1: Parallel PI
*/

// inlude path of your own cuda_runtime.h... if its in the same folder just delete the ../
#include "../cuda_runtime.h"
#include <stdlib.h>
#include <stdio.h>

#define NO_RECTANGLES 1000000000
#define width 1.0/NO_RECTANGLES

#define BLOCKS_PER_GRID 12
#define THREADS_PER_BLOCK 512

// Graphic processor calculation of Pi in parallel way
__global__ void parallel_Pi(double *device_array) {
	int tid = blockIdx.x * blockDim.x + threadIdx.x;
	int aux = tid;
	double mid;
	double height;
	while (aux < NO_RECTANGLES) {
		mid = (aux + 0.5) * width;
		height = 4.0 / (1.0 + mid * mid);
		device_array[tid] += height;
		aux += blockDim.x * gridDim.x;
	}
}

int main(void) {

	int size = THREADS_PER_BLOCK * BLOCKS_PER_GRID;

    // Device array and local array for getting pi values from gpu and copying them to host
    double *local_array;
	double *device_array;

	//allocate local memory to get values from gpu
	local_array = (double*) malloc(size * sizeof(double));
	// allocate memory in device (GPU)
	cudaMalloc((void**) &device_array, size * sizeof(double));


	parallel_Pi<<<BLOCKS_PER_GRID, THREADS_PER_BLOCK>>>(device_array);

	// copy result from device to host so we can handle it
	cudaMemcpy(local_array, device_array,  size * sizeof(double), cudaMemcpyDeviceToHost);

	double pi = 0;

	// Pi is sum of all values in local array multiplied by the specified width of the rectangles
	for(int i = 0; i < size; i++) {
		pi += local_array[i];
	}
	pi = pi * width;

	printf("Calculated pi value: %lf\n", pi);
	printf("YAY PARALLEL\n");

    // Free memory space in device and host
    free(local_array);
	cudaFree(device_array);

	return 0;
}

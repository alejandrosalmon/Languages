#include <stdio.h>
#include <stdlib.h>

#define NO_RECTANGLES 1000000000
#define width 1.0/NO_RECTANGLES

// Calculation of Pi in sequential way
int main() {
  double suma = 0.0;

  for (int i = 0; i < NO_RECTANGLES; i++)
  {
    double mid = (i + 0.5) * width;
    double height = 4.0 / (1.0 + mid * mid);
    suma += height;
    if(i%100000000 == 0 ){
    	printf("wait for it...\n");
    }
  }

  float res =  width * suma;
  printf("Pi sequential is: %f\n", res);
  return 0;
}

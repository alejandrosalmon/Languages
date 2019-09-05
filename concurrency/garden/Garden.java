/* This code acalculate Pi
	Languages Concurrence Lab 2
  Iancarlo Romero Rosas A01206410
 */

public class Garden {
	private static final int MAXTHREADS = Runtime.getRuntime().availableProcessors();
	public static void main(String args[]) {
		//Variables of the garden.
		int person_inside;
		//Doors of the garden.
		Door west = new Door(true);
		Door east = new Door(false);
		//Person visiting the garden.
		Person threads[];
		threads = new Person[MAXTHREADS];
		//Initilized the threads
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Person(west, east);
		}
		System.out.printf("----------The garden is open-----------\n");
		person_inside = west.number() + east.number();
		System.out.printf("Current Number of people inside the building is %d\n", person_inside);

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		person_inside = west.number() + east.number();
		System.out.printf("--------The garden is close----------------\nNumber of people inside the building is %d\n", person_inside);
		System.out.printf("Number of threads running %d\n", MAXTHREADS);

	}
}

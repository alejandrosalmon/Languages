import java.util.Random;

public class Person extends Thread {
	private static final int NUM_PERSONS = 2;
	private Door west, east;

	public Person(Door west, Door east) {
		this.west = west;
		this.east = east;
	}

	public void run() {
		Random r = new Random();
		for (int i = 0; i < NUM_PERSONS; i++){
			try {
				sleep(r.nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(r.nextInt(2) == 0){// Person arrive from west door
				west.in(east);
			}else{
				east.in(west);
			}
			try {
				sleep(r.nextInt(101));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			west.print(east);
			if( r.nextInt(2) == 0){
				west.out(east);
			}else{
				east.out(west);
			}
		}
	}
}

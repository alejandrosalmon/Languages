import java.util.Random;

public class Rabbit implements Runnable{
	int length;
	int sleep_time;
	int sleep_time_flag = 0;

	public Rabbit(int length){
		this.length = length;
		this.sleep_time_flag = 1;
	}

	public Rabbit(int length, int sleep_time){
		this.length = length;
		this.sleep_time = sleep_time;
	}

	public int getRandom(){
		Random rand = new Random(System.currentTimeMillis());
		return rand.nextInt(501) + 500;
	}

	public void sleepRandom(){
		Random rand = new Random(System.currentTimeMillis());
		if(rand.nextInt(2) == 0){
			this.sleep_time = 100;
		} else {
			this.sleep_time = 200;
		}
	}

	public void run(){;
		int distance = 0;
		while((distance < this.length) && !Thread.currentThread().isInterrupted()){
			try {
				if(this.sleep_time_flag == 1){
					sleepRandom();
				}
				distance += getRandom();
				System.out.println("Rabbit walked: " + distance);
				Thread.sleep(sleep_time);
			} catch(InterruptedException e){
				System.out.println("Rabbit Looses");
			}
		}
 	}
}
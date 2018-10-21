import java.util.Random;

public class Rabbit implements Runnable{
	int sleep_time;
	int sleep_time_flag = 0;
	int distance = 0;

	public Rabbit(){
		this.sleep_time_flag = 1;
	}

	public Rabbit( int sleep_time){
		this.sleep_time = sleep_time;
		this.sleep_time_flag = 0;
	}

	public int getDistance(){
		return this.distance;
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
		while(!Thread.currentThread().interrupted()){
			try {
				if(this.sleep_time_flag == 1){
					sleepRandom();
					//System.out.println(this.sleep_time);
				}
				this.distance += getRandom();
				//System.out.println("Rabbit walked: " + this.distance);
				Thread.sleep(this.sleep_time);
			} catch(InterruptedException e){
				System.out.println("Rabbit interrupted!");
				return;
			}
		}
 	}
}

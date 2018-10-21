import java.util.Random;

public class Turtle implements Runnable{
	int speed;
	int distance = 0;
	int speed_flag = 0;

	public Turtle(){
		this.speed_flag = 1;
	}

	public Turtle( int speed, int speed_flag){
		this.speed = speed;
		this.speed_flag = speed_flag;
	}

	public int getDistance(){
		return this.distance;
	}

	public int getRandom(){
		Random rand = new Random();
		return rand.nextInt(8) + 3;
	}

	public void run(){
		while(!Thread.currentThread().interrupted()){
			if(this.speed_flag == 1){
				this.speed = getRandom();
			}
			this.distance += this.speed;
			//System.out.println("Turtle walked: " + distance);
			if(Thread.interrupted()){
				System.out.println("Rabbit interrupted!");
				return;
			}
		}
	}
}

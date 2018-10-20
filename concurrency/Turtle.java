import java.util.Random;

public class Turtle implements Runnable{
	int length;
	int speed;
	int speed_flag = 0;

	public Turtle(int length){
		this.length = length;
		this.speed_flag = 1;
	}

	public Turtle(int length, int speed, int speed_flag){
		this.length = length;
		this.speed = speed;
		this.speed_flag = speed_flag;
	}

	public int getRandom(){
		Random rand = new Random();
		return rand.nextInt(8) + 3;
	}

	public void run(){
		int distance = 0;
		int flag = this.speed_flag;
		while((distance < this.length) && !Thread.currentThread().isInterrupted()){
			try{
				if(flag == 1){
					this.speed = getRandom();
				}
				distance += this.speed;
				System.out.println("Turtle walked: " + distance);
				Thread.sleep(1);
			} catch (InterruptedException e){
				System.out.println("Turtle Looses");
			} 
		}
	}
}
import java.util.Random;

public class Door{
	private int inside, outside;
	private boolean a;

	public Door(boolean a) {
		this.inside = 0;
		this.outside = 0;
		this.a = a;
	}

	public synchronized void in(Door aux) {
		this.inside++;
		if (a){
			System.out.printf("West: Person arrive\n");
		}else{
			System.out.printf("East: Person arrive\n");
		}
	}

	public synchronized void out(Door aux){
		this.outside++;
		if (a){
			System.out.printf("West: Person left\n");
		}else{
			System.out.printf("East: Person left\n");
		}
	}

	public synchronized int number(){
		return this.inside - this.outside;
	}
	public void print(Door aux){
		System.out.printf("Current Number of people inside the building is %d\n", (aux.number() + this.number()));
	}
}

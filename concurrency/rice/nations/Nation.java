import java.util.Random;

public class Nation {
	public int idNation;
	String name;
	
	public int actualRice;	
	public int producedRice = 0;
	public int selledRice = 0;
	public int boughtRice = 0;
	
	Random rand = new Random();
	
	public Nation(int idNation, String name, int actualRice) {
		this.idNation = idNation;
		this.name = name;
		this.actualRice = actualRice;
	}
	
	public boolean canSell(int amount) {
		if(amount >= actualRice)
			return true;
		
		return false;
	}
	
	public synchronized void produce() {	
		producedRice = rand.nextInt(100);
		actualRice =+ producedRice;
	}
	
	public synchronized int sell(int amount) {
		selledRice = amount;
		actualRice =- selledRice;
		return selledRice;
	}
	
	public synchronized void buy(int amount) {
		boughtRice = amount;
		actualRice =+ amount;	
	}
}
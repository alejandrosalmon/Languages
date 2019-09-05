public class NationThread extends Thread{
	private Nations[] nationsArray;
	private int idNation;
	
	Random rand = new Random();
	
	public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude){
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude){
	        if (random < ex){
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
	public NationThread(Nations[] nationsArray, int idNation){
		this.nationsArray = nationsArray;
		this.idNation = idNation;
	}
	
	@Override
	public void run(){
		Nations actualNation = nationsArray[idNation];
		Nations buyNation;
		Nations sellNation;
		int amount = 0;
		int option;
		int year = 0;
		
		while(!isInterrupted()){			
			actualNation.produce();			
						
			amount = rand.nextInt(50);
			option = getRandomWithExclusion(rand, 1, 3, actualNation.idNation);
			buyNation = nationsArray[option];			
			if(buyNation.canSell(amount)){
				actualNation.buy(amount);
				buyNation.sell(amount);
			}								
									
			amount = rand.nextInt(50);
			option = getRandomWithExclusion(rand, 1, 3, actualNation.idNation);
			sellNation = nationsArray[option];
			if (actualNation.canSell(amount)){
				actualNation.sell(amount);
				sellNation.buy(amount);
			}
			
			System.out.println("Año: " + year);
			System.out.println("The country of " + actualNation.name + " produced: " + actualNation.producedRice);
			System.out.println("The country of " + actualNation.name + " selled: " + actualNation.selledRice);
			System.out.println("The country of " + actualNation.name + " bought: " + actualNation.boughtRice);
			System.out.println("The country of " + actualNation.name + " have a total of  " + actualNation.actualRice);
			
			year++;
			
			if(year >= 5) nations.close();
		}
	}
}

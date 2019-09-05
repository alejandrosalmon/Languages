public class Nations {		
	public static NationThread nationA;
	public static NationThread nationB;
	public static NationThread nationC;
	
	public static Nations[] nationArray;
	
	public static Nation germany = new Nation(1,"Germany",100);
	public static Nation mexico = new Nation(2,"Mexico",100);
	public static Nation holland = new Nation(3,"Holland",100);
	
	nationArray[1] = germany;
	nationArray[2] = mexico;
	nationArray[3] = holland;
	
	public static void main(String[] args)
	{		
		nationA = new NationThread(nationArray,1);
		nationB = new NationThread(nationArray,2);
		nationC = new NationThread(nationArray,3);
		
		nationA.start();
		nationB.start();
		nationC.start();
	}
	
	public static synchronized void close(){
		nationA.interrupt();
		nationB.interrupt();
		nationC.interrupt();
	}
}

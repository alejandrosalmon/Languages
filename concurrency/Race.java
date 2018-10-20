public class Race{
	public Race(){

	}

	public static void main(String[] args) throws InterruptedException{
		int option = 0;
		int value;
		int length = 1000;
		int sleeping_rabbit = 0;
		int turtle_speed;
		Thread rabbit = new Thread(new Rabbit(length));
		Thread turtle = new Thread(new Turtle(length));

		if(args.length == 2){
			try{
				option = Integer.parseInt(args[0]);
				value = Integer.parseInt(args[1]);
				if(option > 3 || option < 1){
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println("\nError: The option must be and integer number with a value between 1 and 3 \n Options:");
					System.out.println("\t 1: Length of race \n\t 2: Rabbit sleeping time in miliseconds \n\t 3: Turtle speed\n");
					System.exit(1);
				}
				switch(option){
					//length of race
					case 1:
						length = value;
						turtle = new Thread(new Turtle(length, 1, 1));
						break;
					//rabbit sleeping time
					case 2:
						sleeping_rabbit = value;
						break;
					//turtle speed
					case 3:
						turtle_speed = value;
						turtle = new Thread(new Turtle(length, turtle_speed, 0));
						break;
				}
				rabbit = new Thread(new Rabbit(length, sleeping_rabbit));

			}catch(NumberFormatException e){
				System.err.println("-----------------------------------------------------------------------------------------------------");
				System.err.println("\nError: The option and value must be a positive integer number\n Options:");
				System.err.println("\t 1: Length of race \n\t 2: Rabbit sleeping time in miliseconds \n\t 3: Turtle speed\n");
				System.exit(1);
			}
		} else if(args.length == 1 || args.length > 2){
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("\nError: The program must recieve 2 parameters or none \n\t Race \n\t Race [option] [value] \n Options:");
			System.out.println("\t 1: Length of race \n\t 2: Rabbit sleeping time in miliseconds \n\t 3: Turtle speed\n");
			System.exit(1);
		} 
		rabbit.start();
		turtle.start();
		while(rabbit.isAlive() && turtle.isAlive()){
//			System.out.println("The race goes on...");
			if(!turtle.isAlive()){
				System.out.printf("Interrupt Rabbi\n\n");
				rabbit.interrupt();
			} 
			if(!rabbit.isAlive()){
				System.out.printf("Interrupt Turtle\n\n");
				turtle.interrupt();
			}
		}
	}
}
public class Race{
	public Race(){

	}

	public static void main(String[] args) throws InterruptedException{
		int option = 0;
		int value;
		int length = 1000;
		int sleeping_rabbit = 0;
		int turtle_speed;
		int dif;
		Rabbit rab = new Rabbit();
		Turtle tur = new Turtle();
		Thread rabbit = new Thread(rab);
		Thread turtle = new Thread(tur);

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
						tur = new Turtle();
						turtle = new Thread(tur);
						break;
					//rabbit sleeping time
					case 2:
						sleeping_rabbit = value;
						rab = new Rabbit(sleeping_rabbit);
						break;
					//turtle speed
					case 3:
						turtle_speed = value;
						tur = new Turtle(turtle_speed,0);
						turtle = new Thread(tur);
						break;
				}
				rabbit = new Thread(rab);

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
		turtle.start();
		rabbit.start();
		while(rabbit.isAlive() && turtle.isAlive()){
			if((tur.getDistance() > length) && rabbit.isAlive()){
				dif = length - rab.getDistance();
				System.out.printf("Turtle won!\n Rabbit Looses the race by: " + dif + " \n");
				turtle.interrupt();
				turtle.join();
				rabbit.interrupt();
			} else if((rab.getDistance() > length) && turtle.isAlive()){
				dif = length - tur.getDistance();
				System.out.printf("Rabbit won!\n Turtle Looses the race by: " + dif + " \n");
				rabbit.interrupt();
				rabbit.join();
				turtle.interrupt();
			}
		}
	}
}

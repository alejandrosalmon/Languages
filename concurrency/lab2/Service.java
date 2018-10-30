
//Alejandro Salmon 
// A01201954
import java.util.*;

public class Service extends Thread{
    int visitors = 0;
    int secondsOpen;
    //Doors of the garden
    ArrayList<Door> doors = new ArrayList<Door>();
    Queue<Person> persons = new LinkedList<Person>();
    Person per;
    //Number of services
    int services = 2;


    public Service(int secondsOpen){
        this.secondsOpen = secondsOpen;
    }

    @Override
    public void run() {

        //My program can have more than 2 gates.
        for(int i=0; i<services; i++){
            int id = i+1;
            String name = "Door " + id;
            Door d = new Door(name);
            doors.add(d);

        }


        long t= System.currentTimeMillis();
        long end = t+(secondsOpen*1000);
        while(System.currentTimeMillis() < end) {
            Random random = new Random();
            int rand = random.nextInt(30) + 1;
            Iterator<Door> doorIter = doors.iterator();
            if (rand == 1) {
                synchronized(this){
                    Door d = doorIter.next();
                    if(!d.getUsed()){
                        Person person = new Person(visitors);
                        persons.add(person);
                        System.out.println("Person " + person.getId() + " enters through door number " + d.getNumber() + ".\n");
                        Thread th = new Thread(d);
                        th.start();
                        visitors++;
                        System.out.println("Persons left in garden : " + persons.size());
                    }
                }
            }
            synchronized(this){
                while (doorIter.hasNext()) {
                    Door d = doorIter.next();
                    if (!d.getUsed()) {
                        if (!persons.isEmpty()) {
                            per = persons.remove();

                            System.out.println("Door free, person " + per.getId() + " leaves.\n");
                            d.changeStatus();

                            System.out.println("Being attended at :\n" + d.getNumber());
                            Thread th = new Thread(d);
                            th.start();

                            System.out.println("Leaves.\n");
                        } else {
                            System.out.println("No one to attend at: \n" + d.getNumber());
                        }

                    }
                }
            }
        }

    }

    public static void main(String args[]){


        Service garden = new Service(60);

        //I can run many gardens at the same time...
        Thread father = new Thread(garden);

        father.start();

    }

}


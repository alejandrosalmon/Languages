
import java.util.ArrayList;
import java.util.Random;

public class Nation extends Thread{
    int actualRice = 0;
    int selledRice = 0;
    int producedRice = 0;
    int recievedRice = 0;
    int id;

    String name;
    int years;

    ArrayList<String> transactions = new ArrayList<String>();

    ArrayList<Nation> nations = new ArrayList<Nation>();


    public Nation(String name, int years, int id){
        this.name = name;
        this.years = years;
        this.id = id;
    }


    public synchronized void produceRice(){
        Random random = new Random();
        int randRice = random.nextInt(10) + 1;
        actualRice += randRice;
        producedRice +=randRice;
        String transaction = this.name + " produced " + randRice + ", now has " + actualRice;

        System.out.println(transaction);
        
    }

    public synchronized void sendRice(int rice){
        recievedRice += rice;
        actualRice += rice;
        System.out.println(this.name + " recieved " + rice + ", now has " + actualRice);
    }

    public synchronized void sellRice(){
        Random random = new Random();
        int randRice = random.nextInt(3) + 1;
        int randNation = random.nextInt(3);
        Nation theOneISellTo = nations.get(randNation);
//        System.out.println(this.name + " is about to sell " + randRice + " out of " + actualRice);
        if((actualRice - randRice) <= 0 ){

            System.out.println(this.name + " sold all " + actualRice + " to " + theOneISellTo.gName());
            theOneISellTo.sendRice(actualRice);
            
            selledRice += actualRice;
            actualRice = 0;
        } else {
            System.out.println(this.name + " sold " + randRice + " to " + theOneISellTo.gName());
            theOneISellTo.sendRice(randRice);
            actualRice -= randRice;
            selledRice += randRice;
        }


    }

    @Override
    public void run() {
        Random random = new Random();
        for(int i = 0; i<years; i++ ){
            this.produceRice();
            this.sellRice();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public int getProducedRice() {
        return producedRice;
    }

    public int getActualRice() {
        return actualRice;
    }

    public void setActualRice(int actualRice) {
        this.actualRice = actualRice;
    }

    public int getSelledRice() {
        return selledRice;
    }

    public void setSelledRice(int selledRice) {
        this.selledRice = selledRice;
    }

    public ArrayList<Nation> getNations() {
        return nations;
    }

    public void setNations(ArrayList<Nation> nations) {
        this.nations = nations;
    }

    public String gName(){
        return name;
    }


    public static void main(String[] args) throws InterruptedException{
        ArrayList<Nation> nations = new ArrayList<Nation>();

        Nation nation1 = new Nation("Wakanda",  3, 1);
        Nation nation2 = new Nation("Queretaro",3, 2);
        Nation nation3 = new Nation("Irapuato", 3, 3);

        nations.add(nation1);
        nations.add(nation2);
        nations.add(nation3);

        nation1.setNations(nations);
        nation2.setNations(nations);
        nation3.setNations(nations);


        Thread t1;
        Thread t2;
        Thread t3;

        t1 = new Thread(nation1);
        t2 = new Thread(nation2);
        t3 = new Thread(nation3);

        t1.start();
        t2.start();
        t3.start();
        
        t1.join();
        t2.join();
        t3.join();
        
        System.out.println("---------------------------------------");
        System.out.println(nation1.gName() + " has " + nation1.getActualRice());
        System.out.println(nation2.gName() + " has " + nation2.getActualRice());
        System.out.println(nation3.gName() + " has " + nation3.getActualRice());


    }
}


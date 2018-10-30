import java.util.ArrayList;
import java.util.Random;

public class Door extends Thread{

    String doorNum;

    Boolean used = false;

    ArrayList<Integer> usedTimes = new ArrayList<Integer>();

    public Door(String doorNum){
        this.doorNum = doorNum;
    }

    public synchronized void changeStatus(){
        used = !used;
    }

    public Boolean getUsed() {
        return used;
    }

    public String getNumber() {
        return this.doorNum;
    }

    public ArrayList<Integer> getUsedTimes() {
        return usedTimes;
    }

    @Override
    public void run() {
        Random random = new Random();
        int randSleep = random.nextInt(2000) + 1000;

        try {

            Thread.sleep(randSleep);
            usedTimes.add(randSleep);
            this.changeStatus();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

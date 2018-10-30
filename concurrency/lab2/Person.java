import java.util.Random;

public class Person extends Thread {

    int id;

    public Person(int id){
        this.id = id;
    }

    public long getId(){
    	return this.id;
    }

    public void setId(int id){
    	this.id = id;
    }
}

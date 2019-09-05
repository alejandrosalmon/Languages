//  FILTER IMPLEMENTATION WITH CONCURRENCY JAVA  (CAN ALSO BE DONE IN PARALLEL)
//  Usage is  java Vintage [path to the image]

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.*;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import javax.imageio.ImageIO;

public class Vintage extends RecursiveTask<Void> {
	public static final int THRESHOLD = 300;

	BufferedImage image;
	int start;
	int end;

	public Vintage(BufferedImage image){
		this.image = image;
	}

	public Vintage(BufferedImage image, int start, int end){
		this.image = image;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Void compute(){
		int limit = end-start;
		if(limit < THRESHOLD){
			int cols = image.getWidth();
			for (int i = start; i < end; i++) {
				for (int j = 0; j < cols; j++) {
					Color c = new Color(image.getRGB(j, i));
					float L = (float)(0.2126*(float)c.getRed() + 0.7152*(float)c.getGreen() + 0.0722*(float)c.getBlue());
    				//applies filter removing almost all the blue from the image
    				int red = (int)(c.getRed() * L/255);
    				int green = (int)(c.getGreen() * L/255);
    				int blue = (int)(c.getBlue() *0);
					Color newColor = new Color(red, green, blue);

    				image.setRGB(j, i, newColor.getRGB());
                }
            }
        } else {
        	int mid = (start + end) / 2;
        	Vintage t1, t2;
        	t1 = new Vintage(image, start, mid);
        	t2 = new Vintage(image, mid, end);
            t1.fork(); // new thread is called
            t1.join();
            t2.compute(); // calls actual computation
        }
        return null;
    }

	private static void parallelVintage(BufferedImage image, String name) {
    	try{
	    	Vintage t = new Vintage(image, 0, image.getHeight());
	    	ForkJoinPool pool = new ForkJoinPool();
	    	pool.invoke(t);
	    	pool.shutdown();
	     	File ouptut = new File(name);
	    	ImageIO.write(image, "jpg", ouptut);
	    } catch(Exception e){
	    	System.out.println(e);
	    }
    }

    static public void main(String args[]) throws Exception {
    	File input = new File(args[0]);
    	String name = input.getName();
		//String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
    	BufferedImage image = ImageIO.read(input);
    	long start = System.currentTimeMillis();
    	Vintage obj = new Vintage(image);

		obj.parallelVintage(image, "parallel_"+name);
    	long end = System.currentTimeMillis();
    	long time = end - start;
    	System.out.println("Parallel running time in milliseconds: " +time);
    }

}

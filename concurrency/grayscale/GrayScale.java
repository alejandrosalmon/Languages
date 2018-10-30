import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.*;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import javax.imageio.ImageIO;

//Alejandro Salmon A01201954

//usage:  java GrayScale [FILENAME]

public class GrayScale extends RecursiveTask<Void> {

	public static final int THRESHOLD = 300;

	BufferedImage image;
	int start;
	int end;
	
	public GrayScale(BufferedImage image){
		this.image = image;
	}

	public GrayScale(BufferedImage image, int start, int end) {
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
    				int red = (int)(c.getRed() * 0.299);
    				int green = (int)(c.getGreen() * 0.587);
    				int blue = (int)(c.getBlue() *0.114);

					Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);

    				image.setRGB(j, i, newColor.getRGB());
                }
            }
        } else {
        	int mid = (start + end) / 2;
        	GrayScale t1, t2;
        	t1 = new GrayScale(image, start, mid);
        	t2 = new GrayScale(image, mid, end);
            t1.fork(); // se manda a llamar a un nuevo thread
            t1.join();
            t2.compute(); // se corre sobre el main
        }
        return null;
    }

    public void graySequential(String name){
    	try {
    		int width = image.getWidth();
    		int height = image.getHeight();

    		for(int i=0; i<height; i++) {

    			for(int j=0; j<width; j++) {

    				Color c = new Color(image.getRGB(j, i));
    				int red = (int)(c.getRed() * 0.299);
    				int green = (int)(c.getGreen() * 0.587);
    				int blue = (int)(c.getBlue() *0.114);
    				Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);

    				image.setRGB(j, i, newColor.getRGB());
    			}
    		}

	     	File ouptut = new File(name);
	    	ImageIO.write(image, "jpg", ouptut);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }

    private static void grayParallel(BufferedImage image, String name) {
    	try{
	    	GrayScale t = new GrayScale(image, 0, image.getHeight());
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
    	GrayScale obj = new GrayScale(image);
    	obj.graySequential("sequential_"+name);
    	long end = System.currentTimeMillis();
    	long time = end - start;
    	System.out.println("Sequential running time in milliseconds: " +time);

    	start = System.currentTimeMillis();
		obj.grayParallel(image, "parallel_"+name);
    	end = System.currentTimeMillis();
    	time = end - start;
    	System.out.println("Parallel running time in milliseconds: " +time);
    }
}
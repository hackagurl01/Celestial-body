	

/**
 * @author YOUR NAME THE STUDENT IN 201
 * 
 * Simulation program for the NBody assignment
 */

import java.io.*;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */

	public static double readRadius(String fname) throws FileNotFoundException  {
		/*
		Find universe radius
		 */
		Scanner s = new Scanner(new File(fname));
		s.nextInt();

		double radius = s.nextDouble();

		s.close();
		return radius;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {
		/*
		Reads text file to save planet info line by line as instance into CelestialBody constructor
		 */
		
			Scanner s = new Scanner(new File(fname));
			int nb = 0; // # bodies to be read
			nb = s.nextInt();
			s.nextLine();
			s.nextLine();

			CelestialBody[] planets = new CelestialBody[nb];
//			System.out.print(s.nextLine());
			for(int i=0; i < nb; i++) {
				double xPos = s.nextDouble();
				double yPos = s.nextDouble();
				double xVel = s.nextDouble();
				double yVel = s.nextDouble();
				double mass = s.nextDouble();
				String planet = s.next();

				CelestialBody newBody = new CelestialBody(xPos, yPos, xVel, yVel, mass, planet);
				planets[i] = newBody;
			}
			s.close();
			return planets;
	}
	public static void main(String[] args) throws FileNotFoundException{
		/*
		Runs program
		 */
		double totalTime = 39447000.0;
		double dt = 25000.0;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
		//StdAudio.play("images/2001.wav");
	
		// run simulation until time up

		for(double t = 0.0; t < totalTime; t += dt) {
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];
			for (int i=0; i < bodies.length; i+=1) {
				xforces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yforces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}

			for (int i=0; i < bodies.length; i+=1) {
				bodies[i].update(dt,xforces[i],yforces[i]);
			}
			
			StdDraw.picture(0,0,"images/starfield.jpg");

			for (int i=0; i < bodies.length; i+=1) {
				bodies[i].draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}

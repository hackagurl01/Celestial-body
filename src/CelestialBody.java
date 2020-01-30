
/**
 * Celestial Body class for NBody
 * @seb129 Sydney Ballard
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		/*
		Assign values
		 */
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}

	public CelestialBody(CelestialBody b){
		/*
		Builds constructor
		 */
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	/*
	Pulls private values, shielding them
	 */
	public double getX() {
		// Returns X coordinate
		return myXPos;
	}
	public double getY() {
		// Returns Y coordinate
		return myYPos;
	}
	public double getXVel() {
		// Returns X velocity
		return myXVel;
	}
	public double getYVel() {
		// Returns Y velocity
		return myYVel;
	}
	
	public double getMass() {
		// Returns Mass
		return myMass;
	}
	public String getName() {
		// Returns filename
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	private double deltaX(CelestialBody b) {
		// Change in X pos
		double dX = b.getX() - myXPos;
		return dX;
	}

	private double deltaY(CelestialBody b) {
		// Change in Y pos
		double dY = b.getY() - myYPos;
		return dY;
	}

	public double calcDistance(CelestialBody b) {
		// Uses distance formula to calc distance between celestial bodies
		double dX = deltaX(b);
		double deltaX_sqr = Math.pow(dX,2);

		double dY = deltaY(b);
		double deltaY_sqr = Math.pow(dY,2);

		double distanceSquared = deltaX_sqr + deltaY_sqr;
		double distance = Math.sqrt(distanceSquared);
		return distance;
	}

	public double calcForceExertedBy(CelestialBody b) {
		// Uses force formula to calc force exerted by a celestial body
		final double G = 6.67*1e-11;
		double m1 = myMass;
		double m2 = b.getMass();
		double r2 = Math.pow(calcDistance(b),2);

		return (G*m1*m2/r2);
	}

	public double calcForceExertedByX(CelestialBody b) {
		// Vector force exerted in X plane
		double F = calcForceExertedBy(b);
		double dX = deltaX(b);
		double r = calcDistance(b);

		return (F*dX/r);
	}
	public double calcForceExertedByY(CelestialBody b) {
		// Vector force exerted in Y plane
		double F = calcForceExertedBy(b);
		double dY = deltaY(b);
		double r = calcDistance(b);

		return (F*dY/r);
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		// Net force exerted on celestial body by all other bodies in X plane
		double sumFX = 0.0;

		for (CelestialBody b : bodies) {
			if (b != this) {
				sumFX += calcForceExertedByX(b);
			}
		}
		return sumFX;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		// Net force exerted on celestial body by all other bodies in Y plane
		double sumFY = 0.0;
		for(CelestialBody b : bodies) {
			if (b != this) {
				sumFY += calcForceExertedByY(b);
			}
		}
		return sumFY;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {
		// Updates force values for celestial body after every deltaT interval
		double ax = xforce/getMass();
		double ay = yforce/getMass();

		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;

		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;

		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}

	public void draw() {
		// Draws celestial body
		StdDraw.picture(myXPos,myYPos, "images/"+myFileName);
	}
}

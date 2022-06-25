public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Planet(Planet p){
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet another){
		double xDis = another.xxPos - this.xxPos;
		double yDis = another.yyPos - this.yyPos;
		double distance = Math.sqrt(xDis * xDis + yDis * yDis);
		return distance;
	}
	
	public double calcForceExertedBy(Planet another){
		double distance = this.calcDistance(another);
		double G = 6.67e-11;
		double force = G * this.mass * another.mass / (distance * distance);
		return force;
	}

	public double calcForceExertedByX(Planet another){
		double xDis = another.xxPos - this.xxPos;
		double force = this.calcForceExertedBy(another);
		double distance = this.calcDistance(another);
		return force * xDis / distance;
	}

	public double calcForceExertedByY(Planet another){
		double yDis = another.yyPos - this.yyPos;
		double force = this.calcForceExertedBy(another);
		double distance = this.calcDistance(another);
		return force * yDis / distance;
	}

	public double calcNetForceExertedByX(Planet[] allPlanets){
		double xForce = 0.0;
		for (Planet item : allPlanets){
			if (!this.equals(item)){
				xForce+= this.calcForceExertedByX(item);
			}
			else continue;
		}
		return xForce;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets){
		double yForce = 0.0;
		for (Planet item : allPlanets){
			if (!this.equals(item)){
				yForce+= this.calcForceExertedByY(item);
			}
			else continue;
		}
		return yForce;
	}

	public void update(double dt, double fX, double fY){
		double xAcc = fX / this.mass;
		double yAcc = fY / this.mass;
		this.xxVel+= xAcc * dt;
		this.yyVel+= yAcc * dt;
		this.xxPos+= this.xxVel * dt;
		this.yyPos+= this.yyVel * dt;
	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

}
package movement;
public class Point{
	double x;
	double y;
	double distanceFromStart;
	int e;
	public Point(){
		
	}
	
	public Point(double x, double y, double sx, double sy) {
		this.x = x;
		this.y = y;
		calArea(x,y,sx,sy);		
		this.distanceFromStart = distance(x,y,sx,sy);
	}
	
	public void setpoint(double x, double y, double sx, double sy){
		this.x = x;
		this.y = y;
		calArea(x,y,sx,sy);
		this.distanceFromStart = distance(x,y,sx,sy);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getD() {
		return distanceFromStart;
	}
	
	public int getE() {
		return e;
	}
	
	
	
	public static double distance(double x1, double y1 , double x2 , double y2) {

		double dx = x2 - x1;
		double dy = y2 - y1;
		double l = Math.sqrt(dx*dx + dy*dy);

		return l;
	}
	
	public void calArea(double x, double y, double sx, double sy) {
		if(x<1800) {
			if(y<1800) {
				this.e = 0;
			}else if(y<3600) {
				this.e =5;
			}else if(y<5400){
				this.e =10;
			}else if(y<7200){
				this.e=15;
			}else {
				this.e=20;
			}
		}else if(x<3600) {
			if(y<1800) {
				this.e = 1;
			}else if(y<3600) {
				this.e =6;
			}else if(y<5400){
				this.e =11;
			}else if(y<7200){
				this.e=16;
			}else {
				this.e=21;
			}
			
		}else if(x<5400) {
			if(y<1800) {
				this.e = 2;
			}else if(y<3600) {
				this.e =7;
			}else if(y<5400){
				this.e =12;
			}else if(y<7200){
				this.e=17;
			}else {
				this.e=22;
			}
			
			
		}else if(x<7200) {
			if(y<1800) {
				this.e = 3;
			}else if(y<3600) {
				this.e =8;
			}else if(y<5400){
				this.e =13;
			}else if(y<7200){
				this.e=18;
			}else {
				this.e=23;
			}
			
			
			
		}else {
			if(y<1800) {
				this.e = 4;
			}else if(y<3600) {
				this.e =9;
			}else if(y<5400){
				this.e =14;
			}else if(y<7200){
				this.e=19;
			}else {
				this.e=24;
			}
			
			
			
			
		}
	}	
}
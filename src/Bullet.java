import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bullet {
	
	private double x;
	private double y;
	private double r;
	private double dx;
	private double dy;
	private double speed;
	private double rad;
	
	private BufferedImageLoader Loader;
	private BufferedImage image;
	
	public Bullet(double angle,double x,double y){
		Loader = new BufferedImageLoader();
		image = Loader.Loader("/bullet_image.png");
		r = 10;
		this.x=x;
		this.y=y;

		speed =3;
		
		rad = Math.toRadians(angle);
		dx = Math.cos(rad)*speed;
		dy = Math.sin(rad)*speed;
	}
	
	public double getX() { return x;}
	public double getY() { return y;}
	public double getR() { return r;}
	
	public boolean tick(){
		
		x +=dx;
		y +=dy;
		
		if(x < 0 || x > GamePanel.WIDTH  ||
				y < 0 || y > GamePanel.HEIGHT ){
			return true;
		}
		
		return false;
		
	}
	
	public void render(Graphics2D g){
		
		g.drawImage(image, (int) x, (int) y,(int) r*2+3,(int)r*+3,null);
		
		//g.fillOval((int) x,(int) y, 10, 10);
		
	}
	

}

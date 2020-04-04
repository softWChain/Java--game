import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PowerUp {
	
	private double x;
	private double y;
	private int r;
	private double dx;
	private double dy;
	
	private int type;
	private Color color1;
	private BufferedImageLoader loader;
	private BufferedImage image;
	
	// 1--- +1 life
	// 2----+1power
	// 3----+2power
	
	public PowerUp(int type ){
		
		this.type = type;

		loader = new BufferedImageLoader();
		image = loader.Loader("/power_1.png");
		

		
		if(type == 1){

			r = 8;
		}
		if(type == 2 ){
			r =8;
		}
		if(type == 3 ){
			r =8;
		}
		if(type == 4 ){
			r =8;
		}
		if(type == 5 ){
			r =8;
		}
		if(type == 6 ){

			r =8;
		}
		
		x = Math.random()*500+150;
		y=-(Math.random()*500+200);
		
	}
	
	public double getx() { return x; }
	public double gety() { return y; }
	public double getr() { return r; }
	
	
	public int getType() {return type; }
	
	
	public boolean tick(){
		
	y += 1;
	
	if(y>GamePanel.HEIGHT +r){
		return true;
	}
	
	return  false;
		
		
	}
	public void draw(Graphics2D g){

		g.drawImage(image,(int) (x), (int) (y), 3*r+10, 2*r+8,null);
	
		
		
	}
	
}

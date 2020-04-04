import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	
	private int speed;
	private int count=0;
	private int frames;
	private int index = 0;
	
	private BufferedImage images[];
	private BufferedImage currentImage;
	
	public Animation(int speed,BufferedImage ...args){
		this.speed=speed;
		images= new BufferedImage[args.length];
		for(int i=0;i<args.length;i++){
			images[i]  = args[i];
		}
		frames = args.length;
	}
	
	public void runAnimation(){
		index++;
		if(index>speed){
			nextFrames();
		}
		
	}
	
	public void nextFrames(){
		for(int i=0;i<frames;i++){
			if(count == i){
				currentImage = images[i];
			}
			count++;
			if(count>frames){
				count = 0;
			}
		}
	}
	
	public void drawAnimation(Graphics g,double x ,double y ,int offset,int width,int height){
		g.drawImage(currentImage, (int) x - offset, (int) y, width,height,null);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Background {
	
	private BufferedImage image1,image2;
	private BufferedImageLoader loader;
	private double y1=0;
	private double y2=-600;
	
	public Background(){
		loader = new BufferedImageLoader();
		image1 = loader.Loader("/background.png");
		image2 = loader.Loader("/background.png");
		
	}
	
	public void tick(){
		
		y1 +=2;
		y2 +=2;
		
		if(y2>=0){
			y2=-600;
		}
		if(y1>=600){
			y1=0;
		}
		
	}
	
	public void render(Graphics2D g){
	
		
		g.drawImage(image1, 0, (int) y1,800,600,null);
		g.drawImage(image2, 0, (int) y2,800,600,null);
	}

}

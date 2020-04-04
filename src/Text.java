import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text {
	
	private double x;
	private double y;
	private long time;
	private String s;
	
	private long start;
	
	public Text(double x,double y,long time ,String s){
		this.x=x;
		this.y=y;
		this.time=time;
		this.s=s;
		start = System.nanoTime();
	}
	
	public boolean tick(){
		long elapsed = (System.nanoTime() - start) / 1000000;

		if(elapsed > time){
			return true;
			
		}
		return false;
	}
	
	public void render(Graphics2D g){
		g.setFont(new Font("Century Gothics ",Font.PLAIN,12));
		g.setColor(Color.CYAN);
		g.drawString(s, (int) x, (int) y);
	}

}

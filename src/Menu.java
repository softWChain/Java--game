import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends MouseAdapter{
	
	private BufferedImage play_button;
	private BufferedImage exit_button;
	private BufferedImageLoader loader;
	
	public void Menu(){
		
		loader = new BufferedImageLoader();
		play_button = loader.Loader("/playGameButton.png");
		exit_button = loader.Loader("/exit_button.png");
		
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		if(GamePanel.gameState == STATE.Menu){
			createButton(g,300, 200, 200, 100,"P L A Y",340,260);
			createButton(g,300, 310, 200, 100,"E X İ T",355,370);
		}
		
	
		
	}
	
	public boolean mouseOver(int mx,int my,int x,int y,int width,int height){
		if(mx > x && mx < x + width){
			if(my > y && my < y + height){
				return true;
			}
			return false;
		}
		return false;
	}
	public void createButton(Graphics2D g,int x,int y,int width ,int height,String text,int textX,int textY){
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		g.setFont(new Font("Gothics Century",Font.PLAIN,33));
		g.drawString(text, textX, textY);
		
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 300, 200, 200, 100)){
			GamePanel.gameState = STATE.Game;
		}
		if(mouseOver(mx, my, 300, 310, 200, 100)){
			System.exit(1);
		}
		
	}

}

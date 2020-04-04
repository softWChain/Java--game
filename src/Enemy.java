import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy {
	
	private double x;
	private double y;
	private int r;
	private double dx;
	private double dy;
	private double speed;
	private double rad;
	

	private boolean ready;
	private boolean dead;
	private int health;
	private int type;
	private int rank;
	private int width,height;

	
	private BufferedImageLoader Loader;
	private BufferedImage image;
	private BufferedImage image2;
	
	private Color color1;
	
	
	public Enemy(int type,int rank){
		this.type=type;
		this.rank=rank;
		width = 15;
		height = 15;
		Loader = new BufferedImageLoader();
		image = Loader.Loader("/enemy_image.png");
		image2 = Loader.Loader("/enemy_2.png");
		
		if(type ==1){
			if(rank==1){
				speed  = 2;
				r =20;
				health =1;
			}
		}
		
		if(type ==2){
			if(rank==2){
				speed  = 3;
				r =20;
				health =2;
			}
		}
		if(type ==3){
			if(rank==3){
				speed  = 3.2;
				r =20;
				health =4;
			}
		}
		if(type ==4){
			if(rank==4){
				speed  = 3.5;
				r =20;
				health =6;
			}
		}
		if(type ==5){
			if(rank==5){
				speed  = 3.5;
				r =20;
				health =8;
			}
		}
		if(type ==6){
			if(rank==6){
				speed  = 3.5;
				r =20;
				health =10;
			}
		}

		x = Math.random()*GamePanel.WIDTH/2+GamePanel.WIDTH/4;
		y = -r;
		
		double angle = Math.random()*140 +20;
		rad = Math.toRadians(angle);
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		ready = false;
		dead = false;
	}
	


	public void tick(){
		
		x +=dx;
		y +=dy;
		/*
		if(!ready){
			if(x>r&& x<GamePanel.WIDTH -r &&
					y>r && y<GamePanel.HEIGHT -r){
				ready = true;
			}
		}
		*/
		
		if(x<r-15 && dx<0) dx = -dx;
		if(y<r && dy<0) dy = -dy;
		if(x > GamePanel.WIDTH - 30 && dx>0) dx = -dx;
		if(y > GamePanel.HEIGHT- 50 && dy>0) dy = -dy;
		
		
	}
	
	public void render(Graphics2D g){
		
		if(type == 1 && rank == 1){
			g.drawImage(image, (int) x, (int) y,(int) r*2-10,(int) r*2-10,null);
		}else if(type == 2 && rank == 2){
			g.drawImage(image2, (int) x, (int) y,(int) r*2-10,(int) r*2-10,null);
		}
		else if(type == 3 && rank == 3 ){
			g.drawImage(image2, (int) x, (int) y,(int) r*2-10,(int) r*2-10,null);
			
		}
		if( rank == 4 && type ==4 ){
			g.drawImage(image, (int) x, (int) y,(int) r*2-10,(int) r*2-10,null);
		}
		if( rank == 5 && type ==5 ){
			g.drawImage(image, (int) x, (int) y,(int) r*2-10,(int) r*2-10,null);
		}
		if( rank == 6 && type ==6 ){
			g.drawImage(image2, (int) x, (int) y,(int) r*2-10,(int) r*2-10,null);
		}
	
	}
	
	public double getX() { return x;}
	public double getY() { return y;}
	public double getR() { return r;}


	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void hit(){
		
		health--;
		if(health <= 0){
			dead = true;
		}
	}
	

}

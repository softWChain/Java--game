import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player {
	
	private double x =200;
	private double y = 200;
	private double r;
	private double dx;
	private double dy;
	private int speed;
	
	private BufferedImageLoader loaderImage;
	private BufferedImage image;
	private int lives=3;
	
	private boolean left,right,up,down,sprith;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;

	private int powerLevel;
	private int power;
	private int[] requiredPower  = {
			1,2,3,4,5
	};
	
	private boolean recovering;
	private long recoveringTimer;
	
	
	public Player(){
		
		loaderImage = new BufferedImageLoader();
		image = loaderImage.Loader("/player_game.png");
		
		dx = 0;
		dy = 0;
		speed = 3;
		r = 10;
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay =200;
		
		
		recovering= false;
		recoveringTimer= 0;
		
	
	}
	
	public void loseLife(){
		lives--;
		recovering = true;
		recoveringTimer = System.nanoTime();
		
	}
	
	public void gainLife(){
		lives++;
	}
	
	public void increasePower(int i){
		power +=i;
		if(power >= requiredPower[powerLevel]){
			power -= requiredPower[powerLevel];
			powerLevel++;
		}
	}
	
	public int getPowerLevel() { return powerLevel; }
	public int getPower() { return power; }
	public int getRequiredPower() { return requiredPower[powerLevel]; }

	public void tick(){
		
		
		if(left){
			dx = -speed;
		}
		if(right){
			dx = speed;
		}
		if(up){
			dy = -speed;
		}
		if(down){
			dy = speed;
		}
		if(sprith){
			speed =6;
		}else{
			speed =3;
		}
		
		x += dx;
		y += dy;
		
		if(x<r-13) x = r-13;
		if(y<r) y = r;
		if(x > GamePanel.WIDTH - r -60) x = GamePanel.WIDTH  -r -60 ;
		if(y > GamePanel.HEIGHT- r -90) y = GamePanel.HEIGHT-r -90;
		
		dx = 0;
		dy = 0;
		
		if(firing){
			
			long elapsed = (System.nanoTime() - firingTimer ) / 1000000;
			if(elapsed > firingDelay){
				
				firingTimer = System.nanoTime();
				System.out.println(powerLevel);
				if(powerLevel <= 2){
					GamePanel.bullet.add(new Bullet(270, x + 17 , y+8));
					GamePanel.bullet.add(new Bullet(265, x + 17 , y+8));

				}
				if(powerLevel >=3){
					GamePanel.bullet.add(new Bullet(270, x + 17 , y+8));
					GamePanel.bullet.add(new Bullet(265, x + 17 , y+8));
					GamePanel.bullet.add(new Bullet(260, x + 17 , y+8));

					
				}
				if(powerLevel >= 4){
					GamePanel.bullet.add(new Bullet(270, x + 17 , y+8));
					GamePanel.bullet.add(new Bullet(265, x - 17 , y+8));
					GamePanel.bullet.add(new Bullet(260, x - 17 , y+8));

		
				}
				else if (powerLevel >=5){
					GamePanel.bullet.add(new Bullet(270, x + 17 , y+8));
					GamePanel.bullet.add(new Bullet(265, x - 17 , y+8));
					GamePanel.bullet.add(new Bullet(260, x + 17 , y+8));
					GamePanel.bullet.add(new Bullet(255, x + 17 , y+8));
				}
				
				
				
			}
		}
		
		long elapsed = (System.nanoTime() - recoveringTimer)/1000000;
		if(elapsed > 2000){
			recovering = false;
			recoveringTimer =0;
		}
		
	
		
	}
	
	public void render(Graphics2D g) {
		
		g.setColor(Color.CYAN);
		g.drawImage(image, (int) x, (int) y,(int)r*8-10,(int)r*8-10,null);
		//g.fillRect((int) x, (int) y, speed*5, speed*5);
		
	
	
		
	}
	
	public void setRight(boolean right) { this.right = right;}
	public void setLeft(boolean left) {this.left = left;}
	public void setUp(boolean up){this.up = up;}
	public void setDown(boolean down){this.down = down;}
	
	
	
	public boolean isSprith() {
		return sprith;
	}

	public void setSprith(boolean sprith) {
		this.sprith = sprith;
	}

	public boolean isRecovering(){
		return recovering;
	}
	
	public void setR(double r){
		this.r=r;
	}
	public double getR(){
		return r;
	}
	
	public void setFiring(boolean firring){
		this.firing = firring;
	}
	

	public double getx() { return x; }
	public double gety() { return y; }
	
	public void setLives(int lives){
		this.lives = lives;
	}
	public int getLives(){
		return lives;
	}
	
	


}

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SmartEnemy {
	
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
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;


	
	private BufferedImageLoader Loader;
	private BufferedImage image;
	private BufferedImage image2,imagesSpreet;
	private SpriteSheet ss;
	
	private BufferedImage[] images_hayalet = new BufferedImage[3];
	
	private Animation LeftAnimation,RightAnimation,FrontAnimation;

	
	
	public SmartEnemy(int type,int rank){
		this.type=type;
		this.rank=rank;
		width = 15;
		height = 15;
		Loader = new BufferedImageLoader();
		image = Loader.Loader("/smart_enemy.png");
		image2 = Loader.Loader("/hayalet.png");
		ss = new SpriteSheet(image2);
		
		//LEFT
		images_hayalet[0] = ss.grabImage(5, 6, 54, 73);
		//FRONT
		images_hayalet[1] = ss.grabImage(69, 0, 95, 80);
		//RIGHT
		images_hayalet[2] = ss.grabImage(182, 0, 67, 86);
		
		LeftAnimation = new Animation(8, images_hayalet[0]);
		FrontAnimation = new Animation(8, images_hayalet[1]);
		RightAnimation = new Animation(8, images_hayalet[2]);

		

		if(type ==1){
			if(rank==1){
				speed  = 2;
				r =20;
				health =3;
			}
		}
		if(type ==2){
			if(rank==2){
				speed  = 2;
				r =20;
				health =6;
			}
		}
		
		if(type ==3){
			if(rank==3){
				speed  = 3;
				r =20;
				health =8;
			}
		}
		if(type ==4){
			if(rank==4){
				speed  = 3;
				r =20;
				health =10;
			}
		}
		if(type ==5){
			if(rank==5){
				speed  = 3;
				r =20;
				health =12;
			}
		}
		if(type ==6){
			if(rank==6){
				speed  = 3;
				r =20;
				health =15;
			}
		}
		if(type ==7){
			if(rank==7){
				speed  = 3;
				r =20;
				health =50;
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

		firing = false;
		firingTimer = System.nanoTime();
		firingDelay =200;
		

	}
	
	public void hit(){
		
		health--;
		if(health <= 0){
			dead = true;
		}
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
		
		if(type==7 && rank==7){
			LeftAnimation.runAnimation();
			RightAnimation.runAnimation();
			FrontAnimation.runAnimation();

		}
		
		if(x<r -20  && dx<0) dx = -dx;
		if(y<r && dy<0) dy = -dy;
		if(x > GamePanel.WIDTH - r -35 && dx>0) dx = -dx;
		if(y > GamePanel.HEIGHT- 55 -r && dy>0) dy = -dy;

	
	
	}
	
	public void render(Graphics2D g){
		
		if(type == 1 && rank == 1){
			g.drawImage(image, (int) x, (int) y,(int) r*3-10,(int) r*3-10,null);

		}
		if(type == 2 && rank == 2){
			g.drawImage(image, (int) x, (int) y,(int) r*3-10,(int) r*3-10,null);

		}
		if(type == 3 && rank == 3){
			g.drawImage(image, (int) x, (int) y,(int) r*3-10,(int) r*3-10,null);

		}
		if(type == 4 && rank == 4){
			g.drawImage(image, (int) x, (int) y,(int) r*3-10,(int) r*3-10,null);

		}
		if(type == 5 && rank == 5){
			g.drawImage(image, (int) x, (int) y,(int) r*3-10,(int) r*3-10,null);

		}
		if(type == 6 && rank == 6){
			g.drawImage(image, (int) x, (int) y,(int) r*3-10,(int) r*3-10,null);

		}
		if(type == 7 && rank == 7){
			//g.drawImage(image2, (int) x, (int) y,(int) r*7+10,(int) r*7+10,null);
	
			if(dx==0 && dx==0){
				g.drawImage(images_hayalet[0], (int)x,(int) y,r*7+10,r*7+10,null);
			}else if(Math.abs(GamePanel.player.getx()-x) > Math.abs(GamePanel.player.gety() - y)){
				if(dx > 0){
					RightAnimation.drawAnimation(g, x, y, 0,r*7+10,r*7+10);
				}else if(dx < 0){
					LeftAnimation.drawAnimation(g, x, y, 0,r*7+10,r*7+10);
				}
			}else if(Math.abs(GamePanel.player.gety()-y) > Math.abs(GamePanel.player.getx() - x)){
				if(dy>0){
					FrontAnimation.drawAnimation(g, x, y, 0, r*7+10, r*7+10);
				}
				if(dy<0){
					FrontAnimation.drawAnimation(g, x, y, 0, r*7+10, r*7+10);
				}


			}


		}
		
	}
	
	
	

	
	public boolean isFiring() {
		return firing;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	public double getX() { return x;}
	public double getY() { return y;}
	public double getR() { return r;}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}


	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}


	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
	
	public void setRank(int rank){
		this.rank = rank;
	}
	public int getRank(){
		return rank;
	}




	
}

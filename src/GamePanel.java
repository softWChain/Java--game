import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class GamePanel extends Canvas implements Runnable,KeyListener{
	
	
	private static final long serialVersionUID = 4488944810233726234L;
	
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	private boolean running = false;
	private Thread thread;
	private BufferStrategy bs;
	private Graphics g;
	public static Player player;
	public static ArrayList<Bullet> bullet;
	public static ArrayList<Enemy> enemy;
	public static ArrayList<PowerUp> powerup;
	public static ArrayList<SmartEnemy> smartEnemy;
	public static Menu menu;
	public static ArrayList<Text> text;
	public static Background back;
	public static int ticks1 = 0;
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay =2000;
	public static int score;
	public static boolean fireFree;
	private boolean powerupCollosion = false;
	
	public static STATE gameState = STATE.Menu;

	

	
	public GamePanel(){
		menu = new Menu();
		player = new Player();
		bullet = new ArrayList<Bullet>();
		enemy = new ArrayList<Enemy>();
		powerup =  new ArrayList<PowerUp>();
		smartEnemy = new ArrayList<SmartEnemy>();
		text = new ArrayList<Text>();
		back = new Background();

	
		
		
		addKeyListener(this);
		addMouseListener(new Menu());
		setFocusable(true);
		requestFocus();
		
		
	}
	
	public void init(){

		waveStartTimer=0;
		waveStartTimerDiff=0;
		waveStart=true;
		waveNumber=0;
		score = 0;
	}

	
	public void tick(){
		
		if(gameState==STATE.Game){
			if(player.getLives() != 0 && player.getLives()>0){
				
				ticks1++;
				if(ticks1>=1001){
					ticks1=0;
				}

				//new Wave
				if(waveStartTimer ==0 && enemy.size()==0 && smartEnemy.size()==0){
					waveNumber++;
					waveStart=false;
					waveStartTimer= System.nanoTime();
				}
				else{
					waveStartTimerDiff = (System.nanoTime() - waveStartTimer)/1000000;
					if(waveStartTimerDiff > waveDelay){
						waveStart = true;
						waveStartTimer=0;
						waveStartTimerDiff=0;
					}
				}


				//BACKGROUND
				//back.tick();
				
				//PLAYER UPDATE
				player.tick();

				
				//create enemies
				
				if(waveStart && enemy.size()==0 && smartEnemy.size()==0 ){
					createNewEnemies();
					createSmartEnemy();
				}
				
				//power up update
				for(int i=0;i<powerup.size();i++){
					powerup.get(i).tick();
				}

				//create powerup
				if(waveStart && powerup.size()==0){

					createPowerUp();
				}
				
				//powerup clear and update
				for(int i=0;i<powerup.size();i++){
					boolean remove = powerup.get(i).tick();
					if(remove){
						powerup.remove(i);
						i--;
					}
				}
				
				//Smart Enemy update
				for(int i=0;i<smartEnemy.size();i++){
					smartEnemy.get(i).tick();
				}

				//ENEMY UPDATE
				for(int i=0;i<enemy.size();i++){
					enemy.get(i).tick();
				}
			
				
				//BULLET UPDATE
				for(int i=0;i<bullet.size();i++){
					bullet.get(i).tick();
				}
				//BULLET CLEAR
				for(int i=0;i<bullet.size();i++){
					boolean remove = bullet.get(i).tick();
					if(remove){
						bullet.remove(i);
						i--;
					}
				}
				
				//Player -SmartEnemy Distance 
				
				if(!player.isRecovering()){
					//circumstance - 1 
					int pr1 = (int) player.getR();
					int px1 = (int) player.getx();
					int py1 = (int) player.gety();
					for(int i=0;i<smartEnemy.size();i++){
						SmartEnemy e  = smartEnemy.get(i);
				
						/////circumstance - 1
						double ex = e.getX();
						double ey = e.getY();
						double er = e.getR();
						double dx = px1 -ex;
						double dy = py1 -ey;
						//circumstance - 2
						double diffX = e.getX() - player.getx() +  e.getR();
						double diffY = e.getY() - player.gety() - e.getR();
						
						//circumstance - 1
						double distance1 = Math.sqrt(dx*dx+dy*dy);
						
						//circumstance - 2
						double distance = Math.sqrt((e.getX() - player.getx())*(e.getX() - player.getx()) 
								+ (e.getY() - player.gety())*(e.getY() - player.gety()));
						
						if(distance<200){
							e.setDx((-1/distance*diffX)); 
							e.setDy((-1/distance*diffY));
							e.setFiring(true);
							
						}
						//circumstance - 1
						if(distance1 < pr1 + er){
							player.loseLife();

						}
					}
				}
				
				//Player - Enemy Collosion
				if(!player.isRecovering()){

					int px = (int) player.getx();
					int py = (int) player.gety();
					int pr = (int) player.getR();
					for(int i=0;i<enemy.size();i++){
						Enemy e =enemy.get(i);
						double ex = e.getX();
						double ey = e.getY();
						double er = e.getR();
						
						double dx = px-ex;
						double dy = py -ey;
						double dist = Math.sqrt(dx*dx+dy*dy);
						
						if(dist < pr + er){
							
							player.loseLife();

							}
						}
					}
				
				//Bullet-Animation enemy collosion
				
				
				
				
				//Bullet-SmartEnemy collosion
				
				for(int i=0;i<bullet.size();i++){
					Bullet b = bullet.get(i);
					double bx = b.getX();
					double by = b.getY();
					double br = b.getR();
					for(int j=0;j<smartEnemy.size();j++){
						SmartEnemy se = smartEnemy.get(j);
						double sx = se.getX();
						double sy = se.getY();
						double sr = se.getR();
						
						double dx = bx - sx;
						double dy = by - sy;
						double dist = Math.sqrt(dx*dx + dy*dy);
						
						if(dist < br + sr){
							score +=2;
							se.hit();
							bullet.remove(i);
							i--;
							break;
						}
					}
				}
				//check smartEnemy die
				for(int i=0;i<smartEnemy.size();i++){
					boolean remove = smartEnemy.get(i).isDead();
					if(remove){
						smartEnemy.remove(i);
						i--;
					}
				}
				
				//Bullet-Enemy collosion
				for(int i=0;i<bullet.size();i++){
					
					Bullet b = bullet.get(i);
					double bx = b.getX();
					double by = b.getY();
					double br = b.getR();
					
					for(int j=0;j<enemy.size();j++){
						
						Enemy e = enemy.get(j);
						double ex = e.getX();
						double ey = e.getY();
						double er = e.getR();
						
						double dx = bx - ex;
						double dy = by - ey;
						double dist = Math.sqrt(dx*dx + dy*dy);
						if(dist < br+er){
							score +=2;
							if(score >= 50 && 60 > score){
								player.gainLife();
							}
							e.hit();
							bullet.remove(i);
							i--;
							break;
						}
					}
				}
				//TEXT update
				
				for(int i=0;i<text.size();i++){
					boolean remove = text.get(i).tick();
					if(remove){
						text.remove(i);
						i--;
					}
				}
				
				//player -poweerup Collosion
				
				double px = player.getx();
				double py = player.gety();
				double pr = player.getR();
				
				for(int i=0;i<powerup.size();i++){
					PowerUp p = powerup.get(i);
					double x = p.getx();
					double y = p.gety();
					double r = p.getr();
					
					double dx = px -x;
					double dy = py -y;
					double dist = Math.sqrt(dx*dx + dy*dy);
					
					//collosion powerupp
					if(dist -15< pr + r +5){
						text.add(new Text(p.getx(), p.gety(), 2000, "+1 LİVES"));
						player.gainLife();
						
			
						powerup.remove(i);
						i--;

					}
				}

				//check dead enemies
				for(int i=0;i<enemy.size();i++){
					Enemy e = enemy.get(i);
					//chance for power up
					double rand = Math.random();
					//if(rand < 0.001) powerup.add(new PowerUp(1, e.getX(), e.getY()));
					//else if(rand <0.020) powerup.add(new PowerUp(3, e.getX(), e.getY()));
					//else if(rand <0.120) powerup.add(new PowerUp(2, e.getX(), e.getY()));

					if(enemy.get(i).isDead()){
		
				
						enemy.remove(i);
						i--;
					}
				}
				
			}
		}
		else if(gameState == STATE.Menu){
			menu.tick();
		}
		
		
	}
	
	public void render(){
		
		BufferStrategy bs =  this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(gameState == STATE.Game){
			if(waveNumber <= 7){
				if(player.getLives() != 0 &&player.getLives()>0){

					
					//TEXT DRAW
					for(int i=0;i<text.size();i++){
						text.get(i).render(g);
					}

					//BACKGROUND
					g.clearRect(0, 0, WIDTH, HEIGHT);
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, WIDTH, HEIGHT);
					//back.render(g);
						
					
					
					//SCORE DRAW
					g.setColor(Color.CYAN);
					Font font = new Font("Arial", Font.BOLD,15);
					g.setFont(font);
					g.drawString("SCORE : " + score , WIDTH -115, 18);
					
					//LİVES UPDATES
					for(int i=0;i<player.getLives();i++){
						g.setColor(Color.CYAN);
						g.fillOval(15 + i*20, 25, 15, 15);
					}
					
					//PLAYER DRAW
					player.render(g);
					
					//BULLET DRAW
					for(int i=0;i<bullet.size();i++){
						bullet.get(i).render(g);
					}
					
					//ENEMY DRAW
					for(int i=0;i<enemy.size();i++){
						enemy.get(i).render(g);
					}
					
					//draw Wave number
					
					if(waveStartTimer !=0 && smartEnemy.size() == 0 && enemy.size() == 0){
						if(waveNumber != 7){
							g.setFont(new Font("Century Gothics",Font.PLAIN,18));
							String s = " -  L E V E L   " + waveNumber + "   -";
							int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
							int alpha =(int)(255*Math.sin(3.14*waveStartTimerDiff/waveDelay));
							if(alpha < 255) alpha = 255;
							g.setColor(new Color(255,255,255,alpha));
							g.drawString(s, WIDTH/2 - length/2, HEIGHT/2);
						}
						else{
							g.setFont(new Font("Century Gothics",Font.PLAIN,18));
							String s = " -  B O S S -";
							int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
							int alpha =(int)(255*Math.sin(3.14*waveStartTimerDiff/waveDelay));
							if(alpha < 255) alpha = 255;
							g.setColor(new Color(255,255,255,alpha));
							g.drawString(s, WIDTH/2 - length/2, HEIGHT/2);
							
						}
						
					}
					
					//power up draw
					for(int i=0;i<powerup.size();i++){
						PowerUp power = powerup.get(i);

						
						//PowerUp draw
						powerup.get(i).draw(g);
					}
					
					//LİVES optimization
					if(score >= 50 && 60 > score){
						g.setFont(new Font("Century Gothics",Font.PLAIN,18));
						String s = " -  B O N U S - L İ V E S  -";
						int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
						g.setColor(new Color(255,255,255));
						g.drawString(s, WIDTH/2 - length/2, HEIGHT/2);
						
					}
					//FİRE OPTİMİZATİON
					if(score >= 440 && 700> score){
						g.setFont(new Font("Century Gothics",Font.PLAIN,18));
						String s = " -  B O N U S - F İ R E   -";
						int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
						g.setColor(new Color(255,255,255));
						g.drawString(s, WIDTH/2 - length/2, HEIGHT/2);
					
						}
					
					//SMART ENEMY DRAW
					for (int i=0;i<smartEnemy.size();i++){
						smartEnemy.get(i).render(g);
						SmartEnemy e = smartEnemy.get(i);
						if(waveNumber != 7){
							g.drawOval((int) (e.getX() -(e.getR()/2)-120) , (int) (e.getY()-(e.getR()/2) -120) ,(int) e.getR()*15,(int) e.getR()*15);
							}
						else{
							g.drawOval((int) (e.getX() -(e.getR()/2)-65) , (int) (e.getY()-(e.getR()/2) -65) ,(int) e.getR()*15,(int) e.getR()*15);
						}
						}

					}
					
					
					
					else if(player.getLives()<= 0){
						g.setColor(Color.BLUE);
						g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
						g.setFont(new Font("Century Gothics",Font.PLAIN,18));
						String s = " 	-G A M E  -  O V E R   -";
						int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
						g.setColor(new Color(255,255,255));
						g.drawString(s, GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2);
					}
			}
			else {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Century Gothics",Font.PLAIN,18));
				String s = " 	-TOTALLY SCORE : -   " + score;
				int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				g.setColor(new Color(255,255,255));
				g.drawString(s, GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2);
			}
		}
		else if(gameState == STATE.Menu){
			menu.render(g);
		}

		
		bs.show();
		g.dispose();
		
		
	}
	
	public void createPowerUp(){
		powerup.clear();
		if(waveNumber == 1){
			for(int i=0;i<1;i++){
				powerup.add(new PowerUp(1));
			}
		}
		if(waveNumber == 2){
			for(int i=0;i<1;i++){
				powerup.add(new PowerUp(2));
			}
		}
		if(waveNumber == 3){
			for(int i=0;i<2;i++){
				powerup.add(new PowerUp(3));
			}
		}
		if(waveNumber == 4){
			for(int i=0;i<2;i++){
				powerup.add(new PowerUp(4));
			}
		}
		if(waveNumber == 5){
			for(int i=0;i<2;i++){
				powerup.add(new PowerUp(5));
			}
		}
		if(waveNumber == 6){
			for(int i=0;i<3;i++){
				powerup.add(new PowerUp(6));
			}
		}
	}
	
	public void createSmartEnemy(){
		smartEnemy.clear();
		if(waveNumber == 1){
			for(int i=0;i<2;i++){
				smartEnemy.add(new SmartEnemy(1, 1));
			}
		}
		if(waveNumber == 2){
			for(int i=0;i<4;i++){
				smartEnemy.add(new SmartEnemy(2, 2));
			}
		}
		if(waveNumber == 3){
			for(int i=0;i<6;i++){
				smartEnemy.add(new SmartEnemy(3, 3));
			}
		}
		if(waveNumber == 4){
			for(int i=0;i<6;i++){
				smartEnemy.add(new SmartEnemy(4, 4));
			}
		}
		if(waveNumber == 5){
			for(int i=0;i<7;i++){
				smartEnemy.add(new SmartEnemy(5, 5));
			}
		}
		if(waveNumber == 6){
			for(int i=0;i<8;i++){
				smartEnemy.add(new SmartEnemy(6, 6));
			}
		}
		if(waveNumber == 7){
			for(int i=0;i<1;i++){
				smartEnemy.add(new SmartEnemy(7, 7));
			}
		}


	}
	
	public void createNewEnemies(){
		enemy.clear();
		Enemy e;
		if(waveNumber == 1){
			player.increasePower(2);
			for(int i=0;i<4;i++){
				enemy.add(new Enemy(1,1));
			}
		}
		if(waveNumber == 2){
			player.increasePower(3);
			for(int i=0;i<15;i++){
				enemy.add(new Enemy(2,2));
			}
		}
		if(waveNumber == 3){
			player.increasePower(3);
			for(int i=0;i<25;i++){
				enemy.add(new Enemy(3,3));
			}
		}
		if(waveNumber == 4){
			player.increasePower(3);
			for(int i=0;i<30;i++){
				enemy.add(new Enemy(4,4));
			}
		}
		if(waveNumber == 5){
			player.increasePower(3);
			for(int i=0;i<30;i++){
				enemy.add(new Enemy(5,5));
			}
		}
		if(waveNumber == 6){
			player.increasePower(3);
			for(int i=0;i<40;i++){
				enemy.add(new Enemy(6,6));
			}
		}
		
	}
	

	
	public void run(){
		init();
		
		double FPS = 60.0;
		double timePerSecond = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long now;
		long timer = System.currentTimeMillis();
		
		int frames = 0;
		int update = 0;
		
		
		while(running){
			
			now = System.nanoTime();
			delta += (now - lastTime) / timePerSecond;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				update++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000){
				
				timer += 1000;
				System.out.println("FPS : " + update + "   Updates : " + frames);
				frames = 0;
				update = 0;
			}
		}
		
		stop();
		
	}
	
	public synchronized void start(){
		
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();

	}
	
	public synchronized void stop(){
		
		if(!running)
			return;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			player.setRight(true);

		}
		if(key == KeyEvent.VK_A){
			player.setLeft(true);
		}
		if(key == KeyEvent.VK_W){
			player.setUp(true);
		}
		if(key == KeyEvent.VK_S){
			player.setDown(true);
		}
		if(key == KeyEvent.VK_SHIFT){
			player.setSprith(true);
		}
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}

		if(key == KeyEvent.VK_SPACE){

			player.setFiring(true);
	
		}
		
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			player.setRight(false);
		}
		if(key == KeyEvent.VK_A){
			player.setLeft(false);
		}
		if(key == KeyEvent.VK_W){
			player.setUp(false);
		}
		if(key == KeyEvent.VK_S){
			player.setDown(false);
		}
		if(key == KeyEvent.VK_SHIFT){
			player.setSprith(false);
		}
		if(key == KeyEvent.VK_SPACE){

			player.setFiring(false);
		}
		
	}
	public void keyTyped(KeyEvent e){
		
	}

}

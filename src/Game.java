import java.awt.Dimension;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		GamePanel panel  = new GamePanel();
		
		
		frame.setPreferredSize(new Dimension(GamePanel.WIDTH,GamePanel.HEIGHT));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.pack();
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		panel.start();
	}
	
	public JFrame getFrame(JFrame frame){
		return frame;
	}

}

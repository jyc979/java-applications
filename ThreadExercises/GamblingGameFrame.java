package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Slot Machine Style Gambling Machine
 * Click on any region inside the JFrame and the numbers in the three boxes will change. 
 * If they all match, then you win. If they don't you lose. 
 **/

public class GamblingGameFrame extends JFrame{
	JLabel [] label = new JLabel [3];
	public GamblingGameFrame() {
		super("Gambling Game"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(new GamePanel()); // GamePanel

		setSize(300,200);
		setVisible(true);
		
		// GamePanel
 
	}
	
	
	class GamePanel extends JPanel {
		
		JLabel result = new JLabel("Let's Gamble"); 
		GamblingThread Thread = new GamblingThread(label,result);;
		public GamePanel() {
			setLayout(null); 
			
			
			for(int i=0; i<label.length; i++) {
				label[i] = new JLabel("0"); 
				label[i].setSize(60, 30); 
				label[i].setLocation(30+80*i, 50); 
				label[i].setHorizontalAlignment(JLabel.CENTER); 
				label[i].setOpaque(true); 
				label[i].setBackground(Color.MAGENTA); 
				label[i].setForeground(Color.YELLOW); 				
				label[i].setFont(new Font("Tahoma", Font.ITALIC, 30)); 
				add(label[i]); 
			}
			
			
			result.setSize(200, 20);
			result.setLocation(100, 120);
			add(result);
			Thread.start();
			
			
			addMouseListener(new MouseAdapter() { // KeyAdapter
				public void mousePressed(MouseEvent e) {
						
						Thread.startGambling();
						
				
				}
			});
		}
		class GamblingThread extends Thread{
			JLabel label[];
			JLabel result;
			GamblingThread(JLabel label[],JLabel result){
				this.label = label;
				this.result = result;
			}
			boolean gambling = false;
			synchronized public void waitForGambling(){
				if(!gambling)
					try{this.wait();}
				catch(InterruptedException e){return;}
			}
			synchronized public void startGambling(){
				gambling = true;
				this.notify();
			}
			public void run(){
				while(true){
					waitForGambling();
					if(gambling == true){
						int x1 = (int)(Math.random()*5);
						int x2 = (int)(Math.random()*5);
						int x3 = (int)(Math.random()*5);
					
					try{
						sleep(200);
						label[0].setText(Integer.toString(x1));
						sleep(200);
						label[1].setText(Integer.toString(x2));
						sleep(200);
						label[2].setText(Integer.toString(x3));
					}
					catch(InterruptedException e){
						return;
					}
					if(x1==x2&&x2==x3){
						result.setText("grats");
					}
					else{
						result.setText("BOO");
					}
					}
					gambling =false;
				}
			}
		}
	}
	
	static public void main(String[] arg) {
		new GamblingGameFrame();
	}
}


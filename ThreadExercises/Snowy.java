package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;


/**
 *  Using Thread, gave a snowing effect on an image. 
 */
public class Snowy extends JFrame{
	Vector<Point> snowVector = new Vector<Point>();
	backPanel panel;
	SnowThread b = null;
	Snowy(){
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new backPanel();
		setContentPane(panel);
		b = new SnowThread(panel);
		setSize(500,500);
		setVisible(true);
		b.start();
	}
	class backPanel extends JPanel{
		ImageIcon icon = new ImageIcon("images/back.jpg");
		Image img = icon.getImage();
		backPanel(){
			for(int i=0;i<50;i++){
				int x = (int)(Math.random()*500);
				int y = 0;
				Point p = new Point(x,y);
				snowVector.add(p);
				
			}
			
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), this);
			g.setColor(Color.WHITE);
			for(int i=0;i<snowVector.size();i++){
				Point p = snowVector.get(i);
				g.fillOval(p.x, p.y, 10, 10);
			}
		}
		public void changeSnowPosition(){
			for(int i=0; i<snowVector.size();i++){
				Point p = snowVector.get(i);
				if(p.y>panel.getHeight()){
					p.y=0;
				}
				int drop = (int)(Math.random()*panel.getHeight());
				p.y += drop;
				
			}
		}
	}
	class SnowThread extends Thread{
		JPanel b;
		SnowThread(JPanel b){
			this.b =b; 
		}
		int count =0;
		public void run(){
			while(true){
				
				try{
					sleep(100);
				}
				catch(InterruptedException e){return;}
				panel.changeSnowPosition();
				repaint();
			}
		}
	}
	

	public static void main(String[] args) {
		new Snowy();
		

	}

}

package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;


/**
 * Every time you click somewhere inside the JFrame, an image of chicken appears 
 * and it flies upward. 
 */

public class CountLabel1 extends JFrame{
	Container c;
	draw panel;
	CountLabel1(){
		setTitle("ex");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new draw();
		setLayout(new BorderLayout());
		
		
		
		add(panel,BorderLayout.CENTER);
		setSize(400,300);
		setVisible(true);
	}
class draw extends JPanel{
	JLabel bub;
	ImageIcon icon = new ImageIcon("images/chicken.jpg");
	Image img = icon.getImage();
	upThread b = null;
	draw(){
		setLayout(null);
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				bub = new JLabel(icon);
				bub.setSize(30,30);
				bub.setLocation(e.getX(),e.getY());
				b = new upThread(bub);
				add(bub);
				repaint();
				b.start();
			}
			
		});
	}
	
}
class upThread extends Thread{
	JLabel upLabel;
	upThread(JLabel upLabel){
		this.upLabel = upLabel;
	}
	public void run(){
	while(true){
		try{
			if(upLabel.getLocation().y<=0){
				panel.remove(upLabel);
				panel.repaint();
				
			}
			upLabel.setLocation(upLabel.getLocation().x,upLabel.getLocation().y-5);
			sleep(200);
		}
		catch(InterruptedException e){return;}
	}
	}
}

	


	public static void main(String[] args) {
		new	CountLabel1();
	}
}

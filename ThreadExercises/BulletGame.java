package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;


/**
 * Hit the floating chicken with a red bullet!
 * Using Thread the image of chicken floats from left to right at the top and your job
 * is to hit the chicken by shooting the bullet at the right time.
 */
public class BulletGame extends JFrame{
	int startX = 290;
	int startY = 283;
	int moveX = 0;
	int moveY = 0;
	content panel;
	JLabel bullet ;
	moveThread b =null;
	shootThread c =null;
	ImageIcon icon = new ImageIcon("images/chicken.jpg");
	Image img = icon.getImage();
	public BulletGame(){
		super("BulletGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		panel = new content();
		b = new moveThread();
		add(panel,BorderLayout.CENTER);
		setSize(600,400);
		setVisible(true);
		b.start();
		bullet.requestFocus();
	}
public boolean hit(){
	if(startX+10<=moveX+icon.getIconWidth()&&moveX<=startX+10&&startY+10<=moveY+icon.getIconHeight()&&0<startY+10){
		System.out.print("YOU GOT IT!");
		return true;
	}
	else
		return false;
}
class content extends JPanel{
	content(){
		setLayout(null);
		bullet = new JLabel();
		bullet.setBackground(Color.RED);
		bullet.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					c = new shootThread();
					c.start();
				}
			}
		});
		bullet.setOpaque(true);
		bullet.setSize(20,20);
		bullet.setLocation(startX,startY);
		add(bullet);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, moveX, moveY, icon.getIconWidth(), icon.getIconHeight(), this);
		g.fillRect(this.getWidth()/2-20, this.getHeight()-50, 50, 50);
		g.setColor(Color.RED);
	}
}
class moveThread extends Thread{
	int increment =5;
	public void run(){
		while(true){
			moveX += increment;
			if(moveX > 530){
				increment = -5;
			}
			else if(moveX<0){
				increment =5;
			}
			else if(hit()){
				c.interrupt();
				moveX =0;
			}
			
			
			
			try{
				sleep(20);
				repaint();
			}
			catch(InterruptedException e){
				return;
			}
		}
	}
}
class shootThread extends Thread{
	int increment =0;
	public void run(){
		while(true){
			if(startY<0){
				increment =0;
				startY = 283;
				bullet.setLocation(startX, startY);
				return;
			}
			
			
			else{
				increment =5;
				startY-=increment;
			}
			try{
				sleep(20);
				bullet.setLocation(startX,startY);
				repaint();
			}
			catch(InterruptedException e){
				startY = 283;
				bullet.setLocation(startX, startY);
				return;
			}
		}
	}
}

	
	
	public static void main(String[] args) {
		new BulletGame();
		
	}

}

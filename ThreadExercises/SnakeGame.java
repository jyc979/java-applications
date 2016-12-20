package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.util.*;


/**
 * Using Thread, Create a snake-like figure and move around.
 * There isn't necessarily losing or winning in this version. It's more like a test version
 */
public class SnakeGame extends JFrame{
	pane panel;
	int left =0;
	int right =1;
	int up =2;
	int down =3;
	int direction = 0;
	Vector<JLabel> snake;
	moveThread1 b = null;
	SnakeBody snake1;
	SnakeGame(){
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setLayout(new BorderLayout());
		panel = new pane();
		b = new moveThread1(panel);
		add(panel);
		setSize(600,400);
		setVisible(true);
		panel.requestFocus();
		b.start();
		
		
	}
	class pane extends JPanel{
		ImageIcon icon = new ImageIcon("images/twi.jpg");
		Image img =icon.getImage();
		pane(){
		snake1 = new SnakeBody();
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_UP){
					direction = up;
					System.out.print(direction);
				}
				else if(e.getKeyCode()==KeyEvent.VK_DOWN){
					direction = down;
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					direction = right;
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT){
					direction = left;
				}
			}
		});
		snake1.add(this);
		
		
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0,getWidth(),getHeight(),this);
		}
	}
	class SnakeBody{
		
		SnakeBody(){
			snake = new Vector<JLabel>();
			ImageIcon headimg = new ImageIcon("images/head.jpg");
			JLabel head = new JLabel(headimg);
			head.setSize(headimg.getIconWidth(),headimg.getIconHeight());
			head.setLocation(100,100);
			snake.add(head);
			for(int i=1;i<5;i++){
				ImageIcon bodyimg = new ImageIcon("images/body.jpg");
				JLabel body = new JLabel(bodyimg);
				body.setSize(bodyimg.getIconWidth(),bodyimg.getIconHeight());
				body.setLocation(100+i*20,100);
				snake.add(body);
			}
		}
		public void add(JPanel g){
			for(int i=0;i<5;i++){
			g.add(snake.get(i));
			}
		}
		public void move(int direction){
			for(int i=snake.size()-1;i>0;i--){
				JLabel b = snake.get(i);
				JLabel a = snake.get(i-1);
				b.setLocation(a.getX(),a.getY());
			}
			JLabel head = snake.get(0);
			if(direction == up){
				head.setLocation(head.getLocation().x,head.getLocation().y-20);
			}
			else if(direction == down){
				head.setLocation(head.getLocation().x, head.getLocation().y+20);
			}
			else if(direction == right){
				head.setLocation(head.getLocation().x+20, head.getLocation().y);
			}
			else if(direction == left){
				head.setLocation(head.getLocation().x-20, head.getLocation().y);
			}
		}
		
	}
	class moveThread1 extends Thread{
		JPanel b;
		moveThread1(JPanel b){
			this.b = b;
		}
		public void run(){
			while(true){
				try{
					sleep(200);
					snake1.move(direction);
					
				}
				catch(InterruptedException e){
					return;
				}
			}
		}
	}
	public static void main(String[] args) {
		new SnakeGame();

	}

}

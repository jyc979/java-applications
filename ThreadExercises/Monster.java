package ThreadExercises;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;



/**
 * Simple Game with monster chasing you!
 * Run away from Monster as much as you can
 * Monster is represented as a letter 'M' and the player is '@'
 * It keeps following you and you can use Keyboard keys(up,down left,right) to dodge the Monster!
 */
public class Monster extends JFrame{
	JLabel ava = new JLabel("@");
	JLabel monster = new JLabel("M");
	JLabel result = new JLabel("run");
	drawPanel panel;
	MonsterThread thread = null;
	Monster(){
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new drawPanel();
		setContentPane(panel);
		thread = new MonsterThread(monster);
		setSize(400,400);
		setVisible(true);
		panel.requestFocus();
		thread.start();
	}
	class drawPanel extends JPanel{
		drawPanel(){
			setLayout(null);
			ava.setSize(15,15);
			ava.setForeground(Color.RED);
			ava.setLocation(50,50);
			monster.setSize(15,15);
			monster.setLocation(100,100);
			addKeyListener(new key());
			result.setSize(100,20);
			result.setLocation(170,170);
			add(result);
			add(ava);
			add(monster);
		}
	}
	class key extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(e.getKeyChar()=='q')
				System.exit(0);
			else if(e.getKeyCode()==KeyEvent.VK_UP){
				ava.setLocation(ava.getLocation().x,ava.getLocation().y-10);
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN){
				ava.setLocation(ava.getLocation().x, ava.getLocation().y+10);
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				ava.setLocation(ava.getLocation().x+10, ava.getLocation().y);
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT){
				ava.setLocation(ava.getLocation().x-10, ava.getLocation().y);
			}
		}
	}
	class MonsterThread extends Thread{
		JLabel monster;
		MonsterThread(JLabel monster){
			this.monster = monster;
		}
		public void run(){
			while(true){
				try{
					int x = ava.getLocation().x;
					int y = ava.getLocation().y;
					int mx = monster.getLocation().x;
					int my = monster.getLocation().y;
					if(x>mx){
						sleep(200);
						monster.setLocation(mx+5,my);
					}
					else if(x<mx){
						sleep(200);
						monster.setLocation(mx-5, my);
					}
					else if(y>my){
						sleep(200);
						monster.setLocation(mx, my+5);
					}
					else if(y<my){
						sleep(200);
						monster.setLocation(mx,my-5);
					}
					else if(x==mx&&y==my){
						result.setText("YOU LOST");
					}
					
				}
				catch(InterruptedException e){return;}
			}
		}
	}
	public static void main(String[] args) {
		new Monster();

	}

}

package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;


/**
 * The word java will appear at different location
 */

class TimerThread extends Thread{
	Container contentPane;
	boolean flag = false;
	public TimerThread(Container contentPane){
		this.contentPane = contentPane;
	}
	void finish(){
		flag=true;
	}
	public void run(){
		while(true){
			int x = ((int)(Math.random()*contentPane.getWidth()));
			int y = ((int)(Math.random()*contentPane.getHeight()));
			JLabel label = new JLabel("java");
			label.setSize(80,30);
			label.setLocation(x,y);
			contentPane.add(label);
			contentPane.repaint();
			
			try{
				Thread.sleep(300);
				if(flag == true){
					contentPane.removeAll();
					label = new JLabel("finish");
					label.setSize(80, 30);
					label.setLocation(100, 100);
					label.setForeground(Color.RED);
					contentPane.add(label);
					contentPane.repaint();
					return;
				}
			}
			catch(InterruptedException e){
				return;
			}
		}
	}
}
public class ThreadEx extends JFrame{
	TimerThread th;
	public ThreadEx(){
		setTitle("g");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		th = new TimerThread(c);
		c.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				th.finish();
			}
		});
		
		setSize(300,400);
		setVisible(true);
		th.start();
	}

	public static void main(String[] args) {
		new ThreadEx();
	}

}

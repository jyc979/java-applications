package ThreadExercises;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * Once the game starts, you will see an orange bar in the center. Press any key
 * to fill the bar with pink color. The bar itself will resist the change at
 * certain pace.
 */
class MyLabel extends JLabel{
	int barSize=0;
	int maxBarSize;
	MyLabel(int maxBarSize){
		this.maxBarSize = maxBarSize;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.MAGENTA);
		int width = (int)(((double)(this.getWidth()))/maxBarSize*barSize);
		if(width ==0) return;
		g.fillRect(0, 0, width, this.getHeight());
	}
	synchronized void fill(){
		System.out.println(Thread.currentThread().getName()+"Entered. FILL METHOD");
		if(barSize==maxBarSize){
			try{
				System.out.println(Thread.currentThread().getName()+"WAIT IN FILL");
				wait();
			}
			catch(InterruptedException e){return;}
		}
		barSize++;
		repaint();
		notify();
		System.out.println(Thread.currentThread().getName()+"NOTIFY in FILL METHOD.");
	}
	synchronized void consume(){
		System.out.println(Thread.currentThread().getName()+"ENTERED. CONSUME METHOD");
		if(barSize==0){
			try{
				System.out.println(Thread.currentThread().getName()+"WAIT IN CONSUME.");
				wait();
			}catch(InterruptedException e){return;}
		}
		barSize--;
		repaint();
		notify();
		System.out.println(Thread.currentThread().getName()+"NOTIFY IN CONSUME METHOD.");
	}
}
class ConsumerThread extends Thread{
	MyLabel bar;
	ConsumerThread(MyLabel bar){
		this.bar = bar;
	}
	public void run(){
		while(true){
			try{
				sleep(200);
				bar.consume();
				
			}catch(InterruptedException e){return;}
		}
	}
}

public class TabAndThreadEx extends JFrame{
	MyLabel bar = new MyLabel(100);
	TabAndThreadEx(String title){
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		bar.setBackground(Color.ORANGE);
		bar.setOpaque(true);
		bar.setLocation(20,50);
		bar.setSize(300,20);
		c.add(bar);
		c.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				bar.fill();
			}
		});
		setSize(350,200);
		setVisible(true);
		c.requestFocus();
		ConsumerThread th = new ConsumerThread(bar);
		th.start();
	}
	public static void main(String[] args) {
		new TabAndThreadEx("Go");

	}

}

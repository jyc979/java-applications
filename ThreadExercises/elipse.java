package ThreadExercises;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Once you press somewhere inside the JFrame, an elipse appears.
 * It changes its shape and moves around inside the JFrame.
 */


public class elipse extends JFrame{
	Container c;
	drawPanel panel;
	int x =0 ;
	int y =0;
	int width = 0;
	int height =0;
	drawThread b = null;
	boolean inMouse = false;
	elipse(){
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new drawPanel();
		setContentPane(panel);
		
		b = new drawThread(panel);
		
		setSize(400,400);
		setLocation(500,500);
		setVisible(true);
		b.start();
	}
	class drawPanel extends JPanel{
		drawPanel(){
			setLayout(null);
			addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent e){
					b.startDrawing();
				}
				@Override
				public void mouseExited(MouseEvent e){
					b.waitForMouse();
					inMouse = false;
					
					
				}
			});
			
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.MAGENTA);
			g.fillOval(x, y, width, height);
		}
	}
	class drawThread extends Thread{
		JPanel p;
		drawThread(JPanel p){
			this.p = p;
		}
		synchronized public void waitForMouse(){ 
			 if(!inMouse){
				 try{
					 this.wait();
				 }catch(InterruptedException e){return;}
			 }
			
		}
		synchronized public void startDrawing(){
			inMouse = true;
			if(inMouse)
			this.notify();
		}
		public void run(){
			while(true){
			waitForMouse();
			try{
				x = (int)(Math.random()*panel.getWidth());
				y = (int)(Math.random()*panel.getHeight());
				width = (int)(Math.random()*100+1);
				height =(int)(Math.random()*100+1);
				sleep(300);
				repaint();
			}
			catch(InterruptedException e){return;}
			}
		}
	}

	public static void main(String[] args) {
		new elipse();

	}

}

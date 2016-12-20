package LearnGUI;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Typing Game
 * Words dropping from the top and the player needs to type them before
 * they get to the bottom
 */
public class TypingGame extends JFrame{
	JTextField type; //the textfield at the bottom center
	Container pane; // main JFrame container pane
	String[] list = {"hello","euphoria","rebellion","underworld","yo","you","zoo",
			"hail","hoo","hee","count","word","work","please","why","do","this","to","me"
			,"twenty","go","vote","hehe"}; //23 words
	
	ArrayList<JLabel> data = new ArrayList<JLabel>();
	drawPanel panel; // NORTH panel with text field and gray area
	mainPanel panel2; //CENTER panel with words dropping.
	dropThread thread = null; //thread to drop words
	ArrayList<Integer> heightList = new ArrayList<Integer>(); //randomize the starting y.pos of words
	TypingGame(){
		super("Type Ex");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		
		
		pane = getContentPane();
		panel = new drawPanel();
		panel2 = new mainPanel();
		setContentPane(pane);
		pane.setLayout(new BorderLayout());
		pane.add(panel, BorderLayout.SOUTH);
		pane.add(panel2, BorderLayout.CENTER);
		
		
		setBounds(500,100,800,800);
		setVisible(true);
		type.requestFocus();
	}
	class drawPanel extends JPanel{
		drawPanel(){
			setBackground(Color.GRAY); //gray area 
			Font font = new Font("Arial",Font.BOLD,20);
			type = new JTextField(12); //text field at the bottom center
			type.setFont(font);
			type.addKeyListener(new KeyAdapter(){
				@Override
				public void keyPressed(KeyEvent e){
					if(e.getKeyCode()==KeyEvent.VK_ENTER){ //TYPE ENTER AND CHECK if words are same
						for(int i=0;i<23;i++){
							if(type.getText().equals(list[i])){ 
								
								panel2.remove(data.get(i));   

							

								panel2.revalidate();  

								panel2.repaint(); 

								
								
								type.setText(""); //textfield gets cleared after enter.
							}
						}
					}
				}
			});
			add(type);
			
		}
	}
	class mainPanel extends JPanel{ 
		mainPanel(){ //the center panel where words get dropped.
			setLayout(null);
			for(int i=0;i<23;i++){ //in order to randomize the starting y.pos of word
				heightList.add(-60+i*-100);
			}
			Collections.shuffle(heightList);
			for(int i=0;i<23;i++){ //ADD words at random x.pos and y.pos and start threads.
				Random rnd = new Random();
				int width = (int)(Math.random()*730+1); //randomize x.pos of words
				data.add(new JLabel(list[i]));
				data.get(i).setFont(new Font("Godic",Font.BOLD,15));
				data.get(i).setSize(150,100);
				data.get(i).setLocation(width,heightList.get(i));
				thread = new dropThread(data.get(i));
				add(data.get(i));
				thread.start();
			}
			
			
		}
	}
	class dropThread extends Thread{ //words dropping thread
		JLabel label;
		dropThread(JLabel label){
			this.label= label;
			System.out.println("started thread"+label.getText());
		}
		public void run(){
			while(true){
			try{
				if(label.getLocation().y>670){
											
					System.out.println(label.getText());//if gray area reached, print You Lose.
					return;
				}
				
				int delay = 800; //delay 800
				sleep(delay);
				label.setLocation(label.getLocation().x,label.getLocation().y+50);//drop by 15
				
			}catch(InterruptedException e){
				return;
			}
			}
		}
	}
	

	public static void main(String[] args) {
		new TypingGame();
	}

}


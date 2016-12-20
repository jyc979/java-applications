package ThreadExercises;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 369 Game Using Java Gui.
 * Hit start button to start the game.
 * Once the game starts, the number in the box goes up one by one.
 * If there is 3,6,9 in any digit, then press the counting box in order to not lose and keep playing 
 * the game. Once you fail to do so, the game stops and you lose. You can always replay.
 **/
public class Game369singleFrame extends JFrame{
	public Game369singleFrame() {
		setTitle("369 GAME!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new GamePanel());
		setSize(300,300);
		setVisible(true);	
	}
	
	class GamePanel extends JPanel {
		TimerThread th; 
		JLabel card = new JLabel(); 
		JButton startBtn = new JButton("start");
		int n = 1; 
		boolean singleClicked=false; 
		boolean doubleClicked=false; 
		
		GamePanel() {
			setLayout(null);

			// card
			card.setOpaque(true);
			card.setBackground(Color.ORANGE);
			card.setFont(new Font("Arial", Font.ITALIC, 30)); 
			card.setHorizontalAlignment(JLabel.CENTER); 
			card.setText(Integer.toString(n)); 
			card.setSize(100, 50);
			card.setLocation(100,100);
			card.addMouseListener(new MouseHandler()); 
			add(card);
					
			//startBtn
			startBtn.setLocation(100, 200);
			startBtn.setSize(100, 30);
			
			
			startBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					n = 1;
					card.setText(Integer.toString(n)); 
					singleClicked = false;
					doubleClicked = false; 
					th = new TimerThread();
					th.start(); 
					
					
					JButton b = (JButton)e.getSource();
					b.setEnabled(false); 
				}
			});
			add(startBtn);
		}
		
		synchronized void setSingleClicked(boolean b) {
			singleClicked = b;
		}
		
		synchronized void setDoubleClicked(boolean b) {
			doubleClicked = b;
		}
		
		class TimerThread extends Thread {
			public void run() {
				while(true) {
					try {
						sleep(700); 



						
						int x = n%10; 
						int y = n/10; 
						
						if((x == 3 || x == 6 || x == 9) && (y == 3 || y == 6 || y == 9)) { 
							if(doubleClicked == true) { 
								setDoubleClicked(false); 							
							}
							else {
								msg("Fail^^");
								break;							
							}
						}
						else if(x == 3 || x == 6 || x == 9 || y == 3 || y == 6 || y == 9) { 
							if(singleClicked == true) {
								setSingleClicked(false); 	
							}
							else {
								msg("Fail^");
								break;
							}
						}
						else {
							if(singleClicked == true || doubleClicked == true) { 
								msg("Fail!");
								break;
							}
						}
						
						n++; // ���� ����
						if(n==100) {
							msg("Win!!");
							break; // ������ �����Ѵ�.
						}
						else
							card.setText(Integer.toString(n)); // ���ڸ� ī�忡 ���	
					} catch (InterruptedException e) {
						return; // ���α׷� ����						
					}
				}
				startBtn.setEnabled(true); // ���� ��ư Ȱ��ȭ			
			}
			
			void msg(String s) {
				card.setText(s);
			}
		}
		
		class MouseHandler extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount() == 2) { // ���� Ŭ��. 33, 66, 99 ���� ���
					setDoubleClicked(true); // ���� ���콺 Ŭ�� ǥ��
					setSingleClicked(false); // �̱� ���콺 Ŭ�� ǥ��					
				}
				else { // �̱� Ŭ��
					setSingleClicked(true); // �̱� ���콺 Ŭ�� ǥ��					
				}
				JLabel card = (JLabel)e.getSource();
				card.setBackground(Color.GREEN);
			}
			public void mouseReleased(MouseEvent e) {
				JLabel card = (JLabel)e.getSource();
				card.setBackground(Color.ORANGE);				
			}			
		}
	}
	

	public static void main(String[] args) {
		new Game369singleFrame();
	}

}
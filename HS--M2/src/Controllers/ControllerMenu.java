package Controllers;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import windows.HelpWindow;
import windows.MenuWindow;

public class ControllerMenu implements ActionListener{
	private MenuWindow menuWindow;
	private JFrame frame2;
	public ControllerMenu() throws IOException {
		menuWindow = new MenuWindow();
		menuWindow.getPlay().addActionListener(this);
		menuWindow.getHelp().addActionListener(this);		
		menuWindow.getJoin().addActionListener(this);
		String[] notification = {"java","-cp","./bin","windows.notifywait"};
		ProcessBuilder x = new ProcessBuilder(notification);
		x.start();
		String[] Data = {"java","-cp","./bin","windows.datain"};
		ProcessBuilder y = new ProcessBuilder(Data);
		y.start();
		System.out.println("done");
	}
	public void actionPerformed(ActionEvent e) {
		JButton button =(JButton) (e.getSource());
		if (button.getText().equals("Play")) {
			menuWindow.getFrame().setVisible(false);
			try {
				new ControllerHero();	
			} catch (Exception e2) {
			}
		}
		else if(e.getActionCommand().equals("Join a friend")) {
			frame2 = new JFrame();
			frame2.setVisible(true);
			frame2.setSize(new Dimension(300,100));
			frame2.setLayout(new FlowLayout());
			
			frame2.add(new JLabel("Enter IP"));
			frame2.add(menuWindow.getIp());
			menuWindow.getIp().setPreferredSize(new Dimension(100,50));
			JButton connect = new JButton("Connect!");
			connect.addActionListener(this);
			frame2.add(connect);
			
		}
		else if(e.getActionCommand().equals("Connect!")) {
					String[] x=  {"java","-cp","./bin","windows.ChatWindowClient",menuWindow.getIp().getText()};
					ProcessBuilder p =new ProcessBuilder(x);
					System.out.println("Clicked");
					frame2.setVisible(false);
					try {
						p.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(menuWindow.getFrame(), "Error:"+e1.getMessage());
							e1.printStackTrace();
						}
					}
		
		else if(button.getText().contentEquals("Help")) {
			new HelpWindow();
		}
	}
	public static void main(String[] args) {
		try {
			new ControllerMenu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

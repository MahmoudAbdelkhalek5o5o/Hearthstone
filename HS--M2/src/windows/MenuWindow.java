package windows;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
@SuppressWarnings("serial")
public class MenuWindow extends JFrame{
	private JFrame frame;
	private JButton play;
	private JButton help;
	private JPanel buttons;
	private JLabel i;
	private JPanel Sizeable;
	private JPanel Sizeable2;
	private JFrame frame2;
	private JTextField ip = new JTextField();
	private JButton join;

	public MenuWindow()  {
		frame = new JFrame("HearthStone");
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setSize(new Dimension(1920, 1080));
		frame.setVisible(true);
		i = new JLabel(new ImageIcon(
				((new ImageIcon(getClass().getResource("/menu.jpg"))).getImage())
				.getScaledInstance(1600, 850, java.awt.Image.SCALE_SMOOTH)));
		i.setSize(new Dimension(1620,900));
		i.setLayout(new GridLayout(0,3));
		
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setVisible(true);
		Sizeable = new JPanel();
		Sizeable.setVisible(false);
		Sizeable2 = new JPanel();
		Sizeable2.setVisible(false);
		JButton x = new JButton();
		x.setVisible(false);
		JButton y = new JButton();
		y.setVisible(false);
		JButton z = new JButton();
		z.setVisible(false);
		JButton m = new JButton();
		m.setVisible(false);
		buttons.setLayout(new GridLayout(10,0));
		
		join = new JButton(new ImageIcon(
				((new ImageIcon(getClass().getResource("/Join a Friend.png"))).getImage())
				.getScaledInstance(500, 80, java.awt.Image.SCALE_SMOOTH)));
		join.setText("Join a friend");
		join.setFocusPainted(false);
		join.setContentAreaFilled(false);
		play = new JButton(new ImageIcon(
				((new ImageIcon(getClass().getResource("/Play.png"))).getImage())
				.getScaledInstance(500, 80, java.awt.Image.SCALE_SMOOTH)));
		play.setFocusPainted(false);
		play.setContentAreaFilled(false);
		play.setText("Play");
		
		help = new JButton(new ImageIcon(
				((new ImageIcon(getClass().getResource("/Helpy.png"))).getImage())
				.getScaledInstance(500, 80, java.awt.Image.SCALE_SMOOTH)));
		help.setFocusPainted(false);
		help.setContentAreaFilled(false);
		help.setText("Help");
		
//		play.setBorderPainted(false);
//		play.setFocusPainted(false);
//		play.setContentAreaFilled(false);
		buttons.add(x);
		buttons.add(y);
		buttons.add(play);
		buttons.add(z);
		buttons.add(help);
		buttons.add(m);
		buttons.add(join);
		buttons.setOpaque(false);
		play.setPreferredSize(new Dimension(200,100));
		help.setPreferredSize(new Dimension(200,100));
		join.setPreferredSize(new Dimension(200,100));
		i.add(Sizeable); 
		i.add(buttons); //!
		i.add(Sizeable2); //!
		frame.getContentPane().add(i);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.revalidate();
		frame.repaint();
	}
	public JButton getJoin() {
		return join;
	}
	public JFrame getFrame() {
		return frame;
	}
	public JButton getPlay() {
		return play;
	}
	public JButton getHelp() {
		return help;
	}
	public JFrame getFrame2() {
		return frame2;
	}
	public JTextField getIp() {
		return ip;
	}
	public static void main(String []args) {
			new MenuWindow();
	}
}

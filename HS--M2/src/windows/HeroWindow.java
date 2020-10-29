package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.heroes.Hunter;

public class HeroWindow extends JFrame {
	private JLabel background;
	private ImageIcon image1;
	private JPanel buttons= new JPanel();
	private JButton hunter= new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/HunterB.png"))).getImage())
			.getScaledInstance(280, 80, java.awt.Image.SCALE_SMOOTH)));
	private JButton mage = new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/MageB.png"))).getImage())
			.getScaledInstance(280, 80, java.awt.Image.SCALE_SMOOTH)));
	private JButton paladin= new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/PaladinB.png"))).getImage())
			.getScaledInstance(280, 80, java.awt.Image.SCALE_SMOOTH)));
	private JButton priest= new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/PriestB.png"))).getImage())
			.getScaledInstance(280, 80, java.awt.Image.SCALE_SMOOTH)));
	private JButton warlock= new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/WarlockB.png"))).getImage())
			.getScaledInstance(280, 80, java.awt.Image.SCALE_SMOOTH)));
	private JPanel ha =new JPanel();
	private JLabel sm = new JLabel("Choose first hero",JLabel.CENTER);
	private JPanel ha2 = new JPanel();
	private JPanel ha3 = new JPanel();
	public HeroWindow() {
		super("HearthStone");
		hunter.setText("Hunter");
		hunter.setContentAreaFilled(false);
		mage.setText("Mage");
		mage.setContentAreaFilled(false);
		paladin.setText("Paladin");
		paladin.setContentAreaFilled(false);
		priest.setText("Priest");
		priest.setContentAreaFilled(false);
		warlock.setText("Warlock");
		warlock.setContentAreaFilled(false);
		this.setVisible(true);
		this.setSize(new Dimension(1920, 1080));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		image1 = new ImageIcon(getClass().getResource("/h4.jpg"));
		background = new JLabel(image1);
		buttons.setLayout(new FlowLayout());
		buttons.add(paladin);
		buttons.add(priest);
		buttons.add(warlock);
		buttons.add(hunter);
		buttons.add(mage);
		buttons.setOpaque(false);
		hunter.setPreferredSize(new Dimension(300,100));
		mage.setPreferredSize(new Dimension(300,100));
		paladin.setPreferredSize(new Dimension(300,100));
		priest.setPreferredSize(new Dimension(300,100));
		warlock.setPreferredSize(new Dimension(300,100));
		buttons.setPreferredSize(new Dimension(1920, 200));
		ha.setVisible(true);
		ha.setOpaque(false);
		sm.setBackground(Color.WHITE);
		ha.setLayout(new GridLayout(0,1));
		sm.setFont(new Font("Choose First Hero", Font.PLAIN, 50));
		ha.add(sm);
		sm.setForeground(Color.white);
		ha2.setVisible(false);
		background.setLayout(new GridLayout(3,0));
		background.add(ha);
		background.add(ha2);
		background.add(buttons);
		this.add(background);
		this.revalidate();
		this.repaint();
	}
	public JPanel getButtons() {
		return buttons;
	}
	public JButton getHunter() {
		return hunter;
	}
	public JButton getMage() {
		return mage;
	}
	public JButton getPaladin() {
		return paladin;
	}
	public JButton getPriest() {
		return priest;
	}
	public JButton getWarlock() {
		return warlock;
	}
	public JLabel getSm() {
		return sm;
	}
	public static void main(String[] args) {
		new HeroWindow();
	}
	public void setSm(JLabel sm) {
		this.sm = sm;
	}
	
}

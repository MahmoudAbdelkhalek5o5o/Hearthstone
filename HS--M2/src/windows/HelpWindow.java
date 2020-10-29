package windows;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelpWindow extends JFrame{
	private ImageIcon image = new ImageIcon(getClass().getResource("/Help.png"));
	private JLabel background = new JLabel(image);
	public HelpWindow() {
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(1210,710));
		add(background);
	}
}

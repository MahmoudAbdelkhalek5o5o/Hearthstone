package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import engine.Game;
import exceptions.FullHandException;
import model.heroes.Hero;
import windows.HeroWindow;

public class ControllerHero implements ActionListener{
	private HeroWindow heroWindow;
	private Game game;
	private Hero h1;
	private Hero h2;
	private String s1 = "";
	private String s2 = "";
	private int c=0;
	private Clip clip;
	
	public ControllerHero() throws FullHandException, IOException, CloneNotSupportedException {
		heroWindow = new HeroWindow();
		heroWindow.getHunter().addActionListener(this);
		heroWindow.getMage().addActionListener(this);
		heroWindow.getPriest().addActionListener(this);
		heroWindow.getPaladin().addActionListener(this);
		heroWindow.getWarlock().addActionListener(this);
	}
	public void play(URL file) throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		if (clip!=null)
		clip.stop();
	    AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
	    clip = AudioSystem.getClip();
	    clip.open(inputStream);
	    clip.start();
	}
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)(e.getSource());
		String s = button.getText();
		if (c==0) {
			c=1;
			if (s.equals("Hunter")) {
				s1 = s;
				try {
					play(getClass().getResource("/HunterV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Mage")) {
				s1 = s;
				try {
					play(getClass().getResource("/MageV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Paladin")) {
				s1 = s;
				try {
					play(getClass().getResource("/PaladinV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Priest")) {
				s1 = s;
				try {
					play(getClass().getResource("/PriestV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Warlock")) {
				s1 = s;
				try {
					play(getClass().getResource("/WarlockV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			heroWindow.getSm().setText("Choose Second Hero");
			heroWindow.revalidate();
			heroWindow.repaint();
			return;
		}
		if (c==1) {
			c=2;
			if (s.equals("Hunter")) {
				s2 = s;
				try {
					play(getClass().getResource("/HunterV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Mage")) {
				s2 = s;
				try {
					play(getClass().getResource("/MageV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Paladin")) {
				s2 = s;
				try {
					play(getClass().getResource("/PaladinV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Priest")) {
				s2 = s;
				try {
					play(getClass().getResource("/PriestV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			if (s.equals("Warlock")) {
				s2 = s;
				try {
					play(getClass().getResource("/WarlockV.wav"));
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					
				}
			}
			heroWindow.setVisible(false);
			while (clip.isRunning()) {
				
			}
			try {
				new ControllerGame(s1,s2);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		
	}
	public Game getGame() {
		return game;
	}
	public Hero getH1() {
		return h1;
	}
	public Hero getH2() {
		return h2;
	}
	public String getS1() {
		return s1;
	}
	public String getS2() {
		return s2;
	}
	public int getC() {
		return c;
	}
}

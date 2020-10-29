package windows;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import model.cards.minions.Minion;
import model.heroes.*;

public class GameWindow extends JFrame {
	private JFrame frame;
	private JLabel background;
	private ImageIcon image;
	private JPanel center  = new JPanel();
	//private JPanel notif  = new JPanel();
	private JPanel Deck  = new JPanel();
	private JPanel info  = new JPanel();
	private JPanel p1hand  = new JPanel();
	private JPanel p2hand  = new JPanel();
	private JPanel p1Feild  = new JPanel();
	private JPanel p2Feild  = new JPanel();
	private JPanel buttons  = new JPanel();
	private JPanel miImage = new JPanel();
	private JButton Call = new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/Call a Friend.png"))).getImage())
			.getScaledInstance(230, 50, java.awt.Image.SCALE_SMOOTH)));//can be used for network
	private JButton endturn = new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/EndTurn.png"))).getImage())
			.getScaledInstance(230, 50, java.awt.Image.SCALE_SMOOTH)));
	//private JLabel notify = new JLabel("_");
	private TitledBorder t1 = BorderFactory.createTitledBorder("Opponent Hand");
	private TitledBorder t2 = BorderFactory.createTitledBorder("Opponent Feild");
	private TitledBorder t3 = BorderFactory.createTitledBorder("Your Feild");
	private TitledBorder t4 = BorderFactory.createTitledBorder("Your Hand");
	private JButton OppHeroim;
	private JButton CurrHeroim;
	private JTextArea OppHeroinfo = new JTextArea();
	private JTextArea CurrHeroinfo = new JTextArea();
	private JTextArea miInfo = new JTextArea();
	private JButton cancel = new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/Cancel Action.png"))).getImage())
			.getScaledInstance(230, 50, java.awt.Image.SCALE_SMOOTH)));
	private Clip clip;
	
	public JTextArea getMiInfo() {
		return miInfo;
	}
	public void setMiInfo(JTextArea miInfo) {
		this.miInfo = miInfo;
	}
	public JButton getCall() {
		return Call;
	}
	public JButton getEndturn() {
		return endturn;
	}
//	public JLabel getNotify() {
//		return notify;
//	}
//	public void setNotify(JLabel notify) {
//		this.notify = notify;
//	}
	public JFrame getFrame() {
		return frame;
	}
//	public JLabel getBackground() {
//		return background;
//	}
	public ImageIcon getImage() {
		return image;
	}
	public JPanel getCenter() {
		return center;
	}
//	public JPanel getNotif() {
//		return notif;
//	}
	public JPanel getDeck() {
		return Deck;
	}
	public JPanel getInfo() {
		return info;
	}
	public JPanel getButtons() {
		return buttons;
	}
//	public JLabel getOppdeck() {
//		return oppdeck;
//	}
//	public JLabel getDeckim() {
//		return deckim;
//	}
//	public ImageIcon getDim() {
//		return dim;
//	}
	public TitledBorder getT1() {
		return t1;
	}
	public TitledBorder getT2() {
		return t2;
	}
	public TitledBorder getT3() {
		return t3;
	}
	public TitledBorder getT4() {
		return t4;
	}
	public JTextArea getOppHeroinfo() {
		return OppHeroinfo;
	}
	public JTextArea getCurrHeroinfo() {
		return CurrHeroinfo;
	}
	public JButton getUhp() {
		return uhp;
	}
	public void setCall(JButton call) {
		Call = call;
	}
	public void setEndturn(JButton endturn) {
		this.endturn = endturn;
	}
	public void setUhp(JButton uhp) {
		this.uhp = uhp;
	}
	private JButton uhp = new JButton(new ImageIcon(
			((new ImageIcon(getClass().getResource("/Use Hero Power.png"))).getImage())
			.getScaledInstance(230, 50, java.awt.Image.SCALE_SMOOTH)));
	
	public GameWindow(ImageIcon i1, ImageIcon i2){
		endturn.setText("End Turn");
		Call.setText("Call a friend!");
		cancel.setText("Cancel Action");
		uhp.setText("Use Hero Power");
		endturn.setContentAreaFilled(false);
		Call.setContentAreaFilled(false);
		cancel.setContentAreaFilled(false);
		uhp.setContentAreaFilled(false);
		frame = new JFrame("Hearthstone");
		frame.setVisible(true);
		frame.setSize(new Dimension(1920, 1080));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		image = new ImageIcon(getClass().getResource("/Game.jpg"));
		//dim =new ImageIcon(getClass().getResource("/deck.png"));
		frame.setResizable(true);
		endturn.setActionCommand("End turn!");
		
		background = new JLabel(image);
		//deckim = new JLabel(dim);
		//oppdeck = new JLabel(dim);
		
		background.setLayout(new BorderLayout());
		center.setVisible(true);
		info.setPreferredSize(new Dimension(250,972));
		//info.setBackground(Color.LIGHT_GRAY);
		Deck.setPreferredSize(new Dimension(250,972)); //250,972
		Deck.setBackground(Color.green);
		center.setPreferredSize(new Dimension(1152,972)); //1152,972
		p1Feild.setPreferredSize(new Dimension(805,243));
		
		
		CurrHeroim = new JButton(i1);
		OppHeroim = new JButton(i2);
		
//		notif.setPreferredSize(new Dimension(1000,30)); //1920,50
//		notif.setBackground(Color.white);
//		notif.add(notify);
		
		center.setLayout(new GridLayout(4,0));
		center.setOpaque(false);
		t1.setTitleJustification(TitledBorder.CENTER);
		t1.setTitleColor(Color.WHITE);
		t1.setTitlePosition(TitledBorder.ABOVE_TOP);
		t2.setTitleJustification(TitledBorder.CENTER);
		t2.setTitleColor(Color.WHITE);
		t2.setTitlePosition(TitledBorder.ABOVE_TOP);
		t3.setTitlePosition(TitledBorder.BELOW_BOTTOM);
		t3.setTitleJustification(TitledBorder.CENTER);
		t3.setTitleColor(Color.WHITE);
		t4.setTitleJustification(TitledBorder.CENTER);
		t4.setTitlePosition(TitledBorder.BELOW_BOTTOM);
		t4.setTitleColor(Color.WHITE);
		p2hand.setBorder(t1);
		p2hand.setOpaque(false);
		p2Feild.setBorder(t2);
		p2Feild.setOpaque(false);
		p1Feild.setBorder(t3);
		p1Feild.setOpaque(false);
		p1hand.setBorder(t4);
		p1hand.setOpaque(false);
		
		Deck.setLayout(new GridLayout(3,0));
		
		
		//Deck.setLayout(new GridLayout(3,0));
        buttons.setLayout(new GridLayout(4,0));
        buttons.add(endturn);
        buttons.add(Call);
        buttons.add(uhp);
        buttons.add(cancel);
        endturn.setPreferredSize(new Dimension(110,100)); //110,100
        Call.setPreferredSize(new Dimension(110,100));//110,100
        uhp.setPreferredSize(new Dimension(150,100));
        buttons.setOpaque(false);
        //Deck.add(oppdeck);
        Deck.add(buttons);
        miImage.setOpaque(false);
        Deck.add(miImage);
        miInfo.setOpaque(false);
        Deck.add(miInfo);
        //Deck.add(deckim);
        Deck.setOpaque(false);
		
		
		p1hand.setLayout(new GridLayout(0,10));//115/243
		p2hand.setLayout(new GridLayout(0,10));
		p1Feild.setLayout(new GridLayout(0,9));//164/243
		p2Feild.setLayout(new GridLayout(0,9));
		
		CurrHeroinfo.setOpaque(false);
		OppHeroinfo.setOpaque(false);
		
		center.add(p2hand);
		center.add(p2Feild);
		center.add(p1Feild);
		center.add(p1hand);
		
		info.setLayout(new GridLayout(4,0));
		OppHeroinfo.setEditable(false);
		CurrHeroinfo.setEditable(false);
		info.add(OppHeroim);
		info.add(OppHeroinfo);
		info.add(CurrHeroinfo);
		info.add(CurrHeroim);
		info.setOpaque(false);
		
		
		
		background.add(center,BorderLayout.CENTER);
		background.add(Deck,BorderLayout.EAST);
		background.add(info,BorderLayout.WEST);
		//background.add(notif,BorderLayout.SOUTH);
		frame.add(background);
		frame.setResizable(true);
		frame.revalidate();
		frame.repaint();
		try {
			play(getClass().getResource("/Hearthstone.wav"));
//			while (true) {
//				if (!clip.isOpen()) {
//					play("C:\\Users\\LENOVO\\eclipse-workspace\\HS-M2\\images\\Hearthstone.wav");
//				}
//			}
			
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void playd(String string,int sleep) throws IOException 
//	{
//	    //this part does not recognize file
//	    try {
//	        //System.out.println(System.getProperty());
//
//	        AudioStream AS = new AudioStream(new FileInputStream(string));
//	        Audioplayer AD = AS.getData();
//	        loop = new ContinuousAudioDataStream(AD);
//	    }
//	    catch(IOException error){
//	        System.out.print(string +" file not found");
//	    }
//	    AP.start(loop);     
//	}
	public Clip getClip() {
		return clip;
	}
	public void setClip(Clip clip) {
		this.clip = clip;
	}
	public void play(URL url) throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
	    AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
	    clip = AudioSystem.getClip();
	    clip.open(inputStream);
	    clip.start();
	}
	public JPanel getMiImage() {
		return miImage;
	}
	public void setMiImage(JPanel miImage) {
		this.miImage = miImage;
	}
	public JButton getCancel() {
		return cancel;
	}
	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}
	public JPanel getP1hand() {
		return p1hand;
	}
	public void setP1hand(JPanel p1hand) {
		this.p1hand = p1hand;
	}
	public JPanel getP2hand() {
		return p2hand;
	}
	public void setP2hand(JPanel p2hand) {
		this.p2hand = p2hand;
	}
	public JPanel getP1Feild() {
		return p1Feild;
	}
	
	public JButton getOppHeroim() {
		return OppHeroim;
	}
	public void setOppHeroim(JButton oppHeroim) {
		OppHeroim = oppHeroim;
	}
	public JButton getCurrHeroim() {
		return CurrHeroim;
	}
	public void setCurrHeroim(JButton currHeroim) {
		CurrHeroim = currHeroim;
	}
	public void setP1Feild(JPanel p1Feild) {
		this.p1Feild = p1Feild;
	}
	public JPanel getP2Feild() {
		return p2Feild;
	}
	public void setP2Feild(JPanel p2Feild) {
		this.p2Feild = p2Feild;
	}
	
	public static void main(String []args) {
		
	}
	}


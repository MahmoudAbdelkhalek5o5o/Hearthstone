package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.DivineSpirit;
import model.cards.spells.Flamestrike;
import model.cards.spells.HolyNova;
import model.cards.spells.KillCommand;
import model.cards.spells.LevelUp;
import model.cards.spells.MultiShot;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.SiphonSoul;
import model.cards.spells.*;
import model.cards.spells.TwistingNether;




public class datain {
	private ServerSocket ss;
	private Socket s;
	private DataInputStream in;
	private DataOutputStream out;
	private JFrame cards = new JFrame("Cards");
	private JPanel hand;
	private JPanel feild;
	private JPanel opfield;
	private JTextArea info = new JTextArea(); //health info and stuff for both in a string 
	private TitledBorder t2 = BorderFactory.createTitledBorder("Opponent Feild");
	private TitledBorder t3 = BorderFactory.createTitledBorder("Your Feild");
	private TitledBorder t4 = BorderFactory.createTitledBorder("Your Hand");
	private String input="";
	private String[] h;
	private String[] yf;
	private String[] of;
	private String infostr;
	public datain() throws IOException {
		ss = new ServerSocket(3000);	//declares a socket 

		
		System.out.println("done");
		//networking part, ensure connection established
		s=ss.accept();
		System.out.println("Connected!");
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
		//once established create JFrame 
		this.createJframe();
	}
	public static void main(String args[]) {
		try {
			datain data = new datain();//yes, the beginning of everything, the jframe and the network
			while(true) {
				data.input=data.in.readUTF();
				//System.out.println(data.input);
				data.Update(data.input);	
				
			}
		} catch (IOException e) {
			JFrame f = new JFrame();
			JOptionPane.showMessageDialog(f, "Error:"+e.getMessage());
			e.printStackTrace(); //woops something went wrong, display a msg here on the JFRAME
		}
		
	}
	public void createJframe() {
		//creates a JFrame
		
		cards.setSize(new Dimension(1920,1080));
		cards.setLayout(new GridLayout(4,0));
		
		cards.setVisible(true);
		
		t2.setTitleJustification(TitledBorder.CENTER);
		t2.setTitleColor(Color.black);
		t3.setTitleJustification(TitledBorder.CENTER);
		t3.setTitleColor(Color.black);
		t4.setTitleJustification(TitledBorder.CENTER);
		t4.setTitleColor(Color.black);
		hand = new JPanel();
		feild = new JPanel();
		opfield = new JPanel();
		hand.setBorder(t4);
		feild.setBorder(t3);
		opfield.setBorder(t2);
		hand.setVisible(true);
		feild.setVisible(true);
		opfield.setVisible(true);
		hand.setBackground(Color.white);
		feild.setBackground(Color.blue);
		opfield.setBackground(Color.red);
		
		cards.add(opfield);		//copy-pasted the style from the gamewindow cus it looked cool 
		cards.add(feild);
		
		cards.add(hand);
		cards.add(info);
		
		hand.setLayout(new GridLayout(0,10));
		feild.setLayout(new GridLayout(0,10));
		opfield.setLayout(new GridLayout(0,10));
		cards.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public Minion createMinion(String r[]) {
		if(r!=null) {
		int mc =Integer.parseInt(r[4]);
		int a = Integer.parseInt(r[3]);
		int mh= Integer.parseInt(r[2]);
		int ch =Integer.parseInt(r[1]);
		Rarity rarity;
			if(r[8].equals("BASIC")) {
				rarity=Rarity.BASIC;
			}
			else if(r[8].equals("COMMON")){
				rarity=Rarity.COMMON;
			}
			else if(r[8].equals("EPIC")){
				rarity=Rarity.EPIC;
			}
			else if(r[8].equals("RARE")){
				rarity=Rarity.RARE;
			}
			else {
				rarity=Rarity.LEGENDARY;
			}
		boolean t=false;
		boolean d=false;
		boolean chg=false;
		if(r[7].equals("true")) {
			t=true;
		}
		if(r[5].equals("true")) {
			d=true;
		}
		if(r[6].equals("false")) {
			chg=true;
		}
		
		Minion m = new Minion(r[0],mc,rarity,a,mh,t,d,chg);  //format sent: m.getName()+","+m.getCurrentHP()+","+m.getMaxHP()+","+m.getAttack()+","+m.getManaCost()+","+m.isDivine()+","+m.isSleeping()+","+m.isTaunt()+","+m.getRarity()+"|";
		m.setCurrentHP(ch);
		return m;													//  						0                  1					2				3					4				5					6					7				8				
		}
		else {
			return null;
		}
	}
	public Spell createSpell(String r[]) {			//formatsent:s.getName()+","+s.getManaCost()+","+s.getRarity()+"|";
		Spell c;
		if(r[0].equals("Curse of Weakness")) {
			c = new CurseOfWeakness();
		}
		else if((r[0].equals("Divine Spirit"))) {
			c= new DivineSpirit();
		}
		else if((r[0].equals("Flamestrike"))) {
			c = new Flamestrike();
		}
		else if((r[0].equals("Holy Nova"))) {
					c= new HolyNova();	
			}
		else if((r[0].equals("Kill Command"))) {
				c = new KillCommand();
			}
		else if((r[0].equals("Level Up!"))) {
				c = new LevelUp();
			}
		else if((r[0].equals("Multi-Shot"))) {
			c = new MultiShot();
		}
		else if((r[0].equals("Polymorph"))) {
			c= new Polymorph();
		}
		else if((r[0].equals("Pyroblast"))) {
			c= new Pyroblast();
		}
		else if((r[0].equals("Seal of Champions"))) {
			c= new SealOfChampions();
		}
		else if((r[0].equals("Shadow Word Death"))) {
			c= new ShadowWordDeath();
		}
		else if((r[0].equals("Siphon Soul"))) {
			c= new SiphonSoul();
		}
		else {
			c = new TwistingNether();
		}
		return c;
		
	}
	public JButton CardInHand(Spell s) {
		JButton Card = new JButton();
		ImageIcon sr = new ImageIcon(getClass().getResource("/" + s.getName() + ".png"));
		Image im = sr.getImage();
		Image im2 = im.getScaledInstance(110, 220, java.awt.Image.SCALE_SMOOTH);
		ImageIcon f = new ImageIcon(im2);
		JLabel top = new JLabel(f);
		JPanel bot = new JPanel();
		TitledBorder mana = BorderFactory.createTitledBorder("M:" + s.getManaCost());
		mana.setTitleColor(Color.WHITE);
		mana.setTitleJustification(TitledBorder.CENTER);

		Card.setLayout(new BorderLayout());
		Card.setPreferredSize(new Dimension(110, 243));
		top.setPreferredSize(new Dimension(110, 200));
		bot.setPreferredSize(new Dimension(110, 40));
		
		if(s.getRarity().equals(Rarity.BASIC)) {
			Card.setBackground(Color.WHITE);
		}
		else if(s.getRarity().equals(Rarity.COMMON)) {
			Card.setBackground(Color.BLUE);
		}
		else if(s.getRarity().equals(Rarity.RARE)) {
			Card.setBackground(Color.GREEN);
		}
		else if(s.getRarity().equals(Rarity.EPIC)) {
			Card.setBackground(Color.YELLOW);
		}
		else {
			Card.setBackground(Color.RED);
		}
			
		top.setBorder(mana);

		bot.setBackground(Color.white);

		bot.setLayout(new BorderLayout());
		
		Card.add(top);
		Card.add(bot, BorderLayout.SOUTH);

		return Card;
	}
	public JButton CardInHand(Minion m) {
		JButton Card = new JButton();
		ImageIcon s = new ImageIcon(getClass().getResource("/" + m.getName() + ".png"));
		Image im = s.getImage();
		Image im2 = im.getScaledInstance(110, 220, java.awt.Image.SCALE_SMOOTH);
		ImageIcon f = new ImageIcon(im2);
		JLabel top = new JLabel(f);
		JPanel bot = new JPanel();
		JLabel health = new JLabel("H:" + m.getCurrentHP() + "/" + m.getMaxHP());// 110x243
		JLabel attack = new JLabel("A:" + m.getAttack());
		TitledBorder mana = BorderFactory.createTitledBorder("M:" + m.getManaCost());
		JPanel info = new JPanel();
		mana.setTitleColor(Color.WHITE);
		mana.setTitleJustification(TitledBorder.CENTER);

		Card.setLayout(new BorderLayout());
		Card.setPreferredSize(new Dimension(110, 243));
		top.setPreferredSize(new Dimension(110, 200));
		bot.setPreferredSize(new Dimension(110, 40));

		top.setBorder(mana);

		bot.setBackground(Color.white);

		bot.setLayout(new BorderLayout());
		info.setLayout(new GridLayout(0, 3));
		bot.add(health, BorderLayout.EAST);
		bot.add(attack, BorderLayout.WEST);

		if (m.isDivine()) {
			ImageIcon imported = new ImageIcon(getClass().getResource("/Shield.png"));
			Image im3 = imported.getImage();
			Image im4 = im3.getScaledInstance(20, 40, java.awt.Image.SCALE_SMOOTH);
			ImageIcon fl = new ImageIcon(im4);
			JLabel shield = new JLabel(fl);
			info.add(shield);

		}
		if (m.isTaunt()) {
			ImageIcon imported = new ImageIcon(getClass().getResource("/taunt.jpeg"));
			Image im3 = imported.getImage();
			Image im4 = im3.getScaledInstance(20, 40, java.awt.Image.SCALE_SMOOTH);
			ImageIcon fl = new ImageIcon(im4);
			JLabel taunt = new JLabel(fl);
			info.add(taunt);

		}
		if (m.isSleeping()) {
			ImageIcon imported = new ImageIcon(getClass().getResource("/sleep.png"));
			Image im3 = imported.getImage();
			Image im4 = im3.getScaledInstance(20, 40, java.awt.Image.SCALE_SMOOTH);
			ImageIcon fl = new ImageIcon(im4);
			JLabel sleep = new JLabel(fl);
			sleep.setPreferredSize(new Dimension(40, 50));
			info.add(sleep);

		}
		if(m.getRarity().equals(Rarity.BASIC)) {
			Card.setBackground(Color.WHITE);
		}
		else if(m.getRarity().equals(Rarity.COMMON)) {
			Card.setBackground(Color.BLUE);
		}
		else if(m.getRarity().equals(Rarity.RARE)) {
			Card.setBackground(Color.GREEN);
		}
		else if(m.getRarity().equals(Rarity.EPIC)) {
			Card.setBackground(Color.YELLOW);
		}
		else {
			Card.setBackground(Color.RED);
		}
		bot.add(info, BorderLayout.CENTER);

		Card.add(top);
		Card.add(bot, BorderLayout.SOUTH);

		return Card;
	}
	public void Update(String input) {
		String r[] = input.split("!", 0);
		
		if(r[0]!=null) {
		h=r[0].split("/",0);
		}
		if(r[1]!=null) {
		yf=r[1].split("/",0);
		}
		if(r[2]!=null) {
		of=r[2].split("/",0);
		}
		
		if(r[3]!=null) {
		infostr=r[3];
		}
	
		//here lies the update things as to construct the minions and display them!
		hand.setVisible(false);
		feild.setVisible(false);
		opfield.setVisible(false);
		info.setVisible(false);
		
		hand.removeAll();
		feild.removeAll();
		opfield.removeAll();
		
		
		
		if(h!=null) {
		for(int i =0 ;i<h.length;i++) {
			String x[]= h[i].split(",", 0);
			JButton c;
			//System.out.println(x.length);
			if(x.length!=3) {
				c=CardInHand(createMinion(x));
			}
			else {
				c=CardInHand(createSpell(x));
			}
			hand.add(c);												
		}
		}
		if(!(yf[0].equals("0"))) {
		for(int i =0 ;i<yf.length;i++) {
			String x[]= yf[i].split(",", 0);
			JButton c;
			if(x.length!=3) {
				c=CardInHand(createMinion(x));
			}
			else {
				c=CardInHand(createSpell(x));
			}
			feild.add(c);												
		}
		}
		if(!(of[0].equals("0"))) {
		for(int i =0 ;i<of.length;i++) {
			String x[]= of[i].split(",", 0);
			JButton c;
			if(x.length!=3) {
				c=CardInHand(createMinion(x));
			}
			else {
				c=CardInHand(createSpell(x));
			}
			opfield.add(c);												
		}
		}
		hand.setVisible(true);
		feild.setVisible(true);
		opfield.setVisible(true);
		info.setVisible(true);
		
		String i[] = infostr.split("/n",0);
		
		info.setText("Your Friend: "+i[0]+"\n"+"Opponent"+i[1]);
	}
}


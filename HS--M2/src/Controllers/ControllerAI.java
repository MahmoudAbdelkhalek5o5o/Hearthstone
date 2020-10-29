package Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.DivineSpirit;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import windows.GameWindow;

public class ControllerAI implements ActionListener, GameListener, MouseListener{
	private GameWindow gameWindow;
	private Hero h1;
	private Hero h2;
	private Game game;
	private ImageIcon i1;
	private ImageIcon i2;
	private ArrayList<JButton> hand1 = new ArrayList<JButton>();
	private ArrayList<JButton> hand2 = new ArrayList<JButton>();
	private ArrayList<JButton> field1 = new ArrayList<JButton>();
	private ArrayList<JButton> field2 = new ArrayList<JButton>();
	private JButton attackerButton = null;
	private Card spellCard = null;
	private JButton spellButton = null;
	private static int c = 0;
	private static boolean b;
	private JTextArea t1 = new JTextArea();
	private JTextArea t2 = new JTextArea();
	private boolean uhp = false;
	private JTextField tip;
	private JFrame ipnotfy;
	private Socket s;
	private DataOutputStream out;

	public ControllerAI(String s1, String s2) throws IOException, CloneNotSupportedException, FullHandException {
		if (s1.equals("Hunter")) {
			h1 = new Hunter();
			ImageIcon sr = new ImageIcon(getClass().getResource("/hunter1.jpg"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i1 = f;

		}
		if (s1.equals("Mage")) {
			h1 = new Mage();
			ImageIcon sr = new ImageIcon(getClass().getResource("/mage1.jpg"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i1 = f;
		}
		if (s1.equals("Paladin")) {
			h1 = new Paladin();
			ImageIcon sr = new ImageIcon(getClass().getResource("/Paladin.jpg"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i1 = f;
		}
		if (s1.equals("Priest")) {
			h1 = new Priest();
			ImageIcon sr = new ImageIcon(getClass().getResource("/Priest.png"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i1 = f;
		}
		if (s1.equals("Warlock")) {
			h1 = new Warlock();
			ImageIcon sr = new ImageIcon(getClass().getResource("/Warlock.png"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i1 = f;
		}
		if (s2.equals("Hunter")) {
			h2 = new Hunter();
			ImageIcon sr = new ImageIcon(getClass().getResource("/hunter1.jpg"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i2 = f;
		}
		if (s2.equals("Mage")) {
			h2 = new Mage();
			ImageIcon sr = new ImageIcon(getClass().getResource("/mage1.jpg"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i2 = f;
		}
		if (s2.equals("Paladin")) {
			h2 = new Paladin();
			ImageIcon sr = new ImageIcon(getClass().getResource("/Paladin.jpg"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i2 = f;
		}
		if (s2.equals("Priest")) {
			h2 = new Priest();
			ImageIcon sr = new ImageIcon(getClass().getResource("/Priest.png"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i2 = f;
		}
		if (s2.equals("Warlock")) {
			h2 = new Warlock();
			ImageIcon sr = new ImageIcon(getClass().getResource("/Warlock.png"));
			Image im = sr.getImage();
			Image im2 = im.getScaledInstance(250, 243, java.awt.Image.SCALE_SMOOTH);
			ImageIcon f = new ImageIcon(im2);
			i2 = f;
		}
		game = new Game(h1, h2);
		if (h1 == h1) {
			gameWindow = new GameWindow(i1, i2);
			gameWindow.getCurrHeroim().addActionListener(this);
			gameWindow.getOppHeroim().addActionListener(this);
		} else {
			gameWindow = new GameWindow(i2, i1);
			gameWindow.getCurrHeroim().addActionListener(this);
			gameWindow.getOppHeroim().addActionListener(this);
		}
		game.setListener(this);
		gameWindow.getCancel().addActionListener(this);
		gameWindow.getEndturn().addActionListener(this);
		gameWindow.getCall().addActionListener(this);
		gameWindow.getUhp().addActionListener(this);
		gameWindow.getUhp().addMouseListener(this);
		JButton i1 = gameWindow.getCurrHeroim();
		JButton i2 = gameWindow.getOppHeroim();
		i1.addActionListener(this);
		i2.addActionListener(this);
		t1.setEditable(false);
		t2.setEditable(false);
		t1.setOpaque(false);
		t2.setOpaque(false);
		h1 = h1;
		h2 = h2;
		gameWindow.getInfo().removeAll();
		if (c % 2 == 1) {
			i1.setActionCommand(h2.getName());
			i2.setActionCommand(h1.getName());
			gameWindow.getInfo().add(i1);
			t1.setText(forminfo(h1));
			t2.setText(forminfo(h2));
			t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
			t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
			gameWindow.getInfo().add(t2);
			gameWindow.getInfo().add(t1);
			gameWindow.getInfo().add(i2);
		} else {
			i2.setActionCommand(h2.getName());
			i1.setActionCommand(h1.getName());
			gameWindow.getInfo().add(i2);
			t1.setText(forminfo(h1));
			t2.setText(forminfo(h2));
			t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
			t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
			gameWindow.getInfo().add(t2);
			gameWindow.getInfo().add(t1);
			gameWindow.getInfo().add(i1);
		}
		t1.setForeground(Color.white);
		t2.setForeground(Color.white);
		for (int i = 0; i < h1.getHand().size(); i++) {
			JButton m;
			if (h1.getHand().get(i) instanceof Minion) {
				m = CardInHand(((Minion) h1.getHand().get(i)));
			} else {
				m = CardInHand((Spell) h1.getHand().get(i));
			}
			gameWindow.getP1hand().add(m);
			m.addActionListener(this);
			m.addMouseListener(this);
			hand1.add(m);
		}
		for (int i = 0; i < h2.getHand().size(); i++) {
			JButton m = new JButton(new ImageIcon(getClass().getResource("/deck.png")));
			gameWindow.getP2hand().add(m);
			m.addActionListener(this);
			hand2.add(m);
		}
		gameWindow.revalidate();
		gameWindow.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Send")) {
			String[] chat = { "java", "-cp", "./bin", "windows.ChatWindowServer" };
			String[] notification = { "java", "-cp", "./bin", "windows.notifycall", tip.getText() };
			ProcessBuilder x = new ProcessBuilder(notification);
			try {
				s = new Socket(tip.getText(), 3000);
				out = new DataOutputStream(s.getOutputStream());
				ProcessBuilder p = new ProcessBuilder(chat);
				p.start();
				x.start();
				ipnotfy.setVisible(false);
				sendInfo();
				return;
			} catch (UnknownHostException e2) {
				JOptionPane.showMessageDialog(gameWindow.getFrame(), "Error:" + e2.getMessage());
				e2.printStackTrace();
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(gameWindow.getFrame(), "Error:" + e2.getMessage());
				e2.printStackTrace();
			}

		}
		if (e.getActionCommand().equals("Call a friend!")) {
			ipnotfy = new JFrame();
			tip = new JTextField();
			if (c % 2 == 0) {
				b = true;
			} else {
				b = false;
			}
			ipnotfy.add(new JLabel("Enter Friend IP:"));
			ipnotfy.add(tip);
			tip.setPreferredSize(new Dimension(100, 50));
			ipnotfy.setLayout(new FlowLayout());
			ipnotfy.setSize(new Dimension(300, 100));
			ipnotfy.setVisible(true);
			JButton b = new JButton("Send");
			ipnotfy.add(b);
			b.addActionListener(this);

		}
		JButton m = (JButton) (e.getSource());
		if (m.getText().equals("End Turn")) {
			try {
				game.endTurn();
				t1.setText(forminfo(h1));
				t2.setText(forminfo(h2));
				t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
				t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
				gameWindow.revalidate();
				gameWindow.repaint();
				attackerButton = null;
				spellCard = null;
				spellButton = null;	
				updategame();
				return;
			} catch (FullHandException e1) {
				t1.setText(forminfo(h1));
				t2.setText(forminfo(h2));
				t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
				t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
				gameWindow.revalidate();
				gameWindow.repaint();
				attackerButton = null;
				spellCard = null;
				spellButton = null;	
				if (e1.getBurned() instanceof Minion) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					JOptionPane.showMessageDialog(gameWindow, CardInHand((Minion) e1.getBurned()));
				} else {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					JOptionPane.showMessageDialog(gameWindow, CardInHand((Spell) e1.getBurned()));
				}
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
			}
		} else if (m.getText().equals("Cancel Action")) {
			attackerButton = null;
			spellCard = null;
			spellButton = null;
			uhp = false;
			reset();
		} else if (m.getText().equals("Use Hero Power")) {
			if (h1 instanceof Priest || h1 instanceof Mage) {
				try {
					game.validateUsingHeroPower(h1);
					uhp = true;
					gameWindow.getMiInfo().setText("Inflict one damage point to a \nspecific target a hero or a \nminion \nChoose a target!");
					gameWindow.getMiInfo().setFont(new Font("Inflict one damage point to a \nspecific target a hero or a \nminion \nChoose a target!", Font.BOLD, 16));
					if (s != null) {
						if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
							try {
								sendInfo();
							} catch (IOException e11) {
								// TODO Auto-generated catch block
								e11.printStackTrace();
							}
						}
					}
					return;
				} catch (NotEnoughManaException e2) {
					JOptionPane.showMessageDialog(gameWindow, e2.getMessage());
				} catch (HeroPowerAlreadyUsedException e2) {
					JOptionPane.showMessageDialog(gameWindow, e2.getMessage());
				}
			} else {
				try {
					h1.useHeroPower();
					updategame();
					t1.setText(forminfo(h1));
					t2.setText(forminfo(h2));
					t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
					t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
					
					gameWindow.getMiImage().removeAll();
					gameWindow.getMiImage().setVisible(false);
					gameWindow.getMiInfo().removeAll();
					gameWindow.getMiInfo().setVisible(false);
					gameWindow.revalidate();
					gameWindow.repaint();
					if (s != null) {
						if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
							try {
								sendInfo();
							} catch (IOException e11) {
								// TODO Auto-generated catch block
								e11.printStackTrace();
							}
						}
					}
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (FullHandException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (FullFieldException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				}
			}
		} else if (hand1.contains(m) && h1.getHand().get(hand1.indexOf(m)) instanceof Minion && !uhp
				&& attackerButton == null && spellCard == null) {
			try {

				int num = hand1.indexOf(m);
				JButton cl = CardInHand((Minion) (h1.getHand().get(num)));
				h1.playMinion((Minion) (h1.getHand().get(num)));
				m.setVisible(false);
				cl.setVisible(true);
				gameWindow.getP1hand().remove(m);
				t1.setText(forminfo(h1));
				t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
				updatefield();
				hand1.remove(num);
				gameWindow.getCurrHeroinfo().setText(forminfo(h1));
				gameWindow.getCurrHeroinfo().setFont(new Font(forminfo(h1), Font.BOLD, 20));
				gameWindow.getMiImage().removeAll();
				gameWindow.getMiImage().setVisible(false);
				gameWindow.getMiInfo().removeAll();
				gameWindow.getMiInfo().setVisible(false);
				gameWindow.revalidate();
				gameWindow.repaint();
				if (s != null) {
					if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
						try {
							sendInfo();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				return;
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
			}
		} else if (hand1.contains(m) && h1.getHand().get(hand1.indexOf(m)) instanceof Spell && !uhp
				&& attackerButton == null && spellCard == null) {
			Spell s = (Spell) h1.getHand().get(hand1.indexOf(m));
			if (s instanceof FieldSpell)
				try {

					h1.castSpell((FieldSpell) s);
					m.setVisible(false);
					gameWindow.getP1hand().remove(m);
					hand1.remove(m);
					t1.setText(forminfo(h1));
					t2.setText(forminfo(h2));
					t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
					t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
					updatefield();
					gameWindow.getMiImage().removeAll();
					gameWindow.getMiImage().setVisible(false);
					gameWindow.getMiInfo().removeAll();
					gameWindow.getMiInfo().setVisible(false);
					gameWindow.revalidate();
					gameWindow.repaint();
					if (s != null) {
						if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
							try {
								sendInfo();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				}
			if (s instanceof AOESpell)
				try {
					h1.castSpell((AOESpell) s, h2.getField());
					m.setVisible(false);
					gameWindow.getP1hand().remove(m);
					hand1.remove(m);
					gameWindow.getCurrHeroinfo().setText(forminfo(h1));
					gameWindow.getCurrHeroinfo().setFont(new Font(forminfo(h1), Font.BOLD, 20));
					updatefield();
					t1.setText(forminfo(h1));
					t2.setText(forminfo(h2));
					t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
					t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
					gameWindow.revalidate();
					gameWindow.repaint();
					if (s != null) {
						if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
							try {
								sendInfo();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				}
			if (s instanceof MinionTargetSpell || s instanceof LeechingSpell || s instanceof HeroTargetSpell) {
				try {
					game.validateManaCost(s);
					spellCard = s;
					spellButton = m;
					gameWindow.getMiInfo().setText("\n "+"\n"+"Name: " + s.getName()+"\n"+"Manacost: " + s.getManaCost() + "\n"+"Rarity: " + s.getRarity()+"\n Choose a Target!");
					gameWindow.getMiInfo().setForeground(Color.WHITE);
					gameWindow.getMiInfo().setFont(new Font("\n "+"\n"+"Name: " +s.getName()+"\n"+"Manacost: " + s.getManaCost() + "\n"+"Rarity: " + s.getRarity()+"\n Choose a Target!", Font.BOLD, 16));
					gameWindow.getMiInfo().setVisible(true);
					return;
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				}

			}
		} else if (field1.contains(m) && attackerButton == null && spellCard == null && !uhp) {

			attackerButton = m;
			Minion mi = h1.getField().get(field1.indexOf(m));
			String s1;
			String s2;
			String s3;
			if (mi.isTaunt())
				s1="YES";
			else
				s1 = "NO";
			if (mi.isDivine())
				s2="YES";
			else
				s2= "NO";
			if (mi.isSleeping())
				s3="YES";
			else
				s3= "NO";

			gameWindow.getMiImage().setVisible(true);
			gameWindow.getMiInfo().setText("\n "+"\n"+"Name: " + mi.getName()+"\nManacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Sleeping: "+ s3 + "\nATTACKING!");
			gameWindow.getMiInfo().setForeground(Color.WHITE);
			gameWindow.getMiInfo().setFont(new Font("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Sleeping: "+ s3+ "\nATTACKING!", Font.BOLD, 16));
			gameWindow.getMiInfo().setVisible(true);
			return;
		} else if (attackerButton == null && spellCard == null && uhp) {
			if (h1 instanceof Mage) {
				if (field2.contains(m)) {
					try {
						((Mage) h1).useHeroPower(h2.getField().get(field2.indexOf(m)));
						updatefield();
						t1.setText(forminfo(h1));
						t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
						uhp = false;
						reset();
						if (s != null) {
							if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
								try {
									sendInfo();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					}
				}
				if (hand2.contains(m) || hand1.contains(m)) {
					JOptionPane.showMessageDialog(gameWindow,
							"You can not affect a Card that has not been summoned yet");
					uhp = false;
					reset();
				}
				if (field1.contains(m)) {
					JOptionPane.showMessageDialog(gameWindow, "You do not harm your own Minions");
					uhp = false;
					reset();
				}
				if (m.getActionCommand().equals(h1.getName())) {
					JOptionPane.showMessageDialog(gameWindow, "You do not harm yourself");
					uhp = false;
					reset();
				}
				if (m.getActionCommand().equals(h2.getName())) {
					try {
						((Mage) h1).useHeroPower(h2);
						t1.setText(forminfo(h1));
						t2.setText(forminfo(h2));
						t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
						t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
						uhp = false;
						reset();
						if (s != null) {
							if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
								try {
									sendInfo();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						return;
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					}
				}
			} else {
				if (field1.contains(m)) {
					try {
						((Priest) h1).useHeroPower(h1.getField().get(field1.indexOf(m)));
						updatefield();
						t1.setText(forminfo(h1));
						t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
						uhp = false;
						reset();
						if (s != null) {
							if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
								try {
									sendInfo();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					}
				}
				if (hand2.contains(m) || hand1.contains(m)) {
					JOptionPane.showMessageDialog(gameWindow,
							"You can not affect a Card that has not been summoned yet");
					uhp = false;
					reset();
				}
				if (field2.contains(m)) {
					JOptionPane.showMessageDialog(gameWindow, "You do not heal your Opponent's Minions");
					uhp = false;
					reset();
				}
				if (m.getActionCommand().equals(h2.getName())) {
					JOptionPane.showMessageDialog(gameWindow, "You do not heal your Opponent");
					uhp = false;
					reset();
				}
				if (m.getActionCommand().equals(h1.getName())) {
					try {
						((Priest) h1).useHeroPower(h1);
						t1.setText(forminfo(h1));
						t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
						uhp = false;
						reset();
						if (s != null) {
							if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
								try {
									sendInfo();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						return;
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					}
				}
			}
		} else if (spellCard != null && (spellCard instanceof MinionTargetSpell || spellCard instanceof LeechingSpell)
				|| spellCard instanceof HeroTargetSpell) {
			if (field1.contains(m) && spellCard instanceof LeechingSpell) {
				JOptionPane.showMessageDialog(gameWindow, "You can not harm your own minions");
				spellCard = null;
				spellButton = null;
				reset();
				return;
			}
			if (m.getActionCommand().equals(h2.getName()) && spellCard instanceof HeroTargetSpell) {
				try {
					h1.castSpell((HeroTargetSpell) spellCard, h2);
					spellButton.setVisible(false);
					gameWindow.getP1hand().remove(spellButton);
					hand1.remove(spellButton);
					t1.setText(forminfo(h1));
					t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
					t2.setText(forminfo(h2));
					t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
					spellButton = null;
					spellCard = null;
					reset();
					if (s != null) {
						if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
							try {
								sendInfo();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					return;
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
				}
			}
			if (m.getActionCommand().equals(h1.getName()) && spellCard instanceof HeroTargetSpell) {
				JOptionPane.showMessageDialog(gameWindow, "You do not harm yourself");
				spellCard = null;
				spellButton = null;
				reset();
			}
			if ((m.getActionCommand().equals(h1.getName())
					|| m.getActionCommand().equals(h2.getName()))
					&& !(spellCard instanceof HeroTargetSpell)) {
				JOptionPane.showMessageDialog(gameWindow, "You can not affect heroes with this spell");
				spellCard = null;
				spellButton = null;
				reset();
			}
			if (hand1.contains(m) || hand2.contains(m)) {
				JOptionPane.showMessageDialog(gameWindow, "You can not affect a Card that has not been summoned yet");
				spellCard = null;
				spellButton = null;
				reset();
			}
			if (field1.contains(m)) {
				if (spellCard instanceof DivineSpirit) {
					try {
						h1.castSpell((MinionTargetSpell) spellCard,
								(Minion) (h1.getField().get(field1.indexOf(m))));
						spellButton.setVisible(false);
						gameWindow.getP1hand().remove(spellButton);
						hand1.remove(spellButton);
						spellButton = null;
						spellCard = null;
						t1.setText(forminfo(h1));
						t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
						updatefield();
						reset();
						if (s != null) {
							if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
								try {
									sendInfo();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (InvalidTargetException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
						spellCard = null;
						spellButton = null;
						
					}
				} else {
					JOptionPane.showMessageDialog(gameWindow, "You do not harm your own Minions");
					spellCard = null;
					spellButton = null;
					reset();
				}
			}
			if (field2.contains(m)) {
				if (!(spellCard instanceof DivineSpirit)) {
					try {
						if (spellCard instanceof MinionTargetSpell)
							h1.castSpell((MinionTargetSpell) spellCard,
									(Minion) (h2.getField().get(field2.indexOf(m))));
						if (spellCard instanceof LeechingSpell)
							h1.castSpell((LeechingSpell) spellCard,
									(Minion) (h2.getField().get(field2.indexOf(m))));
						spellButton.setVisible(false);
						gameWindow.getP1hand().remove(spellButton);
						hand1.remove(spellButton);
						spellButton = null;
						spellCard = null;
						t1.setText(forminfo(h1));
						t1.setFont(new Font(forminfo(h1), Font.BOLD, 20));
						updatefield();
						reset();
						if (s != null) {
							if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
								try {
									sendInfo();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					} catch (InvalidTargetException e1) {
						JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
						spellCard = null;
						spellButton = null;
						reset();
					}
				} else {
					JOptionPane.showMessageDialog(gameWindow, "You do not Strength your Opponent's Minions");
					spellCard = null;
					spellButton = null;
					reset();
				}
			}
		} else if (attackerButton != null) {
			if (field2.contains(m)) {
				update(attackerButton, m);
				if (s != null) {
					if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
						try {
							sendInfo();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				return;
			}
			if (field1.contains(m)) {
				JOptionPane.showMessageDialog(gameWindow, "You can not attack a friendly minion");
				attackerButton = null;
				reset();
			}
			if (e.getActionCommand().equals(h1.getName())) {
				JOptionPane.showMessageDialog(gameWindow, "You can not attack yourself with your minions");
				attackerButton = null;
				reset();
			}
			if (hand2.contains(m)) {
				JOptionPane.showMessageDialog(gameWindow,
						"You can not attack with a minion that has not been summoned yet");
				attackerButton = null;
				reset();
			}
			if (e.getActionCommand().equals(h2.getName())) {
				try {
					h1.attackWithMinion(
							(Minion) (h1.getField().get(field1.indexOf(attackerButton))), h2);
					t2.setText(forminfo(h2));
					t2.setFont(new Font(forminfo(h2), Font.BOLD, 20));
					gameWindow.revalidate();
					gameWindow.repaint();
					attackerButton = null;
					reset();
					if (s != null) {
						if ((c % 2 == 0 && b == true) || (c % 2 != 0 && b == false)) {
							try {
								sendInfo();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				} catch (CannotAttackException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					attackerButton = null;
					reset();
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					attackerButton = null;
					reset();
				} catch (TauntBypassException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					attackerButton = null;
					reset();
				} catch (NotSummonedException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					attackerButton = null;
					reset();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(gameWindow, e1.getMessage());
					attackerButton = null;
					reset();
				}
			}
		}
	}

	public void update(JButton m1, JButton m2) {
		Minion mi1 = (Minion) (h1.getField().get(field1.indexOf(m1)));
		Minion mi2 = (Minion) (h2.getField().get(field2.indexOf(m2)));
		try {
			h1.attackWithMinion(mi1, mi2);
			updatefield();
			gameWindow.getMiImage().removeAll();
			gameWindow.getMiImage().setVisible(false);
			attackerButton = null;
			reset();
		} catch (CannotAttackException e) {
			JOptionPane.showMessageDialog(gameWindow, e.getMessage());
			attackerButton = null;
			
		} catch (NotYourTurnException e) {
			JOptionPane.showMessageDialog(gameWindow, e.getMessage());
			attackerButton = null;
			
		} catch (TauntBypassException e) {
			JOptionPane.showMessageDialog(gameWindow, e.getMessage());
			attackerButton = null;
			
		} catch (InvalidTargetException e) {
			JOptionPane.showMessageDialog(gameWindow, e.getMessage());
			attackerButton = null;
			
		} catch (NotSummonedException e) {
			JOptionPane.showMessageDialog(gameWindow, e.getMessage());
			attackerButton = null;
			
		}
	}

	public void updategame() {
		gameWindow.getP1hand().setVisible(false);
		gameWindow.getP1hand().removeAll();
		removy(hand1);
		for (int i = 0; i < h1.getHand().size(); i++) {
			JButton s;
			if (h1.getHand().get(i) instanceof Minion) {
				s = CardInHand(((Minion) h1.getHand().get(i)));
			} else {
				s = CardInHand((Spell) h1.getHand().get(i));
			}
			gameWindow.getP1hand().add(s);
			s.addActionListener(this);
			s.addMouseListener(this);
			hand1.add(s);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		gameWindow.getP1hand().setVisible(true);
		gameWindow.revalidate();
		gameWindow.repaint();
		gameWindow.getP2hand().setVisible(false);
		gameWindow.getP2hand().removeAll();
		removy(hand2);
		for (int i = 0; i < h2.getHand().size(); i++) {
			JButton s = new JButton(new ImageIcon(getClass().getResource("/deck.png")));
			gameWindow.getP2hand().add(s);
			s.addActionListener(this);
			hand2.add(s);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		gameWindow.getP2hand().setVisible(true);
		gameWindow.getP1Feild().setVisible(false);
		gameWindow.getP1Feild().removeAll();
		removy(field1);
		gameWindow.getP1Feild().add(space());
		for (int i = 0; i < h1.getField().size(); i++) {
			JButton x = CardInFeild(h1.getField().get(i));
			gameWindow.getP1Feild().add(x);
			x.addActionListener(this);
			x.addMouseListener(this);
			field1.add(x);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		gameWindow.getP1Feild().setVisible(true);
		gameWindow.getP2Feild().setVisible(false);
		gameWindow.getP2Feild().removeAll();
		removy(field2);
		gameWindow.getP2Feild().add(space());
		for (int i = 0; i < h2.getField().size(); i++) {
			JButton x = CardInFeild(h2.getField().get(i));
			gameWindow.getP2Feild().add(x);
			x.addActionListener(this);
			x.addMouseListener(this);
			field2.add(x);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		gameWindow.getP2Feild().setVisible(true);
		gameWindow.revalidate();
		gameWindow.repaint();
	}

	public void updatefield() {
		gameWindow.getP1Feild().setVisible(false);
		gameWindow.getP1Feild().removeAll();
		removy(field1);
		gameWindow.getP1Feild().add(space());
		for (int i = 0; i < h1.getField().size(); i++) {
			JButton x = CardInFeild(h1.getField().get(i));
			gameWindow.getP1Feild().add(x);
			x.addActionListener(this);
			x.addMouseListener(this);
			field1.add(x);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		gameWindow.getP1Feild().setVisible(true);
		gameWindow.getP2Feild().setVisible(false);
		gameWindow.getP2Feild().removeAll();
		removy(field2);
		gameWindow.getP2Feild().add(space());
		for (int i = 0; i < h2.getField().size(); i++) {
			JButton x = CardInFeild(h2.getField().get(i));
			gameWindow.getP2Feild().add(x);
			x.addActionListener(this);
			x.addMouseListener(this);
			field2.add(x);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		gameWindow.getP2Feild().setVisible(true);
		gameWindow.revalidate();
		gameWindow.repaint();

	}

	public static void removy(ArrayList<JButton> a) {
		while (!a.isEmpty()) {
			a.get(0).setVisible(false);
			a.remove(0);
		}
	}

	public String forminfo(Hero h) {
		return "Name: " + h.getName() + "\n" + "Helath: " + h.getCurrentHP() + "\n" + "Mana: "
				+ h.getCurrentManaCrystals() + "/" + h.getTotalManaCrystals() + "\n" + "Cards in Deck: "
				+ h.getDeck().size();
	}

	public JButton CardInFeild(Minion m) {
		JButton test = new JButton(
				new ImageIcon(((new ImageIcon(getClass().getResource("/" + m.getName() + ".png"))).getImage())
						.getScaledInstance(130, 210, java.awt.Image.SCALE_SMOOTH)));
		test.setLayout(new BorderLayout());
		test.setSize(new Dimension(110, 243));
		test.setPreferredSize(new Dimension(110, 243));
		test.setVisible(true);
		test.setContentAreaFilled(false);
		test.setFocusPainted(false);
		return test;

	}

	public JButton CardInHand(Minion m) {
		JButton Card = new JButton(
				new ImageIcon(((new ImageIcon(getClass().getResource("/" + m.getName() + ".png"))).getImage())
						.getScaledInstance(105, 210, java.awt.Image.SCALE_SMOOTH)));
		Card.setPreferredSize(new Dimension(110, 243));
		Card.setFocusPainted(false);
		Card.setContentAreaFilled(false);
		return Card;
	}

	public JButton CardInHand(Spell s) {
		JButton Card = new JButton(
				new ImageIcon(((new ImageIcon(getClass().getResource("/" + s.getName() + ".png"))).getImage())
						.getScaledInstance(105, 210, java.awt.Image.SCALE_SMOOTH)));
		Card.setLayout(new BorderLayout());
		Card.setPreferredSize(new Dimension(110, 243));
		Card.setContentAreaFilled(false);
		Card.setFocusPainted(false);
		return Card;
	}

	public String disectMinion(Minion m) {
		return m.getName() + "," + m.getCurrentHP() + "," + m.getMaxHP() + "," + m.getAttack() + "," + m.getManaCost()
				+ "," + m.isDivine() + "," + m.isSleeping() + "," + m.isTaunt() + "," + m.getRarity() + "/";
	}

	public String disectSpell(Spell s) {
		return s.getName() + "," + s.getManaCost() + "," + s.getRarity() + "/";
	}

	public void sendInfo() throws IOException {
		String sending = "";
		if (h1.getHand().size() != 0) {
			for (int i = 0; i < h1.getHand().size(); i++) {
				Card c = h1.getHand().get(i);

				if (c instanceof Minion) {
					sending = sending + disectMinion((Minion) c);
				} else {
					sending = sending + disectSpell((Spell) c);
				}
			}
		} else {
			sending = sending + "0";
		}
		sending = sending + "!";
		if (h1.getField().size() != 0) {
			for (int i = 0; i < h1.getField().size(); i++) {
				Card c = h1.getField().get(i);

				if (c instanceof Minion) {
					sending = sending + disectMinion((Minion) c);
				} else {
					sending = sending + disectSpell((Spell) c);
				}
			}
		} else {
			sending = sending + "0";
		}
		sending = sending + "!";
		if (h2.getField().size() != 0) {
			for (int i = 0; i < h2.getField().size(); i++) {
				Card c = h2.getField().get(i);

				if (c instanceof Minion) {
					sending = sending + disectMinion((Minion) c);
				} else {
					sending = sending + disectSpell((Spell) c);
				}
			}
		} else {
			sending = sending + "0";
		}
		sending = sending + "!";
		sending = sending + forminfomodified(h1) + "/n" + forminfomodified(h2);
		out.writeUTF(sending);
	}

	public String forminfomodified(Hero h) {
		return "Name: " + h.getName() + "		" + "Helath: " + h.getCurrentHP() + "		" + "Mana: "
				+ h.getCurrentManaCrystals() + "/" + h.getTotalManaCrystals() + "		" + "Cards in Deck: "
				+ h.getDeck().size();
	}

	@Override
	public void onGameOver() {
		if (h1.getCurrentHP() == 0)
			JOptionPane.showMessageDialog(gameWindow, h2.getName() + " is the WINNER");
		else {
			JOptionPane.showMessageDialog(gameWindow, h1.getName() + " is the WINNER");
		}
		gameWindow.getFrame().setVisible(false);
		gameWindow.getFrame().dispose();
		gameWindow.getClip().stop();
		try {
			new ControllerMenu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public JButton space() {
		JButton space = new JButton();
		space.setVisible(false);
		return space;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton m = (JButton) (e.getSource());
		if (hand1.contains(m) && attackerButton == null && spellCard == null && !uhp) {
			Card card = h1.getHand().get(hand1.indexOf(m));
			if (card instanceof Minion) {
				Minion mi = (Minion)card;
				String s1;
				String s2;
				String s3;
				if (mi.isTaunt())
					s1="YES";
				else
					s1 = "NO";
				if (mi.isDivine())
					s2="YES";
				else
					s2= "NO";
				if (mi.isSleeping())
					s3="NO";
				else
					s3="YES";
				gameWindow.getMiImage()
						.add(new JLabel(new ImageIcon(
								((new ImageIcon(getClass().getResource("/" + card.getName() + ".png"))).getImage())
										.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP() + "\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Charged:" + s3);
				gameWindow.getMiInfo().setForeground(Color.WHITE);
				gameWindow.getMiInfo().setFont(new Font("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Charged:" + s3, Font.BOLD, 16));
				gameWindow.getMiInfo().setVisible(true);
				gameWindow.revalidate();
				gameWindow.repaint();
			}
			if (card instanceof Spell) {
				Spell mi = (Spell)card;
				gameWindow.getMiImage()
						.add(new JLabel(new ImageIcon(
								((new ImageIcon(getClass().getResource("/" + card.getName() + ".png"))).getImage())
										.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity());
				gameWindow.getMiInfo().setForeground(Color.WHITE);
				gameWindow.getMiInfo().setFont(new Font("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity(), Font.BOLD, 16));
				gameWindow.getMiInfo().setVisible(true);
				gameWindow.revalidate();
				gameWindow.repaint();
			}
		}
		if (field1.contains(m) && attackerButton == null && spellCard == null && !uhp) {
			Card card = h1.getField().get(field1.indexOf(m));
			Minion mi = (Minion)card;
			String s1;
			String s2;
			String s3;
			if (mi.isTaunt())
				s1="YES";
			else
				s1 = "NO";
			if (mi.isDivine())
				s2="YES";
			else
				s2= "NO";
			if (mi.isSleeping())
				s3="YES";
			else
				s3= "NO";
			gameWindow.getMiImage()
					.add(new JLabel(new ImageIcon(
							((new ImageIcon(getClass().getResource("/" + card.getName() + ".png"))).getImage())
									.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
			gameWindow.getMiImage().setVisible(true);
			gameWindow.getMiInfo().setText("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Sleeping: "+ s3);
			gameWindow.getMiInfo().setForeground(Color.WHITE);
			gameWindow.getMiInfo().setFont(new Font("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Sleeping: "+ s3, Font.BOLD, 16));
			gameWindow.getMiInfo().setVisible(true);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		if (field2.contains(m) && attackerButton == null && spellCard == null && !uhp) {
			Card card = h2.getField().get(field2.indexOf(m));
			Minion mi = (Minion)card;
			String s1;
			String s2;
			String s3;
			if (mi.isTaunt())
				s1="YES";
			else
				s1 = "NO";
			if (mi.isDivine())
				s2="YES";
			else
				s2= "NO";
			if (mi.isSleeping())
				s3="YES";
			else
				s3= "NO";
			gameWindow.getMiImage()
					.add(new JLabel(new ImageIcon(
							((new ImageIcon(getClass().getResource("/" + card.getName() + ".png"))).getImage())
									.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
			gameWindow.getMiImage().setVisible(true);
			gameWindow.getMiInfo().setText("\n "+"\n"+"Name: " + mi.getName()+"\n Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Sleeping: "+ s3);
			gameWindow.getMiInfo().setForeground(Color.WHITE);
			gameWindow.getMiInfo().setFont(new Font("\n "+"\n"+"Name: " + mi.getName()+"\n"+"Manacost: " + mi.getManaCost() + "\n"+"Rarity: " + mi.getRarity()+ "\n"+"CurrentHP: "+mi.getCurrentHP()+"\n" + "Attack: "+mi.getAttack()+"\n"+"Taunt: "+s1+"\n"+"Sheild: "+s2+"\n"+"Sleeping: "+ s3, Font.BOLD, 16));
			gameWindow.getMiInfo().setVisible(true);
			gameWindow.revalidate();
			gameWindow.repaint();
		}
		if (m.getText().equals("Use Hero Power")) {
			if (h1 instanceof Hunter) {
				gameWindow.getMiImage()
				.add(new JLabel(new ImageIcon(
						((new ImageIcon(getClass().getResource("/HunterH.png"))).getImage())
								.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("Inflict two damage points to the \nopponent hero");
				gameWindow.getMiInfo().setFont(new Font("Inflict two damage points to the \nopponent hero", Font.BOLD, 16));
			}
			if (h1 instanceof Mage) {
				gameWindow.getMiImage()
				.add(new JLabel(new ImageIcon(
						((new ImageIcon(getClass().getResource("/MageH.png"))).getImage())
								.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("Inflict one damage point to a \nspecific target a hero or a \nminion");
				gameWindow.getMiInfo().setFont(new Font("Inflict one damage point to a \nspecific target a hero or a \nminion", Font.BOLD, 16));
			}
			if (h1 instanceof Priest) {
				gameWindow.getMiImage()
				.add(new JLabel(new ImageIcon(
						((new ImageIcon(getClass().getResource("/PriestH.png"))).getImage())
								.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("Restore two health points to a \nspecific target a hero or a \nminion");
				gameWindow.getMiInfo().setFont(new Font("Restore two health points to a \nspecific target a hero or a \nminion", Font.BOLD, 16));
			}
			if (h1 instanceof Paladin) {
				gameWindow.getMiImage()
				.add(new JLabel(new ImageIcon(
						((new ImageIcon(getClass().getResource("/PaladinH.png"))).getImage())
								.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("Create a Silver Hand Recruit");
				gameWindow.getMiInfo().setFont(new Font("Create a Silver Hand Recruit", Font.BOLD, 16));
			}
			if (h1 instanceof Warlock) {
				gameWindow.getMiImage()
				.add(new JLabel(new ImageIcon(
						((new ImageIcon(getClass().getResource("/deck.png"))).getImage())
								.getScaledInstance(200, 270, java.awt.Image.SCALE_SMOOTH))));
				gameWindow.getMiImage().setVisible(true);
				gameWindow.getMiInfo().setText("Draw an extra card and inflict \ntwo damage points to the hero");
				gameWindow.getMiInfo().setFont(new Font("Draw an extra card and inflict two damage points to the hero", Font.BOLD, 16));
			}
			gameWindow.getMiInfo().setForeground(Color.WHITE);
			gameWindow.getMiInfo().setVisible(true);
			gameWindow.getMiImage().setVisible(true);
		}
	}
	public void reset() {
		//
		gameWindow.getMiImage().removeAll();
		gameWindow.getMiImage().setVisible(false);
		gameWindow.getMiInfo().removeAll();
		gameWindow.getMiInfo().setVisible(false);
		gameWindow.revalidate();
		gameWindow.repaint();
	}
	public void mouseExited(MouseEvent e) {
		if (attackerButton == null && spellCard == null && !uhp) {
			reset();
		}
	}
}

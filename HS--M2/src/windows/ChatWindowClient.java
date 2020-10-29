package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindowClient extends JFrame implements ActionListener{
	private DataInputStream in;
	private Socket s;
	private DataOutputStream out;
	private JFrame frame = new JFrame();
	private JTextArea textmsg = new JTextArea();
	private JPanel msgarea = new JPanel();
	private JTextField typefeild = new JTextField(); 
	private JButton send= new JButton("Send!");
	private String ip;
	private int port;
	public ChatWindowClient(String ip,int port)  {
		
		this.ip=ip;
		this.port=port;
		frame.setSize(new Dimension(500,500));
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textmsg.setEditable(false);
		typefeild.setPreferredSize(new Dimension(400,30));
		msgarea.setLayout(new FlowLayout());
		msgarea.add(typefeild);
		msgarea.add(send);
		send.setActionCommand("Send!");
		frame.add(textmsg,BorderLayout.CENTER);
		frame.add(msgarea,BorderLayout.SOUTH);
		frame.setVisible(true);
		this.InfoMsg("Connecting.....");
		send.setEnabled(false);
	
		
		send.addActionListener(this);
		
		

	}
	public String SendMsg(String msg) throws IOException {
		String r =textmsg.getText();
		r=r+"You: " +msg+"\n";
		System.out.println(r);
		textmsg.setText(r);
		return msg;
	}
	public  void InfoMsg(String msg) {
		String r=textmsg.getText();
		r=r+"INFO: "+msg+"\n";
		System.out.println(r);
		textmsg.setText(r);
		
	}
	public void ShowMsgOnScreen(String msg) {
		String r=textmsg.getText();
		r=r+"Your Friend: "+msg+"\n";
		textmsg.setText(r);
		
	}
	public void actionPerformed(ActionEvent e)  {
		if(e.getActionCommand().equals("Send!")) {
			System.out.println("sending");
			try {
				out.writeUTF(this.SendMsg(typefeild.getText()));
				typefeild.setText("");
			} catch (IOException e1) {
				System.out.println("error!");
				InfoMsg(e1.getMessage());
				InfoMsg("Close the window and reconnect again");
			}
		}
	}
	public void conncet() throws UnknownHostException, IOException {
			s = new Socket(ip,port);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			send.setEnabled(true);
			this.InfoMsg("Connected!");
		
	}

	public DataInputStream getIn() {
		return in;
	}
	public Socket getS() {
		return s;
	}
	public DataOutputStream getOut() {
		return out;
	}
	public JFrame getFrame() {
		return frame;
	}
	public JTextArea getTextmsg() {
		return textmsg;
	}
	public JPanel getMsgarea() {
		return msgarea;
	}
	public JTextField getTypefeild() {
		return typefeild;
	}
	public JButton getSend() {
		return send;
	}
	public void setIn(DataInputStream in) {
		this.in = in;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public void setOut(DataOutputStream out) {
		this.out = out;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public void setTextmsg(JTextArea textmsg) {
		this.textmsg = textmsg;
	}
	public void setMsgarea(JPanel msgarea) {
		this.msgarea = msgarea;
	}
	public void setTypefeild(JTextField typefeild) {
		this.typefeild = typefeild;
	}
	public void setSend(JButton send) {
		this.send = send;
	}
	public static void main(String[] args) {
		ChatWindowClient c=new ChatWindowClient(args[0], 1000); 
		try {
			c.conncet();
			while(true) {
				c.ShowMsgOnScreen(c.in.readUTF());
			}
		} catch (UnknownHostException e1) {
			c.InfoMsg(e1.getMessage());
			c.send.setEnabled(false);
			System.out.println(e1.getMessage());
			c.InfoMsg("Close the window and reconnect again");
			
		} catch (IOException e1) {
			c.InfoMsg(e1.getMessage());
			c.send.setEnabled(false);
			e1.getMessage();
			System.out.println(e1.getMessage());
			c.InfoMsg("Close the window and reconnect again");
		}
		
	}
}



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

import javax.swing.*;

public class ChatWindowServer extends JFrame implements ActionListener{
	private ServerSocket ss;
	private DataInputStream in;
	private Socket s;
	private DataOutputStream out;
	private JFrame frame = new JFrame();
	private JTextArea textmsg = new JTextArea();
	private JPanel msgarea = new JPanel();
	private JTextField typefeild = new JTextField(); 
	private JButton send= new JButton("Send!");
	public ChatWindowServer(int port)  {
		
		try {
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
		send.addActionListener(this);
		ss= new ServerSocket(port);
		this.InfoMsg("This is your IP address");
		System.out.println(ss.getInetAddress());
		this.InfoMsg(""+InetAddress.getLocalHost());
		this.InfoMsg("Send this IP address to you friend");
		this.InfoMsg("Waiting on connection");
		this.send.setEnabled(false);
		s=ss.accept();
		this.InfoMsg("Connected!");
		in= new DataInputStream(s.getInputStream());
		out=new DataOutputStream(s.getOutputStream());
		this.send.setEnabled(true);
		}
		catch (IOException e) {
			System.out.println("There seems to be an error in connection");
			this.InfoMsg(e.getMessage());
			this.InfoMsg("Close the window and reconnect again");

		}
	}
	public String SendMsg(String msg) throws IOException {
		String r =textmsg.getText();
		r=r+"You: " +msg+"\n";
		System.out.println(r);
		textmsg.setText(r);
		return msg;
	}
	public void InfoMsg(String msg) {
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
	public void actionPerformed(ActionEvent e) {
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
	public static void main(String args[]) {
		ChatWindowServer c = new ChatWindowServer(1000);
		try {
		
		while(true){
			c.ShowMsgOnScreen(c.in.readUTF());
		} 
		}catch (IOException e) {
			e.printStackTrace();
			c.InfoMsg(e.getMessage());
			c.InfoMsg("Close the window and reconnect again");
		}
	
	
}
	
}

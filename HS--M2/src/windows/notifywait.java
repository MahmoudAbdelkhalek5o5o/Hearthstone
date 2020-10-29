package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class notifywait {
	ServerSocket ss;
	DataInputStream in;
	Socket s;
	DataOutputStream out;
	static String t="Here lies all of your notfications! Closing this will stop making you recieve any notifications!";
	public notifywait() throws IOException {
		ss= new ServerSocket(2000);
		s=ss.accept();
		in = new DataInputStream(s.getInputStream());
	}
	public static void main (String args[]) {
		try {
			notifywait n = new notifywait();
			JFrame f = new JFrame("Notifier");
			JTextArea ta = new JTextArea();
			f.setVisible(true);
			f.setSize(new Dimension(500,500));
			f.setLayout(new BorderLayout());
			ta.setEditable(false);
			f.add(ta,BorderLayout.CENTER);
			t = t+"\n"+n.s.getInetAddress()+"is Calling!!"+"\n";
			JOptionPane.showMessageDialog(f,n.s.getInetAddress()+"is Calling!!"+"\n" );
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ta.setText(t);
			
			while(true) {
				n.ss.accept();
				t = t+""+n.s.getInetAddress()+"is Calling!!"+"\n";
				ta.setText(t);
				JOptionPane.showMessageDialog(f,n.s.getInetAddress()+"is Calling!!"+"\n");
				n.ss.close();
				n.ss = new ServerSocket(2000);
				}
					
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

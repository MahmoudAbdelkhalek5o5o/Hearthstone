package windows;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
public class notifycall {
	Socket s;
	String in;
	DataInputStream inn;
	DataOutputStream out;
	public notifycall(String ip) throws UnknownHostException, IOException{
		s = new Socket(ip,2000);
		inn = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
	}

	public static void main (String[] args) {
		try {
			notifycall x = new notifycall(args[0]);
			System.out.println("called!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
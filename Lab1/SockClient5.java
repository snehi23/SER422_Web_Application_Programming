import java.net.*;
import java.io.*;

class SockClient5 {
	public static void main (String args[]) throws Exception {
		Socket          sock = null;
		OutputStream    out = null;
		InputStream     in = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Integer i1=0, i2=0,result=0;
		char cmd = ' ';
		
		if (args.length < 2 || args.length > 3) {
			System.out.println("USAGE: java SockClient5 <char_command> [<int_id> <int_input>]");
			System.exit(1);
		}

		try {
			sock = new Socket("localhost", 8888);
			out = sock.getOutputStream();
			in = sock.getInputStream();
			dis = new DataInputStream(in);
			dos = new DataOutputStream(out);
			cmd = args[0].charAt(0);
			dos.write(cmd);
			switch (cmd) {
			case 'r':
				i1 = Integer.parseInt(args[1]);
				dos.writeInt(i1);
				break;
			case 't':
				i1 = Integer.parseInt(args[1]);
				dos.writeInt(i1);
				i2 = Integer.parseInt(args[2]);
				dos.writeInt(i2);
				break;
			default: 
				i1 = Integer.parseInt(args[1]);
				dos.writeInt(i1);
				i2 = Integer.parseInt(args[2]);
				dos.writeInt(i2);
			}
				try {
				result = dis.readInt();
				}
				catch (EOFException e) {
					System.out.println("Client "+args[1]+" Does Not Exist");
					System.exit(3);
				}
					System.out.println("Result is " + result);
		} catch (NumberFormatException nfe) {
			System.out.println("Command line args must be integers");
			System.exit(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dos != null) dis.close();
			if (dis != null) dis.close();
			if (out != null)  out.close();
			if (in != null)   in.close();
			if (sock != null) sock.close();
			
		}
	}
}

import java.net.*;
import java.io.*;
import java.util.*;

class SockServer4 {
	public static void main (String args[]) throws Exception {
		ServerSocket    serv = null;
		InputStream in = null;
		OutputStream out = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket sock = null;
		Integer clientId=0,x=0;
		Map<Integer, Integer> totals = new HashMap<Integer, Integer>();

		try {
			serv = new ServerSocket(8888);
		} catch(Exception e) {
			e.printStackTrace();
		}
		while (serv.isBound() && !serv.isClosed()) {
			System.out.println("Server4 Ready...");
			try {
				sock = serv.accept();
				in = sock.getInputStream();
				out = sock.getOutputStream();
				dis = new DataInputStream(in);
				dos = new DataOutputStream(out);
				
				char c = (char) dis.read();
				System.out.print("Server4 received " + c);
				if (args.length > 0) Thread.sleep(Long.parseLong(args[0]));
				switch (c) {
				case 'r': 
					clientId = dis.readInt();
					totals.put(clientId, 0);
					dos.writeInt(0);
					break;
				case 't': 
					clientId = dis.readInt();
					x = dis.readInt();
					System.out.print(" for client " + clientId + " " + x);
					Integer total = totals.get(clientId);
					if (total == null) {
						total = 0;
					}
					totals.put(clientId, total + x);
					dos.writeInt(totals.get(clientId).intValue());
					break;
				default:
					Integer x2 = dis.readInt();
					Integer y = dis.readInt();
					System.out.print(" " + x2 + " " + y);
					dos.writeInt(x2 + y);
				}
				System.out.println("");
				dos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (dis != null) dis.close();
				if (out != null)  out.close();
				if (in != null)   in.close();
				if (sock != null) sock.close();
			}
		}
	}
}


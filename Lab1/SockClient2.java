import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SockClient2 {
	
	public static void main (String args[]) throws Exception {
        Socket          sock = null;
        OutputStream    out = null;
        InputStream     in = null;
        int i1=0;
        String cmd = "r";

	if (args.length != 1) {
	    System.out.println("USAGE: java SockClient2 int1 OR java SockClient2 char");
	    System.exit(1);
	}
        try {
            sock = new Socket("localhost", 8888);
            out = sock.getOutputStream();
            in = sock.getInputStream();
            	
            	if(cmd.equals(args[0])) {
            		out.write(cmd.charAt(0));		
            	} else {
            		
            		try {
            		i1 = Integer.parseInt(args[0]);
            		out.write('t');
            		out.write(i1);
            		} catch(NumberFormatException nfe) {
            			System.out.println("Command line args must be integer or char 'r'");
            			System.exit(2);
            		}
				}	

            int result = in.read();
            System.out.println("Result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null)  out.close();
            if (in != null)   in.close();
            if (sock != null) sock.close();
        }
    }

}

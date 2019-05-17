package tb.sockets.server;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Konsola{
	
	public static void main(String[] args) throws IOException 
	{
		ServerSocket sSock;
		Socket sock;
		sSock = new ServerSocket(6666);
		sock = sSock.accept();
		Thread t = new Thread(new server(sock,sSock));
		t.start();
	}
}

class server implements Runnable
{
	DataInputStream in;
	BufferedReader is;
	String odczyt;
	StringBuilder strbuild;
	String odczyt2;
	Socket sock;
	ServerSocket sSock;
	int counter=0;
	server(Socket sock_,ServerSocket sSock_)
	{
		sock=sock_;
		sSock=sSock_;
	}
	@Override
	public void run() {
		String listen = null;
			while(true)
			{
				try {
					listen=Listen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(listen);
			}
	}
	public String Listen() throws IOException
	{
		try {
		sock = sSock.accept();
		in = new DataInputStream(sock.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		is = new BufferedReader(new InputStreamReader(in));
		odczyt = is.readLine();
		if(odczyt==null)
			return null;
		strbuild = new StringBuilder(odczyt);
		for(int i = 0;i<strbuild.length();i++)
			strbuild.deleteCharAt(i);
		
		odczyt2=strbuild.toString();
		return odczyt;
	}
}

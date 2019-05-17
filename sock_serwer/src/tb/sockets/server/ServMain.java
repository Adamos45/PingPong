package tb.sockets.server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class ServMain extends JFrame {
	private JPanel contentPane;
	PrintWriter so;
	ServerSocket sSock;
	Socket sock;
	String host;
	int port;
	static int mypoints=0,hispoints=0;
	static double x;
	static OrderPane panel;
	String strtosend;
	static Thread t;
	protected JTextArea myptsshow;
	protected JTextArea hisptsshow;

	JLabel lblNotConnected;
	public static void main(String[] args)  throws IOException
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServMain frame = new ServMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
public ServMain() throws ParseException, IOException {
	
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 650, 500);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblHost = new JLabel("Host:");
	lblHost.setBounds(10, 12, 26, 14);
	contentPane.add(lblHost);
	
	JTextField frmtdtxtfldIp = new JTextField();
	frmtdtxtfldIp.setBounds(43, 11, 90, 20);
	frmtdtxtfldIp.setEnabled(false);
	contentPane.add(frmtdtxtfldIp);
	
	JLabel lblPort = new JLabel("Port:");
	lblPort.setBounds(10, 40, 26, 14);
	frmtdtxtfldIp.setFocusable(false);
	contentPane.add(lblPort);
	
	JTextField frmtdtxtfldXxxx = new JFormattedTextField();
	SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			panel.initialize();
		}
	});
	frmtdtxtfldXxxx.setBounds(43, 39, 90, 20);
	contentPane.add(frmtdtxtfldXxxx);
	JButton connect = new JButton("Connect");
	connect.setBounds(10, 70, 100, 23);
	contentPane.add(connect);
	connect.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = frmtdtxtfldXxxx.getText();
			try {
				transferFocus();
				sSock=new ServerSocket(Integer.parseInt(str));
				sSock.setSoTimeout(100);
				try{
				sock = sSock.accept();}
				catch(SocketTimeoutException e2){}
				frmtdtxtfldIp.setText(sSock.getInetAddress().toString());
				frmtdtxtfldXxxx.setText(Integer.toString(sSock.getLocalPort()));
				t = new Thread(new server(sock,sSock));
				t.start();
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	});
	
	JLabel mypts = new JLabel("Twoje punkty ");
	mypts.setBounds(10, 90, 100, 25);
	contentPane.add(mypts);
	
	myptsshow = new JTextArea();
	myptsshow.setBounds(10, 110, 100, 23);
	myptsshow.setEditable(false);
	contentPane.add(myptsshow);
	
	JLabel hispts = new JLabel("Punky przec. ");
	hispts.setBounds(10, 135, 100, 25);
	contentPane.add(hispts);
	
	hisptsshow = new JTextArea();
	hisptsshow.setBounds(10, 160, 100, 23);
	hisptsshow.setEditable(false);
	contentPane.add(hisptsshow);
	
	panel = new OrderPane();
	panel.setBounds(145, 14, 487, 448);
	panel.setFocusable(true);
	panel.requestFocusInWindow();
	contentPane.add(panel);
	
	
	lblNotConnected = new JLabel();
	lblNotConnected.setBounds(10, 190, 123, 23);
	lblNotConnected.setOpaque(true);
	lblNotConnected.setForeground(new Color(255, 255, 255));
	lblNotConnected.setBackground(new Color(128, 128, 128));
	lblNotConnected.setText("Not Connected");
	contentPane.add(lblNotConnected);
	
	JButton exit = new JButton("Wyjœcie");
	exit.setBounds(10, 300, 100, 20);
	exit.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
	contentPane.add(exit);
}

class server implements Runnable
{
	
	DataInputStream in;
	PrintWriter so;
	BufferedReader is;
	String odczyt;
	StringBuilder strbuild;
	Socket sock;
	ServerSocket sSock;
	server(Socket sock_,ServerSocket sSock_)
	{
		sock=sock_;
		sSock=sSock_;
	}
	@Override
	public void run() {
			while(true)
			{
			try {
				try{
					sock = sSock.accept();
				}
				catch(SocketTimeoutException e)
				{
					lblNotConnected.setForeground(new Color(255, 255, 255));
					lblNotConnected.setBackground(new Color(128, 128, 128));
					lblNotConnected.setText("Not Connected");
					continue;
				}
					lblNotConnected.setForeground(new Color(255, 255, 255));
					lblNotConnected.setBackground(Color.GREEN);
					lblNotConnected.setText("Connected");

				in = new DataInputStream(sock.getInputStream());
				is = new BufferedReader(new InputStreamReader(in));
				odczyt = is.readLine();	
				if(odczyt==null)
					continue;
				int index = odczyt.indexOf(' ');
				int index2 = odczyt.indexOf('S', index+1);
				int index3 = odczyt.indexOf('P');
				String rockstr = odczyt.substring(0, index-1);
				String ballstr;
				if(index2==-1)
					ballstr = odczyt.substring(index+1, index3);
				else
				{
					ballstr = odczyt.substring(index+1, index3-1);
				}
				OrderPane.rocket2.setObjLocation(Double.parseDouble(rockstr));
				OrderPane.pilka.inputLoc(ballstr);
				String mypts = odczyt.substring(index3+1,odczyt.length());
				myptsshow.setText(mypts);
				
				so =new PrintWriter(sock.getOutputStream());
				String loc = OrderPane.rocket.getObjLocation()+" "+hispoints;
				hisptsshow.setText(Integer.toString(hispoints));
				so.println(loc);
				so.flush();
				so.close();
				is.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}}
}
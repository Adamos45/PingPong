package tb.sockets.client;

import java.awt.EventQueue;
import java.net.DatagramSocket;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import tb.sockets.client.kontrolki.KKButton;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.border.LineBorder;

public class MainFrame extends JFrame
{
	private JPanel contentPane;
	PrintWriter so;
	DataInputStream in;
	DatagramSocket sock;
	String host;
	int port;
	static int mypoints=0,hispoints=0;
	String strtosend;
	static OrderPane panel;
	static String receive;
	static Thread positionSender;
	static Thread positionReceiver;
	boolean connstate=false,start=true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					panel.requestFocusInWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame()	throws ParseException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(10, 12, 26, 14);
		contentPane.add(lblHost);
		
		JTextField frmtdtxtfldIp;
		frmtdtxtfldIp = new JTextField();
		frmtdtxtfldIp.setFocusable(true);
		frmtdtxtfldIp.setBounds(43, 11, 90, 20);
		frmtdtxtfldIp.setText("xxx.xxx.xxx.xxx");
		contentPane.add(frmtdtxtfldIp);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(10, 40, 26, 14);
		contentPane.add(lblPort);
		
		JFormattedTextField frmtdtxtfldXxxx = new JFormattedTextField();
		frmtdtxtfldXxxx.setText("xxxx");
		frmtdtxtfldXxxx.setBounds(43, 39, 90, 20);
		contentPane.add(frmtdtxtfldXxxx);
		
		JLabel mypts = new JLabel("Twoje punkty ");
		mypts.setBounds(10, 80, 100, 25);
		contentPane.add(mypts);
		
		JTextArea myptsshow = new JTextArea();
		myptsshow.setBounds(10, 100, 100, 23);
		myptsshow.setEditable(false);
		contentPane.add(myptsshow);
		
		JLabel hispts = new JLabel("Punky przec. ");
		hispts.setBounds(10, 120, 100, 25);
		contentPane.add(hispts);
		
		JTextArea hisptsshow = new JTextArea();
		hisptsshow.setBounds(10, 140, 100, 23);
		hisptsshow.setEditable(false);
		contentPane.add(hisptsshow);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 60, 75, 25);
		btnConnect.setFocusable(false);
		contentPane.add(btnConnect);
		
		panel = new OrderPane();
		panel.setBounds(145, 14, 487, 448);
		contentPane.add(panel);
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				panel.initialize();
			}
		});
		
		positionSender = new Thread(new Runnable()
		{
			@Override
			public void run() {
				BufferedReader is;
				while(true)
				{
					if(connstate==false)
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					else
					{
					try {
					sock = new Socket(host, port);
					in = new DataInputStream(sock.getInputStream());
					so=new PrintWriter(sock.getOutputStream());
					is = new BufferedReader(new InputStreamReader(in));
					String loc;
					try{
						loc = OrderPane.rocket.getObjLocation()+" "+OrderPane.pilka.getObjLoc()+"P"+hispoints;
					}
					catch(NullPointerException e)
					{
						loc="150.0 100.0 100.0P0";
					}
					so.println(loc);
					so.flush();
					//System.out.println("Oczekiwanie na znaki");
					receive = is.readLine();
					//System.out.println(receive);
					if(receive==null)
						continue;
					int index = receive.indexOf(' ');
					String servlo=receive.substring(0, index+1);
					double servloc=Double.parseDouble(servlo);
					mypoints=Integer.parseInt(receive.substring(index+1, receive.length()));
					OrderPane.rocket2.setObjLocation(servloc);
					myptsshow.setText(Integer.toString(mypoints));
					hisptsshow.setText(Integer.toString(hispoints));
					
					is.close();
					so.close();
					
					Thread.sleep(10);
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					}
				}
			}
		});
		
		JLabel lblNotConnected = new JLabel("Not Connected");
		lblNotConnected.setForeground(new Color(255, 255, 255));
		lblNotConnected.setBackground(new Color(128, 128, 128));
		lblNotConnected.setOpaque(true);
		lblNotConnected.setBounds(10, 170, 123, 23);
		contentPane.add(lblNotConnected);
		btnConnect.setFocusable(false);
		btnConnect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
					if(frmtdtxtfldIp.getText().equals("   .   .   .   "))
					{
						host=null;
					}
					else
						host=frmtdtxtfldIp.getText();
					port=Integer.parseInt(frmtdtxtfldXxxx.getText());
					panel.requestFocusInWindow();
					if(connstate==false)
					{
					if(start==true)
						{
							positionSender.start();
							start=false;
						}
					try {
						sock = new DatagramSocket(port,host);
						frmtdtxtfldIp.setEditable(false);
						frmtdtxtfldXxxx.setEditable(false);
						connstate=true;
						lblNotConnected.setForeground(new Color(255, 255, 255));
						lblNotConnected.setBackground(Color.GREEN);
						lblNotConnected.setText("Connected");
						
					} catch (UnknownHostException e) {
						System.out.println("Nieznany host");
						e.printStackTrace();
						connstate=false;
					}
					catch(ConnectException e)
					{
						System.out.println("Po��czenie odrzucone");
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						connstate=false;
					}
					}
					else
					{
						try {
							sock.close();
							lblNotConnected.setForeground(new Color(255, 255, 255));
							lblNotConnected.setBackground(new Color(128, 128, 128));
							lblNotConnected.setText("Not connected");
							connstate=false;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							connstate=true;
						}
					}
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setFocusable(false);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_1.setBounds(10, 200, 123, 123);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 2, 5, 5));
		
		JButton btn1 = new JButton();
		JButton btn2 = new JButton();
		btn1.setFocusable(false);
		panel_1.add(btn1);
		panel_1.add(btn2);
		btn2.setEnabled(false);
		btn1.setText("Start");
		btn1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				OrderPane.pilka.accesssleep(false);
				btn2.setEnabled(true);
				btn1.setEnabled(false);
				panel.requestFocusInWindow();
			}
			
		});
		

		btn2.setText("Stop");
		btn2.setFocusable(false);
		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				OrderPane.pilka.accesssleep(true);
				btn1.setEnabled(true);
				btn2.setEnabled(false);
			}
			
		});
		
		
		JButton exit = new JButton("Wyj�cie");
		exit.setFocusable(false);
		exit.setBounds(10, 400, 100, 20);
		exit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
				}
				});
		contentPane.add(exit);
	}
}

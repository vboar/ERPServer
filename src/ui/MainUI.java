/**
 * 服务器启动界面
 * @author Vboar
 * @date 2014/11/15
 */

package ui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.JButton;
import javax.swing.JFrame;

import data.userdata.UserDataServiceImpl;
import dataservice.userdataservice.UserDataService;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 400;
	
	private static final int HEIGHT = 400;
	
	private JButton btnStart;
	
	private JButton btnStop;
	
	public static final int PORT = 8888;
	
	public static final String ADDRESS = "rmi://127.0.0.1:";
	
	public static void main(String[] args) {
		
		new MainUI().createUI();
		
	}
	
	public void createUI() {
		
		this.setSize(WIDTH, HEIGHT);
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((screenWidth-WIDTH)>>1, (screenHeight-HEIGHT)>>1);
		this.setDefaultCloseOperation(3);
		this.getContentPane().setLayout(null);
		
		btnStart = new JButton("启动服务");
		btnStart.setBounds(0, 0, 400, 200);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LocateRegistry.createRegistry(PORT);
					System.out.println("Service Start!");
					UserDataService userDataService = new UserDataServiceImpl();
					Naming.rebind(MainUI.ADDRESS + MainUI.PORT + "/UserDataService", userDataService);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		this.getContentPane().add(btnStart);
		
		btnStop = new JButton("停止服务");
		btnStop.setBounds(0, 200, 400, 200);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(3);
			}
			
		});
		this.getContentPane().add(btnStop);
		
		this.setVisible(true);
		
	}

}

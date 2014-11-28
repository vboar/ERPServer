/**
 * 服务器启动界面
 * @author Vboar
 * @date 2014/11/15
 */

package ui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import data.datafactory.DataFactoryImpl;
import dataservice.datafactoryservice.DataFactory;

@SuppressWarnings("serial")
public class MainUI extends JFrame {
	
	/**
	 * 窗口宽度
	 */
	private static final int WIDTH = 800;
	
	/**
	 * 窗口高度
	 */
	private static final int HEIGHT = 500;
	
	/**
	 * 面板
	 */
	private JPanel panel;
	
	/**
	 * 启动服务按钮
	 */
	private JButton btnStart;
	
	/**
	 * 停止服务按钮
	 */
	private JButton btnStop;
	
	/**
	 * 更改端口按钮
	 */
	private JButton btnChange;
	
	/**
	 * 输出区域
	 */
	private JTextArea outputArea;
	
	/**
	 * 服务器信息
	 */
	private JLabel labInfo;
	
	/**
	 * 服务器运行状态
	 */
	private JLabel runningInfo;
	
	/**
	 * 端口值
	 */
	public int port = 8888;
	
	/**
	 * IP地址
	 */
	public String address = "127.0.0.1";
	
	/**
	 * 服务开启状态
	 */
	private boolean isOn;
	
	private Remote reg;
	
	/**
	 * 更改端口窗体
	 */
	private ChangeDialog dialog;
	
	/**
	 * 服务端主入口
	 * @param args
	 */
	public static void main(String[] args) {
		// 解决输入法冲突
		System.setProperty("sun.java2d.noddraw", "true");
		MainUI.changeFont();
		MainUI.changeLook();
		new MainUI();
	}
	
	/**
	 * 创建MainUI
	 */
	public MainUI() {
		this.createUI();
		this.initButtons();
		this.initLabels();
		this.repaint();
	}
	
	/**
	 * 更改默认字体
	 */
	private static void changeFont() {
		Font font = new Font("微软雅黑", Font.PLAIN, 15);   
		@SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();   
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();   
		    Object value = UIManager.get(key);    
		    if (value instanceof javax.swing.plaf.FontUIResource) {
		    	UIManager.put(key, font);     
		    } 
		} 
	}
	
	/**
	 * 更改外观
	 */
	private static void changeLook() {
	    try {
	    	BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        // 关闭右上角设置
	        UIManager.put("RootPane.setupButtonVisible", false);
	    } catch(Exception e) {
	    }
	}
	
	/**
	 * 创建窗体
	 */
	private void createUI() {
		
		this.setTitle("ERP企业进销存管理系统服务器端");
		this.setSize(WIDTH, HEIGHT);
		((JComponent) this.getContentPane()).setOpaque(true);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((screenWidth-WIDTH)>>1, (screenHeight-HEIGHT)>>1);
		this.setDefaultCloseOperation(3);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		panel = (JPanel) this.getContentPane();
		
	}
	
	/**
	 * 初始化按钮
	 */
	private void initButtons() {
		
		btnStart = new JButton("启动服务");
		btnStart.setBounds(550, 10, 100, 25);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!isOn) {
						reg = LocateRegistry.createRegistry(port);
						DataFactory dataFacory = new DataFactoryImpl();
						Naming.rebind("rmi://" + address + ":" + port + "/DataFactory", dataFacory);
						runningInfo.setText("服务运行中...");
						isOn = true;
					} else {
						JOptionPane.showMessageDialog(null, "服务正在运行中.....");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		panel.add(btnStart);
		
		btnStop = new JButton("停止服务");
		btnStop.setBounds(670, 10, 100, 25);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(isOn) {
						UnicastRemoteObject.unexportObject(reg, true);
						runningInfo.setText("服务已停止...");
						isOn = false;
					} else {
						JOptionPane.showMessageDialog(null, "服务已经停止了！");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnStop);
		
		btnChange = new JButton("更改端口");
		btnChange.setBounds(320, 10, 100, 25);
		btnChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isOn) {
					JOptionPane.showMessageDialog(null, "请先停止服务！");
				} else {
					showChangeDialog();
				}
			}
			
		});
		panel.add(btnChange);
	}
	
	/**
	 * 初始化标签
	 */
	private void initLabels() {
		
		labInfo = new JLabel("服务器信息(IP:PORT)：" + this.address + ":" + this.port);
		labInfo.setBounds(20, 10, 300, 25);
		panel.add(labInfo);
		
		runningInfo = new JLabel("服务未开启！");
		runningInfo.setBounds(440, 10, 150, 25);
		panel.add(runningInfo);
		
		outputArea = new JTextArea("监控系统运行：\n");
		outputArea.setBounds(20, 50, 750, 400);
		outputArea.setEditable(false);
		// 自动换行
		outputArea.setLineWrap(true);
		// 断行不断字
		outputArea.setWrapStyleWord(true); 
		// 添加滚动条
		JScrollPane js = new JScrollPane(outputArea);
		js.setBounds(20, 50, 750, 400);
		panel.add(js);
		// 获取重定向流
		this.redirectSystemStreams();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	private void showChangeDialog() {
		dialog = new ChangeDialog(this, panel);
		dialog.setVisible(true);
	}
	
	/**
	 * 更新服务器信息
	 */
	public void updateInfo() {
		labInfo.setText("服务器信息(IP:PORT)：" + this.address + ":" + this.port);
	}
	
	/**
	 * 更新输出区域线程
	 * @param text
	 */
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				outputArea.append(text);
				// 强制到最后一行
				outputArea.selectAll();
			}
		});
	}
	
	/**
	 * 输出流重定向
	 */
	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}
		 
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		    	write(b, 0, b.length);
		    }
		};
		 
		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}

}

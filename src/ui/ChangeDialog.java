package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 改变服务器端口对话框
 */
@SuppressWarnings("serial")
public class ChangeDialog extends JDialog {

	/**
	 * 当前端口显示标签
	 */
	private JLabel currentPort;

	/**
	 * 新端口显示标签
	 */
	private JLabel newPort;

	/**
	 * 新端口输入文本框
	 */
	private JTextField newPortTxt;

	/**
	 * 提交按钮
	 */
	private JButton btnSubmit;

	/**
	 * 取消按钮
	 */
	private JButton btnCancel;

	/**
	 * 服务器主窗体
	 */
	private MainUI mainFrame;

	/**
	 * 固定宽度
	 */
	private static final int WIDTH = 300;

	/**
	 * 固定高度
	 */
	private static final int HEIGHT = 200;

	/**
	 * 构造方法
	 * @param mainFrame
	 */
	public ChangeDialog(MainUI mainFrame) {
		super(mainFrame, true);
		// 设置对话框为不透明（UI Jar包的显示问题）
		((JComponent) this.getContentPane()).setOpaque(true);
		this.mainFrame = mainFrame;
		this.createUI();
		this.initLabels();
		this.initButtons();
		this.initTextFields();
	}

	/**
	 * 初始化对话框
	 */
	private void createUI() {
		this.setTitle("更改端口");
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(mainFrame.getX() + (mainFrame.getWidth()-WIDTH)/2 ,
				mainFrame.getY() + (mainFrame.getHeight()-HEIGHT)/2);
		this.setResizable(false);
	}

	/**
	 * 初始化文本标签
	 */
	private void initLabels() {
		
		currentPort = new JLabel("当前端口：   " + mainFrame.getPort());
		currentPort.setBounds(20, 20, 300, 25);
		this.getContentPane().add(currentPort);
		
		newPort = new JLabel("请输入新的端口：");
		newPort.setBounds(20, 65, 200, 25);
		this.getContentPane().add(newPort);
	}

	/**
	 * 初始化文本框
	 */
	private void initTextFields() {
		newPortTxt = new JTextField();
		newPortTxt.setBounds(150, 65, 100, 25);
		this.getContentPane().add(newPortTxt);
	}

	/**
	 * 初始化按钮
	 */
	private void initButtons() {
		btnSubmit = new JButton("提交");
		btnSubmit.setBounds(90, 120, 75, 25);
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// 对输入的新端口进行合法化的验证
				if(newPortTxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "输入的端口为空！");
					newPortTxt.setText("");
					return;
				}
				boolean valid = true;
				char[] input = newPortTxt.getText().toCharArray();
				for(int i = 0; i < input.length; i++) {
					if(input[i] < '0' || input[i] >'9') {
						valid = false;
					}
				}
				if(!valid) {
					JOptionPane.showMessageDialog(null, "输入的端口含有非法字符！");
					newPortTxt.setText("");
					return;
				}
				if(newPortTxt.getText().length() > 5) {
					JOptionPane.showMessageDialog(null, "输入的端口非法！");
					newPortTxt.setText("");
					return;
				}
				int port = Integer.parseInt(newPortTxt.getText());
				if(port < 1000 || port >= 65536) {
					JOptionPane.showMessageDialog(null, "输入的端口非法！");
					newPortTxt.setText("");
					return;
				}
				mainFrame.setPort(port);
				JOptionPane.showMessageDialog(null, "端口更改成功！");
				ChangeDialog.this.dispose();
				mainFrame.updateInfo();
			}
			
		});
		this.getContentPane().add(btnSubmit);
		
		
		btnCancel = new JButton("取消");
		btnCancel.setBounds(175, 120, 75, 25);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChangeDialog.this.dispose();
			}
			
		});
		this.getContentPane().add(btnCancel);
	}

}

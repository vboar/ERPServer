/**
 * 消息数据层操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.messagedata;

import data.dataioutility.DataIOUtility;
import dataservice.messagedataservice.MessageDataService;
import po.MessagePO;
import po.UserPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MessageDataServiceImpl extends UnicastRemoteObject implements MessageDataService {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "message";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public MessageDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(MessagePO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(MessagePO po) throws RemoteException {
		print();
		ArrayList<String[]> lists = d.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getId())) {
				lists.remove(s);
				break;
			}
		}
		d.writeData(d.arrayToStringAll(lists));
	}
	
	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(MessagePO po) throws RemoteException {
		print();
		String[] temp = this.poToString(po).split(";");
		ArrayList<String[]> lists = d.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getId())) {
				for(int i = 1; i < s.length; i++) {
					s[i] = temp[i];
				}
				break;
			}
		}
		d.writeData(d.arrayToStringAll(lists));
	}

	/**
	 * 根据用户返回PO对象
	 */
	@Override
	public ArrayList<MessagePO> showByUser(UserPO po) throws RemoteException {
		print();
		ArrayList<MessagePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<MessagePO> lists = new ArrayList<MessagePO>();
		for(MessagePO mpo: tLists) {
			if(mpo.getReceiverId().equals(po.getId())) {
				lists.add(mpo);
			}
		}
		return lists;
	}

	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(MessagePO po) {
		return po.getId() + ";" + po.getTime() + ";" + po.getState()
				+ ";" + po.getReceiverId() + ";" + po.getContent() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private MessagePO stringToPo(String s) {
		String[] strs = s.split(";");
		int state = Integer.parseInt(strs[2]);
		return new MessagePO(strs[0], strs[1], state, strs[3], strs[4]);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<MessagePO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<MessagePO> lists = new ArrayList<MessagePO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

	/**
	 * 输出执行的类名及方法名
	 */
	private void print() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + ": executing " +
				Thread.currentThread().getStackTrace()[2].getMethodName());
	}

}

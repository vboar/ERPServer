/**
 * 用户数据层操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.userdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.UserPO;
import data.dataioutility.DataIOUtility;
import dataservice.userdataservice.UserDataService;

public class UserDataServiceImpl extends UnicastRemoteObject implements UserDataService {

	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "user";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造函数
	 * @throws RemoteException
	 */
	public UserDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(UserPO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));

	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(UserPO po) throws RemoteException {
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
	public void update(UserPO po) throws RemoteException {
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
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<UserPO> findById(String id) throws RemoteException {
		print();
		ArrayList<UserPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(UserPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据用户姓名模糊查找PO对象
	 */
	@Override
	public ArrayList<UserPO> findByName(String name) throws RemoteException {
		print();
		ArrayList<UserPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(UserPO po: tLists) {
			if(po.getName().contains(name)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据用户类型模糊查找PO对象
	 */
	@Override
	public ArrayList<UserPO> findByType(int type) throws RemoteException {
		print();
		ArrayList<UserPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(UserPO po: tLists) {
			if(po.getType() == type) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据用户ID准确查找PO对象
	 */
	@Override
	public UserPO getById(String id) throws RemoteException {
		print();
		ArrayList<UserPO> lists = this.stringToPoAll(d.readData());
		for(UserPO po: lists) {
			if(id.equals(po.getId())) {
				return po;
			}
		}
		return null;
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<UserPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(UserPO po) {
		return po.getId() + ";" + po.getPassword() + ";" + po.getType()
				+ ";" + po.getPermission() + ";" + po.getName() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private UserPO stringToPo(String s) {
		String[] strs = s.split(";");
		int type = Integer.parseInt(strs[2]);
		int permission = Integer.parseInt(strs[3]);
		return new UserPO(strs[0], strs[1], type, permission, strs[4]);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<UserPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
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

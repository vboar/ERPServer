/**
 * 用户数据实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.userdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.UserPO;
import util.UserType;
import data.dataioutility.DataIOUtility;
import dataservice.userdataservice.UserDataService;

public class UserDataServiceImpl extends UnicastRemoteObject implements UserDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "user";
	
	private DataIOUtility d = null;

	public UserDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	@Override
	public void insert(UserPO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));
	}

	@Override
	public void delete(UserPO po) throws RemoteException {
		ArrayList<String[]> lists = this.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getId())) {
				lists.remove(s);
			}
		}
		d.writeData(this.arrayToStringAll(lists));
	}

	@Override
	public void update(UserPO po) throws RemoteException {
		String[] temp = this.poToString(po).split(";");
		ArrayList<String[]> lists = this.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getId())) {
				for(int i = 1; i < s.length; i++) {
					s[i] = temp[i];
				}
			}
		}
		d.writeData(this.arrayToStringAll(lists));
	}

	@Override
	public ArrayList<UserPO> findById(String id) throws RemoteException {
		ArrayList<UserPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(UserPO po: tLists) {
			// TODO
			if(po.getId().equals(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	@Override
	public ArrayList<UserPO> findByName(String name) throws RemoteException {
		ArrayList<UserPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(UserPO po: tLists) {
			// TODO
			if(po.getName().equals(name)) {
				lists.add(po);
			}
		}
		return lists;
	}

	@Override
	public ArrayList<UserPO> findByType(UserType type) throws RemoteException {
		ArrayList<UserPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(UserPO po: tLists) {
			if(po.getType() == type) {
				lists.add(po);
			}
		}
		return lists;
	}

	@Override
	public ArrayList<UserPO> show() throws RemoteException {
		return this.stringToPoAll(d.readData());
	}
	
	private String poToString(UserPO po) {
		return po.getId() + ";" + po.getPassword() + ";" + po.getType() 
				+ ";" + po.getPermission() + ";" + po.getName();
	}
	
	private UserPO stringToPo(String s) {
		String[] strs = s.split(";");
		int permission = Integer.parseInt(strs[3]);
		return new UserPO(strs[0], strs[1], UserType.valueOf(strs[2]), permission, strs[4]);
	}
	
//	private ArrayList<String> poToStringAll(ArrayList<UserPO> lists) {
//		ArrayList<String> strs = new ArrayList<String>();
//		for(UserPO po: lists) {
//			strs.add(this.poToString(po));
//		}
//		return strs;
//	}
	
	private ArrayList<UserPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<UserPO> lists = new ArrayList<UserPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}
	
	private ArrayList<String[]> stringToArrayAll(ArrayList<String> strs) {
		ArrayList<String[]> lists = new ArrayList<String[]>();
		for(String s: strs) {
			lists.add(s.split(";"));
		}
		return lists;
	}
	
	private ArrayList<String> arrayToStringAll(ArrayList<String[]> lists) {
		ArrayList<String> strs = new ArrayList<String>();
		for(String[] s: lists) {
			strs.add(s[0] + ";" + s[1] + ";" + s[2] + ";" + s[3] + ";" + s[4]);
		}
		return strs;
	}

}

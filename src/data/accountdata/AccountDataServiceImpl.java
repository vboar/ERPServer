/**
 * 账户管理数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.accountdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.AccountPO;
import data.dataioutility.DataIOUtility;
import dataservice.accountdataservice.AccountDataService;

public class AccountDataServiceImpl extends UnicastRemoteObject implements AccountDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "account";
	
	private DataIOUtility d;

	public AccountDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(AccountPO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(AccountPO po) throws RemoteException {
		ArrayList<String[]> lists = d.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getAccount())) {
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
	public void update(AccountPO po) throws RemoteException {
		String[] temp = this.poToString(po).split(";");
		ArrayList<String[]> lists = d.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getAccount())) {
				for(int i = 1; i < s.length; i++) {
					s[i] = temp[i];
				}
				break;
			}
		}
		d.writeData(d.arrayToStringAll(lists));
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<AccountPO> show() throws RemoteException {
		ArrayList<String> strs = d.readData();
		ArrayList<AccountPO> lists = new ArrayList<AccountPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

	/**
	 * 根据名称模糊查找PO对象
	 */
	@Override
	public ArrayList<AccountPO> findByName(String name) throws RemoteException {
		ArrayList<AccountPO> tLists = this.show();
		ArrayList<AccountPO> lists = new ArrayList<AccountPO>();
		for(AccountPO po: tLists) {
			if(po.getName().contains(name)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public AccountPO findByAccount(String account) throws RemoteException {
		ArrayList<AccountPO> lists = this.stringToPoAll(d.readData());
		for(AccountPO po: lists) {
			if(account.equals(po.getAccount())) {
				return po;
			}
		}
		return null;
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(AccountPO po) {
		return po.getAccount() + ";" + po.getName() + ";" + po.getBalance() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private AccountPO stringToPo(String s) {
		String[] strs = s.split(";");
		double balance = Double.parseDouble(strs[2]);
		return new AccountPO(strs[1], strs[0], balance);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<AccountPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<AccountPO> lists = new ArrayList<AccountPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

}

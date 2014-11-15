/**
 * 账户管理数据实现
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
	
	@Override
	public ArrayList<AccountPO> find(String name) throws RemoteException {
		ArrayList<AccountPO> tLists = this.show();
		ArrayList<AccountPO> lists = new ArrayList<AccountPO>();
		for(AccountPO po: tLists) {
			if(po.getName().equals(name)) {
				lists.add(po);
			}
		}
		return lists;
	}

	@Override
	public void insert(AccountPO po) throws RemoteException {
		d.writeDataAdd(this.POToString(po));
	}

	@Override
	public void delete(AccountPO po) throws RemoteException {
		ArrayList<AccountPO> lists = this.show();
		for(AccountPO temp: lists) {
			if(temp.getAccount().equals(po.getAccount())) {
				lists.remove(temp);
				break;
			}
		}
		ArrayList<String> strs = new ArrayList<String>();
		for(AccountPO temp: lists) {
			strs.add(this.POToString(temp));
		}
		d.writeData(strs);
	}

	@Override
	public void update(AccountPO po) throws RemoteException {
		ArrayList<AccountPO> lists = this.show();
		ArrayList<String> strs = new ArrayList<String>();
		for(AccountPO temp: lists) {
			if(temp.getAccount().equals(po.getAccount())) {
				temp.setName(po.getName());
				temp.setBalance(po.getBalance());
			}
			strs.add(this.POToString(temp));
		}
		d.writeData(strs);
	}

	@Override
	public ArrayList<AccountPO> show() throws RemoteException {
		ArrayList<String> strs = d.readData();
		ArrayList<AccountPO> lists = new ArrayList<AccountPO>();
		for(String s: strs) {
			lists.add(this.StringToPO(s));
		}
		return lists;
	}
	
	private String POToString(AccountPO po) {
		return po.getAccount() + ";" + po.getName() + ";" + po.getBalance();
	}
	
	private AccountPO StringToPO(String s) {
		String[] strs = s.split(";");
		double balance = Double.parseDouble(strs[2]);
		return new AccountPO(strs[1], strs[0], balance);
	}

}

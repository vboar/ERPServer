/**
 * 客户管理数据层操作接口
 * @author Vboar
 * @date 2014/11/15
 */

package data.customerdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CustomerPO;
import data.dataioutility.DataIOUtility;
import dataservice.customerdataservice.CustomerDataService;

public class CustomerDataServiceImpl extends UnicastRemoteObject implements CustomerDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "user";
	
	private DataIOUtility d = null;

	public CustomerDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}
	
	@Override
	public void insert(CustomerPO po) throws RemoteException {
	}

	@Override
	public void delete(CustomerPO po) throws RemoteException {
	}

	@Override
	public void update(CustomerPO po) throws RemoteException {
	}

	@Override
	public ArrayList<CustomerPO> findByName(String name) throws RemoteException {
		return null;
	}

	@Override
	public ArrayList<CustomerPO> findById(String id) throws RemoteException {
		return null;
	}
	
	@Override
	public CustomerPO getById(String id) throws RemoteException {
		return null;
	}

	@Override
	public ArrayList<CustomerPO> show() throws RemoteException {
		return null;
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(CustomerPO po) {
		return po.getId() + ";" + po.getCategory() + ";" + po.getLevel() + ";" + 
				po.getName() + ";" + po.getPhoneNumber() + ";" + po.getAddress() + ";" + 
				po.getPostalCode() + ";" + po.getEmail() + ";" + po.getCreditLimit() + ";" + 
				po.getReceivables() + ";" + po.getPaybles() + ";" + po.getDefaultOperatorId()
				 + ";" + po.isDeletable() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private CustomerPO stringToPo(String s) {
		String[] strs = s.split(";");
		int category = Integer.parseInt(strs[1]);
		int level = Integer.parseInt(strs[2]);
		double creditLimit = Double.parseDouble(strs[8]);
		double receivables = Double.parseDouble(strs[9]);
		double paybles = Double.parseDouble(strs[10]);
		boolean isDeletable = Boolean.parseBoolean(strs[12]);
		return new CustomerPO(strs[0], category, level, strs[3], strs[4], strs[5], strs[6], strs[7],
				creditLimit, receivables, paybles, strs[11], isDeletable);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<CustomerPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<CustomerPO> lists = new ArrayList<CustomerPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

}

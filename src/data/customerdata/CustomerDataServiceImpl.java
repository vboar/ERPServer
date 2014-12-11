/**
 * 客户管理数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.customerdata;

import data.dataioutility.DataIOUtility;
import dataservice.customerdataservice.CustomerDataService;
import po.CustomerPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CustomerDataServiceImpl extends UnicastRemoteObject implements CustomerDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "customer";
	
	private DataIOUtility d = null;

	public CustomerDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}
	
	/**
	 * 添加一个PO对象
	 */
	@Override
	public void insert(CustomerPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(CustomerPO po) throws RemoteException {
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
	public void update(CustomerPO po) throws RemoteException {
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
	 * 根据姓名模糊查找PO对象
	 */
	@Override
	public ArrayList<CustomerPO> findByName(String name) throws RemoteException {
		print();
		ArrayList<CustomerPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CustomerPO> lists = new ArrayList<CustomerPO>();
		for(CustomerPO po: tLists) {
			if(po.getName().contains(name)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<CustomerPO> findById(String id) throws RemoteException {
		print();
		ArrayList<CustomerPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CustomerPO> lists = new ArrayList<CustomerPO>();
		for(CustomerPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public CustomerPO getById(String id) throws RemoteException {
		print();
		ArrayList<CustomerPO> lists = this.stringToPoAll(d.readData());
		for(CustomerPO po: lists) {
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
	public ArrayList<CustomerPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 期初建账根据账id返回所有PO对象
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<CustomerPO> showByInitial(String id) throws RemoteException {
		d.fatherPath = "data/" + id + "/";
		print();
		return this.stringToPoAll(d.readData());
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
				po.getReceivables() + ";" + po.getPaybles() + ";" + po.getSalesman()
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
	
	private void print() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + ": executing " + 
				Thread.currentThread().getStackTrace()[2].getMethodName());
	}

}

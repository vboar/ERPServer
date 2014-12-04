/**
 * 期初建账数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.initialdata;

import data.dataioutility.DataIOUtility;
import dataservice.initialdataservice.InitialDataService;
import po.InitialPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class InitialDataServiceImpl extends UnicastRemoteObject implements InitialDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "initial";
	
	private DataIOUtility d = null;

	public InitialDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}
	
	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(InitialPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<InitialPO> findById(String id) throws RemoteException {
		print();
		ArrayList<InitialPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<InitialPO> lists = new ArrayList<InitialPO>();
		for(InitialPO po: tLists) {
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
	public InitialPO getById(String id) throws RemoteException {
		print();
		ArrayList<InitialPO> lists = this.stringToPoAll(d.readData());
		for(InitialPO po: lists) {
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
	public ArrayList<InitialPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(InitialPO po) {
		return po.getId() + ";" + po.getName() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private InitialPO stringToPo(String s) {
		String[] strs = s.split(";");
		return new InitialPO(strs[0], strs[1]);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<InitialPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<InitialPO> lists = new ArrayList<InitialPO>();
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

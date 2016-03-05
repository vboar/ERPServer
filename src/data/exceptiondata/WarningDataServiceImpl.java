/**
 * 报警单数据操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.exceptiondata;

import data.dataioutility.DataIOUtility;
import dataservice.exceptiondataservice.WarningDataService;
import po.WarningLineItemPO;
import po.WarningPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WarningDataServiceImpl extends UnicastRemoteObject implements WarningDataService {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "warning";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public WarningDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(WarningPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 根据日期时间段查找PO对象
	 */
	@Override
	public ArrayList<WarningPO> show(String time1, String time2)
			throws RemoteException {
		print();
		ArrayList<WarningPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<WarningPO> lists = new ArrayList<WarningPO>();
		for(WarningPO po: tLists) {
			if(po.getTime().compareTo(time1) >= 0 && po.getTime().compareTo(time2) <= 0) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<WarningPO> findById(String id) throws RemoteException {
		print();
		ArrayList<WarningPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<WarningPO> lists = new ArrayList<WarningPO>();
		for(WarningPO po: tLists) {
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
	public WarningPO getById(String id) throws RemoteException {
		print();
		ArrayList<WarningPO> lists = this.stringToPoAll(d.readData());
		for(WarningPO po: lists) {
			if(id.equals(po.getId())) {
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
	private String poToString(WarningPO po) {
		String str = po.getId() + ";" + po.getTime() + ";" + po.getDocumentType() + ";";
		for(WarningLineItemPO wPo: po.getList()) {
			str += wPo.getId() + "," + wPo.getName() + "," + wPo.getModel() + "," + 
					wPo.getStockNumber() + "," + wPo.getWarningNumber() + DataIOUtility.splitStr;
		}
		if(po.getList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private WarningPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[3].split(DataIOUtility.splitStr);
		ArrayList<WarningLineItemPO> wPos = new ArrayList<WarningLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			wPos.add(new WarningLineItemPO(str3[0], str3[1], str3[2], Integer.parseInt(str3[3]), Integer.parseInt(str3[4])));
		}
		WarningPO po = new WarningPO(str1[0], str1[1], wPos, Integer.parseInt(str1[2]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<WarningPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<WarningPO> lists = new ArrayList<WarningPO>();
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

/**
 * 报警单数据操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.exceptiondata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.WarningLineItemPO;
import po.WarningPO;
import data.dataioutility.DataIOUtility;
import dataservice.exceptiondataservice.WarningDataService;

public class WarningDataServiceImpl extends UnicastRemoteObject implements WarningDataService {
	
	private static final long serialVersionUID = 1L;

	private String path = "warning";
	
	private DataIOUtility d = null;
	
	public WarningDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(WarningPO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 根据日期时间段查找PO对象
	 */
	@Override
	public ArrayList<WarningPO> show(String time1, String time2)
			throws RemoteException {
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
			str += wPo.getId() + "," + wPo.getStockNumber() + "," + wPo.getWarningNumber() + "|";
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
		String[] str2 = str1[3].split("|");
		ArrayList<WarningLineItemPO> wPos = new ArrayList<WarningLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			wPos.add(new WarningLineItemPO(str3[0], Integer.parseInt(str3[1]), Integer.parseInt(str3[2])));
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

}

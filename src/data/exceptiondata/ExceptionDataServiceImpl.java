/**
 * 报溢报损单数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.exceptiondata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.ExceptionLineItemPO;
import po.ExceptionPO;
import data.dataioutility.DataIOUtility;
import dataservice.exceptiondataservice.ExceptionDataService;

public class ExceptionDataServiceImpl extends UnicastRemoteObject implements ExceptionDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "exception";
	
	private DataIOUtility d = null;

	public ExceptionDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(ExceptionPO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(ExceptionPO po) throws RemoteException {
	}

	/**
	 * 根据日期时间段返回PO对象
	 */
	@Override
	public ArrayList<ExceptionPO> show(String time1, String time2)
			throws RemoteException {
		ArrayList<ExceptionPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<ExceptionPO> lists = new ArrayList<ExceptionPO>();
		for(ExceptionPO po: tLists) {
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
	public ArrayList<ExceptionPO> findById(String id) throws RemoteException {
		ArrayList<ExceptionPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<ExceptionPO> lists = new ArrayList<ExceptionPO>();
		for(ExceptionPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据单据状态查找PO对象
	 */
	@Override
	public ArrayList<ExceptionPO> findByStatus(int status)
			throws RemoteException {
		ArrayList<ExceptionPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<ExceptionPO> lists = new ArrayList<ExceptionPO>();
		for(ExceptionPO po: tLists) {
			if(po.getDocumentStatus() == status) {
				lists.add(po);
			}
		}
		return lists;
	}

	@Override
	public ExceptionPO getById(String id) throws RemoteException {
		return null;
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(ExceptionPO po) {
		String str = po.getId() + ";" + po.getTime() + ";" + po.getDocumentStatus() + ";" + 
				po.getDocumentType() + ";" + po.isWriteoff() + ";";
		for(ExceptionLineItemPO ePo: po.getList()) {
			str += ePo.getId() + "," + ePo.getSystemNumber() + "," + ePo.getActualNumber() + "|";
		}
		if(po.getList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private ExceptionPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[5].split("|");
		ArrayList<ExceptionLineItemPO> ePos = new ArrayList<ExceptionLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			ePos.add(new ExceptionLineItemPO(str3[0], Integer.parseInt(str3[1]), Integer.parseInt(str3[2])));
		}
		ExceptionPO po = new ExceptionPO(str1[0], str1[1], ePos, Integer.parseInt(str1[2]), 
				Integer.parseInt(str1[3]), Boolean.parseBoolean(str1[4]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<ExceptionPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<ExceptionPO> lists = new ArrayList<ExceptionPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

}

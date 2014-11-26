/**
 * 赠送单数据操作实现
 * @author vboar
 * @date 2014/11/25
 */
package data.presentdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.PresentLineItemPO;
import po.PresentPO;
import data.dataioutility.DataIOUtility;
import dataservice.presentdataservice.PresentDataService;

public class PresentDataServiceImpl extends UnicastRemoteObject implements PresentDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "present";
	
	private DataIOUtility d = null;

	public PresentDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(PresentPO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(PresentPO po) throws RemoteException {
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
	public ArrayList<PresentPO> findById(String id) {
		ArrayList<PresentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PresentPO> lists = new ArrayList<PresentPO>();
		for(PresentPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据日期时间段查找PO对象
	 */
	@Override
	public ArrayList<PresentPO> findByTime(String time1, String time2) {
		ArrayList<PresentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PresentPO> lists = new ArrayList<PresentPO>();
		for(PresentPO po: tLists) {
			if(po.getTime().compareTo(time1) >= 0 && po.getTime().compareTo(time2) <= 0) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据单据状态查找PO对象
	 */
	@Override
	public ArrayList<PresentPO> findByStatus(int status) throws RemoteException {
		ArrayList<PresentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PresentPO> lists = new ArrayList<PresentPO>();
		for(PresentPO po: tLists) {
			if(po.getDocumentStatus() == status) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据客户编号模糊查找PO对象
	 */
	@Override
	public ArrayList<PresentPO> findByCustomerId(String customerId)
			throws RemoteException {
		ArrayList<PresentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PresentPO> lists = new ArrayList<PresentPO>();
		for(PresentPO po: tLists) {
			if(po.getCustomerId().equals(customerId)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public PresentPO getById(String id) throws RemoteException {
		ArrayList<PresentPO> lists = this.stringToPoAll(d.readData());
		for(PresentPO po: lists) {
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
	private String poToString(PresentPO po) {
		String str = po.getId() + ";" + po.getTime() + ";" + po.getCustomerId() + ";" + 
				po.getCustomerName() + ";" + po.getDocumentStatus() + ";" + 
				po.getDocumentType() + ";" + po.isWriteoff() + ";";
		for(PresentLineItemPO pPo: po.getList()) {
			str += pPo.getId() + "," + pPo.getName() + "," + pPo.getModel() + "," + 
					pPo.getNumber() + "|";
		}
		if(po.getList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private PresentPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[7].split("|");
		ArrayList<PresentLineItemPO> pPos = new ArrayList<PresentLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			pPos.add(new PresentLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3])));
		}
		PresentPO po = new PresentPO(str1[0], str1[1], str1[2], str1[3], pPos,
				Integer.parseInt(str1[4]), Integer.parseInt(str1[5]), 
				Boolean.parseBoolean(str1[6]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<PresentPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<PresentPO> lists = new ArrayList<PresentPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

}

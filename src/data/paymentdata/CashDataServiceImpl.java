/**
 * 现金费用单数据操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.paymentdata;

import data.dataioutility.DataIOUtility;
import dataservice.paymentdataservice.CashDataService;
import po.CashPO;
import po.ClauseLineItemPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CashDataServiceImpl extends UnicastRemoteObject implements CashDataService {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "cash";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public CashDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(CashPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(CashPO po) throws RemoteException {
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
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<CashPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<CashPO> findById(String id) throws RemoteException {
		print();
		ArrayList<CashPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CashPO> lists = new ArrayList<CashPO>();
		for(CashPO po: tLists) {
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
	public ArrayList<CashPO> findByTime(String time1, String time2)
			throws RemoteException {
		print();
		ArrayList<CashPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CashPO> lists = new ArrayList<CashPO>();
		for(CashPO po: tLists) {
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
	public ArrayList<CashPO> findByStatus(int status) throws RemoteException {
		print();
		ArrayList<CashPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CashPO> lists = new ArrayList<CashPO>();
		for(CashPO po: tLists) {
			if(po.getDocumentStatus() == status) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public CashPO getById(String id) throws RemoteException {
		print();
		ArrayList<CashPO> lists = this.stringToPoAll(d.readData());
		for(CashPO po: lists) {
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
	private String poToString(CashPO po) {
		String str = po.getId() + ";" + po.getTime() + ";" + po.getOperatorId() + ";" + 
				po.getBankAccount() + ";" + po.getTotal() + ";" + po.getDocumentStatus() + ";" + 
				po.isWriteOff() + ";" + po.isCanWriteOff() + ";" + po.getDocumentType() + ";";
		for(ClauseLineItemPO cPo: po.getClauseList()) {
			str += cPo.getName() + "," + cPo.getAccount() + "," + cPo.getRemark() + DataIOUtility.splitStr;
		}
		if(po.getClauseList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private CashPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[9].split(DataIOUtility.splitStr);
		ArrayList<ClauseLineItemPO> cPos = new ArrayList<ClauseLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			cPos.add(new ClauseLineItemPO(str3[0], Double.parseDouble(str3[1]), str3[2]));
		}
		CashPO po = new CashPO(str1[0], str1[1], str1[2], str1[3], cPos,
				Double.parseDouble(str1[4]), Integer.parseInt(str1[5]), 
				Boolean.parseBoolean(str1[6]), Boolean.parseBoolean(str1[7]),Integer.parseInt(str1[8]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<CashPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<CashPO> lists = new ArrayList<CashPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

	/**
	 * 输出执行的类名及对象
	 */
	private void print() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + ": executing " +
				Thread.currentThread().getStackTrace()[2].getMethodName());
	}

}

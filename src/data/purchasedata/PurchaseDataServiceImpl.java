/**
 * 进货数据操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.purchasedata;

import data.dataioutility.DataIOUtility;
import dataservice.purchasedataservice.PurchaseDataService;
import po.CommodityLineItemPO;
import po.PurchasePO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PurchaseDataServiceImpl extends UnicastRemoteObject implements PurchaseDataService {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "purchase";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public PurchaseDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(PurchasePO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(PurchasePO po) throws RemoteException {
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
	 * 根据日期时间段查找PO对象
	 */
	@Override
	public ArrayList<PurchasePO> findByTime(String time1, String time2)
			throws RemoteException {
		print();
		ArrayList<PurchasePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PurchasePO> lists = new ArrayList<PurchasePO>();
		for(PurchasePO po: tLists) {
			if(po.getTime().compareTo(time1) >= 0 && po.getTime().compareTo(time2) <= 0) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据客户ID查找PO对象
	 */
	@Override
	public ArrayList<PurchasePO> findByCustomer(String customer)
			throws RemoteException {
		print();
		ArrayList<PurchasePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PurchasePO> lists = new ArrayList<PurchasePO>();
		for(PurchasePO po: tLists) {
			if(po.getCustomerId().equals(customer)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据仓库查找PO对象
	 */
	@Override
	public ArrayList<PurchasePO> findByStorage(String storage)
			throws RemoteException {
		print();
		ArrayList<PurchasePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PurchasePO> lists = new ArrayList<PurchasePO>();
		for(PurchasePO po: tLists) {
			if(po.getStorage().equals(storage)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<PurchasePO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据单据状态查找PO对象
	 */
	@Override
	public ArrayList<PurchasePO> findByStatus(int status)
			throws RemoteException {
		print();
		ArrayList<PurchasePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PurchasePO> lists = new ArrayList<PurchasePO>();
		for(PurchasePO po: tLists) {
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
	public PurchasePO getById(String id) throws RemoteException {
		print();
		ArrayList<PurchasePO> lists = this.stringToPoAll(d.readData());
		for(PurchasePO po: lists) {
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
	private String poToString(PurchasePO po) {
		String str = po.getId() + ";" + po.getPurId() + ";"+ po.getTime() + ";" + po.getCustomerId() + ";" + 
				po.getCustomerName() + ";" + po.getOperatorId() + ";" + po.getStorage() + ";" + 
				po.getTotal() + ";" +  po.getRemark() + ";" + po.getDocumentStatus() + ";" + 
				po.isWriteOff() + ";" + po.isCanReturn() + ";" +
				po.isCanWriteOff() + ";" + po.getDocumentType() + ";";
		for(CommodityLineItemPO cPo: po.getSaleList()) {
			str += cPo.getId() + "," + cPo.getName() + "," + cPo.getModel() + "," + 
					cPo.getNumber() + "," + cPo.getPrice() + "," + cPo.getTotal()
					+ "," + cPo.getRemark() + DataIOUtility.splitStr;
		}
		if(po.getSaleList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private PurchasePO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[14].split(DataIOUtility.splitStr);
		ArrayList<CommodityLineItemPO> tPos = new ArrayList<CommodityLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			tPos.add(new CommodityLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3]), Double.parseDouble(str3[4]),
					Double.parseDouble(str3[5]), str3[6]));
		}
		PurchasePO po = new PurchasePO(str1[0], str1[1], str1[2], str1[3], str1[4], str1[5], str1[6], tPos,
				Double.parseDouble(str1[7]), str1[8], Integer.parseInt(str1[9]), 
				Boolean.parseBoolean(str1[10]), Boolean.parseBoolean(str1[11]),
				Boolean.parseBoolean(str1[12]),Integer.parseInt(str1[13]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<PurchasePO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<PurchasePO> lists = new ArrayList<PurchasePO>();
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

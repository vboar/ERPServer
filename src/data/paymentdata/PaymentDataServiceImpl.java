/**
 * 收付款单数据操作的实现
 * @author vboar
 * @date 2014/11/25
 */
package data.paymentdata;

import data.dataioutility.DataIOUtility;
import dataservice.paymentdataservice.PaymentDataService;
import po.PaymentPO;
import po.TransferLineItemPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PaymentDataServiceImpl extends UnicastRemoteObject implements PaymentDataService {

	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "payment";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public PaymentDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(PaymentPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(PaymentPO po) throws RemoteException {
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
	public ArrayList<PaymentPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<PaymentPO> findById(String id) throws RemoteException {
		print();
		ArrayList<PaymentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PaymentPO> lists = new ArrayList<PaymentPO>();
		for(PaymentPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据日期时间段模糊查找PO对象
	 */
	@Override
	public ArrayList<PaymentPO> findByTime(String time1, String time2)
			throws RemoteException {
		print();
		ArrayList<PaymentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PaymentPO> lists = new ArrayList<PaymentPO>();
		for(PaymentPO po: tLists) {
			if(po.getTime().compareTo(time1) >= 0 && po.getTime().compareTo(time2) <= 0) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据客户ID模糊查找PO对象
	 */
	@Override
	public ArrayList<PaymentPO> findByCustomer(String customerId)
			throws RemoteException {
		print();
		ArrayList<PaymentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PaymentPO> lists = new ArrayList<PaymentPO>();
		for(PaymentPO po: tLists) {
			if(po.getCustomerId().equals(customerId)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据操作员ID模糊查找PO对象
	 */
	@Override
	public ArrayList<PaymentPO> findByOperator(String operator)
			throws RemoteException {
		print();
		ArrayList<PaymentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PaymentPO> lists = new ArrayList<PaymentPO>();
		for(PaymentPO po: tLists) {
			if(po.getOperatorId().equals(operator)) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据单据状态查找PO对象
	 */
	@Override
	public ArrayList<PaymentPO> findByStatus(int status) throws RemoteException {
		print();
		ArrayList<PaymentPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<PaymentPO> lists = new ArrayList<PaymentPO>();
		for(PaymentPO po: tLists) {
			if(po.getApprovalStatus() == status) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public PaymentPO getById(String id) throws RemoteException {
		print();
		ArrayList<PaymentPO> lists = this.stringToPoAll(d.readData());
		for(PaymentPO po: lists) {
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
	private String poToString(PaymentPO po) {
		String str = po.getId() + ";" + po.getTime() + ";" + po.getCustomerId() + ";" + 
				po.getCustomerName() + ";" + po.getOperatorId() + ";" + po.getTotal() + ";" + 
				po.getApprovalStatus() + ";" + po.isWriteOff() + ";" 
				+ po.isCanWriteOff() + ";" +po.getDocumentType() + ";";
		for(TransferLineItemPO tPo: po.getTransferList()) {
			str += tPo.getName() + "," + tPo.getBankAccount() + "," + tPo.getAccount() + "," + tPo.getRemark()
					+ DataIOUtility.splitStr;
		}
		if(po.getTransferList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private PaymentPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[10].split(DataIOUtility.splitStr);
		ArrayList<TransferLineItemPO> tPos = new ArrayList<TransferLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			tPos.add(new TransferLineItemPO(str3[0], str3[1], Double.parseDouble(str3[2]), str3[3]));
		}
		PaymentPO po = new PaymentPO(str1[0], str1[1], str1[2], str1[3], str1[4], tPos,
				Double.parseDouble(str1[5]), Integer.parseInt(str1[6]), 
				Boolean.parseBoolean(str1[7]),Boolean.parseBoolean(str1[8]), Integer.parseInt(str1[9]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<PaymentPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<PaymentPO> lists = new ArrayList<PaymentPO>();
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

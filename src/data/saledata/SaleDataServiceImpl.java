/**
 * 销售数据操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.saledata;

import data.dataioutility.DataIOUtility;
import dataservice.saledataservice.SaleDataService;
import po.CommodityLineItemPO;
import po.SalePO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SaleDataServiceImpl extends UnicastRemoteObject implements SaleDataService {

	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "sale";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造昂法
	 * @throws RemoteException
	 */
	public SaleDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(SalePO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(SalePO po) throws RemoteException {
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
	public ArrayList<SalePO> findByTime(String time1, String time2)
			throws RemoteException {
		print();
		ArrayList<SalePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
		for(SalePO po: tLists) {
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
	public ArrayList<SalePO> findByCustomer(String customer)
			throws RemoteException {
		print();
		ArrayList<SalePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
		for(SalePO po: tLists) {
			if(po.getCustomerId().equals(customer)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据业务员查找PO对象
	 */
	@Override
	public ArrayList<SalePO> findBySalesman(String salesman)
			throws RemoteException {
		print();
		ArrayList<SalePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
		for(SalePO po: tLists) {
			if(po.getSalesmanId().equals(salesman)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据仓库查找PO对象
	 */
	@Override
	public ArrayList<SalePO> findByStorage(String storage)
			throws RemoteException {
		print();
		ArrayList<SalePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
		for(SalePO po: tLists) {
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
	public ArrayList<SalePO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据单据状态查找PO对象
	 */
	@Override
	public ArrayList<SalePO> findByStatus(int status) throws RemoteException {
		print();
		ArrayList<SalePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
		for(SalePO po: tLists) {
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
	public SalePO getById(String id) throws RemoteException {
		print();
		ArrayList<SalePO> lists = this.stringToPoAll(d.readData());
		for(SalePO po: lists) {
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
	private String poToString(SalePO po) {
		String str = po.getId() + ";" + po.getSaleId() + ";" + po.getTime() + ";" + po.getCustomerId() + ";" + 
				po.getCustomerName() + ";" + po.getCustomerVIP() + ";" + po.getSalesmanId()
				 + ";" + po.getOperatorId()+ ";" + po.getStorage() + ";" + po.getTotalBeforeDiscount()
				 + ";" + po.getDiscount() + ";" + po.getVoucher() + ";" + po.getTotalAfterDiscount() 
				 + ";" + po.getRemark() + ";" + po.getDocumentStatus() + ";" + po.isWriteOff()
				 + ";" + po.isCanReturn() + ";" + po.isCanWriteOff()
				+ ";" + po.getDocumentType() + ";" + po.getPresentId() + ";";
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
	private SalePO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[20].split(DataIOUtility.splitStr);
		ArrayList<CommodityLineItemPO> tPos = new ArrayList<CommodityLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			tPos.add(new CommodityLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3]), Double.parseDouble(str3[4]),
					Double.parseDouble(str3[5]), str3[6]));
		}
		String presentId = str1[19];
		SalePO po = new SalePO(str1[0], str1[1], str1[2], str1[3], str1[4],Integer.parseInt(str1[5]), str1[6], 
				str1[7], str1[8], tPos, presentId, Double.parseDouble(str1[9]), Double.parseDouble(str1[10]),
				Double.parseDouble(str1[11]), Double.parseDouble(str1[12]), str1[13], 
				Integer.parseInt(str1[14]), Boolean.parseBoolean(str1[15]),Boolean.parseBoolean(str1[16]),
				Boolean.parseBoolean(str1[17]),Integer.parseInt(str1[18]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<SalePO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
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

/**
 * 销售数据操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.saledata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CommodityLineItemPO;
import po.PresentLineItemPO;
import po.SalePO;
import data.dataioutility.DataIOUtility;
import dataservice.saledataservice.SaleDataService;

public class SaleDataServiceImpl extends UnicastRemoteObject implements SaleDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "sale";
	
	private DataIOUtility d = null;

	public SaleDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(SalePO po) throws RemoteException {
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(SalePO po) throws RemoteException {
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
		ArrayList<SalePO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SalePO> lists = new ArrayList<SalePO>();
		for(SalePO po: tLists) {
			if(po.getSalesman().equals(salesman)) {
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
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据单据状态查找PO对象
	 */
	@Override
	public ArrayList<SalePO> findByStatus(int status) throws RemoteException {
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
		String str = po.getId() + ";" + po.getTime() + ";" + po.getCustomerId() + ";" + 
				po.getCustomerName() + ";" + po.getCustomerVIP() + ";" + po.getSalesman()
				 + ";" + po.getOperatorId()+ ";" + po.getStorage() + ";" + po.getTotalBeforeDiscount()
				 + ";" + po.getDiscount() + ";" + po.getVoucher() + ";" + po.getTotalAfterDiscount() 
				 + ";" + po.getRemark() + ";" + po.isWriteOff() + ";" + po.getDocumentType() + ";";
		for(CommodityLineItemPO cPo: po.getSaleList()) {
			str += cPo.getId() + "," + cPo.getName() + "," + cPo.getModel() + "," + 
					cPo.getNumber() + "," + cPo.getPrice() + "," + cPo.getRemark()
					 + "," + cPo.getRemark()+ "|";
		}
		if(po.getSaleList().size() != 0) str += ";";
		for(PresentLineItemPO pPo: po.getGiftList()) {
			str += pPo.getId() + "," + pPo.getName() + "," + pPo.getModel() + "," + 
					pPo.getNumber() + "|";
		}
		if(po.getGiftList().size() != 0) str +=";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private SalePO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[16].split("|");
		String[] str4 = str1[17].split("|");
		ArrayList<CommodityLineItemPO> tPos = new ArrayList<CommodityLineItemPO>();
		ArrayList<PresentLineItemPO> pPos = new ArrayList<PresentLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			tPos.add(new CommodityLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3]), Double.parseDouble(str3[4]),
					Double.parseDouble(str3[5]), str3[6]));
		}
		for(int i = 0; i < str4.length; i++) {
			String[] str3 = str4[i].split(",");
			pPos.add(new PresentLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3])));
		}
		SalePO po = new SalePO(str1[0], str1[1], str1[2], str1[3], Integer.parseInt(str1[4]), str1[5], 
				str1[6], str1[7], tPos, pPos, Double.parseDouble(str1[8]), Double.parseDouble(str1[9]),
				Double.parseDouble(str1[10]), Double.parseDouble(str1[11]), str1[12], 
				Integer.parseInt(str1[13]), Boolean.parseBoolean(str1[14]), Integer.parseInt(str1[15]));
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

}

/**
 * 客户等级促销策略数据操作实现
 * @author Vboar
 * @date 2014/11/27
 */

package data.promotiondata;

import data.dataioutility.DataIOUtility;
import dataservice.promotiondataservice.CustomerGiftDataService;
import po.CustomerGiftPO;
import po.PresentLineItemPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CustomerGiftDataserviceImpl extends UnicastRemoteObject implements CustomerGiftDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "customergift";
	
	private DataIOUtility d = null;

	public CustomerGiftDataserviceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(CustomerGiftPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(CustomerGiftPO po) throws RemoteException {
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
	public ArrayList<CustomerGiftPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据生效状态查找PO对象
	 */
	@Override
	public ArrayList<CustomerGiftPO> findByValid(boolean valid)
			throws RemoteException {
		print();
		ArrayList<CustomerGiftPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CustomerGiftPO> lists = new ArrayList<CustomerGiftPO>();
		for(CustomerGiftPO po: tLists) {
			if(po.isValid() == valid) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public CustomerGiftPO getById(String id) throws RemoteException {
		print();
		ArrayList<CustomerGiftPO> lists = this.stringToPoAll(d.readData());
		for(CustomerGiftPO po: lists) {
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
	private String poToString(CustomerGiftPO po) {
		String str = po.getId() + ";" + po.getVIP() + ";" + po.getDiscount() + ";" + 
				po.getVoucher() + ";" + po.getStartTime() + ";" + po.getEndTime() + ";" + 
				po.isValid() + ";";
		for(PresentLineItemPO pPo: po.getGiftInfo()) {
			str += pPo.getId() + "," + pPo.getName() + "," + pPo.getModel() + "," + 
					pPo.getNumber() + DataIOUtility.splitStr;
		}
		if(po.getGiftInfo().size() == 0) str += DataIOUtility.splitStr;
		str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private CustomerGiftPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[7].split(DataIOUtility.splitStr);
		ArrayList<PresentLineItemPO> pPos = new ArrayList<PresentLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			pPos.add(new PresentLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3])));
		}
		CustomerGiftPO po = new CustomerGiftPO(str1[0], Integer.parseInt(str1[1]), pPos,
				Double.parseDouble(str1[2]), Double.parseDouble(str1[3]), str1[4], str1[5],
				Boolean.parseBoolean(str1[6]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<CustomerGiftPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<CustomerGiftPO> lists = new ArrayList<CustomerGiftPO>();
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

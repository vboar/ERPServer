/**
 * 商品管理数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.commoditydata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CommodityPO;
import data.dataioutility.DataIOUtility;
import dataservice.commoditydataservice.CommodityDataService;

public class CommodityDataServiceImpl extends UnicastRemoteObject implements CommodityDataService {
	
	private static final long serialVersionUID = 1L;

	private String path = "commodity";
	
	private DataIOUtility d = null;
	
	public CommodityDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(CommodityPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(CommodityPO po) throws RemoteException {
		print();
		ArrayList<String[]> lists = d.stringToArrayAll(d.readData());
		for(String[] s: lists) {
			if(s[0].equals(po.getId())) {
				lists.remove(s);
				break;
			}
		}
		d.writeData(d.arrayToStringAll(lists));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(CommodityPO po) throws RemoteException {
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
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<CommodityPO> findById(String id) throws RemoteException {
		print();
		ArrayList<CommodityPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CommodityPO> lists = new ArrayList<CommodityPO>();
		for(CommodityPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据名称模糊查找PO对象
	 */
	@Override
	public ArrayList<CommodityPO> findByName(String name) throws RemoteException {
		print();
		ArrayList<CommodityPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CommodityPO> lists = new ArrayList<CommodityPO>();
		for(CommodityPO po: tLists) {
			if(po.getName().contains(name)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据型号模糊查找PO对象
	 */
	@Override
	public ArrayList<CommodityPO> findByModel(String model) throws RemoteException {
		print();
		ArrayList<CommodityPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CommodityPO> lists = new ArrayList<CommodityPO>();
		for(CommodityPO po: tLists) {
			if(po.getModel().contains(model)) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public CommodityPO getById(String id) throws RemoteException {
		print();
		ArrayList<CommodityPO> lists = this.stringToPoAll(d.readData());
		for(CommodityPO po: lists) {
			if(id.equals(po.getId())) {
				return po;
			}
		}
		return null;
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<CommodityPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(CommodityPO po) {
		return po.getId() + ";" + po.getName() + ";" + po.getModel() + ";" + po.getNumber()
				+ ";" + po.getPurchasePrice() + ";" + po.getSalePrice() + ";" + po.getRecentPurchasePrice()
				+ ";" + po.getRecentSalePrice() + ";" + po.getWarningNumber()
				+ ";" + po.isTrade() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private CommodityPO stringToPo(String s) {
		String[] strs = s.split(";");
		int number = Integer.parseInt(strs[3]);
		double purchasePrice = Double.parseDouble(strs[4]);
		double salePrice = Double.parseDouble(strs[5]);
		double recentPurchasePrice = Double.parseDouble(strs[6]);
		double recentSalePrice = Double.parseDouble(strs[7]);
		int warningNumber = Integer.parseInt(strs[8]);
		boolean isTrade = Boolean.parseBoolean(strs[9]);
		return new CommodityPO(strs[0], strs[1], strs[2], number, purchasePrice,
				salePrice, recentPurchasePrice, recentSalePrice, warningNumber, isTrade);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<CommodityPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<CommodityPO> lists = new ArrayList<CommodityPO>();
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

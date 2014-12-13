/**
 * 库存盘点数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.stockdata;

import data.dataioutility.DataIOUtility;
import dataservice.stockdataservice.StockDataService;
import po.StockPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class StockDataServiceImpl extends UnicastRemoteObject implements StockDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "stock";
	
	private DataIOUtility d = null;

	public StockDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(StockPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 根据批次和批号查找PO对象
	 */
	@Override
	public ArrayList<StockPO> findByDate(String batch, String batchNumber)
			throws RemoteException {
		print();
		ArrayList<StockPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<StockPO> lists = new ArrayList<StockPO>();
		for(StockPO po: tLists) {
			if(po.getBatch().equals(batch) && po.getBatchNumber().equals(batchNumber)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<StockPO> show() throws RemoteException {
		print();
		return stringToPoAll(d.readData());
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(StockPO po) {
		return po.getBatch() + ";" + po.getBatchNumber() + ";" + po.getCommodityId()
				+ ";" + po.getCommodityName() + ";" + po.getCommodityModel() + ";" + 
				po.getNumber() + ";" + po.getAvgPrice() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private StockPO stringToPo(String s) {
		String[] strs = s.split(";");
		int number = Integer.parseInt(strs[5]);
		double avgPrive = Double.parseDouble(strs[6]);
		return new StockPO(strs[2], strs[3], strs[4], number, avgPrive, strs[0], strs[1]);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<StockPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<StockPO> lists = new ArrayList<StockPO>();
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

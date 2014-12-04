/**
 * 总价促销策略数据操作实现
 * @author Vboar
 * @date 2014/11/27
 */

package data.promotiondata;

import data.dataioutility.DataIOUtility;
import dataservice.promotiondataservice.TotalGiftDataService;
import po.PresentLineItemPO;
import po.TotalGiftPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class TotalGiftDataServiceImpl extends UnicastRemoteObject implements TotalGiftDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "totalgift";
	
	private DataIOUtility d = null;

	public TotalGiftDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(TotalGiftPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(TotalGiftPO po) throws RemoteException {
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
	public ArrayList<TotalGiftPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据生效状态查找PO对象
	 */
	@Override
	public ArrayList<TotalGiftPO> findByValid(boolean valid) throws RemoteException {
		print();
		ArrayList<TotalGiftPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<TotalGiftPO> lists = new ArrayList<TotalGiftPO>();
		for(TotalGiftPO po: tLists) {
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
	public TotalGiftPO getById(String id) throws RemoteException {
		print();
		ArrayList<TotalGiftPO> lists = this.stringToPoAll(d.readData());
		for(TotalGiftPO po: lists) {
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
	private String poToString(TotalGiftPO po) {
		String str = po.getId() + ";" + po.getTotal() + ";" + po.getVoucher() + ";" + 
				po.getStartTime() + ";" + po.getEndTime() + ";" + po.isValid() + ";";
		for(PresentLineItemPO pPo: po.getGiftInfo()) {
			str += pPo.getId() + "," + pPo.getName() + "," + pPo.getModel() + "," + 
					pPo.getNumber() + DataIOUtility.splitStr;
		}
		if(po.getGiftInfo().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private TotalGiftPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[6].split(DataIOUtility.splitStr);
		ArrayList<PresentLineItemPO> pPos = new ArrayList<PresentLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			pPos.add(new PresentLineItemPO(str3[0], str3[1], str3[2], 
					Integer.parseInt(str3[3])));
		}
		TotalGiftPO po = new TotalGiftPO(str1[0], Double.parseDouble(str1[1]), pPos,
				Double.parseDouble(str1[2]), str1[3], str1[4], Boolean.parseBoolean(str1[5]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<TotalGiftPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<TotalGiftPO> lists = new ArrayList<TotalGiftPO>();
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

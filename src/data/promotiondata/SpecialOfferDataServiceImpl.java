/**
 * 特价包促销策略数据操作实现
 * @author Vboar
 * @date 2014/11/27
 */

package data.promotiondata;

import data.dataioutility.DataIOUtility;
import dataservice.promotiondataservice.SpecialOfferDataService;
import po.CommodityLineItemPO;
import po.SpecialOfferPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SpecialOfferDataServiceImpl extends UnicastRemoteObject implements SpecialOfferDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "specialoffer";
	
	private DataIOUtility d = null;

	public SpecialOfferDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(SpecialOfferPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(SpecialOfferPO po) throws RemoteException {
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
	public ArrayList<SpecialOfferPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 根据生效状态查找PO对象
	 */
	@Override
	public ArrayList<SpecialOfferPO> findByValid(boolean valid) throws RemoteException {
		print();
		ArrayList<SpecialOfferPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<SpecialOfferPO> lists = new ArrayList<SpecialOfferPO>();
		for(SpecialOfferPO po: tLists) {
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
	public SpecialOfferPO getById(String id) throws RemoteException {
		print();
		ArrayList<SpecialOfferPO> lists = this.stringToPoAll(d.readData());
		for(SpecialOfferPO po: lists) {
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
	private String poToString(SpecialOfferPO po) {
		String str = po.getId() + ";" + po.getTotal() + ";" + po.getStartTime() + ";" + 
				po.getEndTime() + ";" + po.isValid()  + ";";
		for(CommodityLineItemPO pPo: po.getCommodityList()) {
			str += pPo.getId() + "," + pPo.getName() + "," + pPo.getModel() + "," + 
					pPo.getNumber() + "," + pPo.getPrice() + "," + pPo.getTotal() + "," + 
					pPo.getRemark()+ DataIOUtility.splitStr;
		}
		if(po.getCommodityList().size() != 0) str += ";";
		return str;
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private SpecialOfferPO stringToPo(String s) {
		String[] str1 = s.split(";");
		String[] str2 = str1[5].split(DataIOUtility.splitStr);
		ArrayList<CommodityLineItemPO> cPos = new ArrayList<CommodityLineItemPO>();
		for(int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			cPos.add(new CommodityLineItemPO(str3[0], str3[1], str3[2], Integer.parseInt(str3[3]),
					Double.parseDouble(str3[4]), Double.parseDouble(str3[5]), str3[6]));
		}
		SpecialOfferPO po = new SpecialOfferPO(str1[0], cPos, Double.parseDouble(str1[1]), str1[2], 
				str1[3], Boolean.parseBoolean(str1[4]));
		return po;
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<SpecialOfferPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<SpecialOfferPO> lists = new ArrayList<SpecialOfferPO>();
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

/**
 * 商品分类数据层操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.commoditydata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.AccountPO;
import po.CategoryPO;
import data.dataioutility.DataIOUtility;
import dataservice.commoditydataservice.CategoryDataService;

public class CategoryDataServiceImpl extends UnicastRemoteObject implements CategoryDataService {

	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "category";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public CategoryDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}
	
	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(CategoryPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(CategoryPO po) throws RemoteException {
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
	public void update(CategoryPO po) throws RemoteException {
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
	public ArrayList<CategoryPO> findById(String id) throws RemoteException {
		print();
		ArrayList<CategoryPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CategoryPO> lists = new ArrayList<CategoryPO>();
		for(CategoryPO po: tLists) {
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
	public ArrayList<CategoryPO> findByName(String name) throws RemoteException {
		print();
		ArrayList<CategoryPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<CategoryPO> lists = new ArrayList<CategoryPO>();
		for(CategoryPO po: tLists) {
			if(po.getName().contains(name)) {
				lists.add(po);
			}
		}
		return lists;
	}
	
	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public CategoryPO getById(String id) throws RemoteException {
		print();
		ArrayList<CategoryPO> lists = this.stringToPoAll(d.readData());
		for(CategoryPO po: lists) {
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
	public ArrayList<CategoryPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}

	/**
	 * 期初建账根据账id返回所有PO对象
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<CategoryPO> showByInitial(String id) throws RemoteException {
		d.fatherPath = "data/" + id + "/";
		print();
		ArrayList<String> strs = d.readData();
		ArrayList<CategoryPO> lists = new ArrayList<CategoryPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(CategoryPO po) {
		return po.getId() + ";" + po.getName() + ";" + po.getNumber() +  ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private CategoryPO stringToPo(String s) {
		String[] strs = s.split(";");
		int number = Integer.parseInt(strs[2]);
		return new CategoryPO(strs[0], strs[1], number);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<CategoryPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<CategoryPO> lists = new ArrayList<CategoryPO>();
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

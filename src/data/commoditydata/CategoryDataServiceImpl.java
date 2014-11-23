/**
 * 商品分类数据层操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.commoditydata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import data.dataioutility.DataIOUtility;
import dataservice.commoditydataservice.CategoryDataService;
import po.CategoryPO;
import po.UserPO;

public class CategoryDataServiceImpl extends UnicastRemoteObject implements CategoryDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "category";
	
	private DataIOUtility d = null;

	public CategoryDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}
	
	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(CategoryPO po) throws RemoteException {
	}

	/**
	 * 删除一个PO对象
	 */
	@Override
	public void delete(CategoryPO po) throws RemoteException {
	}

	/**
	 * 更新一个PO对象
	 */
	@Override
	public void update(CategoryPO po) throws RemoteException {
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<CategoryPO> findById(String id) throws RemoteException {
		return null;
	}

	/**
	 * 根据名称模糊查找PO对象
	 */
	@Override
	public ArrayList<CategoryPO> findByName(String name) throws RemoteException {
		return null;
	}
	
	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public CategoryPO getById(String id) throws RemoteException {
		return null;
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<CategoryPO> show() throws RemoteException {
		return null;
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

}

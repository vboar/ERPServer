/**
 * 日志数据层操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.logdata;

import data.dataioutility.DataIOUtility;
import dataservice.logdataservice.LogDataService;
import po.LogPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LogDataServiceImpl extends UnicastRemoteObject implements LogDataService {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "log";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public LogDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(LogPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
	}

	/**
	 * 根据日期时间段来查找PO对象
	 */
	@Override
	public ArrayList<LogPO> findByTime(String time1, String time2)
			throws RemoteException {
		print();
		ArrayList<LogPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<LogPO> lists = new ArrayList<LogPO>();
		for(LogPO po: tLists) {
			if(po.getTime().compareTo(time1) >= 0 && po.getTime().compareTo(time2) <= 0) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<LogPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(LogPO po) {
		return po.getId() + ";" + po.getTime() + ";" + po.getOperatorId()
				+ ";" + po.getContent() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private LogPO stringToPo(String s) {
		String[] strs = s.split(";");
		return new LogPO(strs[0], strs[1], strs[2], strs[3]);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<LogPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<LogPO> lists = new ArrayList<LogPO>();
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

/**
 * 报溢报损单数据层操作实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.exceptiondata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import data.dataioutility.DataIOUtility;
import dataservice.exceptiondataservice.ExceptionDataService;
import po.ExceptionPO;
import util.DocumentStatus;

public class ExceptionDataServiceImpl extends UnicastRemoteObject implements ExceptionDataService {

	private static final long serialVersionUID = 1L;
	
	private String path = "exception";
	
	private DataIOUtility d = null;

	public ExceptionDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}

	@Override
	public void insert(ExceptionPO po) throws RemoteException {
	}

	@Override
	public void update(ExceptionPO po) throws RemoteException {
	}

	@Override
	public ArrayList<ExceptionPO> show(String time1, String time2)
			throws RemoteException {
		return null;
	}

	@Override
	public ArrayList<ExceptionPO> findById(String id) throws RemoteException {
		return null;
	}

	@Override
	public ArrayList<ExceptionPO> findByStatus(DocumentStatus status)
			throws RemoteException {
		return null;
	}

	@Override
	public ExceptionPO getById(String id) throws RemoteException {
		return null;
	}

}

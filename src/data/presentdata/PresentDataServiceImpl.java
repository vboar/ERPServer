package data.presentdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.presentdataservice.PresentDataService;
import po.PresentPO;

public class PresentDataServiceImpl extends UnicastRemoteObject implements PresentDataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PresentDataServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public void insert(PresentPO po) throws RemoteException {
	}

	@Override
	public void update(PresentPO po) throws RemoteException {
	}

	@Override
	public ArrayList<PresentPO> findById(String id) {
		return null;
	}

	@Override
	public ArrayList<PresentPO> findByTime(String time1, String time2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentPO> findByStatus(int status) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentPO> findByCustomerId(String customerId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PresentPO getById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}

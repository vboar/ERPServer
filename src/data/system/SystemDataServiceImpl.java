/**
 * 系统数据实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.system;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dataservice.systemdateservice.SystemDataService;

public class SystemDataServiceImpl extends UnicastRemoteObject implements SystemDataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemDataServiceImpl() throws RemoteException {
		super();
	}

}

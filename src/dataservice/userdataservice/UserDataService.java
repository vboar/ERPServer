/**
 * 用户数据层操作的接口
 * @author chengcheng
 * @date 2014/10/25
 */
package dataservice.userdataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.UserPO;

public interface UserDataService extends Remote {
	
	public void insert(UserPO po) throws RemoteException;
	
	public void delete(UserPO po) throws RemoteException;
	
	public void update(UserPO po) throws RemoteException;
	
	public ArrayList<UserPO> findById(String id) throws RemoteException;
	
	public ArrayList<UserPO> findByName(String name) throws RemoteException;
	
	public ArrayList<UserPO> findByType(int type) throws RemoteException;
	
	public UserPO getById(String id) throws RemoteException;
	
	public ArrayList<UserPO> show() throws RemoteException;

}

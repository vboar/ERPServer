/**
 * 客户等级促销策略数据操作接口
 * @author Vboar
 * @date 2014/11/15
 */

package dataservice.promotiondataservice;

import po.CustomerGiftPO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CustomerGiftDataService extends Remote {

    public void insert(CustomerGiftPO po) throws RemoteException;

    public void update(CustomerGiftPO po) throws RemoteException;

    public ArrayList<CustomerGiftPO> show() throws RemoteException;

    public ArrayList<CustomerGiftPO> findByValid(boolean valid) throws RemoteException;

    public CustomerGiftPO getById(String id) throws RemoteException;

}

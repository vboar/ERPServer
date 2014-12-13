package test;

import data.accountdata.AccountDataServiceImpl;
import data.dataioutility.DataIOUtility;
import dataservice.accountdataservice.AccountDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.AccountPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * AccountDataService的JUnit测试用例
 * Created by Vboar on 2014/12/11.
 */
public class TestAccount extends TestCase {

    private AccountDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("account").clearData("account");
        impl = new AccountDataServiceImpl();
        // 添加第一个账户
        String name = "中国银行";
        String account = "622206328888";
        double balance = 10000;
        AccountPO po = new AccountPO(name, account, balance);
        impl.insert(po);
        // 添加第二个账户
        name = "工商银行";
        account = "622214526666";
        balance = 9000.5;
        po = new AccountPO(name, account, balance);
        impl.insert(po);
        // 添加第三个账户
        name = "农业银行";
        account = "622214527777";
        balance = 500;
        po = new AccountPO(name, account, balance);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        // 添加一个账户
        String name = "建设银行";
        String account = "622206327788";
        double balance = 10500;
        AccountPO po = new AccountPO(name, account, balance);
        impl.insert(po);
        AccountPO temp = impl.show().get(3);
        assertEquals(name, temp.getName());
        assertEquals(account, temp.getAccount());
        assertEquals(balance, temp.getBalance());
        init();
    }

    @Test
    public void testDelete() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        // 删除一个账户(不存在)
        String name = "中国银行";
        String account = "622206328668";
        double balance = 10000;
        AccountPO po = new AccountPO(name, account, balance);
        AccountPO temp1 = impl.show().get(0);
        impl.delete(po);
        AccountPO temp = impl.show().get(0);
        assertEquals(temp1.getName(), temp.getName());
        assertEquals(temp1.getAccount(), temp.getAccount());
        assertEquals(temp1.getBalance(), temp.getBalance());
        // 删除第一个账户
        name = "中国银行";
        account = "622206328888";
        balance = 10000;
        po = new AccountPO(name, account, balance);
        temp = impl.show().get(1);
        impl.delete(po);
        temp1 = impl.show().get(0);
        assertEquals(temp.getAccount(), temp1.getAccount());
        // 删除第二个账户
        name = "工商银行";
        account = "622214526666";
        balance = 9000.5;
        po = new AccountPO(name, account, balance);
        temp = impl.show().get(1);
        impl.delete(po);
        temp1 = impl.show().get(0);
        assertEquals(temp.getAccount(), temp1.getAccount());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        // 更新第一个账户
        String name = "中国银行1";
        String account = impl.show().get(0).getAccount();
        double balance = 50;
        AccountPO po = new AccountPO(name, account, balance);
        impl.update(po);
        AccountPO temp = impl.show().get(0);
        assertEquals(name, temp.getName());
        assertEquals(balance, temp.getBalance());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        assertEquals(3, impl.show().size());
        init();
    }

    public void testShowByInitial() throws RemoteException {
        // TODO
    }

    @Test
    public void testFindByName() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        // "银行" - 3
        ArrayList<AccountPO> list = impl.findByName("银行");
        assertEquals(3, list.size());
        assertEquals("622214527777", list.get(2).getAccount());
        assertEquals("工商银行", list.get(1).getName());
        // "工商银行" - 1
        list = impl.findByName("工商银行");
        assertEquals("622214526666", list.get(0).getAccount());
        // "渣打银行" - 0
        list = impl.findByName("渣打银行");
        assertEquals(0, list.size());
        init();
    }

    @Test
    public void testfindByAccount() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        // "1452" -2
        ArrayList<AccountPO> list = impl.findByAccount("1452");
        assertEquals(2, list.size());
        assertEquals("农业银行", list.get(1).getName());
        // "14525" - 0
        list = impl.findByAccount("14525");
        assertEquals(0, list.size());
        init();
    }

    public void testGetByAccount() throws RemoteException {
        init();
        impl = new AccountDataServiceImpl();
        // "622206328888"
        AccountPO po = impl.getByAccount("622206328888");
        assertEquals("中国银行", po.getName());
        assertEquals("622206328888", po.getAccount());
        assertEquals(10000.0, po.getBalance());
        // "6222145277778"
        assertNull(impl.getByAccount("6222145277778"));
        init();
    }

}

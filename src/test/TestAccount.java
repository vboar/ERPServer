package test;

import data.accountdata.AccountDataServiceImpl;
import data.dataioutility.DataIOUtility;
import dataservice.accountdataservice.AccountDataService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import po.AccountPO;

import java.rmi.RemoteException;

/**
 * AccountDataService的JUnit测试用例
 * Created by Vboar on 2014/12/11.
 */
public class TestAccount extends TestCase {

    private AccountDataService impl;

    @Before
    public void testClear() throws RemoteException {
        new DataIOUtility("account").clearData("account");
    }

    @Test
    public void testInsert() throws RemoteException  {
        impl = new AccountDataServiceImpl();
        // 添加第一个账户
        String name = "中国银行";
        String account = "622206328888";
        double balance = 10000;
        AccountPO po = new AccountPO(name, account, balance);
        impl.insert(po);
        AccountPO temp = impl.show().get(0);
        assertEquals(name, temp.getName());
        assertEquals(account, temp.getAccount());
        assertEquals(balance, temp.getBalance());
        // 添加第二个账户
        name = "工商银行";
        account = "622214526666";
        balance = 9000.5;
        po = new AccountPO(name, account, balance);
        impl.insert(po);
        temp = impl.show().get(1);
        assertEquals(name, temp.getName());
        assertEquals(account, temp.getAccount());
        assertEquals(balance, temp.getBalance());
    }

}

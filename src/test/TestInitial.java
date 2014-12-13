package test;

import dataservice.initialdataservice.InitialDataService;
import junit.framework.TestCase;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * InitialDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestInitial extends TestCase {

    private InitialDataService impl;

    public void init() throws RemoteException {
//        new DataIOUtility("initial").clearData("initial");
//        impl = new InitialDataServiceImpl();
//        // 添加三个期初信息
//        String id = "2012-INI";
//        String name = "2012年期初账";
//        InitialPO po = new InitialPO(id, name);
//        impl.insert(po);
//
//        id = "2012-END";
//        name = "2012年期末账";
//        po = new InitialPO(id, name);
//        impl.insert(po);
//
//        id = "2013-INI";
//        name = "2012年期初账";
//        po = new InitialPO(id, name);
//        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
    }

    @Test
    public void testSaveEnd() throws RemoteException {
        // TODO
    }

    @Test
    public void testFindById() throws RemoteException {
        init();
    }

    @Test
    public void testGetById() throws RemoteException {
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
//        ArrayList<InitialPO> list = impl.show();
//        assertEquals(3, list.size());
//        assertEquals("2013-INI", list.get(2).getId());
    }


}

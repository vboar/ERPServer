package test;

import data.dataioutility.DataIOUtility;
import data.logdata.LogDataServiceImpl;
import dataservice.logdataservice.LogDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.LogPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * LogDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestLog extends TestCase {

    private LogDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("log").clearData("log");
        impl = new LogDataServiceImpl();
        // 添加三条日志
        String id = "Log-2014/12/13-00001";
        String time = "2014/12/13 01:12:12";
        String operatorId = "00001";
        String content = "操作1";
        LogPO po = new LogPO(id, time, operatorId, content);
        impl.insert(po);

        id = "Log-2014/12/13-00002";
        time = "2014/12/13 02:12:12";
        operatorId = "00002";
        content = "操作2";
        po = new LogPO(id, time, operatorId, content);
        impl.insert(po);

        id = "Log-2014/12/14-00001";
        time = "2014/12/14 02:12:12";
        operatorId = "00001";
        content = "操作3";
        po = new LogPO(id, time, operatorId, content);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一条日志
        String id = "Log-2014/12/14-00002";
        String time = "2014/12/14 01:12:18";
        String operatorId = "00001";
        String content = "操作4";
        LogPO po = new LogPO(id, time, operatorId, content);
        impl.insert(po);
        assertEquals(4, impl.show().size());
        po = impl.show().get(3);
        assertEquals("Log-2014/12/14-00002", po.getId());
        assertEquals("2014/12/14 01:12:18", po.getTime());
        assertEquals("00001", po.getOperatorId());
        assertEquals("操作4", po.getContent());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        ArrayList<LogPO> list = impl.show();
        assertEquals(3, list.size());
        assertEquals("Log-2014/12/14-00001", list.get(2).getId());
    }

    @Test
    public void testFingByTime() throws RemoteException {
        init();

        ArrayList<LogPO> list = impl.findByTime("2014/12/13", "2014/12/14");
        assertEquals(2, list.size());
        assertEquals("Log-2014/12/13-00001", list.get(0).getId());
        assertEquals("Log-2014/12/13-00002", list.get(1).getId());

        list = impl.findByTime("2014/12/14 00:01:01", "2014/12/14 22:22:22");
        assertEquals(1, list.size());
        assertEquals("Log-2014/12/14-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/15 00:01:01", "2014/12/15 22:22:22");
        assertEquals(0, list.size());
    }

}

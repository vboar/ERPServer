package test;

import data.dataioutility.DataIOUtility;
import data.exceptiondata.ExceptionDataServiceImpl;
import dataservice.exceptiondataservice.ExceptionDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.ExceptionLineItemPO;
import po.ExceptionPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * ExceptionDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestException extends TestCase {

    private ExceptionDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("exception").clearData("exception");
        impl = new ExceptionDataServiceImpl();
        // 添加两个报溢单一个报损单
        String id = "BYD-20141212-00001";
        String time = "2014/12/12/09/31/21";
        int documentStatus = 0;
        int documentType = 1;
        boolean isWriteoff = false;
        ArrayList<ExceptionLineItemPO> list = new ArrayList<ExceptionLineItemPO>();
        list.add(new ExceptionLineItemPO("00001-00001-00001", "百度日光灯", "B001", 50, 60));
        list.add(new ExceptionLineItemPO("00001-00001-00002", "淘宝日光灯", "T011", 40, 80));
        ExceptionPO po = new ExceptionPO(id, time, list, documentStatus, documentType, isWriteoff,true);
        impl.insert(po);

        id = "BSD-20141212-00001";
        time = "2014/12/12/09/35/21";
        documentStatus = 1;
        documentType = 2;
        isWriteoff = true;
        list = new ArrayList<ExceptionLineItemPO>();
        list.add(new ExceptionLineItemPO("00001-00001-00001", "百度日光灯", "B001", 50, 40));
        list.add(new ExceptionLineItemPO("00001-00001-00002", "淘宝日光灯", "T011", 40, 20));
        po = new ExceptionPO(id, time, list, documentStatus, documentType, isWriteoff,true);
        impl.insert(po);

        id = "BYD-20141213-00002";
        time = "2014/12/13/09/35/21";
        documentStatus = 2;
        documentType = 1;
        isWriteoff = false;
        list = new ArrayList<ExceptionLineItemPO>();
        list.add(new ExceptionLineItemPO("00001-00001-00001", "百度日光灯", "B001", 50, 80));
        list.add(new ExceptionLineItemPO("00001-00001-00002", "淘宝日光灯", "T011", 80, 100));
        po = new ExceptionPO(id, time, list, documentStatus, documentType, isWriteoff,true);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个报损单
        String id = "BSD-20141213-00001";
        String time = "2014/12/13/09/31/21";
        int documentStatus = 1;
        int documentType = 2;
        boolean isWriteoff = false;
        ArrayList<ExceptionLineItemPO> list = new ArrayList<ExceptionLineItemPO>();
        list.add(new ExceptionLineItemPO("00001-00001-00001", "百度日光灯", "B001", 50, 30));
        list.add(new ExceptionLineItemPO("00001-00001-00004", "腾讯日光灯", "T111", 40, 20));
        ExceptionPO po = new ExceptionPO(id, time, list, documentStatus, documentType, isWriteoff,true);
        impl.insert(po);
        ArrayList<ExceptionPO> eList = impl.show("1970/01/01", "2014/12/15");
        assertEquals(4, eList.size());
        assertEquals("BSD-20141213-00001", eList.get(3).getId());
        assertEquals("2014/12/13/09/31/21", eList.get(3).getTime());
        assertEquals(1, eList.get(3).getDocumentStatus());
        assertEquals(2, eList.get(3).getDocumentType());
        assertEquals(false, eList.get(3).isWriteoff());
        po = eList.get(3);
        assertEquals(2, po.getList().size());
        assertEquals("00001-00001-00004", po.getList().get(1).getId());
        assertEquals("腾讯日光灯", po.getList().get(1).getName());
        assertEquals("T111", po.getList().get(1).getModel());
        assertEquals(40, po.getList().get(1).getSystemNumber());
        assertEquals(20, po.getList().get(1).getActualNumber());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个报溢单
        String id = "BYD-20141212-00001";
        String time = "2014/12/12/09/31/25";
        int documentStatus = 2;
        int documentType = 1;
        boolean isWriteoff = true;
        ArrayList<ExceptionLineItemPO> list = new ArrayList<ExceptionLineItemPO>();
        list.add(new ExceptionLineItemPO("00001-00001-00001", "百度日光灯", "B001", 30, 80));
        list.add(new ExceptionLineItemPO("00001-00001-00002", "淘宝日光灯", "T011", 70, 100));
        ExceptionPO po = new ExceptionPO(id, time, list, documentStatus, documentType, isWriteoff,true);
        impl.update(po);
        po = impl.show("1970/01/01", "2014/12/15").get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(documentType, po.getDocumentType());
        assertEquals(isWriteoff, po.isWriteoff());
        assertEquals(30, po.getList().get(0).getSystemNumber());
        assertEquals(80, po.getList().get(0).getActualNumber());
        assertEquals(70, po.getList().get(1).getSystemNumber());
        assertEquals(100, po.getList().get(1).getActualNumber());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        // "all"
        ArrayList<ExceptionPO> list = impl.show("1970/01/01", "2014/12/15");
        assertEquals(3, list.size());
        assertEquals("BYD-20141213-00002", list.get(2).getId());
        // "2014/12/12"
        list = impl.show("2014/12/12", "2014/12/13");
        assertEquals(2, list.size());
        list = impl.show("2014/12/12/09/30/00", "2014/12/12/09/38/52");
        assertEquals(2, list.size());
        // "2014/12/01" "2014/12/12"
        list = impl.show("2014/12/01/09/30/00", "2014/12/12/09/11/52");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindById() throws RemoteException {
        init();
        // "BYD"
        ArrayList<ExceptionPO> list = impl.findById("BYD");
        assertEquals(2, list.size());
        assertEquals("BYD-20141213-00002", list.get(1).getId());
        // "11"
        list = impl.findById("11");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByStatus() throws RemoteException {
        init();
        // 0
        ArrayList<ExceptionPO> list = impl.findByStatus(0);
        assertEquals(1, list.size());
        assertEquals("BYD-20141212-00001", list.get(0).getId());
        // 3
        list = impl.findByStatus(3);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();
        // "BYD-20141212"
        ExceptionPO po = impl.getById("BYD-20141212");
        assertNull(po);
        // "BYD-20141213-00002"
        po = impl.getById("BYD-20141213-00002");
        assertEquals("BYD-20141213-00002", po.getId());
    }

}

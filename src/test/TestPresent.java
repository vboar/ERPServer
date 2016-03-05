package test;

import data.dataioutility.DataIOUtility;
import data.presentdata.PresentDataServiceImpl;
import dataservice.presentdataservice.PresentDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.PresentLineItemPO;
import po.PresentPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * PresentDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestPresent extends TestCase {

    private PresentDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("present").clearData("present");
        impl = new PresentDataServiceImpl();
        //新增两个赠送单
        String id = "ZSD-2014/12/13-00001";
        String time = "2014/12/13/12/12/12";
        String customerId = "KH-00001";
        String customerName = "百度";
        ArrayList<PresentLineItemPO> list = new ArrayList<PresentLineItemPO>();
        list.add(new PresentLineItemPO("00001-00001", "百度台灯", "B001", 50));
        list.add(new PresentLineItemPO("00001-00002", "天猫台灯", "T001", 40));
        int documentStatus = 1;
        int documentType = 0;
        boolean isWriteoff = true;
        PresentPO po = new PresentPO(id, time, customerId, customerName, list, documentStatus,
                documentType, isWriteoff,true);
        impl.insert(po);

        id = "ZSD-2014/12/13-00002";
        time = "2014/12/13/18/12/12";
        customerId = "KH-00002";
        customerName = "腾讯";
        list = new ArrayList<PresentLineItemPO>();
        list.add(new PresentLineItemPO("00002-00001", "百度灯", "B011", 80));
        list.add(new PresentLineItemPO("00002-00002", "天猫灯", "T011", 60));
        documentStatus = 0;
        documentType = 0;
        isWriteoff = false;
        po = new PresentPO(id, time, customerId, customerName, list, documentStatus,
                documentType, isWriteoff,true);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个赠送单
        String id = "ZSD-2014/12/15-00001";
        String time = "2014/12/15/12/12/12";
        String customerId = "KH-00001";
        String customerName = "百度";
        ArrayList<PresentLineItemPO> list = new ArrayList<PresentLineItemPO>();
        list.add(new PresentLineItemPO("00001-00001", "百度台灯", "B001", 50));
        list.add(new PresentLineItemPO("00001-00002", "天猫台灯", "T001", 40));
        int documentStatus = 1;
        int documentType = 0;
        boolean isWriteoff = true;
        PresentPO po = new PresentPO(id, time, customerId, customerName, list, documentStatus,
                documentType, isWriteoff,true);
        impl.insert(po);
        assertEquals(3, impl.findByTime("1970/01/01", "2014/12/16").size());
        po = impl.findByTime("1970/01/01", "2014/12/16").get(2);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(list.get(0).getId(), po.getList().get(0).getId());
        assertEquals(list.get(0).getName(), po.getList().get(0).getName());
        assertEquals(list.get(0).getModel(), po.getList().get(0).getModel());
        assertEquals(list.get(0).getNumber(), po.getList().get(0).getNumber());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(documentType,po.getDocumentType());
        assertEquals(isWriteoff, po.isWriteoff());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        //更新第一个赠送单
        String id = "ZSD-2014/12/13-00001";
        String time = "2014/12/13/12/15/12";
        String customerId = "KH-00002";
        String customerName = "淘宝";
        ArrayList<PresentLineItemPO> list = new ArrayList<PresentLineItemPO>();
        list.add(new PresentLineItemPO("00001-00001", "百度台灯1", "B002", 30));
        list.add(new PresentLineItemPO("00001-00002", "天猫台灯2", "T002", 50));
        int documentStatus = 2;
        int documentType = 0;
        boolean isWriteoff = false;
        PresentPO po = new PresentPO(id, time, customerId, customerName, list, documentStatus,
                documentType, isWriteoff,true);
        impl.update(po);
        po = impl.findByTime("1970/01/01", "2014/12/16").get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(list.get(0).getId(), po.getList().get(0).getId());
        assertEquals(list.get(0).getName(), po.getList().get(0).getName());
        assertEquals(list.get(0).getModel(), po.getList().get(0).getModel());
        assertEquals(list.get(0).getNumber(), po.getList().get(0).getNumber());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(documentType,po.getDocumentType());
        assertEquals(isWriteoff, po.isWriteoff());
        init();
    }

    @Test
    public void testFindById() throws RemoteException {
        init();

        ArrayList<PresentPO> list = impl.findById("ZSD-2014/12/13-00001");
        assertEquals(1, list.size());
        assertEquals("ZSD-2014/12/13-00001", list.get(0).getId());

    }

    @Test
    public void testFindByTime() throws RemoteException {
        init();

        ArrayList<PresentPO> list = impl.findByTime("2014/12/12", "2014/12/13");
        assertEquals(0, list.size());

        list = impl.findByTime("2014/12/13", "2014/12/14");
        assertEquals(2, list.size());
        assertEquals("ZSD-2014/12/13-00001", list.get(0).getId());
    }

    @Test
    public void testFindByStatus() throws RemoteException {
        init();

        ArrayList<PresentPO> list = impl.findByStatus(0);
        assertEquals(1, list.size());
        assertEquals("ZSD-2014/12/13-00002", list.get(0).getId());

    }

    @Test
    public void testFingByCustomerId() throws RemoteException {
        init();

        ArrayList<PresentPO> list = impl.findByCustomerId("KH-00001");
        assertEquals(1, list.size());
        assertEquals("ZSD-2014/12/13-00001", list.get(0).getId());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        PresentPO po = impl.getById("ZSD-2014/12/13-00001");
        assertEquals("ZSD-2014/12/13-00001", po.getId());

        po = impl.getById("ZSD-2014/12/13-000015");
        assertNull(po);
    }

}

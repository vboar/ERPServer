package test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import po.CommodityLineItemPO;
import po.PurchasePO;
import data.dataioutility.DataIOUtility;
import data.purchasedata.PurchaseDataServiceImpl;
import dataservice.purchasedataservice.PurchaseDataService;

/**
 * PurchaseDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestPurchase extends TestCase {

    private PurchaseDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("purchase").clearData("purchase");
        impl = new PurchaseDataServiceImpl();
        //添加一个进货单一个进货退货单
        String id = "JHD-20141212-00001";
        String time = "2014/12/12 12:12:12";
        String customerId = "KH00001";
        String customerName = "客户1";
        String operatorId = "00001";
        String storage = "仓库1";
        ArrayList<CommodityLineItemPO> saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("000001", "名称01", "型号01", 500, 100, 50000, "备注01"));
        saleList.add(new CommodityLineItemPO("000002", "名称02", "型号02", 100, 200, 20000, "备注02"));
        double total = 5000;
        String remark = "备注";
        int documentStatus = 1;
        boolean isWriteOff = true;
        boolean canReturn = true;
        boolean canWriteOff = true;
        int documentType = 7;
        PurchasePO po = new PurchasePO(id, id, time, customerId, customerName, operatorId, storage, saleList,
                total, remark, documentStatus, isWriteOff, canReturn, canWriteOff,documentType);
        impl.insert(po);

        id = "JHTHD-20141213-00001";
        time = "2014/12/13 12:12:12";
        customerId = "KH00002";
        customerName = "客户2";
        operatorId = "00002";
        storage = "仓库2";
        saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("000001", "名称01", "型号01", 500, 100, 50000, "备注01"));
        saleList.add(new CommodityLineItemPO("000002", "名称02", "型号02", 100, 200, 20000, "备注02"));
        total = 50000;
        remark = "备注2";
        documentStatus = 2;
        isWriteOff = false;
        canReturn = true;
        canWriteOff = true;
        documentType = 8;
        po = new PurchasePO(id, id,time, customerId, customerName, operatorId, storage, saleList,
                total, remark, documentStatus, isWriteOff,canReturn,canWriteOff, documentType);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();

        // 添加一个进货单
        String id = "JHD-20141212-00002";
        String time = "2014/12/14 12:12:12";
        String customerId = "KH00003";
        String customerName = "客户3";
        String operatorId = "00003";
        String storage = "仓库3";
        ArrayList<CommodityLineItemPO> saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("000003", "名称01", "型号01", 500, 100, 50000, "备注01"));
        saleList.add(new CommodityLineItemPO("000003", "名称02", "型号02", 100, 200, 20000, "备注02"));
        double total = 300;
        String remark = "备注3";
        int documentStatus = 2;
        boolean isWriteOff = false;
        boolean canReturn = true;
        boolean canWriteOff = true;
        int documentType = 7;
        PurchasePO po = new PurchasePO(id, id,time, customerId, customerName, operatorId, storage, saleList,
                total, remark, documentStatus, isWriteOff, canReturn, canWriteOff,documentType);
        impl.insert(po);
        assertEquals(3, impl.show().size());
        po = impl.show().get(2);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals(storage, po.getStorage());
        assertEquals(saleList.get(0).getId(), po.getSaleList().get(0).getId());
        assertEquals(saleList.get(0).getModel(), po.getSaleList().get(0).getModel());
        assertEquals(saleList.get(0).getNumber(), po.getSaleList().get(0).getNumber());
        assertEquals(saleList.get(0).getRemark(), po.getSaleList().get(0).getRemark());
        assertEquals(saleList.get(0).getName(), po.getSaleList().get(0).getName());
        assertEquals(saleList.get(0).getPrice(), po.getSaleList().get(0).getPrice());
        assertEquals(saleList.get(0).getTotal(), po.getSaleList().get(0).getTotal());
        assertEquals(total, po.getTotal());
        assertEquals(remark, po.getRemark());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(isWriteOff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();

        // 更新第一个进货单
        String id = "JHD-20141212-00001";
        String time = "2014/12/12 12:15:12";
        String customerId = "KH00005";
        String customerName = "客户5";
        String operatorId = "00005";
        String storage = "仓库5";
        ArrayList<CommodityLineItemPO> saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("000005", "名称05", "型号05", 500, 100, 50000, "备注05"));
        saleList.add(new CommodityLineItemPO("000005", "名称05", "型号02", 100, 200, 20000, "备注05"));
        double total = 5050;
        String remark = "备注5";
        int documentStatus = 2;
        boolean isWriteOff = false;
        boolean canReturn = true;
        boolean canWriteOff = true;
        int documentType = 7;
        PurchasePO po = new PurchasePO(id, id,time, customerId, customerName, operatorId, storage, saleList,
                total, remark, documentStatus, isWriteOff, canReturn, canWriteOff,documentType);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals(storage, po.getStorage());
        assertEquals(saleList.get(0).getId(), po.getSaleList().get(0).getId());
        assertEquals(saleList.get(0).getModel(), po.getSaleList().get(0).getModel());
        assertEquals(saleList.get(0).getNumber(), po.getSaleList().get(0).getNumber());
        assertEquals(saleList.get(0).getRemark(), po.getSaleList().get(0).getRemark());
        assertEquals(saleList.get(0).getName(), po.getSaleList().get(0).getName());
        assertEquals(saleList.get(0).getPrice(), po.getSaleList().get(0).getPrice());
        assertEquals(saleList.get(0).getTotal(), po.getSaleList().get(0).getTotal());
        assertEquals(total, po.getTotal());
        assertEquals(remark, po.getRemark());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(isWriteOff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        init();
    }

    @Test
    public void testFindByTime() throws RemoteException {
        init();

        ArrayList<PurchasePO> list = impl.findByTime("2014/12/12", "2014/12/14");
        assertEquals(2, list.size());
        assertEquals("JHD-20141212-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/12", "2014/12/13");
        assertEquals(1, list.size());
        assertEquals("JHD-20141212-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/14", "2014/12/15");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByCustomer() throws RemoteException {
        init();

        ArrayList<PurchasePO> list = impl.findByCustomer("KH00001");
        assertEquals(1, list.size());
        assertEquals("JHD-20141212-00001", list.get(0).getId());

        list = impl.findByCustomer("KH000001");
        assertEquals(0, list.size());

    }

    @Test
    public void testFindByStorage() throws RemoteException {
        init();

        ArrayList<PurchasePO> list = impl.findByStorage("仓库1");
        assertEquals(1, list.size());
        assertEquals("JHD-20141212-00001", list.get(0).getId());

        list = impl.findByStorage("仓库");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByStatus() throws RemoteException {
        init();

        ArrayList<PurchasePO> list = impl.findByStatus(1);
        assertEquals(1, list.size());
        assertEquals("JHD-20141212-00001", list.get(0).getId());

        list = impl.findByStatus(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<PurchasePO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("JHD-20141212-00001", list.get(0).getId());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        PurchasePO po = impl.getById("JHD-20141212-00001");
        assertEquals("JHD-20141212-00001", po.getId());

        po = impl.getById("JHD-20141212-00");
        assertNull(po);
    }
}

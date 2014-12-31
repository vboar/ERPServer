package test;

import data.dataioutility.DataIOUtility;
import data.saledata.SaleDataServiceImpl;
import dataservice.saledataservice.SaleDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CommodityLineItemPO;
import po.SalePO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * SaleDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestSale extends TestCase {

    private SaleDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("sale").clearData("sale");
        impl = new SaleDataServiceImpl();
        // 增加一个销售单一个销售退货单
        String id = "XSD-20141212-00001";
        String time = "2014/12/12 12:12:12";
        String customerId = "KH00001";
        String customerName = "客户1";
        int customerVIP = 3;
        String salesmanId = "00001";
        String operatorId = "00002";
        String storage = "仓库1";
        ArrayList<CommodityLineItemPO> saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("000001", "名称01", "型号01", 500, 100, 50000, "备注01"));
        saleList.add(new CommodityLineItemPO("000002", "名称02", "型号02", 100, 200, 20000, "备注02"));
        String presentId = "ZSD-20141112-00001";
        double totalBeforeDiscount = 500;
        double discount = 0.8;
        double voucher = 50;
        double totalAfterDiscount = 300;
        String remark = "备注";
        int documentStatus = 2;
        boolean isWriteOff = true;
        int documentType = 5;
        SalePO po = new SalePO(id, id,time, customerId, customerName, customerVIP, salesmanId, operatorId,
                storage, saleList, presentId, totalBeforeDiscount, discount, voucher, totalAfterDiscount, remark,
                documentStatus, isWriteOff, true,true,documentType);
        impl.insert(po);

        id = "XSTHD-20141213-00001";
        time = "2014/12/13 12:12:12";
        customerId = "KH00002";
        customerName = "客户2";
        customerVIP = 1;
        salesmanId = "00002";
        operatorId = "00003";
        storage = "仓库2";
        saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("00001", "名称1", "型号1", 50, 10, 500, "备注1"));
        saleList.add(new CommodityLineItemPO("00002", "名称2", "型号2", 10, 20, 200, "备注2"));
        presentId = "ZSD-20141112-00002";
        totalBeforeDiscount = 5000;
        discount = 0.7;
        voucher = 500;
        totalAfterDiscount = 1000;
        remark = "备注0";
        documentStatus = 0;
        isWriteOff = false;
        documentType = 6;
        po = new SalePO(id, id,time, customerId, customerName, customerVIP, salesmanId, operatorId,
                storage, saleList, presentId, totalBeforeDiscount, discount, voucher, totalAfterDiscount, remark,
                documentStatus, isWriteOff, true,true,documentType);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个销售单
        String id = "XSD-20141215-00002";
        String time = "2014/12/15 12:12:12";
        String customerId = "KH00003";
        String customerName = "客户3";
        int customerVIP = 5;
        String salesmanId = "00002";
        String operatorId = "00001";
        String storage = "仓库3";
        ArrayList<CommodityLineItemPO> saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("000002", "名称02", "型号02", 50, 20, 1000, "备注0"));
        saleList.add(new CommodityLineItemPO("000003", "名称03", "型号03", 10, 200, 2000, "备注01"));
        String presentId = "ZSD-20141112-00005";
        double totalBeforeDiscount = 50000;
        double discount = 0.6;
        double voucher = 5000;
        double totalAfterDiscount = 50000;
        String remark = "备注ll";
        int documentStatus = 0;
        boolean isWriteOff = false;
        int documentType = 5;
        SalePO po = new SalePO(id,id, time, customerId, customerName, customerVIP, salesmanId, operatorId,
                storage, saleList, presentId, totalBeforeDiscount, discount, voucher, totalAfterDiscount, remark,
                documentStatus, isWriteOff,true,true, documentType);
        impl.insert(po);
        assertEquals(3, impl.show().size());
        po = impl.show().get(2);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(customerVIP, po.getCustomerVIP());
        assertEquals(salesmanId, po.getSalesmanId());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals(storage, po.getStorage());
        assertEquals(saleList.get(0).getId(), po.getSaleList().get(0).getId());
        assertEquals(saleList.get(0).getModel(), po.getSaleList().get(0).getModel());
        assertEquals(saleList.get(0).getNumber(), po.getSaleList().get(0).getNumber());
        assertEquals(saleList.get(0).getRemark(), po.getSaleList().get(0).getRemark());
        assertEquals(saleList.get(0).getName(), po.getSaleList().get(0).getName());
        assertEquals(saleList.get(0).getPrice(), po.getSaleList().get(0).getPrice());
        assertEquals(saleList.get(0).getTotal(), po.getSaleList().get(0).getTotal());
        assertEquals(totalBeforeDiscount, po.getTotalBeforeDiscount());
        assertEquals(discount, po.getDiscount());
        assertEquals(voucher, po.getVoucher());
        assertEquals(totalAfterDiscount, po.getTotalAfterDiscount());
        assertEquals(remark, po.getRemark());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(isWriteOff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个销售单
        String id = "XSD-20141212-00001";
        String time = "2014/12/12 12:12:13";
        String customerId = "KH00006";
        String customerName = "客户6";
        int customerVIP = 2;
        String salesmanId = "00005";
        String operatorId = "00006";
        String storage = "仓库4";
        ArrayList<CommodityLineItemPO> saleList = new ArrayList<CommodityLineItemPO>();
        saleList.add(new CommodityLineItemPO("100001", "名称11", "型号11", 50, 10, 500, "备注11"));
        saleList.add(new CommodityLineItemPO("100002", "名称22", "型号22", 10, 20, 200, "备注22"));
        String presentId = "ZSD-20141112-00005";
        double totalBeforeDiscount = 400;
        double discount = 0.75;
        double voucher = 40;
        double totalAfterDiscount = 350;
        String remark = "备注5";
        int documentStatus = 1;
        boolean isWriteOff = false;
        int documentType = 5;
        SalePO po = new SalePO(id, id,time, customerId, customerName, customerVIP, salesmanId, operatorId,
                storage, saleList, presentId, totalBeforeDiscount, discount, voucher, totalAfterDiscount, remark,
                documentStatus, isWriteOff, true,true,documentType);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(customerVIP, po.getCustomerVIP());
        assertEquals(salesmanId, po.getSalesmanId());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals(storage, po.getStorage());
        assertEquals(saleList.get(0).getId(), po.getSaleList().get(0).getId());
        assertEquals(saleList.get(0).getModel(), po.getSaleList().get(0).getModel());
        assertEquals(saleList.get(0).getNumber(), po.getSaleList().get(0).getNumber());
        assertEquals(saleList.get(0).getRemark(), po.getSaleList().get(0).getRemark());
        assertEquals(saleList.get(0).getName(), po.getSaleList().get(0).getName());
        assertEquals(saleList.get(0).getPrice(), po.getSaleList().get(0).getPrice());
        assertEquals(saleList.get(0).getTotal(), po.getSaleList().get(0).getTotal());
        assertEquals(totalBeforeDiscount, po.getTotalBeforeDiscount());
        assertEquals(discount, po.getDiscount());
        assertEquals(voucher, po.getVoucher());
        assertEquals(totalAfterDiscount, po.getTotalAfterDiscount());
        assertEquals(remark, po.getRemark());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(isWriteOff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        init();
    }

    @Test
    public void testFindByTime() throws RemoteException {
        init();

        ArrayList<SalePO> list = impl.findByTime("2014/12/12", "2014/12/14");
        assertEquals(2, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/12", "2014/12/13");
        assertEquals(1, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/14", "2014/12/15");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByCustomer() throws RemoteException {
        init();

        ArrayList<SalePO> list = impl.findByCustomer("KH00001");
        assertEquals(1, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());

        list = impl.findByCustomer("KH000001");
        assertEquals(0, list.size());

    }

    @Test
    public void testFindBySalesman() throws RemoteException {
        init();

        ArrayList<SalePO> list = impl.findBySalesman("00001");
        assertEquals(1, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());

        list = impl.findBySalesman("0000");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByStorage() throws RemoteException {
        init();

        ArrayList<SalePO> list = impl.findByStorage("仓库1");
        assertEquals(1, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());

        list = impl.findByStorage("仓库");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByStatus() throws RemoteException {
        init();

        ArrayList<SalePO> list = impl.findByStatus(2);
        assertEquals(1, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());

        list = impl.findByStatus(1);
        assertEquals(0, list.size());
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<SalePO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("XSD-20141212-00001", list.get(0).getId());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        SalePO po = impl.getById("XSD-20141212-00001");
        assertEquals("XSD-20141212-00001", po.getId());

        po = impl.getById("XSD-20141212-00");
        assertNull(po);
    }
}

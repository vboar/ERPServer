package test;

import data.dataioutility.DataIOUtility;
import data.paymentdata.PaymentDataServiceImpl;
import dataservice.paymentdataservice.PaymentDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.PaymentPO;
import po.TransferLineItemPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * PaymentDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestPayment extends TestCase {

    private PaymentDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("payment").clearData("payment");
        impl = new PaymentDataServiceImpl();
        // 添加一个收款单，一个付款单
        String id = "SKD-2014/12/12-00001";
        String time = "2014/12/12/12/12/12";
        String customerId = "KH-00001";
        String customerName = "淘宝";
        String operatorId = "00001";
        ArrayList<TransferLineItemPO> list = new ArrayList<TransferLineItemPO>();
        list.add(new TransferLineItemPO("62220222", 500, "备注1"));
        list.add(new TransferLineItemPO("62220002", 1000, "备注2"));
        double total = 1500;
        int approvalStatus = 1;
        boolean isWriteOff = true;
        int documentType = 8;
        PaymentPO po = new PaymentPO(id, time, customerId, customerName, operatorId,
                list, total, approvalStatus, isWriteOff, documentType);
        impl.insert(po);

        id = "FKD-2014/12/12-00001";
        time = "2014/12/12/12/18/12";
        customerId = "KH-00002";
        customerName = "百度";
        operatorId = "00001";
        list = new ArrayList<TransferLineItemPO>();
        list.add(new TransferLineItemPO("62220282", 5000, "备注3"));
        list.add(new TransferLineItemPO("62220082", 10000, "备注4"));
        total = 15000;
        approvalStatus = 2;
        isWriteOff = false;
        documentType = 9;
        po = new PaymentPO(id, time, customerId, customerName, operatorId,
                list, total, approvalStatus, isWriteOff, documentType);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        //添加一个收款单
        String id = "SKD-2014/12/12-00002";
        String time = "2014/12/12/12/14/12";
        String customerId = "KH-00002";
        String customerName = "百度";
        String operatorId = "00002";
        ArrayList<TransferLineItemPO> list = new ArrayList<TransferLineItemPO>();
        list.add(new TransferLineItemPO("62220221", 100, "备注5"));
        list.add(new TransferLineItemPO("62220001", 150, "备注6"));
        double total = 250;
        int approvalStatus = 1;
        boolean isWriteOff = false;
        int documentType = 8;
        PaymentPO po = new PaymentPO(id, time, customerId, customerName, operatorId,
                list, total, approvalStatus, isWriteOff, documentType);
        impl.insert(po);
        assertEquals(3, impl.show().size());
        po = impl.show().get(2);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals("62220221", po.getTransferList().get(0).getBankAccount());
        assertEquals(100, (int)po.getTransferList().get(0).getAccount());
        assertEquals("备注5", po.getTransferList().get(0).getRemark());
        assertEquals(total, po.getTotal());
        assertEquals(approvalStatus, po.getApprovalStatus());
        assertEquals(isWriteOff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个收款单
        String id = "SKD-2014/12/12-00001";
        String time = "2014/12/12/12/15/15";
        String customerId = "KH-00002";
        String customerName = "百度";
        String operatorId = "00002";
        ArrayList<TransferLineItemPO> list = new ArrayList<TransferLineItemPO>();
        list.add(new TransferLineItemPO("62220255", 1500, "备注5"));
        list.add(new TransferLineItemPO("62220056", 1200, "备注6"));
        double total = 2700;
        int approvalStatus = 0;
        boolean isWriteOff = false;
        int documentType = 8;
        PaymentPO po = new PaymentPO(id, time, customerId, customerName, operatorId,
                list, total, approvalStatus, isWriteOff, documentType);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(customerId, po.getCustomerId());
        assertEquals(customerName, po.getCustomerName());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals("62220255", po.getTransferList().get(0).getBankAccount());
        assertEquals(1500, (int)po.getTransferList().get(0).getAccount());
        assertEquals("备注5", po.getTransferList().get(0).getRemark());
        assertEquals(total, po.getTotal());
        assertEquals(approvalStatus, po.getApprovalStatus());
        assertEquals(isWriteOff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        ArrayList<PaymentPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("SKD-2014/12/12-00001", list.get(0).getId());
    }

    @Test
    public void testFindById() throws RemoteException {
        init();

        ArrayList<PaymentPO> list = impl.findById("SKD-2014/12/12-00001");
        assertEquals(1, list.size());
        assertEquals("SKD-2014/12/12-00001", list.get(0).getId());

        list = impl.findById("SKD-2014/12/12-00002");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByTime() throws RemoteException {
        init();

        ArrayList<PaymentPO> list = impl.findByTime("2014/12/12", "2014/12/13");
        assertEquals(2, list.size());
        assertEquals("SKD-2014/12/12-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/13", "2014/12/14");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByCustomer() throws RemoteException {
        init();

        ArrayList<PaymentPO> list = impl.findByCustomer("KH-00002");
        assertEquals(1, list.size());
        assertEquals("FKD-2014/12/12-00001", list.get(0).getId());

        list = impl.findByCustomer("KH-00003");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByOperator() throws RemoteException {
        init();

        ArrayList<PaymentPO> list = impl.findByOperator("00001");
        assertEquals(2, list.size());

        list = impl.findByOperator("00003");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByStatus() throws RemoteException {
        init();

        ArrayList<PaymentPO> list = impl.findByStatus(1);
        assertEquals(1, list.size());

        list = impl.findByStatus(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        PaymentPO po = impl.getById("SKD-2014/12/12");
        assertNull(po);

        po = impl.getById("SKD-2014/12/12-000015");
        assertNull(po);

        po = impl.getById("SKD-2014/12/12-00001");
        assertEquals("SKD-2014/12/12-00001", po.getId());
    }
}

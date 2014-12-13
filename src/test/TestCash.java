package test;

import data.dataioutility.DataIOUtility;
import data.paymentdata.CashDataServiceImpl;
import dataservice.paymentdataservice.CashDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CashPO;
import po.ClauseLineItemPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * CashDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestCash extends TestCase {

    private CashDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("cash").clearData("cash");
        impl = new CashDataServiceImpl();
        // 增加两个现金费用单
        String id = "XJFYD-2014/12/12-00001";
        String time = "2014/12/12/12/12/12";
        String operatorId = "00001";
        String bankAccount = "62220002";
        ArrayList<ClauseLineItemPO> cList = new ArrayList<ClauseLineItemPO>();
        cList.add(new ClauseLineItemPO("名称1", 50, "备注1"));
        cList.add(new ClauseLineItemPO("名称2", 100, "备注2"));
        double total = 150;
        int documentStatus = 1;
        boolean isWriteoff = false;
        int documentType = 10;
        CashPO po = new CashPO(id, time, operatorId, bankAccount, cList, total, documentStatus,
                isWriteoff, documentType);
        impl.insert(po);

        id = "XJFYD-2014/12/12-00002";
        time = "2014/12/12/12/15/12";
        operatorId = "00002";
        bankAccount = "62220012";
        cList = new ArrayList<ClauseLineItemPO>();
        cList.add(new ClauseLineItemPO("名称1", 150, "备注1"));
        cList.add(new ClauseLineItemPO("名称2", 100, "备注2"));
        total = 250;
        documentStatus = 0;
        isWriteoff = true;
        documentType = 10;
        po = new CashPO(id, time, operatorId, bankAccount, cList, total, documentStatus,
                isWriteoff, documentType);
        impl.insert(po);
    }

    public void testInsert() throws RemoteException {
        init();
        // 增加一条现金费用单
        String id = "XJFYD-2014/12/13-00001";
        String time = "2014/12/13/12/12/12";
        String operatorId = "00001";
        String bankAccount = "62220002";
        ArrayList<ClauseLineItemPO> cList = new ArrayList<ClauseLineItemPO>();
        cList.add(new ClauseLineItemPO("名称1", 60, "备注1"));
        cList.add(new ClauseLineItemPO("名称2", 120, "备注2"));
        double total = 180;
        int documentStatus = 0;
        boolean isWriteoff = false;
        int documentType = 10;
        CashPO po = new CashPO(id, time, operatorId, bankAccount, cList, total, documentStatus,
                isWriteoff, documentType);
        impl.insert(po);
        assertEquals(3, impl.show().size());
        po = impl.show().get(2);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals(bankAccount, po.getBankAccount());
        assertEquals(total, po.getTotal());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(isWriteoff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        assertEquals("名称2", po.getClauseList().get(1).getName());
        assertEquals(120, (int)po.getClauseList().get(1).getAccount());
        assertEquals("备注2", po.getClauseList().get(1).getRemark());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个现金费用单
        String id = "XJFYD-2014/12/12-00001";
        String time = "2014/12/14/12/12/12";
        String operatorId = "00002";
        String bankAccount = "62220052";
        ArrayList<ClauseLineItemPO> cList = new ArrayList<ClauseLineItemPO>();
        cList.add(new ClauseLineItemPO("名称3", 65, "备注3"));
        cList.add(new ClauseLineItemPO("名称4", 125, "备注4"));
        double total = 190;
        int documentStatus = 1;
        boolean isWriteoff = true;
        int documentType = 10;
        CashPO po = new CashPO(id, time, operatorId, bankAccount, cList, total, documentStatus,
                isWriteoff, documentType);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(operatorId, po.getOperatorId());
        assertEquals(bankAccount, po.getBankAccount());
        assertEquals(total, po.getTotal());
        assertEquals(documentStatus, po.getDocumentStatus());
        assertEquals(isWriteoff, po.isWriteOff());
        assertEquals(documentType, po.getDocumentType());
        assertEquals("名称4", po.getClauseList().get(1).getName());
        assertEquals(125, (int)po.getClauseList().get(1).getAccount());
        assertEquals("备注4", po.getClauseList().get(1).getRemark());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        ArrayList<CashPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("XJFYD-2014/12/12-00001", list.get(0).getId());
    }

    @Test
    public void testFindById() throws RemoteException {
        init();

        ArrayList<CashPO> list = impl.findById("XJFYD-2014/12/12");
        assertEquals(2, list.size());
        assertEquals("XJFYD-2014/12/12-00002", list.get(1).getId());

        list = impl.findById("XJFYD-2014/12/12-00002");
        assertEquals(1, list.size());
        assertEquals("XJFYD-2014/12/12-00002", list.get(0).getId());

        list = impl.findById("XJFYD-2014/12/12-00002-1");
        assertEquals(0, list.size());

        list = impl.findById("XJFYD-2014/12/12-00502");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByTime() throws RemoteException {
        init();

        ArrayList<CashPO> list = impl.findByTime("2014/12/12", "2014/12/13");
        assertEquals(2, list.size());
        assertEquals("XJFYD-2014/12/12-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/12/01/01/01", "2014/12/12/15/20/55");
        assertEquals(2, list.size());
        assertEquals("XJFYD-2014/12/12-00001", list.get(0).getId());

        list = impl.findByTime("2014/12/13/01/01/01", "2014/12/13/15/20/55");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByStatus() throws RemoteException {
        init();

        ArrayList<CashPO> list = impl.findByStatus(0);
        assertEquals(1, list.size());
        assertEquals(0, list.get(0).getDocumentStatus());

        list = impl.findByStatus(1);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getDocumentStatus());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        CashPO po = impl.getById("XJFYD-2014/12/12-00002");
        assertEquals("XJFYD-2014/12/12-00002", po.getId());

        po =  impl.getById("XJFYD-2014/12/12-00003");
        assertNull(po);
    }

}

package test;

import data.dataioutility.DataIOUtility;
import data.promotiondata.TotalGiftDataServiceImpl;
import dataservice.promotiondataservice.TotalGiftDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CustomerGiftPO;
import po.PresentLineItemPO;
import po.TotalGiftPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * TotalGiftDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestTotalGift extends TestCase {

    private TotalGiftDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("totalgift").clearData("totalgift");
        impl = new TotalGiftDataServiceImpl();
        // 添加两个促销策略
        String id = "CX00001";
        double total = 500;
        ArrayList<PresentLineItemPO > giftInfo = new ArrayList<>();
        giftInfo.add(new PresentLineItemPO("00001", "名称1", "型号1", 50));
        giftInfo.add(new PresentLineItemPO("00002", "名称2", "型号2", 60));
        double voucher = 50;
        String startTime = "2014/12/12";
        String endTime = "2014/12/14";
        boolean valid = true;
        TotalGiftPO po = new TotalGiftPO(id, total, giftInfo, voucher, startTime, endTime, valid);
        impl.insert(po);

        id = "CX00002";
        total = 5000;
        giftInfo = new ArrayList<>();
        giftInfo.add(new PresentLineItemPO("000001", "名称01", "型号01", 500));
        giftInfo.add(new PresentLineItemPO("000002", "名称02", "型号02", 600));
        voucher = 500;
        startTime = "2014/12/13";
        endTime = "2014/12/15";
        valid = false;
        po = new TotalGiftPO(id, total, giftInfo, voucher, startTime, endTime, valid);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个促销策略
        String id = "CX00003";
        double total = 300;
        ArrayList<PresentLineItemPO > giftInfo = new ArrayList<>();
        giftInfo.add(new PresentLineItemPO("00003", "名称3", "型号3", 500));
        giftInfo.add(new PresentLineItemPO("00004", "名称4", "型号4", 600));
        double voucher = 150;
        String startTime = "2014/12/15";
        String endTime = "2014/12/16";
        boolean valid = false;
        TotalGiftPO po = new TotalGiftPO(id, total, giftInfo, voucher, startTime, endTime, valid);
        impl.insert(po);
        po = impl.show().get(2);
        assertEquals(3, impl.show().size());
        assertEquals(id, po.getId());
        assertEquals(total, po.getTotal());
        assertEquals(giftInfo.get(0).getNumber(), po.getGiftInfo().get(0).getNumber());
        assertEquals(giftInfo.get(0).getId(), po.getGiftInfo().get(0).getId());
        assertEquals(giftInfo.get(0).getModel(), po.getGiftInfo().get(0).getModel());
        assertEquals(giftInfo.get(0).getName(), po.getGiftInfo().get(0).getName());
        assertEquals(startTime, po.getStartTime());
        assertEquals(endTime, po.getEndTime());
        assertEquals(voucher,po.getVoucher());
        assertEquals(valid, po.isValid());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个促销策略
        String id = "CX00001";
        double total = 350;
        ArrayList<PresentLineItemPO > giftInfo = new ArrayList<>();
        giftInfo.add(new PresentLineItemPO("000001", "名称10", "型号10", 52));
        giftInfo.add(new PresentLineItemPO("000002", "名称20", "型号20", 62));
        double voucher = 52;
        String startTime = "2014/12/16";
        String endTime = "2014/12/18";
        boolean valid = false;
        TotalGiftPO po = new TotalGiftPO(id, total, giftInfo, voucher, startTime, endTime, valid);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(total, po.getTotal());
        assertEquals(giftInfo.get(0).getNumber(), po.getGiftInfo().get(0).getNumber());
        assertEquals(giftInfo.get(0).getId(), po.getGiftInfo().get(0).getId());
        assertEquals(giftInfo.get(0).getModel(), po.getGiftInfo().get(0).getModel());
        assertEquals(giftInfo.get(0).getName(), po.getGiftInfo().get(0).getName());
        assertEquals(startTime, po.getStartTime());
        assertEquals(endTime, po.getEndTime());
        assertEquals(voucher,po.getVoucher());
        assertEquals(valid, po.isValid());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<TotalGiftPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("CX00001", list.get(0).getId());

    }

    @Test
    public void testFindByValid() throws RemoteException {
        init();

        ArrayList<TotalGiftPO> list = impl.findByValid(true);
        assertEquals(1, list.size());
        assertEquals("CX00001", list.get(0).getId());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        TotalGiftPO po = impl.getById("CX00001");
        assertEquals("CX00001", po.getId());

        po = impl.getById("CX0000001");
        assertNull(po);
    }

}

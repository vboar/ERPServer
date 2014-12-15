package test;

import data.dataioutility.DataIOUtility;
import data.promotiondata.CustomerGiftDataserviceImpl;
import dataservice.promotiondataservice.CustomerGiftDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CustomerGiftPO;
import po.PresentLineItemPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * CustomerGiftDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestCustomerGift extends TestCase {

    private CustomerGiftDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("customergift").clearData("customergift");
        impl = new CustomerGiftDataserviceImpl();
        // 添加两个促销策略
        String id = "CX00001";
        int vip = 1;
        ArrayList<PresentLineItemPO> giftInfo = new ArrayList<PresentLineItemPO>();
        giftInfo.add(new PresentLineItemPO("00001", "赠品1", "型号1", 5));
        giftInfo.add(new PresentLineItemPO("00002", "赠品2", "型号2", 10));
        double discount = 0.8;
        double voucher = 50;
        String startTime = "2014/12/12/12/12/12";
        String endTime = "2014/12/15/12/12/12";
        boolean valid = true;
        CustomerGiftPO po = new CustomerGiftPO(id, vip, giftInfo, discount, voucher, startTime, endTime, valid);
        impl.insert(po);

        id = "CX00002";
        vip = 3;
        giftInfo = new ArrayList<PresentLineItemPO>();
        giftInfo.add(new PresentLineItemPO("00001", "赠品1", "型号1", 15));
        giftInfo.add(new PresentLineItemPO("00002", "赠品2", "型号2", 50));
        discount = 0.7;
        voucher = 60;
        startTime = "2014/12/15/12/12/12";
        endTime = "2014/12/18/12/12/12";
        valid = false;
        po = new CustomerGiftPO(id, vip, giftInfo, discount, voucher, startTime, endTime, valid);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个促销策略
        String id = "CX00003";
        int vip = 4;
        ArrayList<PresentLineItemPO> giftInfo = new ArrayList<PresentLineItemPO>();
        giftInfo.add(new PresentLineItemPO("00005", "赠品5", "型号5", 15));
        giftInfo.add(new PresentLineItemPO("00006", "赠品6", "型号6", 11));
        double discount = 0.9;
        double voucher = 500;
        String startTime = "2014/12/15/12/12/12";
        String endTime = "2014/12/16/12/12/12";
        boolean valid = false;
        CustomerGiftPO po = new CustomerGiftPO(id, vip, giftInfo, discount, voucher, startTime, endTime, valid);
        impl.insert(po);
        assertEquals(3, impl.show().size());
        po = impl.show().get(2);
        assertEquals(id, po.getId());
        assertEquals(vip, po.getVIP());
        assertEquals(giftInfo.get(0).getId(), po.getGiftInfo().get(0).getId());
        assertEquals(giftInfo.get(0).getName(), po.getGiftInfo().get(0).getName());
        assertEquals(giftInfo.get(0).getModel(), po.getGiftInfo().get(0).getModel());
        assertEquals(giftInfo.get(0).getNumber(), po.getGiftInfo().get(0).getNumber());
        assertEquals(discount, po.getDiscount());
        assertEquals(voucher, po.getVoucher());
        assertEquals(startTime ,po.getStartTime());
        assertEquals(endTime, po.getEndTime());
        assertEquals(valid, po.isValid());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个促销策略
        String id = "CX00001";
        int vip = 5;
        ArrayList<PresentLineItemPO> giftInfo = new ArrayList<PresentLineItemPO>();
        giftInfo.add(new PresentLineItemPO("00003", "赠品3", "型号3", 55));
        giftInfo.add(new PresentLineItemPO("00004", "赠品4", "型号4", 15));
        double discount = 0.5;
        double voucher = 500;
        String startTime = "2014/12/15/12/12/12";
        String endTime = "2014/12/16/12/12/12";
        boolean valid = false;
        CustomerGiftPO po = new CustomerGiftPO(id, vip, giftInfo, discount, voucher, startTime, endTime, valid);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(vip, po.getVIP());
        assertEquals(giftInfo.get(0).getId(), po.getGiftInfo().get(0).getId());
        assertEquals(giftInfo.get(0).getName(), po.getGiftInfo().get(0).getName());
        assertEquals(giftInfo.get(0).getModel(), po.getGiftInfo().get(0).getModel());
        assertEquals(giftInfo.get(0).getNumber(), po.getGiftInfo().get(0).getNumber());
        assertEquals(discount, po.getDiscount());
        assertEquals(voucher, po.getVoucher());
        assertEquals(startTime ,po.getStartTime());
        assertEquals(endTime, po.getEndTime());
        assertEquals(valid, po.isValid());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<CustomerGiftPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("CX00001", list.get(0).getId());

    }

    @Test
    public void testFindByValid() throws RemoteException {
        init();

        ArrayList<CustomerGiftPO> list = impl.findByValid(true);
        assertEquals(1, list.size());
        assertEquals("CX00001", list.get(0).getId());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        CustomerGiftPO po = impl.getById("CX00001");
        assertEquals("CX00001", po.getId());

        po = impl.getById("CX0000001");
        assertNull(po);
    }

}

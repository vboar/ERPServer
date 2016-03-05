package test;

import data.dataioutility.DataIOUtility;
import data.promotiondata.CustomerGiftDataserviceImpl;
import data.promotiondata.SpecialOfferDataServiceImpl;
import dataservice.promotiondataservice.SpecialOfferDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CommodityLineItemPO;
import po.CustomerGiftPO;
import po.SpecialOfferPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * SpecialOfferDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestSpecialOffer extends TestCase {

    private SpecialOfferDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("specialoffer").clearData("specialoffer");
        impl = new SpecialOfferDataServiceImpl();
        // 增加两条促销策略
        String id = "CX00001";
        ArrayList<CommodityLineItemPO> commodityList = new ArrayList<CommodityLineItemPO>();
        commodityList.add(new CommodityLineItemPO("00001", "名称1", "型号1", 50, 10, 500, "备注1"));
        commodityList.add(new CommodityLineItemPO("00002", "名称2", "型号2", 10, 20, 200, "备注2"));
        double total = 700;
        String startTime = "2014/12/08";
        String endTime = "2014/12/12";
        boolean valid = true;
        SpecialOfferPO po = new SpecialOfferPO(id, commodityList, total, startTime, endTime, valid);
        impl.insert(po);

        id = "CX00002";
        commodityList = new ArrayList<CommodityLineItemPO>();
        commodityList.add(new CommodityLineItemPO("000001", "名称01", "型号01", 500, 100, 50000, "备注10"));
        commodityList.add(new CommodityLineItemPO("000002", "名称02", "型号02", 100, 200, 20000, "备注20"));
        total = 70000;
        startTime = "2014/12/12";
        endTime = "2014/12/15";
        valid = false;
        po = new SpecialOfferPO(id, commodityList, total, startTime, endTime, valid);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        //增加一个促销策略
        String id = "CX00003";
        ArrayList<CommodityLineItemPO> commodityList = new ArrayList<CommodityLineItemPO>();
        commodityList.add(new CommodityLineItemPO("00003", "名称3", "型号3", 30, 15, 450, "备注3"));
        commodityList.add(new CommodityLineItemPO("00004", "名称4", "型号4", 10, 20, 200, "备注4"));
        double total = 650;
        String startTime = "2014/12/15";
        String endTime = "2014/12/16";
        boolean valid = false;
        SpecialOfferPO po = new SpecialOfferPO(id, commodityList, total, startTime, endTime, valid);
        impl.insert(po);
        assertEquals(3, impl.show().size());
        po = impl.show().get(2);
        assertEquals(id, po.getId());
        assertEquals(commodityList.get(0).getId(), po.getCommodityList().get(0).getId());
        assertEquals(commodityList.get(0).getNumber(), po.getCommodityList().get(0).getNumber());
        assertEquals(commodityList.get(0).getModel(), po.getCommodityList().get(0).getModel());
        assertEquals(commodityList.get(0).getName(), po.getCommodityList().get(0).getName());
        assertEquals(commodityList.get(0).getPrice(), po.getCommodityList().get(0).getPrice());
        assertEquals(commodityList.get(0).getRemark(), po.getCommodityList().get(0).getRemark());
        assertEquals(commodityList.get(0).getTotal(), po.getCommodityList().get(0).getTotal());
        assertEquals(total, po.getTotal());
        assertEquals(startTime, po.getStartTime());
        assertEquals(endTime, po.getEndTime());
        assertEquals(valid, po.isValid());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个促销策略
        String id = "CX00001";
        ArrayList<CommodityLineItemPO> commodityList = new ArrayList<CommodityLineItemPO>();
        commodityList.add(new CommodityLineItemPO("000001", "名称01", "型号01", 500, 100, 50000, "备注01"));
        commodityList.add(new CommodityLineItemPO("000002", "名称02", "型号02", 100, 200, 20000, "备注02"));
        double total = 70000;
        String startTime = "2014/12/14";
        String endTime = "2014/12/15";
        boolean valid = false;
        SpecialOfferPO po = new SpecialOfferPO(id, commodityList, total, startTime, endTime, valid);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(commodityList.get(0).getId(), po.getCommodityList().get(0).getId());
        assertEquals(commodityList.get(0).getNumber(), po.getCommodityList().get(0).getNumber());
        assertEquals(commodityList.get(0).getModel(), po.getCommodityList().get(0).getModel());
        assertEquals(commodityList.get(0).getName(), po.getCommodityList().get(0).getName());
        assertEquals(commodityList.get(0).getPrice(), po.getCommodityList().get(0).getPrice());
        assertEquals(commodityList.get(0).getRemark(), po.getCommodityList().get(0).getRemark());
        assertEquals(commodityList.get(0).getTotal(), po.getCommodityList().get(0).getTotal());
        assertEquals(total, po.getTotal());
        assertEquals(startTime, po.getStartTime());
        assertEquals(endTime, po.getEndTime());
        assertEquals(valid, po.isValid());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<SpecialOfferPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("CX00001", list.get(0).getId());

    }

    @Test
    public void testFindByValid() throws RemoteException {
        init();

        ArrayList<SpecialOfferPO> list = impl.findByValid(true);
        assertEquals(1, list.size());
        assertEquals("CX00001", list.get(0).getId());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        SpecialOfferPO po = impl.getById("CX00001");
        assertEquals("CX00001", po.getId());

        po = impl.getById("CX0000001");
        assertNull(po);
    }

}

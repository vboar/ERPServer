package test;

import data.commoditydata.CommodityDataServiceImpl;
import data.dataioutility.DataIOUtility;
import dataservice.commoditydataservice.CommodityDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CommodityPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * CommodityDataService的JUnit测试用例
 * Created by Vboar on 2014/12/12.
 */
public class TestCommodity extends TestCase {

    private CommodityDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("commodity").clearData("commodity");
        impl = new CommodityDataServiceImpl();
        // 添加三个商品
        String id = "00001-00001-00001";
        String name = "飞利浦日光灯";
        String model = "R001";
        int number = 0;
        double purchasePrice = 50;
        double salePrice = 80;
        double recentPurchasePrice = 50;
        double recentSalePrice = 80;
        int warningNumber = 30;
        boolean isTrade = false;
        CommodityPO po = new CommodityPO(id, name, model, number, purchasePrice, salePrice,
                recentPurchasePrice, recentSalePrice, warningNumber, isTrade);
        impl.insert(po);

        id = "00001-00001-00002";
        name = "飞利浦日光灯";
        model = "R002";
        number = 0;
        purchasePrice = 70;
        salePrice = 100;
        recentPurchasePrice = 70;
        recentSalePrice = 100;
        warningNumber = 50;
        isTrade = false;
        po = new CommodityPO(id, name, model, number, purchasePrice, salePrice,
                recentPurchasePrice, recentSalePrice, warningNumber, isTrade);
        impl.insert(po);

        id = "00001-00002-00001";
        name = "索尼日光灯";
        model = "S001";
        number = 0;
        purchasePrice = 10;
        salePrice = 30;
        recentPurchasePrice = 10;
        recentSalePrice = 30;
        warningNumber = 100;
        isTrade = false;
        po = new CommodityPO(id, name, model, number, purchasePrice, salePrice,
                recentPurchasePrice, recentSalePrice, warningNumber, isTrade);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个商品
        String id = "00001-00001-00003";
        String name = "飞利浦日光灯";
        String model = "R003";
        int number = 0;
        double purchasePrice = 50;
        double salePrice = 80;
        double recentPurchasePrice = 50;
        double recentSalePrice = 80;
        int warningNumber = 30;
        boolean isTrade = false;
        CommodityPO po = new CommodityPO(id, name, model, number, purchasePrice, salePrice,
                recentPurchasePrice, recentSalePrice, warningNumber, isTrade);
        impl.insert(po);
        CommodityPO temp = impl.show().get(3);
        assertEquals(id, temp.getId());
        assertEquals(name, temp.getName());
        assertEquals(model, temp.getModel());
        assertEquals(number, temp.getNumber());
        assertEquals(purchasePrice, temp.getPurchasePrice());
        assertEquals(salePrice, temp.getSalePrice());
        assertEquals(recentPurchasePrice, temp.getRecentPurchasePrice());
        assertEquals(recentSalePrice, temp.getRecentSalePrice());
        assertEquals(warningNumber, temp.getWarningNumber());
        assertEquals(isTrade, temp.isTrade());
        init();
    }

    @Test
    public void testDelete() throws RemoteException {
        init();
        // 删除第三个商品
        String id = "00001-00002-00001";
        CommodityPO po = new CommodityPO(id, null, null, 0, 0, 0, 0, 0, 0, false);
        impl.delete(po);
        ArrayList<CommodityPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("00001-00001-00001", list.get(0).getId());
        assertEquals("00001-00001-00002", list.get(1).getId());
        // 删除第一个商品
        id = "00001-00001-00001";
        po = new CommodityPO(id, null, null, 0, 0, 0, 0, 0, 0, false);
        impl.delete(po);
        list = impl.show();
        assertEquals(1, list.size());
        assertEquals("00001-00001-00002", list.get(0).getId());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个商品
        String id = "00001-00001-00001";
        String name = "飞利浦的日光灯";
        String model = "R0010";
        int number = 50;
        double purchasePrice = 55;
        double salePrice = 88;
        double recentPurchasePrice = 60;
        double recentSalePrice = 90;
        int warningNumber = 40;
        boolean isTrade = true;
        CommodityPO po = new CommodityPO(id, name, model, number, purchasePrice, salePrice,
                recentPurchasePrice, recentSalePrice, warningNumber, isTrade);
        impl.update(po);
        CommodityPO temp = impl.show().get(0);
        assertEquals(id, temp.getId());
        assertEquals(name, temp.getName());
        assertEquals(model, temp.getModel());
        assertEquals(number, temp.getNumber());
        assertEquals(purchasePrice, temp.getPurchasePrice());
        assertEquals(salePrice, temp.getSalePrice());
        assertEquals(recentPurchasePrice, temp.getRecentPurchasePrice());
        assertEquals(recentSalePrice, temp.getRecentSalePrice());
        assertEquals(warningNumber, temp.getWarningNumber());
        assertEquals(isTrade, temp.isTrade());
        init();
    }

    @Test
    public void testFindById() throws RemoteException {
        init();
        // "00001-00001" -2
        ArrayList<CommodityPO> list = impl.findById("00001-00001");
        assertEquals(2, list.size());
        assertEquals("00001-00001-00001", list.get(0).getId());
        assertEquals("00001-00001-00002", list.get(1).getId());
        // "00001-00002-00002" -0
        list = impl.findById("00001-00002-00002");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByName() throws RemoteException {
        init();
        // "日光灯" -3
        ArrayList<CommodityPO> list = impl.findByName("日光灯");
        assertEquals(3, list.size());
        assertEquals("飞利浦日光灯", list.get(0).getName());
        assertEquals("飞利浦日光灯", list.get(1).getName());
        assertEquals("索尼日光灯", list.get(2).getName());
        // "吊灯" -0
        list = impl.findByName("吊灯");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByModel() throws RemoteException {
        init();
        // "R00" -2
        ArrayList<CommodityPO> list = impl.findByModel("R00");
        assertEquals(2, list.size());
        assertEquals("00001-00001-00001", list.get(0).getId());
        assertEquals("00001-00001-00002", list.get(1).getId());
        // "R11" -0
        list = impl.findByModel("R11");
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();
        // "00001-00002-00001"
        String id = "00001-00002-00001";
        CommodityPO po = impl.getById(id);
        assertEquals(id, po.getId());
        // "00001-00002-00005"
        id = "00001-00002-00005";
        po = impl.getById(id);
        assertNull(po);
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        ArrayList<CommodityPO> list = impl.show();
        assertEquals(3, list.size());
    }

    @Test
    public void testShowByInitial() throws RemoteException {
        // TODO
    }

}

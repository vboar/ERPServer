package test;

import data.dataioutility.DataIOUtility;
import data.stockdata.StockDataServiceImpl;
import dataservice.saledataservice.SaleDataService;
import dataservice.stockdataservice.StockDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.StockPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * StockDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestStock extends TestCase {

    private StockDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("stock").clearData("stock");
        impl = new StockDataServiceImpl();
        // 增加两个库存快照
        String commodityId = "00001";
        String commodityName = "名称1";
        String commodityModel = "型号1";
        int number = 50;
        double avgPrice = 20;
        String batch = "2014/10/10";
        String batchNumber = "00001";
        StockPO po = new StockPO(commodityId, commodityName, commodityModel, number, avgPrice, batch, batchNumber);
        impl.insert(po);

        commodityId = "00002";
        commodityName = "名称2";
        commodityModel = "型号2";
        number = 40;
        avgPrice = 10;
        batch = "2014/10/15";
        batchNumber = "00002";
        po = new StockPO(commodityId, commodityName, commodityModel, number, avgPrice, batch, batchNumber);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 增加一个库存快照
        String commodityId = "00003";
        String commodityName = "名称3";
        String commodityModel = "型号3";
        int number = 30;
        double avgPrice = 15;
        String batch = "2014/12/10";
        String batchNumber = "00003";
        StockPO po = new StockPO(commodityId, commodityName, commodityModel, number, avgPrice, batch, batchNumber);
        impl.insert(po);
        po = impl.show().get(2);
        assertEquals(3, impl.show().size());
        assertEquals(commodityId, po.getCommodityId());
        assertEquals(commodityModel, po.getCommodityModel());
        assertEquals(commodityName, po.getCommodityName());
        assertEquals(number, po.getNumber());
        assertEquals(avgPrice, po.getAvgPrice());
        assertEquals(batch, po.getBatch());
        assertEquals(batchNumber, po.getBatchNumber());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<StockPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("00001", list.get(0).getCommodityId());
    }

    @Test
    public void testFindByDate() throws RemoteException {
        init();

        ArrayList<StockPO> list = impl.findByDate("2014/10/15", null);
        assertEquals(1, list.size());
        assertEquals("00002", list.get(0).getCommodityId());
    }

}

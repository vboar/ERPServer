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
                documentType, isWriteoff);
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
                documentType, isWriteoff);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个赠送单

    }

}

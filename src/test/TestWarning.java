package test;

import data.dataioutility.DataIOUtility;
import data.exceptiondata.WarningDataServiceImpl;
import dataservice.exceptiondataservice.WarningDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.WarningLineItemPO;
import po.WarningPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * WarningDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestWarning extends TestCase {

    private WarningDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("warning").clearData("warning");
        impl = new WarningDataServiceImpl();
        // 添加三个报警单
        String id = "BJD-20141212-00001";
        String time = "2014/12/12/10/10/10";
        int documentType = 3;
        ArrayList<WarningLineItemPO> list = new ArrayList<WarningLineItemPO>();
        list.add(new WarningLineItemPO("00001-00001", "百度吊灯", "R0001", 50, 60));
        list.add(new WarningLineItemPO("00001-00002", "淘宝吊灯", "R0002", 40, 50));
        WarningPO po = new WarningPO(id, time, list, documentType);
        impl.insert(po);

        id = "BJD-20141212-00002";
        time = "2014/12/12/10/15/10";
        documentType = 3;
        list = new ArrayList<WarningLineItemPO>();
        list.add(new WarningLineItemPO("00001-00001", "百度吊灯", "R0001", 40, 80));
        list.add(new WarningLineItemPO("00001-00002", "淘宝吊灯", "R0002", 50, 90));
        po = new WarningPO(id, time, list, documentType);
        impl.insert(po);

        id = "BJD-20141213-00001";
        time = "2014/12/13/10/15/10";
        documentType = 3;
        list = new ArrayList<WarningLineItemPO>();
        list.add(new WarningLineItemPO("00001-00001", "百度吊灯", "R0001", 40, 80));
        list.add(new WarningLineItemPO("00001-00002", "淘宝吊灯", "R0002", 50, 90));
        po = new WarningPO(id, time, list, documentType);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 增加一个报警单
        String id = "BJD-20141213-00004";
        String time = "2014/12/13/10/15/10";
        int documentType = 3;
        ArrayList<WarningLineItemPO> list = new ArrayList<WarningLineItemPO>();
        list.add(new WarningLineItemPO("00001-00001", "百度吊灯", "R0001", 50, 60));
        list.add(new WarningLineItemPO("00001-00002", "淘宝吊灯", "R0002", 40, 50));
        WarningPO po = new WarningPO(id, time, list, documentType);
        impl.insert(po);
        assertEquals(4, impl.show("1970/01/01", "2014/12/14").size());
        po = impl.show("1970/01/01", "2014/12/14").get(3);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(documentType, po.getDocumentType());
        assertEquals(2, po.getList().size());
        assertEquals("00001-00001", po.getList().get(0).getId());
        assertEquals("百度吊灯", po.getList().get(0).getName());
        assertEquals("R0001", po.getList().get(0).getModel());
        assertEquals(50, po.getList().get(0).getStockNumber());
        assertEquals(60, po.getList().get(0).getWarningNumber());
        init();
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        // "all"
        ArrayList<WarningPO> list = impl.show("1970/01/01", "2014/12/14");
        assertEquals(3, list.size());
        // "2014/12/12"
        list = impl.show("2014/12/12", "2014/12/13");
        assertEquals(2, list.size());
        assertEquals("BJD-20141212-00002", list.get(1).getId());
        list = impl.show("2014/12/12/09/01/01", "2014/12/12/11/01/01");
        assertEquals(2, list.size());
        assertEquals("BJD-20141212-00002", list.get(1).getId());
        // "2014/12/10" "2014/12/11"
        list = impl.show("2014/12/10", "2014/12/11");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindById() throws RemoteException {
        init();
        // BJD-20141212
        ArrayList<WarningPO> list = impl.findById("BJD-20141212");
        assertEquals(2, list.size());
        assertEquals("BJD-20141212-00002", list.get(1).getId());
        // BJD-20141212-00001
        list = impl.findById("BJD-20141212-00001");
        assertEquals(1, list.size());
        assertEquals("BJD-20141212-00001", list.get(0).getId());
        // BJD-20141212-00005
        list = impl.findById("BJD-20141212-00005");
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();
        // BJD-20141212-00001
        WarningPO po = impl.getById("BJD-20141212-00001");
        assertEquals("BJD-20141212-00001", po.getId());
        // BJD-20141212
        po = impl.getById("BJD-20141212");
        assertNull(po);
        // BJD-20141212-00005
        po = impl.getById("BJD-20141212-00005");
        assertNull(po);
    }
}

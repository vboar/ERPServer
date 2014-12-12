package test;

import data.commoditydata.CategoryDataServiceImpl;
import data.dataioutility.DataIOUtility;
import dataservice.commoditydataservice.CategoryDataService;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import po.CategoryPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * CategoryDataService的JUnit测试用例
 * Created by Vboar on 2014/12/11.
 */
public class TestCategory extends TestCase {

    private CategoryDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("account").clearData("category");
        impl = new CategoryDataServiceImpl();
        // 添加三个分类
        String id = "00001";
        String name = "吊灯";
        int number = 0;
        CategoryPO po = new CategoryPO(id, name, number);
        impl.insert(po);
        id = "00001-00001";
        name = "飞利浦吊灯";
        number = 0;
        po = new CategoryPO(id, name, number);
        impl.insert(po);
        id = "00002";
        name = "台灯";
        number = 0;
        po = new CategoryPO(id, name, number);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        impl = new CategoryDataServiceImpl();
        // 添加一个分类
        String id = "00001-00002";
        String name = "雅格吊灯";
        int number = 0;
        CategoryPO po = new CategoryPO(id, name, number);
        impl.insert(po);
        CategoryPO temp = impl.show().get(3);
        assertEquals(id, temp.getId());
        assertEquals(name, temp.getName());
        assertEquals(number, temp.getNumber());
    }

    @Test
    public void testDelete() throws RemoteException {
        init();
        impl = new CategoryDataServiceImpl();
        // 删除第二个分类
        String id = "00001-00001";
        String name = "飞利浦吊灯";
        int number = 0;
        CategoryPO po = new CategoryPO(id, name, number);
        impl.delete(po);
        ArrayList<CategoryPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("00002", list.get(1).getId());
        // 再删除第二个分类
        id = "00002";
        name = "台灯";
        number = 0;
        po = new CategoryPO(id, name, number);
        impl.delete(po);
        list = impl.show();
        assertEquals(1, list.size());
        assertEquals("00001", list.get(0).getId());
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        impl = new CategoryDataServiceImpl();
        // 更新第三个分类
        String id = "00002";
        String name = "飞利浦日光灯";
        int number = 5;
        CategoryPO po = new CategoryPO(id, name, number);
        impl.update(po);
        CategoryPO temp = impl.show().get(2);
        assertEquals(id , temp.getId());
        assertEquals(name, temp.getName());
        assertEquals(number, temp.getNumber());
    }

    @Test
    public void testFindById() throws RemoteException {
        init();
        impl = new CategoryDataServiceImpl();
        // "00001" -2
        ArrayList<CategoryPO> list = impl.findById("00001");
        assertEquals(2, list.size());
        assertEquals("00001-00001", list.get(1).getId());
        assertEquals("飞利浦吊灯", list.get(1).getName());
        // "00003" -0
        list = impl.findById("00003");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByName() throws RemoteException {
        init();
        impl = new CategoryDataServiceImpl();
        // "吊灯" -2
        ArrayList<CategoryPO> list = impl.findByName("吊灯");
        assertEquals(2, list.size());
        assertEquals("00001-00001", list.get(1).getId());
        assertEquals("飞利浦吊灯", list.get(1).getName());
        // "日光灯" -0
        list = impl.findByName("日光灯");
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();
        impl = new CategoryDataServiceImpl();
        // "00001"
        CategoryPO po = impl.getById("00001");
        assertEquals("00001", po.getId());
        assertEquals("吊灯", po.getName());
        // "00003"
        po = impl.getById("00003");
        assertNull(po);
    }

    @After
    public void finish() throws RemoteException {
        init();
    }

}

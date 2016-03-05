package test;

import data.customerdata.CustomerDataServiceImpl;
import data.dataioutility.DataIOUtility;
import dataservice.customerdataservice.CustomerDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.CustomerPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * CustomerDataService的JUnit测试用例
 * Created by Vboar on 2014/12/12.
 */
public class TestCustomer extends TestCase {

    private CustomerDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("customer").clearData("customer");
        impl = new CustomerDataServiceImpl();
        // 添加三个客户
        String id = "KH00001";
        int category = 1;
        int level = 1;
        String name = "万达";
        String phoneNumber = "88888888";
        String address = "南京新街口5号";
        String postalCode = "520000";
        String email = "wanda@wanda.com";
        double creditLimit = 5000;
        double receivables = 10;
        double paybles = 20;
        String salesman = "00001";
        boolean isDeletable = true;
        CustomerPO po = new CustomerPO(id, category, level, name, phoneNumber, address,
                postalCode, email, creditLimit, receivables, paybles, salesman, isDeletable);
        impl.insert(po);

        id = "KH00002";
        category = 0;
        level = 3;
        name = "阿里";
        phoneNumber = "99999999";
        address = "上海南京路8号";
        postalCode = "100000";
        email = "alibaba@alibaba.com";
        creditLimit = 1000;
        receivables = 0;
        paybles = 0;
        salesman = "00002";
        isDeletable = true;
        po = new CustomerPO(id, category, level, name, phoneNumber, address,
                postalCode, email, creditLimit, receivables, paybles, salesman, isDeletable);
        impl.insert(po);

        id = "KH00003";
        category = 1;
        level = 3;
        name = "百度";
        phoneNumber = "11112222";
        address = "北京天安门";
        postalCode = "120000";
        email = "1@baidu.com";
        creditLimit = 10000;
        receivables = 500;
        paybles = 0;
        salesman = "00001";
        isDeletable = true;
        po = new CustomerPO(id, category, level, name, phoneNumber, address,
                postalCode, email, creditLimit, receivables, paybles, salesman, isDeletable);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 添加一个客户
        String id = "KH00010";
        int category = 1;
        int level = 1;
        String name = "天猫";
        String phoneNumber = "11112525";
        String address = "东莞世博广场";
        String postalCode = "523000";
        String email = "1@tmall.com";
        double creditLimit = 5000;
        double receivables = 10;
        double paybles = 20;
        String salesman = "00003";
        boolean isDeletable = true;
        CustomerPO po = new CustomerPO(id, category, level, name, phoneNumber, address,
                postalCode, email, creditLimit, receivables, paybles, salesman, isDeletable);
        impl.insert(po);
        assertEquals(4, impl.show().size());
        po = impl.show().get(3);
        assertEquals(id, po.getId());
        assertEquals(category, po.getCategory());
        assertEquals(level, po.getLevel());
        assertEquals(name, po.getName());
        assertEquals(phoneNumber, po.getPhoneNumber());
        assertEquals(address, po.getAddress());
        assertEquals(postalCode, po.getPostalCode());
        assertEquals(email, po.getEmail());
        assertEquals(creditLimit, po.getCreditLimit());
        assertEquals(receivables, po.getReceivables());
        assertEquals(paybles, po.getPaybles());
        assertEquals(salesman, po.getSalesman());
        assertEquals(isDeletable, po.isDeletable());
        init();
    }

    @Test
    public void testDelete() throws RemoteException {
        init();
        // 删除第一个客户
        String id = "KH00001";
        CustomerPO po = new CustomerPO(id, 0, 0, null, null, null, null, null,
                0, 0, 0, null, false);
        impl.delete(po);
        ArrayList<CustomerPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("KH00002", list.get(0).getId());
        assertEquals("KH00003", list.get(1).getId());
        // 删除第二个客户
        id = "KH00003";
        po = new CustomerPO(id, 0, 0, null, null, null, null, null,
                0, 0, 0, null, false);
        impl.delete(po);
        list = impl.show();
        assertEquals(1, list.size());
        assertEquals("KH00002", list.get(0).getId());
        init();
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个客户
        String id = "KH00001";
        int category = 0;
        int level = 2;
        String name = "小万达";
        String phoneNumber = "88888885";
        String address = "南京新街口8号";
        String postalCode = "520003";
        String email = "wandada@wanda.com";
        double creditLimit = 50000;
        double receivables = 10000;
        double paybles = 20000;
        String salesman = "00002";
        boolean isDeletable = false;
        CustomerPO po = new CustomerPO(id, category, level, name, phoneNumber, address,
                postalCode, email, creditLimit, receivables, paybles, salesman, isDeletable);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(category, po.getCategory());
        assertEquals(level, po.getLevel());
        assertEquals(name, po.getName());
        assertEquals(phoneNumber, po.getPhoneNumber());
        assertEquals(address, po.getAddress());
        assertEquals(postalCode, po.getPostalCode());
        assertEquals(email, po.getEmail());
        assertEquals(creditLimit, po.getCreditLimit());
        assertEquals(receivables, po.getReceivables());
        assertEquals(paybles, po.getPaybles());
        assertEquals(salesman, po.getSalesman());
        assertEquals(isDeletable, po.isDeletable());
        init();
    }

    @Test
    public void testFindByName() throws RemoteException {
        init();
        // "万达" -1
        ArrayList<CustomerPO> list = impl.findByName("万达");
        assertEquals(1, list.size());
        assertEquals("KH00001", list.get(0).getId());
        // "腾讯" -0
        list = impl.findByName("腾讯");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindById() throws RemoteException {
        init();
        // "H0000" -3
        ArrayList<CustomerPO> list = impl.findById("H0000");
        assertEquals(3, list.size());
        assertEquals("KH00001", list.get(0).getId());
        assertEquals("KH00002", list.get(1).getId());
        assertEquals("KH00003", list.get(2).getId());
        // "00004" -0
        list = impl.findById("00004");
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();
        // "KH00003"
        CustomerPO po = impl.getById("KH00003");
        assertEquals("KH00003", po.getId());
        // "KH000035"
        po = impl.getById("KH000035");
        assertNull(po);
    }

    @Test
    public void testShow() throws RemoteException {
        init();
        ArrayList<CustomerPO> list = impl.show();
        assertEquals(3, list.size());
        assertEquals("KH00001", list.get(0).getId());
        assertEquals("KH00002", list.get(1).getId());
        assertEquals("KH00003", list.get(2).getId());
    }

    @Test
    public void showByInitial() throws RemoteException {
        init();
        assertEquals(true, true);
    }

}

package test;

import data.dataioutility.DataIOUtility;
import data.userdata.UserDataServiceImpl;
import dataservice.userdataservice.UserDataService;
import junit.framework.TestCase;
import org.junit.Test;
import po.UserPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * UserDataService的JUnit测试用例
 * Created by Vboar on 2014/12/15.
 */
public class TestUser extends TestCase {

    private UserDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("user").clearData("user");
        impl = new UserDataServiceImpl();
        // 增加两个用户
        String id = "00001";
        String password = "123456";
        int type = 2;
        int permission = 1;
        String name = "大佬";
        UserPO po = new UserPO(id, password, type, permission, name);
        impl.insert(po);

        id = "00002";
        password = "1234567";
        type = 4;
        permission = 0;
        name = "小白";
        po = new UserPO(id, password, type, permission, name);
        impl.insert(po);
    }

    @Test
    public void testInsert() throws RemoteException {
        init();
        // 增加一个用户
        String id = "00005";
        String password = "123455";
        int type = 1;
        int permission = 2;
        String name = "大佬1";
        UserPO po = new UserPO(id, password, type, permission, name);
        impl.insert(po);
        po = impl.show().get(2);
        assertEquals(3, impl.show().size());
        assertEquals(id, po.getId());
        assertEquals(name, po.getName());
        assertEquals(password, po.getPassword());
        assertEquals(type, po.getType());
        assertEquals(permission, po.getPermission());
        init();
    }

    @Test
    public void testDelete() throws RemoteException {
        init();
        // 删除第一个用户
        String id = "00001";
        UserPO po = new UserPO(id, null, 0, 0, null);
        impl.delete(po);
        assertEquals(1, impl.show().size());
        assertEquals("00002", impl.show().get(0).getId());
    }

    @Test
    public void testUpdate() throws RemoteException {
        init();
        // 更新第一个用户
        String id = "00001";
        String password = "12345556";
        int type = 1;
        int permission = 0;
        String name = "大111";
        UserPO po = new UserPO(id, password, type, permission, name);
        impl.update(po);
        po = impl.show().get(0);
        assertEquals(id, po.getId());
        assertEquals(name, po.getName());
        assertEquals(password, po.getPassword());
        assertEquals(type, po.getType());
        assertEquals(permission, po.getPermission());
        init();
    }

    @Test
    public void testFindById() throws RemoteException {
        init();

        ArrayList<UserPO> list = impl.findById("00001");
        assertEquals(1, list.size());
        assertEquals("00001", list.get(0).getId());

        list = impl.findById("000011");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByName() throws RemoteException {
        init();

        ArrayList<UserPO> list = impl.findByName("大佬");
        assertEquals(1, list.size());
        assertEquals("00001", list.get(0).getId());

        list = impl.findByName("大佬1");
        assertEquals(0, list.size());
    }

    @Test
    public void testFindByType() throws RemoteException {
        init();

        ArrayList<UserPO> list = impl.findByType(2);
        assertEquals(1, list.size());
        assertEquals("00001", list.get(0).getId());

        list = impl.findByType(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetById() throws RemoteException {
        init();

        UserPO po = impl.getById("00001");
        assertEquals("00001", po.getId());

        po = impl.getById("000010");
        assertNull(po);
    }

    @Test
    public void testShow() throws RemoteException {
        init();

        ArrayList<UserPO> list = impl.show();
        assertEquals(2, list.size());
        assertEquals("00001", list.get(0).getId());
    }



}

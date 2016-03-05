package test;

import data.dataioutility.DataIOUtility;
import data.messagedata.MessageDataServiceImpl;
import dataservice.messagedataservice.MessageDataService;
import junit.framework.TestCase;
import po.MessagePO;
import po.UserPO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * MessageDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestMessage extends TestCase {

    private MessageDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("message").clearData("message");
        impl = new MessageDataServiceImpl();
        // 添加三条信息
        String id = "MSG-2014/12/12-00001";
        String time = "2014/12/12/12/12/12";
        int state = 0;
        String receiverId = "00001";
        String content = "消息1";
        MessagePO po = new MessagePO(id, time, state, receiverId, content);
        impl.insert(po);

        id = "MSG-2014/12/12-00002";
        time = "2014/12/12/12/15/12";
        state = 1;
        receiverId = "00002";
        content = "消息2";
        po = new MessagePO(id, time, state, receiverId, content);
        impl.insert(po);

        id = "MSG-2014/12/13-00001";
        time = "2014/12/13/12/16/12";
        state = 0;
        receiverId = "00003";
        content = "消息3";
        po = new MessagePO(id, time, state, receiverId, content);
        impl.insert(po);
    }

    public void testInsert() throws RemoteException {
        init();
        // 增加一条信息
        String id = "MSG-2014/12/13-00002";
        String time = "2014/12/13/12/12/19";
        int state = 0;
        String receiverId = "00001";
        String content = "消息4";
        MessagePO po = new MessagePO(id, time, state, receiverId, content);
        impl.insert(po);
        ArrayList<MessagePO> list = impl.showByUser(new UserPO("00001", null, 0, 0, null));
        po = list.get(1);
        assertEquals("MSG-2014/12/13-00002", po.getId());
        assertEquals("2014/12/13/12/12/19", po.getTime());
        assertEquals(0, po.getState());
        assertEquals("00001", po.getReceiverId());
        assertEquals("消息4", po.getContent());
        init();
    }

    public void testDelete() throws RemoteException {
        init();
        // 删除第一条消息
        String id = "MSG-2014/12/12-00001";
        String time = "2014/12/12/12/12/12";
        int state = 0;
        String receiverId = "00001";
        String content = "消息1";
        MessagePO po = new MessagePO(id, time, state, receiverId, content);
        impl.delete(po);
        ArrayList<MessagePO> list = impl.showByUser(new UserPO("00001", null, 0, 0, null));
        assertEquals(0, list.size());
        init();
    }

    public void testUpdate() throws RemoteException {
        init();
        // 更新第一条消息
        String id = "MSG-2014/12/12-00001";
        String time = "2014/12/12/12/12/15";
        int state = 1;
        String receiverId = "00001";
        String content = "消息5";
        MessagePO po = new MessagePO(id, time, state, receiverId, content);
        impl.update(po);
        po = impl.showByUser(new UserPO("00001", null, 0, 0, null)).get(0);
        assertEquals(id, po.getId());
        assertEquals(time, po.getTime());
        assertEquals(state, po.getState());
        assertEquals(receiverId, po.getReceiverId());
        assertEquals(content, po.getContent());
        init();
    }

    public void testShowByUser() throws RemoteException {
        init();

        ArrayList<MessagePO> list = impl.showByUser(new UserPO("00003", null, 0, 0, null));
        assertEquals(1, list.size());
        assertEquals("MSG-2014/12/13-00001", list.get(0).getId());

        list = impl.showByUser(new UserPO("00005", null, 0, 0, null));
        assertEquals(0, list.size());
    }

}

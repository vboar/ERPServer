package test;

import data.dataioutility.DataIOUtility;
import data.messagedata.MessageDataServiceImpl;
import dataservice.messagedataservice.MessageDataService;
import junit.framework.TestCase;
import po.MessagePO;

import java.rmi.RemoteException;

/**
 * MessageDataService的JUnit测试用例
 * Created by Vboar on 2014/12/13.
 */
public class TestMessage extends TestCase {

    private MessageDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("account").clearData("account");
        impl = new MessageDataServiceImpl();
        // 添加三条信息
        String id = "MSG-2014/12/12-00001";
        String time = "2014/12/12/12/12/12";
        int state = 0;
        String receiverId = "00001";
        String content = "消息1";
        MessagePO po = new MessagePO(id, time, state, receiverId, content);

        id = "MSG-2014/12/12-00002";
        time = "2014/12/12/12/15/12";
        state = 0;
        receiverId = "00001";
        content = "消息1";
        MessagePO po = new MessagePO(id, time, state, receiverId, content);
    }

}

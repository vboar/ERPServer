package test;

import data.dataioutility.DataIOUtility;
import dataservice.commoditydataservice.CategoryDataService;
import junit.framework.TestCase;
import po.CategoryPO;

import java.rmi.RemoteException;

/**
 * CategoryDataService的JUnit测试用例
 * Created by Vboar on 2014/12/11.
 */
public class TestCategory extends TestCase {

    private CategoryDataService impl;

    public void init() throws RemoteException {
        new DataIOUtility("account").clearData("category");
        // 添加三个目录
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

}

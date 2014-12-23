package ui;

import data.datafactory.DataFactoryImpl;
import dataservice.datafactoryservice.DataFactory;
import util.CheckItSelf;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 快速启动服务端
 * Created by Vboar on 2014/12/1.
 */
public class QuickStart {

    public static void main(String[] args) {
        try {
            // 启动默认端口8888
            LocateRegistry.createRegistry(8888);
            DataFactory dataFactory = new DataFactoryImpl();
            Naming.rebind("rmi://127.0.0.1:8888/DataFactory", dataFactory);
            new CheckItSelf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

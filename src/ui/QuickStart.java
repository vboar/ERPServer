package ui;

import data.datafactory.DataFactoryImpl;
import dataservice.datafactoryservice.DataFactory;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 快速启动服务端
 * Created by Vboar on 2014/12/1.
 */
public class QuickStart {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8888);
            DataFactory dataFactory = new DataFactoryImpl();
            Naming.rebind("rmi://127.0.0.1:8888/DataFactory", dataFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

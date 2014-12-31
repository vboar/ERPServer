package ui;

import data.datafactory.DataFactoryImpl;
import dataservice.datafactoryservice.DataFactory;
import util.CheckItSelf;

import java.net.InetAddress;
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
            DataFactory dataFactory = DataFactoryImpl.getInstance();
            String address = InetAddress.getLocalHost().getHostAddress();
            Naming.rebind("rmi://" + address + ":8888/DataFactory", dataFactory);
            System.out.println("==============================");
            System.out.println("本服务器回送地址为：127.0.0.1，端口为：8888");
            System.out.println("本服务器内网地址为：" + address + "，端口为：8888");
            System.out.println("==============================\n");
            new CheckItSelf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

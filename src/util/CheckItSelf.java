package util;

import data.datafactory.DataFactoryImpl;
import po.CustomerGiftPO;
import po.SpecialOfferPO;
import po.TotalGiftPO;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 服务器自查数据
 * Created by Vboar on 2014/12/22.
 */
public class CheckItSelf {

    public CheckItSelf() {
        // 自查内部类线程启动
        new CheckThread().start();
    }

    /**
     * 自查内部类线程
     */
    private class CheckThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {

                    // 检查并更改促销策略状态
                    checkPromotion();

                    // 每100秒检查一次
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * 检查并更改促销策略状态
         * 如果促销策略过期了，把它设为失效状态
         */
        private void checkPromotion() {
            try {
                String time = CheckItSelf.getCurrentTime();
                ArrayList<CustomerGiftPO> list1 = DataFactoryImpl.getInstance().
                        getCustomerGiftData().show();
                for(CustomerGiftPO po: list1) {
                    if(time.compareTo(po.getEndTime()) > 0) {
                        po.setValid(false);
                        DataFactoryImpl.getInstance().getCustomerGiftData().update(po);
                    }
                }
                ArrayList<TotalGiftPO> list2 = DataFactoryImpl.getInstance().getTotalGiftData().show();
                for(TotalGiftPO po: list2) {
                    if(time.compareTo(po.getEndTime()) > 0) {
                        po.setValid(false);
                        DataFactoryImpl.getInstance().getTotalGiftData().update(po);
                    }
                }
                ArrayList<SpecialOfferPO> list3 = DataFactoryImpl.getInstance().getSpecialOfferData().show();
                for(SpecialOfferPO po: list3) {
                    if(time.compareTo(po.getEndTime()) > 0) {
                        po.setValid(false);
                        DataFactoryImpl.getInstance().getSpecialOfferData().show();
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得当前时间（标准格式）
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = df.format(new Date());
        return time;
    }
}

package com.junshijia.Alg;

import com.junshijia.Alg.domain.AlgData;
import com.junshijia.Alg.health.SendHealth;

public class Main {
    public static void main(String[] args) {
        while(true) {
            FetchAlgData f = new FetchAlgData();
            SendHealth s = new SendHealth();
            s.sendAllHealth();
            //用第一条数据测试
            AlgData2Json data1 = new AlgData2Json(f.getDatas().get(0));
            AlgData2Json data2 = new AlgData2Json(f.getDatas().get(1));
            AlgData2Json data3 = new AlgData2Json(f.getDatas().get(2));
            AlgData2Json data4 = new AlgData2Json(f.getDatas().get(3));
            AlgData2Json data5 = new AlgData2Json(f.getDatas().get(4));
            new Thread(data1).start();
            new Thread(data2).start();
            new Thread(data3).start();
            new Thread(data4).start();
            new Thread(data5).start();
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

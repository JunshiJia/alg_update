package com.junshijia.Alg;

import com.junshijia.Alg.domain.AlgData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AlgData2Json implements Runnable{
    //n号风机的算法资料
    private AlgData data;
    //算法总数
    private final int MAX = 34;
    //正在观察算法的编号
    private int entering;
    //data类的class
    private Class dataClz;
    //算法数字数组
    private int[] algList;
    //风机号码
    private String TId;
    public AlgData2Json() {
    }

    public void setAlgList() {
        this.algList = new int[]{1,2,3,4,5,6,7,8,9,10,11,
                12,13,14,15,16,17,18,19,20,21,60,61,62,
                63,64,65,66,67,68,69,70,71,72};
    }

    public int[] getAlgList() {
        return algList;
    }

    //观察并发送每一个风机的一个算法
    public AlgData2Json(AlgData data) {
        this.setAlgList();
        this.data = data;
        this.TId = data.getWtid();
        this.dataClz = data.getClass();

    }

    @Override
    public void run() {
        while(true) {
            this.traverseData();
            System.out.println("成功发出");
            try {
                Thread.sleep(1200000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void traverseData(){
        String id = "getID";
        String id_time = "_updatetime";
        String methodName1;
        String methodName2;
        int[] list = this.getAlgList();
        Integer algLevel;
        String algTime;

        for(int i = 0; i < list.length; i++){
            methodName1 = id+list[i];
            methodName2 = id+list[i]+id_time;
            try {
                Method method1 = dataClz.getMethod(methodName1);
                Method method2 = dataClz.getMethod(methodName2);
                algLevel = (Integer) method1.invoke(data);
                algTime = (String) method2.invoke(data);
                Util.sendData(algLevel,algTime,this.TId,list[i]);
                //System.out.println(method1.invoke(data));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

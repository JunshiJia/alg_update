package com.junshijia.Alg.health;

import com.alibaba.fastjson.JSONObject;
import com.junshijia.Alg.Util;

import java.util.Calendar;
import java.util.Random;

public class SendHealth {
    String[] partsNames;
    String[] ids;
    public SendHealth() {
        ids = new String[]{"WT1","WT2","WT3","WT4","WT5"};
        partsNames = new String[]{"叶片","变桨系统","电气系统","偏航系统","发电机机传动链","结构","整机综合评估"};
    }

    public void sendAllHealth(){
        for(String id : this.ids){
            for(String name : this.partsNames){
                this.sendHealthData(id,name);
            }
        }
    }


    private void sendHealthData(String id, String part){
        JSONObject paramJson = new JSONObject();
        paramJson.put("Query", "SubHealth");
        paramJson.put("SubHealthType", "PredictCountAdd");

        //更新参数json
        JSONObject subHealthInfo = new JSONObject();

        //支持同时更新多条记录
        //level
        Random ra =new Random();
        int levelNum = ra.nextInt(5)+93;

        subHealthInfo.put("Level", levelNum);

        //厂家
        subHealthInfo.put("Factory", "东方风电");
        //farm name
        subHealthInfo.put("FarmName", "内蒙古霍林河四期智慧风场");
        //farmCode
        subHealthInfo.put("FarmCode", "30000");

        //风机编码
        String WTGSCode = Util.id2WTGSCode(id);
        subHealthInfo.put("WTGSCode", WTGSCode);

        //locationCode = 风机号
        subHealthInfo.put("LocationCode", Util.id2WTNum(id));

        //算法模型id
        subHealthInfo.put("ModelId", "风机健康度");

        //模型名称
        subHealthInfo.put("ModelName", "无");

        //部件名称
        subHealthInfo.put("RelatedParts", part);

        //endTime
        Calendar calendar = Calendar.getInstance();
        String time = calendar.getTime().toString();
        subHealthInfo.put("EndTime", time);
        subHealthInfo.put("StartTime", time);

        //end
        paramJson.put("PredictCountInfo", subHealthInfo);

        //调用接口
        System.out.println("输入：" + paramJson.toJSONString());
        JSONObject jsonObject = Util.postAPIJSONResult("http://192.168.101.100:8081/service/", paramJson.toJSONString());
        System.out.println("输出："+jsonObject.toJSONString());
    }
}

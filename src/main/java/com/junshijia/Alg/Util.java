package com.junshijia.Alg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {
    public static void sendData(Integer algLevel, String algTime, String TId, int algId){
        if(algLevel!=null) {
            JSONObject paramJson = new JSONObject();
            paramJson.put("Query", "SubHealth");
            paramJson.put("SubHealthType", "PredictCountAdd");

            //更新参数json
            JSONObject subHealthInfo = new JSONObject();

            //支持同时更新多条记录
            //level
            if(algLevel>0) {
                subHealthInfo.put("Level", Util.id2NameAndPart(algId)[3]);
            }else{
                return;
            }
            if(Util.id2NameAndPart(algId)[2].equals("999")){
                return;
            }
            //厂家
            subHealthInfo.put("Factory", "东方风电");
            //farm name
            subHealthInfo.put("FarmName", "内蒙古霍林河四期智慧风场");
            //farmCode
            subHealthInfo.put("FarmCode", "30000");

            //风机编码
            String WTGSCode = Util.id2WTGSCode(TId);
            subHealthInfo.put("WTGSCode", WTGSCode);

            //locationCode = 风机号
            String locationString = TId;
            subHealthInfo.put("LocationCode", locationString);

            //算法模型id
            subHealthInfo.put("ModelId", Util.id2NameAndPart(algId)[2]);

            //模型名称
            subHealthInfo.put("ModelName", Util.id2NameAndPart(algId)[0]);

            //部件名称
            subHealthInfo.put("RelatedParts", Util.id2NameAndPart(algId)[1]);

            //endTime
            subHealthInfo.put("EndTime", algTime);
            subHealthInfo.put("StartTime", algTime);

            //end
            paramJson.put("PredictCountInfo", subHealthInfo);

            //调用接口
            System.out.println("输入：" + paramJson.toJSONString());
            JSONObject jsonObject = postAPIJSONResult("http://192.168.101.100:8081/service/", paramJson.toJSONString());
            System.out.println("输出："+jsonObject.toJSONString());
        }
    }

    public static JSONObject postAPIJSONResult(String url, String paramJson){
        //返回json结果
        JSONObject retJSON = null;
        try {
            String result = "";
            // post提交
            URL targetUrl = new URL(url);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setReadTimeout(30000);//设置超时时间30秒
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(paramJson.getBytes("UTF-8"));//增加参数UTF-8编码防止出现乱码
            outputStream.flush();
            outputStream.close();
            if (httpConnection.getResponseCode() != 200) {
                System.out.println("访问地址返回状态：" + httpConnection.getResponseCode());
            }
            // 返回值
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()),"UTF-8"));
            String res = "";
            StringBuilder sb = new StringBuilder("");
            while ((res = responseBuffer.readLine()) != null) {
                sb.append(res.trim());
            }
            responseBuffer.close();
            httpConnection.disconnect();
            result = sb.toString();
            if(StringUtils.isNotBlank(result)){
                retJSON = JSON.parseObject(result);
            }
        } catch (Exception e) {
            //logger.info("访问地址失败：" + url+" 参数："+paramJson);
            System.out.println("访问地址失败：" + url+" 参数："+paramJson);
            //logger.error(e.getMessage());
            System.out.println();
            e.printStackTrace();
        }
        return retJSON;
    }

    public static String[] id2NameAndPart(int algId){
        String name;
        String part;
        String num = "999";
        String level = "NaN";
        switch (algId) {
            case 1:
                name = "塔基沉降异常模型";
                part = "塔基";
                num = "999";
                level = "Ⅰ";
                break;
            case 2:
                name = "偏航驱动异常模型";
                part = "偏航系统";
                num = "126";
                level = "Ⅲ";
                break;
            case 3:
                name = "变桨轴承开裂的接近开关及振动模型";
                part = "变桨系统";
                num = "127";
                level = "Ⅰ";
                break;
            case 4:
                name = "变桨电机温度异常模型";
                part = "变桨系统";
                num = "128";
                level = "Ⅲ";
                break;
            case 5:
                name = "变桨控制异常模型";
                part = "变桨系统";
                num = "129";
                level = "Ⅰ";
                break;
            case 6:
                name = "发电机轴承温度异常模型";
                part = "发电机";
                num = "130";
                level = "Ⅱ";
                break;
            case 7:
                name = "发电机轴承卡滞模型";
                part = "偏航系统";
                num = "131";
                level = "Ⅱ";
                break;
            case 8:
                name = "偏航对风不正识别模型";
                part = "偏航系统";
                num = "125";
                level = "Ⅲ";
                break;
            case 9:
                name = "发电机绕组温度异常模型";
                part = "发电机";
                num = "133";
                level = "Ⅱ";
                break;
            case 10:
                name = "机舱振动异常模型";
                part = "机舱";
                num = "144";
                level = "Ⅰ";
                break;
            case 11:
                name = "功率异常偏移模型";
                part = "集群监测";
                num = "146";
                level = "Ⅱ";
                break;
            case 12:
                name = "塔筒振动异常模型";
                part = "塔筒";
                num = "134";
                level = "Ⅰ";
                break;
            case 13:
                name = "塔筒倾角异常模型";
                part = "塔筒";
                num = "135";
                level = "Ⅱ";
                break;
            case 14:
                name = "叶片结冰识别模型";
                part = "叶片";
                num = "136";
                level = "Ⅲ";
                break;
            case 15:
                name = "叶片固有频率偏移模型（振动）";
                part = "叶片";
                num = "137";
                level = "Ⅰ";
                break;
            case 16:
                name = "叶片噪声分析模型";
                part = "叶片";
                num = "138";
                level = "Ⅰ";
                break;
            case 17:
                name = "风速仪卡滞/松动模型";
                part = "风速风向仪";
                num = "139";
                level = "Ⅱ";
                break;
            case 18:
                name = "风向仪零位偏移";
                part = "风速风向仪";
                num = "140";
                level = "Ⅱ";
                break;
            case 19:
                name = "IGBT温度异常模型";
                part = "机内柜体";
                num = "141";
                level = "Ⅰ";
                break;
            case 20:
                name = "变流器温度异常";
                part = "机内柜体";
                num = "142";
                level = "Ⅰ";
                break;
            case 21:
                name = "控制柜温度异常模型";
                part = "机内柜体";
                num = "143";
                level = "Ⅰ";
                break;
            case 60:
                name = "风轮不平衡异常";
                part = "叶片";
                num = "146";
                level = "Ⅰ";
                break;
            case 61:
                name = "风轮严重气动异常";
                part = "叶片";
                num = "242";
                level = "Ⅰ";
                break;
            case 62:
                name = "塔架谐振异常";
                part = "塔筒";
                num = "243";
                level = "Ⅰ";
                break;
            case 63:
                name = "塔架固有频率偏移";
                part = "塔筒";
                num = "244";
                level = "Ⅰ";
                break;
            case 64:
                name = "叶片一阶摆振异常";
                part = "叶片";
                num = "245";
                level = "Ⅰ";
                break;
            case 65:
                name = "叶片二阶摆振异常";
                part = "叶片";
                num = "246";
                level = "Ⅲ";
                break;
            case 66:
                name = "叶片一阶舞振异常";
                part = "叶片";
                num = "247";
                level = "Ⅰ";
                break;
            case 67:
                name = "叶片二阶舞振异常";
                part = "叶片";
                num = "248";
                level = "Ⅲ";
                break;
            case 68:
                name = "叶片一阶摆震固有频率偏移";
                part = "叶片";
                num = "249";
                level = "Ⅰ";
                break;
            case 69:
                name = "叶片二阶摆震固有频率偏移";
                part = "叶片";
                num = "250";
                level = "Ⅲ";
                break;
            case 70:
                name = "叶片一阶舞震固有频率偏移";
                part = "叶片";
                num = "251";
                level = "Ⅰ";
                break;
            case 71:
                name = "叶片二阶舞震固有频率偏移";
                part = "叶片";
                num = "252";
                level = "Ⅲ";
                break;
            case 72:
                name = "主轴前轴承异常";
                part = "主轴承";
                num = "253";
                level = "Ⅰ";
                break;
            default:
                name = "Invalid Alg Id";
                part = "NaN";
                num = "999";
                level = "NaN";
                break;
        }
        String[] nameAndPart = {name, part, num, level};
        return nameAndPart;
    }

    public static String id2WTGSCode(String TId){
        String WTGSCode;

        switch (TId) {
            case "WT1":
                WTGSCode = "30000025";
                break;
            case "WT2":
                WTGSCode = "30000026";
                break;
            case "WT3":
                WTGSCode = "30000027";
                break;
            case "WT4":
                WTGSCode = "30000028";
                break;
            case "WT5":
                WTGSCode = "30000029";
                break;
            default:
                WTGSCode = "99999999";
                break;
        }

        return WTGSCode;
    }
}

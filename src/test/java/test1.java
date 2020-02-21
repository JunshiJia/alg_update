import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.junshijia.Alg.AlgData2Json;
import com.junshijia.Alg.FetchAlgData;
import com.junshijia.Alg.domain.AlgData;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class test1 {
    /**
     * 测试更新预警接口
     */
    @Test
    public void testUpdateWarningState(){
        JSONObject paramJson = new JSONObject();
        paramJson.put("Query", "SubHealth");
        paramJson.put("SubHealthType", "UpdateWarningState");
        //更新参数json
        JSONObject subHealthInfo = new JSONObject();
        //支持同时更新多条记录
        //厂家
        JSONArray factoryArr = new JSONArray();
        factoryArr.add("东方电气");//预警ID
        subHealthInfo.put("Factory",factoryArr);
        //farm name
        JSONArray farmNameArr = new JSONArray();
        farmNameArr.add("内蒙古霍林河四期智慧风场");
        subHealthInfo.put("FarmName",farmNameArr);
        //locationCode = 风机号
        JSONArray locationCodeArr = new JSONArray();
        //locationCodeArr.add();
        subHealthInfo.put("FarmName",farmNameArr);

        paramJson.put("SubHealthInfo", subHealthInfo);

        //调用接口
        System.out.println("输入："+paramJson.toJSONString());
        JSONObject jsonObject = postAPIJSONResult("http://192.168.0.26:8081/Service", paramJson.toJSONString());
        System.out.println("输出："+jsonObject.toJSONString());
    }

    /**
     * 通过post方式提交参数获取restful接口返回结果
     *  zhanglinbo 20190320
     * @param url 接口地址
     * @param paramJson 参数 json格式
     * @return JSON对象
     */
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


    @Test
    public void test0FetchAlgData(){
        FetchAlgData f = new FetchAlgData();
        //用第一条数据测试
        AlgData2Json a = new AlgData2Json(f.getDatas().get(0));
        Thread t = new Thread(a) ;
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

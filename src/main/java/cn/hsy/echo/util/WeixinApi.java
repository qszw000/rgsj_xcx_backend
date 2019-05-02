package cn.hsy.echo.util;


import cn.hsy.echo.exception.CodeErrorException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@Component
public class WeixinApi {
    private static final String APPID = "wx72cd08754362b281";
    private static final String APPSECRET = "bc97f9cd99ff0c623a33a6e1c7bd034a";

    public String getOpenId(String code) {
        StringBuffer url = new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?appid=")
                .append(APPID).append("&secret=").append(APPSECRET).append("&js_code=").append(code).append("&grant_type=authorization_code");
        String result = getResult(url.toString());
        JSONObject convertValue = (JSONObject) JSON.parse(result);
        String openId = (String) convertValue.get("openid");
        if (openId == null) {
            throw new CodeErrorException();
        }
        return openId;
    }

    private static String getResult(String url) {
        String result="";
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
                if(inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

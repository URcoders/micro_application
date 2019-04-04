package com.linxu.microapp.utils;

import com.google.gson.Gson;
import com.linxu.microapp.dtos.WxAuthMsg;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

/**
 * @author linxu
 * @date 2019/4/5
 */
public class RequestUtil {
    private static final String BASE_URL = "https://api.weixin.qq.com/sns/jscode2session?";
    private static final String APP_ID = "appid=wx2861d908ee53fba2";
    private static final String SECRET = "secret=7c29a4dbd3513d719bce2be1d2655e49";
    private static final String JS_CODE = "js_code=";
    private static final String GRANT_TYPE = "grant_type=authorization_code";

    /**
     *
     * @param code from website
     * @return a string obj  which contains openid、session_key,even unionid
     */
    public String code2Session(@NotNull String code) {
        String url = BASE_URL + APP_ID + SECRET + JS_CODE + code + GRANT_TYPE;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (!EmptyUtil.isEmpty(responseEntity) && responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return null;
    }
}

package com.lxl.demo.community.provider;

import com.alibaba.fastjson.JSON;
import com.lxl.demo.community.dto.AccessTokenDTO;
import com.lxl.demo.community.dto.GitHupUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHupProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenStr=split[0];
            String[] split1 = tokenStr.split("=");
            String token =split1[1];
            return  token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitHupUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken )
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHupUser gitHupUser = JSON.parseObject(string, GitHupUser.class);
            return gitHupUser;
        } catch (IOException e) {

        }
        return  null;
    }
}

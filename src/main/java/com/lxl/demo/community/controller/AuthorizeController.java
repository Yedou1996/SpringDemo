package com.lxl.demo.community.controller;

import com.lxl.demo.community.dto.AccessTokenDTO;
import com.lxl.demo.community.dto.GitHupUser;
import com.lxl.demo.community.provider.GitHupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHupProvider gitHupProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("77e29ec87ed5c314b1e5");
        accessTokenDTO.setClient_secret("0d0f793514d17edb1cb956d5b756e71b41f86112");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = gitHupProvider.getAccessToken(accessTokenDTO);
        GitHupUser user = gitHupProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}

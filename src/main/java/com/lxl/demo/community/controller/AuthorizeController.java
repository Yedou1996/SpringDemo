package com.lxl.demo.community.controller;

import com.lxl.demo.community.dto.AccessTokenDTO;
import com.lxl.demo.community.dto.GitHupUser;
import com.lxl.demo.community.provider.GitHupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHupProvider gitHupProvider;
    @Value("${gitHup.client.id}")
    private String clientId;
    @Value("${gitHup.client.secret}")
    private String clientSecret;
    @Value("${gitHup.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHupProvider.getAccessToken(accessTokenDTO);
        GitHupUser user = gitHupProvider.getUser(accessToken);
        if (user != null){
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            //登录失败，需要重新登录
            return "redirect:/";
        }


    }
}

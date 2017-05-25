package com.javaweb.controller;

import com.javaweb.model.UserEntity;
import com.javaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.lang.*;

/**
 * Created by zgr on 2017/5/10.
 */
@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login" , method = {RequestMethod.GET})
    public String Login(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginFlag = session.getAttribute("loginFlag");
        if(loginFlag == null){
            return "login";
        } else {
            if(Integer.parseInt(loginFlag.toString()) == 1){
                return "redirect:admin/blogs";
            } else {
                return "login";
            }
        }

    }

    @RequestMapping(value = "/loginP" , method = {RequestMethod.POST})
    public String LoginP(@Valid UserEntity user, HttpServletRequest request , ModelMap modelMap){

        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        if(nickname == null || nickname.equals("nickname")){
            return "login";
        } else if(password == null || password.equals("password")){
            return "login";
        }

        String password1 = convertMD5(password);

        UserEntity userEntity = userRepository.selectUser(nickname , password1);

        if(userEntity == null || "".equals(userEntity)){
            modelMap.addAttribute("loginError" , "登录密码错误");
            return "login";
        } else {
            String name = userEntity.getNickname();
            Integer id = userEntity.getId();
            HttpSession session = request.getSession();
            session.setAttribute("loginFlag" , 1);
            session.setAttribute("ID" , id);
            session.setAttribute("nickName" , name);
            return "redirect:admin/blogs";
        }
    }

    /**
     * 加密md5
     */
    public static String convertMD5(String str) {
        char[] a = str.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    @RequestMapping(value = "/logout" , method = {RequestMethod.GET})
    public String Logout(HttpServletRequest request , HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session == null){
            return "redirect:login";
        } else {
            session.removeAttribute("loginFlag");
            session.removeAttribute("ID");
            session.removeAttribute("nickName");
            return "redirect:login";
        }

    }

}

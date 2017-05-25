package com.javaweb.controller;

import com.javaweb.model.UserEntity;
import com.javaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by zgr on 2017/5/10.
 */
@Controller
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/register" , method = {RequestMethod.GET})
    public String Register(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginFlag = session.getAttribute("loginFlag");
        if(loginFlag == null || "".equals(loginFlag)){
            return "register";
        } else {
            if(Integer.parseInt(loginFlag.toString()) == 1){
                return "redirect:admin/blogs";
            } else {
                return "register";
            }
        }

    }

    @RequestMapping(value = "/registerSave" , method = {RequestMethod.POST})
    public String registerSave(ModelMap modelMap , HttpServletRequest request , @Valid UserEntity userEntity , BindingResult result){
        UserEntity userEntity1 = userRepository.selectUserName(userEntity.getNickname());
        if(userEntity1 == null || "".equals(userEntity1)){

            if(userEntity.getPassword() != null || userEntity.getPassword().trim().equals("") ){
                String password = convertMD5(userEntity.getPassword());
                userEntity.setPassword(password);
            }
            Object stat = userRepository.save(userEntity);

            if(stat != null){
                HttpSession session = request.getSession();
                session.setAttribute("loginFlag" , 1);
                session.setAttribute("ID" , userEntity.getId());
                session.setAttribute("nickName" , userEntity.getNickname());
                return "redirect:admin/blogs";
            } else {
                modelMap.addAttribute("nickNameError" , "保存失败");
                return "register";
            }
        } else {
            modelMap.addAttribute("nickNameExist" , "用户名已存在");
            return "register";
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


}

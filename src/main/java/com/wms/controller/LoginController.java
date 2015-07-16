package com.wms.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.util.UserDenied;
import com.util.UtilsDes;
import com.wms.dao.UserDao;
import com.wms.model.User;

@Controller
@SessionAttributes("user")
public class LoginController {
    
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    private static final String DEFAULT_FROM = "/";
    private static final String COOKIE_NAME = "wh_cookie";
    private static final Integer COOKIE_EXPIRE = 7 * 24 * 3600;
    private static final String HMAC_KEY = "warehouse";
    private static final String HMAC_ALGORITHM = "HmacMD5";
    private static final char[] hex = "0123456789abcdef".toCharArray();
    
    @Autowired
    private UserDao userDao;
    
    @RequestMapping("/login")
    public ModelAndView login(
            @CookieValue(value = COOKIE_NAME, required = false) String cookieToken,
            @RequestParam(value = "from", defaultValue = DEFAULT_FROM) String from,
            HttpServletRequest request,
            Model model, HttpServletResponse response) throws Exception {
        
        from = "redirect:" + from;
        logger.info("RequestMapping :/login. from:" + from);
        
        ModelAndView modelView = new ModelAndView();
        User user = null ;

        if (!StringUtils.isEmpty(cookieToken)) {
            try {
                user = loginByCookie(cookieToken);
            } catch (Exception e) {
                logger.debug("Login by cookie failed, remove the cookie." + e);
                response.addCookie(new Cookie(COOKIE_NAME, null));
            }

            if (user != null) {
                logger.info("Login by cookie: " + user.getUsername());
                modelView.setViewName(from);
                
                Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
                String userIds = UserDenied.getUserIds(users, user.getRole());
                user.setUserIds(userIds);
                modelView.addObject("user", user);
                
                request.getSession().setAttribute("user", user);
                //request.getSession().setAttribute("userIds", userIds);
                
                return modelView;
            }
        }
        
        String username = null;
        String password = null;
        String passwordTmp = null;
        
        try {
            username = request.getParameter("username");
            passwordTmp = request.getParameter("password");
            password = UtilsDes.encrypt(passwordTmp);
            logger.info(passwordTmp);
        } catch (Exception e) {
            // TODO: handle exception
            //e.printStackTrace();
            logger.debug("request null faield ." + e);
            modelView.setViewName("/login");
            return modelView;
        }

        user = userDao.findByUsernameAndPassword(username, password);
        if(user != null) {
            logger.info("login by password. " + user.getTruename());
            
            Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
            String userIds = UserDenied.getUserIds(users, user.getRole());
            modelView.addObject("userIds", userIds);
            user.setUserIds(userIds);
            
            modelView.addObject("user", user);
            request.getSession().setAttribute("user", user);
            //request.getSession().setAttribute("userIds", userIds);
            
            Cookie cookie = null;
            
            try {
                cookie = new Cookie(COOKIE_NAME,
                        getCookieToken(user.getId()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cookie.setMaxAge(COOKIE_EXPIRE);
            response.addCookie(cookie);
            modelView.setViewName(from);
            return modelView;
        }
        
        modelView.addObject("username", username);
        modelView.addObject("password", passwordTmp);
        modelView.setViewName("/login");
        return modelView;
    }
    
    
    @RequestMapping("/logout")
    public ModelAndView logout(SessionStatus status, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/login");
        
        status.setComplete();
        response.addCookie(new Cookie(COOKIE_NAME, null));
        return modelView;
    }
    
    
    private User loginByCookie(String cookieToken) throws Exception {

        Pattern ptrn = Pattern.compile("^([0-9]+)_(.*)$");
        Matcher matcher = ptrn.matcher(cookieToken);
        if (!matcher.matches()) {
            throw new Exception("Invalid token format.");
        }

        if (!matcher.group(2).equals(hmac(matcher.group(1)))) {
            throw new Exception("Invalid token.");
        }

        return userDao.get(Long.parseLong(matcher.group(1)));
    }

    private String getCookieToken(long userId) throws Exception {
        return String.format("%d_%s", userId, hmac(String.valueOf(userId)));
    }

    private String hmac(String input) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(new SecretKeySpec(HMAC_KEY.getBytes(), HMAC_ALGORITHM));
        return toHexString(mac.doFinal(input.getBytes()));
    }

    private String toHexString(byte[] bytes) {
        if (null == bytes) {
            return null;
        }

        StringBuilder sb = new StringBuilder(bytes.length << 1);

        for (int i = 0; i < bytes.length; ++i) {
            sb.append(hex[(bytes[i] & 0xf0) >> 4]).append(
                    hex[(bytes[i] & 0x0f)]);
        }

        return sb.toString();
    }
    

}

package com.wms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponentsBuilder;

import com.wms.model.User;


public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
    
    //最后执行，可用于释放资源
    @Override
    public void afterCompletion(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex) throws Exception{
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") instanceof User) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return false;
            }
            if (request.getRequestURI().contains("/users") && user.getRole() != User.ROLE_ADMIN) {
                response.sendRedirect(UriComponentsBuilder
                        .fromPath(request.getContextPath()).path("/info/denied")
                        .build().toUriString());
                return false;
            }
            return true;
        }else {
//            response.sendRedirect(UriComponentsBuilder
//                    .fromPath(request.getContextPath()).path("/login")
//                    .queryParam("from", getUrl(request)).build().toUriString());
//            
            request.getRequestDispatcher(UriComponentsBuilder
                    .fromPath(request.getContextPath()).path("/login")
                    .queryParam("from", getUrl(request)).build().toUriString()).forward(request, response);
            
            return false;
        }

    }
    

    private String getUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        int index = url.indexOf("?");
        if (index>0) {
            url = url.substring(0, index);
        }
        System.out.println("request url:" + url.substring(request.getContextPath().length()));

        StringBuilder sb = new StringBuilder(url.substring(request.getContextPath().length()));
        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append("?").append(queryString);
        }
        return sb.toString();
    }

}

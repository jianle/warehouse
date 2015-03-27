package com.wh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponentsBuilder;

import com.wh.model.User;


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
            return true;
        }else {
            response.sendRedirect(UriComponentsBuilder
                    .fromPath(request.getContextPath()).path("/login")
                    .queryParam("from", getUrl(request)).build().toUriString());

            return super.preHandle(request, response, handler);
        }

    }
    

    private String getUrl(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder(request.getRequestURL());
        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append("?").append(queryString);
        }
        return sb.toString();
    }

}

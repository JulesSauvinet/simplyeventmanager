package fr.univlyon1.sem.filter;

import fr.univlyon1.sem.bean.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    SessionBean session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Inject la session pour les JSP
        request.setAttribute("session", session);
        return true;
    }
}

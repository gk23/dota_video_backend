package so.pada.dota.servlet;

import so.pada.dota.youku.Oauth;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gk23
 * Date: 14-3-18
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */
public class OauthServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String code = request.getParameter("code");
        Oauth oauth = new Oauth(code);

        oauth.get();
        //response.sendRedirect("index.jsp");
    }
}

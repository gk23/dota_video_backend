package so.pada.dota.youku;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gk23
 * Date: 14-3-18
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public class Oauth {
    public static final String OAUTH_URL = "https://openapi.youku.com/v2/oauth2/authorize";
    public static final String TOKEN_URL = "https://openapi.youku.com/v2/oauth2/token";
    public static final String CLENT_ID = "cb79cfdaa0768aa9";
    public static final String SERECT_ID = "fec4f8bbd233b8f586762c6abde1623b";
    public static final String REDIRECT_URL = "http://127.0.0.1:8080/oauth";
    private String code;

    public static void main(String[] args) {
        Oauth oauth = new Oauth("stestest");
        oauth.get();
    }

    public Oauth(String code){
        this.code = code;
    }
    public void get() {


        try {
            // 获取授权码
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(OAUTH_URL);
            HttpMethodParams params = new HttpMethodParams();
//            params.setParameter("client_id", CLENT_ID);
//            params.setParameter("response_type", "code");
//            params.setParameter("redirect_uri", REDIRECT_URL);
//            method.setParams(params);
//            client.executeMethod(method);
//            String content = method.getResponseBodyAsString();
//            System.out.println(content);
//            method.releaseConnection();

            // 获得access token
            method = new PostMethod(TOKEN_URL);
            params = new HttpMethodParams();
            params.setParameter("client_id", CLENT_ID);
            params.setParameter("client_secret", SERECT_ID);
            params.setParameter("grant_type", "authorization_code");
            params.setParameter("redirect_uri", REDIRECT_URL);
            params.setParameter("code", code);
            method.setParams(params);

            System.out.println("code: " + code);

            client.executeMethod(method);
            String content = method.getResponseBodyAsString();
            System.out.println("content: "+content);
            method.releaseConnection();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // 获得access token
    }
}

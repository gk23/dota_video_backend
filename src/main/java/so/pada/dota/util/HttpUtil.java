package so.pada.dota.util;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);
    // 支持多线程
    private static HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
    private static int TIMEOUT = 5000;// 超时时间

//    public static String getContentWithCache(String link, Map<String, String> headerMap, MemcachedService memcachedService) {
//        Object value = memcachedService.get(link);
//        if (value != null)
//            return (String) value;
//        String content = HttpUtil.getContent(link, headerMap);
//        if (content == null)
//            return null;
//        memcachedService.set(link, MemcachedService.SECONDS_PER_DAY, content);
//        return content;
//    }

//    public static String getContentWithCache(String link, MemcachedService memcachedService) {
//        return getContentWithCache(link, null, memcachedService);
//    }

    public static String getContent(String url) {
        return getContent(url, null);
    }

    public static String getContent(String url, Map<String, String> headerMap) {
        // 请求的内容
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);

            // method.setRequestHeader("Accept",
            // "text/html,application/xhtml+xml,application/xml;");
            method.setRequestHeader("Accept-Language", "zh-cn");
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1");
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    method.setRequestHeader(key, headerMap.get(key));
                }
            }
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Method failed: " + method.getStatusLine());
            }
            Header header = method.getResponseHeader("Content-Encoding");
            System.out.println("char-set----" + method.getResponseCharSet());
            if (header != null) {
                String encoding = header.getValue();
                if (encoding != null && encoding.toLowerCase().contains("gzip")) {
                    return getGzipInput(method);
                }
            }
            String charset = method.getResponseCharSet();
            BufferedReader reader;
            if (charset == null || charset.trim().length() == 0) {
                reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset.trim()));
            }
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append('\n');
                line = reader.readLine();
            }
            return decodeStream(sb.toString(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return null;
    }

    private static String getGzipInput(GetMethod method) throws Exception {
        InputStream is = method.getResponseBodyAsStream();
        GZIPInputStream gzin = new GZIPInputStream(is);
        String charset = method.getResponseCharSet();
        InputStreamReader isr = new InputStreamReader(gzin, method.getResponseCharSet());
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String tempbf;
        while ((tempbf = br.readLine()) != null) {
            sb.append(tempbf);
            sb.append("\r\n");
        }
        isr.close();
        gzin.close();
        return decodeStream(sb.toString(), charset);
    }

    private static String decodeStream(String content, String charset) throws UnsupportedEncodingException {
        if (charset == null || charset.trim().length() == 0 || "iso-8859-1".equals(charset.trim().toLowerCase())) {
            charset = CharsetDetector.detector(content);
            if (charset != null && !charset.equals("ASCII")) {
                if ("GB2312".equals(charset))
                    charset = "GBK";
                return new String(content.getBytes("iso-8859-1"), charset);
            }
        }
        return content;
    }

    public static long getLongValue(HttpServletRequest request, String name, boolean isHeader) {
        String value = isHeader ? request.getHeader(name) : request.getParameter(name);
        if (value != null && value.trim().length() > 0) {
            try {
                return Long.valueOf(value);
            } catch (Exception e) {
                logger.error("Error while getting value from request.", e);
                e.printStackTrace();
            }
        }
        return 0;
    }
}

package so.pada.dota.servlet;

import so.pada.dota.youku.Video;
import so.pada.dota.youku.VideoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gk23
 * Date: 14-3-18
 * Time: 下午9:20
 * To change this template use File | Settings | File Templates.
 */
public class VideoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        name = URLEncoder.encode(name,"utf-8");

        VideoService service = new VideoService();
        service.setName(name);
        List<Video> videos = service.getVideo();
        request.setAttribute("videos",videos);
        request.getRequestDispatcher("/videos.jsp").forward(request,response);
    }
}

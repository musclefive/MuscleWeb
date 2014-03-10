package com.musclefive.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by shixin on 14-3-8.
 */
public class MyFirstServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(MyFirstServlet.class);

    private String initParam1;
    private String initParam2;

    @Override
    public void init() {
        this.initParam1 = getServletConfig().getInitParameter("p1");
        this.initParam2 = getInitParameter("p2");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Enter MyFirstServlet.");

        //如果不做特殊处理，下面输出中文的是乱码
        //s1
        if (request.getParameter("useLocal") != null) {
            response.setLocale(request.getLocale());
            //可以在web.xml中配置语系和编码的对应，Local有默认的编码格式，web.xml可以覆盖它
        }
        //s2
        if (request.getParameter("useContentType") != null) {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
        }

        if (request.getParameter("sendError") != null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "自定义");
        }
        if (request.getParameter("sendRedirect") != null) {
            response.sendRedirect("/forward.servlet");
        }

        PrintWriter printWriter = response.getWriter();
        printWriter.write("<html>");
        printWriter.write("<head>");
        printWriter.write("</head>");
        printWriter.write("<body>");

        printWriter.write("<p>" + "My first servlet!" + "</p>");

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p><h1>Request info:</h1></p>");
        printWriter.write("<p>" + "Protocol :" + request.getProtocol() + "</p>");
        printWriter.write("<p>" + "Method :" + request.getMethod() + "</p>");
        printWriter.write("<p>" + "CharacterEncoding :" + request.getCharacterEncoding() + "</p>");
        printWriter.write("<p>" + "Local:" + request.getLocale() + "</p>");
        printWriter.write("<p>" + "RequestURI :" + request.getRequestURI() + "</p>");
        printWriter.write("<p>" + "RequestURL :" + request.getRequestURL() + "</p>");
        printWriter.write("<p>" + "ContextPath :" + request.getContextPath() + "</p>");
        printWriter.write("<p>" + "ServletPath :" + request.getServletPath() + "</p>");
        printWriter.write("<p>" + "PathInfo :" + request.getPathInfo() + "</p>");

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p><h1>Response info:</h1></p>");
        printWriter.write("<p>" + "CharacterEncoding:" + response.getCharacterEncoding() + "</p>");

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p><h1>Header info:</h1></p>");
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getHeader(name);
            printWriter.write("<p>" + "Header name :" + name + ", value :" + value + "</p>");
        }

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p><h1>Parameter info:</h1></p>");
        if (request.getMethod().equalsIgnoreCase("post")) {
            if (request.getParameter("zh") != null) {
                request.setCharacterEncoding("UTF-8");
                printWriter.write("<p>" + "Parameter zh :" + request.getParameter("zh") + "</p>");
            }
        } else {
            if (request.getParameter("zh") != null) {
                String value = new String(request.getParameter("zh").getBytes("ISO-8859-1"), "UTF-8");
                printWriter.write("<p>" + "Parameter zh :" + value + "</p>");
            }
        }

        printWriter.write("<p>" + "Parameter a :" + request.getParameter("a") + "</p>");
        printWriter.write("<p>" + "Parameter b :" + request.getParameter("b") + "</p>");

        String[] cValues = request.getParameterValues("c");
        if (null != cValues) {
            for (String cValue : cValues) {
                printWriter.write("<p>" + "Parameter c :" + cValue + "</p>");
            }
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        if (null != parameterNames) {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                printWriter.write("<p>" + "Parameter name :" + parameterName + "</p>");
            }
        }
        printWriter.write("<p>-------------------------------------------</p>");
        Shixin me = new Shixin();
        me.setName("JamesXin");
        request.setAttribute("shixin", me);

        if (request.getParameter("include") != null) {
            request.getRequestDispatcher("include.servlet").include(request, response);
        } else if (request.getParameter("forward") != null) {
            request.getRequestDispatcher("forward.servlet").forward(request, response);
        }

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p>" + "输出中文试试" + "</p>");
        printWriter.print("<p>" + "输出中文试试" + "</p>");
        String s = "<p>输出中文试试</p>";
        s = new String(s.getBytes("UTF-8"), "UTF-8");
        printWriter.write("<p>" + s + "</p>");

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p><h1>Init parameters:</h1></p>");
        printWriter.println("<p>" + this.initParam1 + "</p>");
        printWriter.println("<p>" + this.initParam2 + "</p>");

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("<p><h1>Use ServletContext to show resources:</h1></p>");
        ServletContext servletContext = getServletContext();
        Set<String> paths = servletContext.getResourcePaths("/");
        for (String path : paths) {
            printWriter.println("<p>" + path + "</p>");
        }

        printWriter.write("<p>-------------------------------------------</p>");
        printWriter.write("</body>");
        printWriter.write("</html>");
        printWriter.close();
    }

    /**
     * 可以用getReader方法获取以post方式发送的body中的内容
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String readBody(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = request.getReader();
        String body = "";
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            body += line + "<br>";
        }
        return body;
    }

    /**
     * 常用ServletInputStream来出来http上传文件的功能
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String useServletInputStream(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        return null;
    }
}

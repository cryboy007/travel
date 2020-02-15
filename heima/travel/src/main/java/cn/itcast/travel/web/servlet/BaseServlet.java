package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求uri
        String requestURI = request.getRequestURI();
        //System.out.println(requestURI);
        //获取方法名
        String methodName = requestURI.substring(requestURI.lastIndexOf("/")+1);
        //获取方法对象method
        //System.out.println("方法名称"+methodName);
        //获取方法对象Method
        try {
            //this 代表的对象是 哪个调我，就是谁
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将对象序列化成json对象，并且写回客户端
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValue (Object obj,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将对象序列化成String字符串
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString (Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}

package goodthings.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Joiner;
import goodthings.filter.RereadRequestFilter;
import goodthings.util.LoggerUtils;
import goodthings.util.http.WrappedHttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by tujl on 2018/12/18.
 */
public class LogCostInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogCostInterceptor.class);
    //请求开始时间标记
    private static final String LOGGER_SEND_TIME = "_send_time";

    /**
     *  * 获取请求Body
     *  *
     *  * @param request
     *  * @return
     *  
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            handler) throws Exception {
        long start = System.currentTimeMillis();
        String params = "";
        if (httpServletRequest instanceof WrappedHttpServletRequest) {
            WrappedHttpServletRequest requestWrapper = (WrappedHttpServletRequest) httpServletRequest;
            params = IOUtils.toString(requestWrapper.getBody(),httpServletRequest.getCharacterEncoding());
            if (params.length() > 1000) {
                params = params.substring(0, 1000) + "........";
            }
        }else {
            params = JSON.toJSONString(httpServletRequest.getParameterMap(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        }

//        httpServletRequest.setAttribute("postData", params);
        //设置请求开始时间
        httpServletRequest.setAttribute(LOGGER_SEND_TIME, start);
        logger.info("ip:{},请求url:{},参数:{}", LoggerUtils.getCliectIp(httpServletRequest), httpServletRequest.getRequestURI(), params);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o, ModelAndView modelAndView) throws Exception {
        long time = Long.parseLong(httpServletRequest.getAttribute(LOGGER_SEND_TIME).toString());
        logger.info("ip:{},请求url:{} cost={}", LoggerUtils.getCliectIp(httpServletRequest), httpServletRequest.getRequestURI(), (System.currentTimeMillis() - time));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) throws Exception {
    }
}
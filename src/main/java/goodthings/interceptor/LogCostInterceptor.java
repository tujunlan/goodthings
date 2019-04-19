package goodthings.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import goodthings.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tujl on 2018/12/18.
 */
public class LogCostInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogCostInterceptor.class);
    //请求开始时间标记
    private static final String LOGGER_SEND_TIME="_send_time";
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        String params = JSON.toJSONString(httpServletRequest.getParameterMap(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        if (params.length() > 1000) {
            params = params.substring(0, 1000) + "........";
        }
        //设置请求开始时间
        httpServletRequest.setAttribute(LOGGER_SEND_TIME, start);
        logger.info("ip:{},请求url:{},参数:{}", LoggerUtils.getCliectIp(httpServletRequest), httpServletRequest.getRequestURI(), params);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        long time = Long.parseLong(httpServletRequest.getAttribute(LOGGER_SEND_TIME).toString());
        logger.info("ip:{},请求url:{} cost={}", LoggerUtils.getCliectIp(httpServletRequest), httpServletRequest.getRequestURI(), (System.currentTimeMillis() - time));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
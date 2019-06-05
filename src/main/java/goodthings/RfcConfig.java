package goodthings;

/*
 * 文件名：RfcConfig.java 版权：Copyright by gogym 描述： 修改人：gogym 修改时间：2018年12月18日 跟踪单号： 修改单号： 修改内容：
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RfcConfig
{
    @Bean
    public Integer setRfc()
    {
        // 指定jre系统属性，允许特殊符号， 如{} 做入参，其他符号按需添加。见 tomcat的HttpParser源码。
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}[].");
        return 0;
    }

}

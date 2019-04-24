/**
 * 
 */
package goodthings.util.render;

import com.hylanda.service.http.webwind.renderer.Renderer;
import goodthings.util.general.SystemUtility;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;

/**
 * @author 王楠
 * @createDate 2016年8月11日
 */
public abstract class RendererSupport  extends Renderer
{
	/**
	 * http response 添加tracker header头信息
	 */
	public static final String HTTP_RESPONSE_ADD_HEADNAME_TRACKER="tracker";
	/**
	 * http response 添加tracker header头信息，内容的附加信息 
	 */
	public static final int  HTTP_RESPONSE_ADD_HEADNAME_TRACKER_SALT=17;
	private static InetAddress address=null ;
	private static String hostIp;
	static 
	{
		if( StringUtils.isEmpty ( hostIp ))
		{			
			try
			{
				address = InetAddress.getLocalHost ( ) ;
				hostIp = address.getHostAddress ( );
			} catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * reponse添加tracker自定义头信息，用来跟踪服务请求
	 * 提示：这个方法需要在reset、resetBuffer方法调用，否者被前面的方法清楚
	 * @param context
	 * @param httpServletRequest
	 * @param httpServletResponse
	 */
	protected void addHttpResopnseHeaderTracker(ServletContext context, HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
	{
		
		//如果信息已经提交，不在进行添加,避免出现问题 
		if( !httpServletResponse.isCommitted ( ) )
		{
			String tracker = "";
			String sip = hostIp;
			String ip_number_str =  sip.replace (".","" );
			if( StringUtils.isNumeric ( ip_number_str ))
			{
				long ip_number= NumberUtils.createLong ( ip_number_str )*HTTP_RESPONSE_ADD_HEADNAME_TRACKER_SALT;
				tracker = String.valueOf ( ip_number );
			}
			else 
			{
				tracker= SystemUtility.md5(sip, true);
			}
		    
		     httpServletResponse.setHeader ( "tracker" , tracker );
		}
		
	}
}

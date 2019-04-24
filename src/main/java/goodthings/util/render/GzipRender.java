package goodthings.util.render;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

public class GzipRender extends RendererSupport{
	public static String TYPE_TEXT="text";
	public static String TYPE_JSON="json";
	private final String GZIP= "gzip" ;
	private Object object;
	private boolean isSkipNull;
	public GzipRender(Object object, String type, boolean isSkipNull)
	{
		this.object=object;
		this.isSkipNull = isSkipNull;
		if( type != null )
		{
			if( type.equals(TYPE_JSON))
			{
				setContentType("application/json");
			}
			else if( type.equals(TYPE_TEXT))
			{
				setContentType("text/html");
			}
		}

	}

	@Override
	public void render(ServletContext context, HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws Exception {
		StringBuilder sb = new StringBuilder(64);
		String characterEncoding = "UTF-8";
		OutputStream stream = null ;
		httpServletResponse.reset();
		httpServletResponse.resetBuffer();
		this.addHttpResopnseHeaderTracker ( context , httpServletRequest , httpServletResponse );
        sb.append(contentType)
          .append(";charset=")
          .append(characterEncoding);
        httpServletResponse.setContentType(sb.toString());
        //判断客户端是否支持Gzip
        String headEncoding = httpServletRequest.getHeader("accept-encoding") ;
        if ( headEncoding != null && headEncoding.indexOf( GZIP) != -1 )
        {
        	httpServletResponse.setHeader("content-encoding", GZIP) ;
        	stream =  new GZIPOutputStream(httpServletResponse.getOutputStream()) ;
        }
        else
        {
        	stream = httpServletResponse.getOutputStream() ;
        }

       PrintWriter pw = new PrintWriter(stream) ;

        BufferedWriter bufferedWriter = new BufferedWriter(pw);
        //String json = "";
        if( !isSkipNull)
        {
        	JSON.writeJSONStringTo(object, bufferedWriter, SerializerFeature.WriteMapNullValue, SerializerFeature.BrowserCompatible);

        }
        else
        {
        	JSON.writeJSONStringTo(object, bufferedWriter, SerializerFeature.BrowserCompatible);
        }

        bufferedWriter.flush();
        bufferedWriter.close();
	}
}

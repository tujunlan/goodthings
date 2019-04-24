package goodthings.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class HttpCommons {
	private static final Logger logger = LoggerFactory.getLogger(HttpCommons.class);
	public final static String Content_Type_JSON="application/json";
	public static interface Mapping<T>
	{
		public T mapping(Map<String, String> map);
	}
	public static String getRemoteClientIP(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
	/**
	 * 获得请求的Query
	 * @param url url地址
	 * @return 返回search内容
	 */
	public static String getRequestSearchString(String url)
	{
		String query="";
		int param_index = url != null ? url.indexOf("?") : -1;
		if( param_index != -1 )
		{
			query =url.substring(param_index+1);
		}
		return query ;
	}

	public static <T> T  getRequestSearch2Map(String search , Mapping<T> mapping)
	{
		Map map =getRequestSearch2Map(search);
		T t = (T) mapping.mapping(map);
		return  t ;
	}
	public static Map<String, String> getRequestSearch2Map(String search)
	{
		Map<String, String> search_map = new HashMap<String, String>() ;
		if( search != null && !search.isEmpty())
		{
			int index = search.indexOf("?");
			if( index != -1  )
			{
				search = search.substring(index+1);
			}
			String kv_array[]=search.split("\\&");
			for( String item  :kv_array)
			{
				 int split_index = item.indexOf("=");
				 if( split_index != -1 )
				 {
					 String key=item.substring(0,split_index);
					 String value=item.substring(split_index+1);
					 search_map.put(key, value);
				 }
			}
		}
		return search_map ;
	}
	public static  void HttpResponeWrite(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest , String contentType, Object object )
	{
		StringBuilder sb = new StringBuilder(64);
		String characterEncoding = "UTF-8";
		String GZIP= "gzip" ;
		OutputStream stream = null ;
		 sb.append(contentType)
         .append(";charset=")
         .append(characterEncoding);
		 httpServletResponse.reset();
		 httpServletResponse.resetBuffer();
		 httpServletResponse.setContentType(sb.toString());
	     String headEncoding = httpServletRequest.getHeader("accept-encoding") ;
	     try
	     {	     
	        if ( headEncoding != null && headEncoding.indexOf( GZIP) != -1 )
	        {
	        	httpServletResponse.setHeader("content-encoding", GZIP) ;
	        	stream =  new GZIPOutputStream(httpServletResponse.getOutputStream()) ;
	        }
	        else
	        {
	        	stream = httpServletResponse.getOutputStream() ;
	        }
	     }
	     catch(Exception exception)
	     {
	    	 exception.printStackTrace (  );
	    	 logger.error ( "HttpResponeWrite操作异常:{}", exception);
	     }
	     PrintWriter pw = new PrintWriter(stream) ;

	     try(  BufferedWriter bufferedWriter = new BufferedWriter(pw))
	     {
		       JSON.writeJSONStringTo(object, bufferedWriter, SerializerFeature.BrowserCompatible);
		       bufferedWriter.flush();
	     }
	     catch(Exception exception)
	     {
	    	 exception.printStackTrace (  );
	    	 logger.error ( "HttpResponeWrite->bufferedWriter操作异常:{}", exception);
	     }
	}
	public static String urlDecode(String str) {
		try {
			str = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String urlEncode(String str) {
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 转义html特殊字符
	 *
	 * @param html
	 * @return
	 */
	public static String htmlConvertSpecial(String html) {
		/*
		 * < &lt; > &gt; & &amp; " &quot; ' &apos;
		 */
		if (html != null && !html.isEmpty()) {
			html = html.replace("&lt;", "<");
			html = html.replace("&gt;", ">");
			html = html.replace("&amp;", "&");
			html = html.replace("&quot;", "\"");
			html = html.replace("&apos;", "'");
		}
		return html;
	}

	public static boolean doDownload(String url, String strFile, int nRetryCnt) throws IOException {
		if (nRetryCnt < 0) {
			return false;
		}
		String dir = FilenameUtils.getFullPath(strFile);
		FileUtils.forceMkdir(new File(dir));
		boolean bOk = false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);

			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			int nCode = response.getStatusLine().getStatusCode();
			if (nCode == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					File storeFile = new File(strFile);
					FileOutputStream output = new FileOutputStream(storeFile);

					output.write(bytes);
					output.close();
					bOk = true;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		if (!bOk) {
			return doDownload(url, strFile, nRetryCnt - 1);
		}

		return bOk;
	}
}

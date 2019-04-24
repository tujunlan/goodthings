package goodthings.util.http;

import com.google.common.io.Closeables;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;


public class HttpSend {
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_CHARSET_UTF8 = "UTF-8";
	public static final String HTTP_CHARSET_GBK = "GBK";
	private static Logger logger = LoggerFactory.getLogger(HttpSend.class);

	public static InputStream send(String param, String urlStr, String chatset,
                                   String method)
			{
				return send(param, urlStr, chatset, method,false);
			}
	/**
	 * 
	 * @param param
	 * @param urlStr
	 * @param chatset
	 * @param method
	 * @return
	 */
	public static InputStream send(String param, String urlStr, String chatset,
                                   String method, boolean iscompress) {
		
		OutputStreamWriter out = null;
		GZIPOutputStream gzipOutputStream = null;
		InputStream inputStream = null;

		// retry count when network is not accessible. each retry will add another 500ms sleep time
		int RETRY_COUNT = 3;
				
		if (method.equals(HTTP_METHOD_GET)) {//HTTP_METHOD_GET
			if (urlStr.indexOf("?") == -1) {
				urlStr += "?";
			}
			urlStr += "&" + param;
		}
		
		HttpURLConnection huc = null;
		
		// retry openConnection for RETRY_COUNT times if failed
		for (int i = 0; i < RETRY_COUNT; i++) {
			try {
				URL u = new URL(urlStr);
				huc = (HttpURLConnection) u.openConnection();
				break;
			} catch (Exception e) {
				logger.warn(String.format("HttpURLConnection failed at URL %s, retry %d", urlStr, (i + 1)));
				huc = null;
				
				try {
					Thread.sleep(500 * (i + 1));
				} catch (InterruptedException e1) {
				}
			}
		}
		
		// if retry failed, then abort current action
		if (null == huc) {
			logger.error("fail to open connection, url={}", urlStr);
			return inputStream;
		}
		
		try {
			huc.setRequestMethod(method);
			huc.setUseCaches(false);
			huc.setDoInput(true);
			huc.setReadTimeout(1000 * 60 * 3);
			huc.setDoOutput(true);
			if (method.equals(HTTP_METHOD_POST)) {
				if (!iscompress) {
					out = new OutputStreamWriter(huc.getOutputStream(), chatset);
					out.write(param, 0, param.length());
					out.flush();
				} else {
					gzipOutputStream = new GZIPOutputStream(
							huc.getOutputStream());
					gzipOutputStream.write(param.getBytes());
					gzipOutputStream.finish();
				//	byte[] buf = param.getBytes();
				//	System.out.println(buf.length);
				}
				Closeables.close(out, true);
				Closeables.close(gzipOutputStream,true);

				if (urlStr.indexOf("set2") != -1) {
					param += "&";
					param=param.replaceAll("&wts=.*?&", "&wts=XXX&");
					param=param.replaceAll("&wr_ext=.*?&", "&wr_ext=XXX&");
					param=param.replaceAll("&rules=.*?&", "&rules=XXX&");
					param=param.replaceAll("&feature_words=.*?&", "&feature_words=XXX&");
				}
			}
			logger.info(String.format("SendUrl:%s,params:%s", urlStr,param));
			inputStream = huc.getInputStream();
		} catch (Exception exception) {
			logger.error(String.format("httpsendUrl:%s , iscompress:%s", urlStr,iscompress), exception);
			exception.printStackTrace();
		} finally {
			try {
				Closeables.close(out, true);
				Closeables.close(gzipOutputStream, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return inputStream;
	}

	public static InputStream send(Set<String> params, String urlStr,
                                   String chatset, String method) {
		return send(params, urlStr, chatset, method, false);
	}

	/**
	 * 
	 * @param params
	 * @param urlStr
	 * @param chatset
	 * @param method
	 * @param iscompress
	 * @return
	 */
	public static InputStream send(Set<String> params, String urlStr,
                                   String chatset, String method, boolean iscompress) {
		String paramStr = "";
		if (params != null) {

			for (String s : params) {
				paramStr += s + "&";
			}
			if (paramStr.endsWith("&")) {
				paramStr = paramStr.substring(0, paramStr.length() - 1);
			}
		}
		return send(paramStr, urlStr, chatset, method, iscompress);
	}

	public static InputStream send(Map params_map, String urlStr,
                                   String chatset, String method) {
		return send(params_map, urlStr, chatset, method, false);
	}

	public static InputStream send(Map params_map, String urlStr,
                                   String chatset, String method, boolean compress) {
		Set<String> params = mapToSetParas(params_map);
		return send(params, urlStr, chatset, method, compress);
	}

	public static String HttpResponeToString(InputStream inputStream,
                                             String charset) {
		String result = null;
		try {
			result = IOUtils.toString(inputStream, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
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
	 * 用来将map转成set形式
	 * 
	 * @param map
	 * @return
	 */
	public static Set<String> mapToSetParas(Map map, boolean isencode) {
		Set<String> params = new HashSet<String>();
		if (map != null && !map.isEmpty()) {
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = (String) map.get(key);
				if (isencode && value != null) {
					value = urlEncode(value);
				}
				key = key.replaceAll("^#\\d+?#", "");
				params.add(String.format("%s=%s", key, value));
			}
		}
		return params;
	}

	/**
	 * 用来将map转成set形式
	 * 
	 * @param map
	 * @return
	 */
	public static Set<String> mapToSetParas(Map map) {
		return mapToSetParas(map, true);
	}
}

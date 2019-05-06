package goodthings.controller;

import com.hylanda.service.http.webwind.ActionContext;
import com.hylanda.service.http.webwind.renderer.BinaryRenderer;
import com.hylanda.service.http.webwind.renderer.FileRenderer;
import com.hylanda.service.http.webwind.renderer.Renderer;
import com.hylanda.service.http.webwind.renderer.TextRenderer;
import goodthings.util.http.HttpCommons;
import goodthings.util.render.GzipRender;
import goodthings.util.render.JsonRenderer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 控制器通用功能类
 *
 * @author wn
 */
public class ControllerSupport implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String HTTP_HEAD_REFERER = "Referer";
	public static final String RENDERER_TYPE_JSON = "json";
	public static final String RENDERER_TYPE_TEXT = "text";
	public static final String RENDERER_TYPE_BIN = "Binary";
	public static final String RENDERER_TYPE_FILE = "file";
	public static final String RENDERER_TYPE_TEXT_GZIP = "text_gzip";
	public static final String RENDERER_TYPE_JSON_GZIP = "json_gzip";
	public static final String RENDERER_TYPE_JSON_SKIP_NULL_GZIP = "json_skip_null_gzip";
	public static final String RENDERER_CONTENTTYPE_FILE = "application/x-zip-compressed";

	protected ObjectMapper mapper = new ObjectMapper();
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String webroot = null;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		
	}

	/**
	 * 返回一个JsonRenderer对象
	 *
	 * @param object
	 * @return
	 */
	public Renderer createRenderer(Object object) {
		return this.createRenderer(object, RENDERER_TYPE_JSON_GZIP);
	}

	public HttpSession getHttpSession() {
		HttpSession session = this.getHttpServletRequest().getSession();

		return session;
	}

	public Renderer createRenderer(Object object, String type) {
		return createRenderer(object, type, null);
	}

	/**
	 * 类型如果是 Binary object 必须是byte[]
	 *
	 * @param object
	 * @param type
	 * @return
	 */
	public Renderer createRenderer(Object object, String type,
                                   String contentType) {
		Renderer renderer = null;
		if (type != null) {
			if (type.equals(RENDERER_TYPE_JSON)) {
				renderer = new JsonRenderer(object);
			} else if (type.equals(RENDERER_TYPE_TEXT)) {
				renderer = new TextRenderer(object.toString(), "UTF-8");
			} else if (type.equals(RENDERER_TYPE_TEXT_GZIP)) {
				renderer = new GzipRender(object, GzipRender.TYPE_TEXT, false);
			} else if (type.equals(RENDERER_TYPE_JSON_GZIP)) {
				renderer = new GzipRender(object, GzipRender.TYPE_JSON, false);
			} else if (type.equals(RENDERER_TYPE_BIN)) {
				BinaryRenderer binaryRenderer = new BinaryRenderer();
				binaryRenderer.setData((byte[]) object);
				binaryRenderer.setContentType(contentType);
				renderer = binaryRenderer;
			} else if (type.equals(RENDERER_TYPE_JSON_SKIP_NULL_GZIP)) {
				renderer = new GzipRender(object, GzipRender.TYPE_JSON, true);
			}else if(type.equals(RENDERER_TYPE_FILE)){
				File f = new File(object.toString());
				renderer = new FileRenderer(object.toString());
				renderer.setContentType(contentType);
				try {
                    this.getHttpServletResponse().addHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(f.getName(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
				this.getHttpServletResponse().addHeader("Content-Disposition", "attachment; filename="+f.getName());
                }
			}
		}

		return renderer;
	}

	public ServletInputStream getRequestInputStream() {
		ServletInputStream stream = null;
		try {
			stream = this.getHttpServletRequest().getInputStream();
		} catch (Exception exception) {
			this.logger.error("getRequestInputStream异常 ", exception);
		}
		return stream;
	}


	public String getWebRoot() {
		if (webroot == null || webroot.isEmpty()) {
			String url = this.getHttpServletRequest().getRequestURL()
					.toString();
			int start = url.indexOf("//") + 2;
			String protocol = url.substring(0, start);
			url = url.replace(protocol, "");
			String rootpath = url.substring(0, url.indexOf("/"));
			url = url.replaceAll("^" + rootpath + "/", "");
			String webroot_ = url.substring(0, url.indexOf("/"));
			this.webroot = protocol.concat(rootpath).concat("/")
					.concat(webroot_).concat("/");
		}
		return webroot;
	}

	public String getDomain() {
		String domain = "";
		HttpServletRequest request = this.getHttpServletRequest();
		int port = this.getPort();
		domain = request.getServerName();

		return domain;
	}

	public int getPort() {
		int port = 0;
		HttpServletRequest request = this.getHttpServletRequest();
		port = request.getServerPort();
		return port;
	}

	public String getDomainPort() {
		String domain = this.getDomain();
		int port = this.getPort();
		domain += port != 80 ? (":" + port) : "";
		return domain;
	}

	protected HttpServletResponse getHttpServletResponse() {
		return ActionContext.getActionContext().getHttpServletResponse();
	}

	protected HttpServletRequest getHttpServletRequest() {
		return ActionContext.getActionContext().getHttpServletRequest();
	}

	public boolean isGetMethod() {
		return getRequestMethod().equalsIgnoreCase("get");
	}

	public boolean isPostMethod() {
		return getRequestMethod().equalsIgnoreCase("post");
	}

	public String getRequestMethod() {
		return getHttpServletRequest().getMethod();
	}

	private String getParams() {
		HttpServletRequest request = this.getHttpServletRequest();
		StringBuilder params_buffer = new StringBuilder();
		Map<String, String[]> map = request.getParameterMap();
		Set<Entry<String, String[]>> iterator = map.entrySet();
		for (Entry<String, String[]> entry : iterator) {
			String param = entry.getKey();

		}
		return null;
	}

	public String getRequestParam(String name) {
		HttpServletRequest request = this.getHttpServletRequest();
		return request.getParameter(name);
	}

	public String getHttpReferer() {
		String referer = this.getHttpServletRequest().getHeader(
				HTTP_HEAD_REFERER);
		return referer;
	}

	/**
	 * 获得Http Head Referer属性
	 *
	 * @return 返回一个Map对象
	 */
	public Map<String, String> getHttpRefererMap() {
		String url = this.getHttpReferer();
		Map<String, String> map = HttpCommons.getRequestSearch2Map(url);
		return map;
	}

	public String getRequestInfo() {
		StringBuilder requestinfo_buffer = new StringBuilder();
		HttpServletRequest request = this.getHttpServletRequest();
		requestinfo_buffer.append(",").append("RequestURI:")
				.append(request.getRequestURI()).append(",").append("method:")
				.append(request.getMethod());
		return requestinfo_buffer.substring(1);
	}

	public String Object2JSON(Object target) {
		String json = null;
		try {
			json = mapper.writeValueAsString(target);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将json字符串内容转成对象
	 *
	 * @param str
	 * @param valueType
	 * @return
	 */
	public <T> T readValue(String str, Class<T> valueType) {
		T t = null;
		try {
			t = mapper.readValue(str, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return t;
	}
	public static class ControllerResult implements ControllerStandardResult
	{
		private String msg ;
		private String state ;
		private Object data;
		public ControllerResult() {

		}
		public ControllerResult(String msg, String state, Object data) {

			this.msg = msg;
			this.state = state;
			this.data = data;
		}			
			public String getMsg() {
				return msg;
			}
			public void setMsg(String msg) {
				this.msg = msg;
			}
			public String getState() {
				return state;
			}
			public void setState(String state) {
				this.state = state;
			}
			public Object getData() {
				return data;
			}
			public void setData(Object data) {
				this.data = data;
			}
	}
}

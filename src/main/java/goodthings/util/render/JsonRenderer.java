package goodthings.util.render;

import com.alibaba.fastjson.JSON;
import com.hylanda.service.http.webwind.renderer.Renderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonRenderer extends Renderer {
	private Object object;
	public JsonRenderer(Object o) {
		this.object = o;
		setContentType("application/json");
	}
	@Override
	public void render(ServletContext context, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
		StringBuilder sb = new StringBuilder(64);
		String characterEncoding = "UTF-8";
        sb.append(contentType)
          .append(";charset=")
          .append(characterEncoding);
        response.setContentType(sb.toString());
        
        PrintWriter pw = response.getWriter();
        JSON.writeJSONStringTo(object, pw);
        pw.flush();
        pw.close();
	}

}

package goodthings.controller;

import java.io.Serializable;

public interface Controller extends Serializable {
	/**
	 * 结尾不带斜杠
	 */
	public static final String SERVICE_PREFIX="/service";
	/**
	 * 参数名content
	 */
	public static final String PARAM_CONTNET="content";
	/**
	 * 结尾带斜杠
	 */
	public static final String SERVICE_PREFIX_SLASH=SERVICE_PREFIX+"/";
}

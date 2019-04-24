package goodthings.controller;

public interface ControllerStandardResult {
	/**
	 * 状态-成功
	 */
	public static final String STATE_SUCCEED = "0";
	/**
	 * 状态-错误
	 */
	public static final String STATE_ERROR = "-2";
	/**
	 * 状态-无效
	 */
	public static final String STATE_INVALID = "-1";

	/**
	 * 状态
	 *
	 * @return
	 */
	public String getState();

	/**
	 * 数据集合
	 *
	 * @return
	 */
	public Object getData();

	/**
	 * 信息内容
	 *
	 * @return
	 */
	public String getMsg();

}

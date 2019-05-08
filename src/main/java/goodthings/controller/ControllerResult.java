package goodthings.controller;

import com.alibaba.fastjson.JSONObject;

public class ControllerResult {
	public int code;
	public Object data;
	public String message;

    public ControllerResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ControllerResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

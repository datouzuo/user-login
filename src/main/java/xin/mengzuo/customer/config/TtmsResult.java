package xin.mengzuo.customer.config;

import java.io.Serializable;

public class TtmsResult implements Serializable{

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static TtmsResult build(Integer status, String msg, Object data) {
        return new TtmsResult(status, msg, data);
    }

    public static TtmsResult ok(Object data) {
        return new TtmsResult(data);
    }

    public static TtmsResult ok() {
        return new TtmsResult(null);
    }

    public TtmsResult() {

    }

    public static TtmsResult build(Integer status, String msg) {
        return new TtmsResult(status, msg, null);
    }

    public TtmsResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public TtmsResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

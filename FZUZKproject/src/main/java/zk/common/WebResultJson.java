package zk.common;

import lombok.Data;

/**
 * 通用的返回结果封装
 */
@Data
public class WebResultJson {
    /**
     *  1 返回成功
     *  0 返回失败
     */
    public final static int OK = 1;
    public final static String OK_MSG = "操作成功！";
    public final static int FAIL = 0;
    public final static String FAIL_MSG = "操作失败！";
 
 
    private int code;
    private String msg;
    private Object data;
 
 
    private WebResultJson(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
 
    public static WebResultJson ok(){
        return ok(OK_MSG,null);
    }
    public static WebResultJson ok(String msg){
        return new WebResultJson(OK,msg,null);
    }
    public static WebResultJson ok(Object data){
        return new WebResultJson(OK,OK_MSG,data);
    }
    public static WebResultJson ok(String msg,Object data){
        return new WebResultJson(OK,msg,data);
    }
 
 
    public static WebResultJson fail(){
        return fail(FAIL_MSG,null);
    }
    public static WebResultJson fail(String msg){
        return new WebResultJson(FAIL,msg,null);
    }
    public static WebResultJson fail(Object data){
        return new WebResultJson(FAIL,FAIL_MSG,data);
    }
    public static WebResultJson fail(String msg,Object data){
        return new WebResultJson(FAIL,msg,data);
    }
}
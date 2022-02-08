package common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServerResponse<T> implements Serializable {
    private int code;
    private String msg;
    private  T data;

    private ServerResponse(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    public ServerResponse(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    // 成功的方法
    public static <T> ServerResponse<T>success(){
        return new ServerResponse<T>(CodeEnum.SUCCESS.getCode(),CodeEnum.SUCCESS.getMsg());
    }
    public static <T> ServerResponse<T> success(T data){
        return new ServerResponse<T>(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(),data);
    }
    public static <T> ServerResponse<T> success(CodeEnum codeEnum,T data){
        return new ServerResponse<T>(codeEnum.getCode(), codeEnum.getMsg(),data);
    }

    //失败的方法
    public static <T> ServerResponse<T> error(){
        return new ServerResponse<T>(CodeEnum.ERROR.getCode(),CodeEnum.ERROR.getMsg());
    }

    public static <T>ServerResponse<T> error(CodeEnum codeEnum){
        return new ServerResponse<T>(codeEnum.getCode(),codeEnum.getMsg());
    }
}

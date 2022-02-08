package common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    //1.与用户相关的信息
    USER_LOGIN_ERROR(10001,"用户名或密码错误"),

    //2.通用的两个状态
    SUCCESS(1000,"success"),
    ERROR(2000,"error"),
    ;

    private int code;
    private String msg;
}

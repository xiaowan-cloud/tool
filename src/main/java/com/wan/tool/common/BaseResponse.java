package com.wan.tool.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wan
 * @version 1.0.0
 * @Description 基础返回实体类
 * @createTime 2021年10月12日 15:23:24
 */
@Data
public class BaseResponse<T> {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("描述信息")
    private String message;

    @ApiModelProperty("返回对象")
    private T data;

    public BaseResponse() {
        this.code = 200;
        this.message = "success";
    }

    public BaseResponse(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static int num = 0;
    public static void method1() throws InterruptedException {
        Runnable rb = () -> {
            for(int i=0;i<10;i++) {
                num ++;
                System.out.println(num);
            }
        };
        List<Thread> ths = new ArrayList<>();
        for(int i=0;i<5;i++) {
            Thread t = new Thread(rb);
            t.start();
            ths.add(t);
        }
        for(Thread t : ths) {
            t.join();
            System.out.println(t.getId() + " joined");
        }
        System.out.println("asdw"+num);
    }
}

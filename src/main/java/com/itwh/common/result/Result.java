package com.itwh.common.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 自定义响应结构
 */
@Data
public class Result {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public Result() {
    }

    public Result(Object data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    public Result(String message) {
        this.code = 200;
        this.msg = message;
        this.data = null;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.msg = message;
        this.data = null;
    }

    public Result(String message, Object data) {
        this.code = 200;
        this.msg = message;
        this.data = data;
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public static Result success(String message) {
        return new Result(message);
    }
    public static Result success(Object data) {
        return new Result(data);
    }
    public static Result success(String message, Object data) {
        return new Result(message, data);
    }

    public static Result error(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static Result error(Integer code, String message, Object data) {
        return new Result(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * JSON字符串转成 Result 对象
     * @param json
     * @return
     */
    public static Result format(String json) {
        try {
            return JSON.parseObject(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
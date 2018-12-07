package cc.myosotis.leisure.util;

/**
 * 返回数据模型
 */

public class Result {

    private String message;

    private Integer code;

    private Object data;

    public Result() {
    }

    public Result(String message, Integer code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result("true", 200, data);
    }

    public static Result error(String msg) {
        return new Result(msg, 201, null);
    }

    public static Result error(String msg, Object data) {
        return new Result(msg, 201, data);
    }

    public static Result callback(Register resource, RegisterData token, RegisterData data, Callback callback, Integer frequency) {
        System.out.println("尚未实现，提示：先实现Register");
        return null;
    }

}


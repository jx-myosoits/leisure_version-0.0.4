package cc.myosotis.leisure.util;

public interface Callback {

    /**
     * 成功的调用方法
     *
     * @return Result
     */
    Result success();

    /**
     * 错误的调用方法
     *
     * @return Result
     */
    Result error();

}

package com.zccp.commons.result;

/**
 * @author chenmeng
 * @date 2018-04-16 10:01
 * @desc
 */
public class Result<T> {
    private Boolean success;

    private int codeNum;

    private String codeDesc;

    private T value;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", codeNum=" + codeNum +
                ", codeDesc='" + codeDesc + '\'' +
                ", result=" + value +
                '}';
    }
}

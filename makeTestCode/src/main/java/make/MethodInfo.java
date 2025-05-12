package make;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
    private String testType;// OK, NG
    private boolean isStatic;//スタチックフラグ
    private String methodName;//メソッド名
    private String returnTypeName;//戻り値データ型
    private List<String> setParamList= new ArrayList<String>();//パラメータ設定処理部分
    private List<Param> paramList = new ArrayList<Param>();
    private String expected;
    private ExceptionInfo exceptionInfo= new ExceptionInfo();
    
    public MethodInfo() {}
    
    
    /**
     * @return exceptionInfo
     */
    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }


    /**
     * @param exceptionInfo セットする exceptionInfo
     */
    public void setExceptionInfo(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }


    /**
     * @return testType
     */
    public String getTestType() {
        return testType;
    }
    /**
     * @param testType セットする testType
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }
    /**
     * @return isStatic
     */
    public boolean isStatic() {
        return isStatic;
    }
    /**
     * @param isStatic セットする isStatic
     */
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
    /**
     * @return methodName
     */
    public String getMethodName() {
        return methodName;
    }
    /**
     * @param methodName セットする methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /**
     * @return returnTypeName
     */
    public String getReturnTypeName() {
        return returnTypeName;
    }
    /**
     * @param returnTypeName セットする returnTypeName
     */
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }
    /**
     * @return setParamList
     */
    public List<String> getSetParamList() {
        return setParamList;
    }
    /**
     * @param setParamList セットする setParamList
     */
    public void setSetParamList(List<String> setParamList) {
        this.setParamList = setParamList;
    }
    /**
     * @return paramList
     */
    public List<Param> getParamList() {
        return paramList;
    }
    /**
     * @param paramList セットする paramList
     */
    public void setParamList(List<Param> paramList) {
        this.paramList = paramList;
    }
    /**
     * @return expected
     */
    public String getExpected() {
        return expected;
    }
    /**
     * @param expected セットする expected
     */
    public void setExpected(String expected) {
        this.expected = expected;
    }

    
}

package make;

import java.util.ArrayList;
import java.util.List;

public class EditInfo {
    
    private String className;
    private String methodName;
    private String returnTypeName;	//テスト対象メソッド戻り値のデータ型
    boolean isStatic ;				//スタティックフラグ
    private String mkinstance;		//インスタンス生成部分
//    private ArrayList<String> paramTypeNameList = new ArrayList<String>();//テスト対象メソッドパラメータデータ型
    private List<Param> paramList = new ArrayList<Param>();

    private String invoke;		//テスト対象メソッド呼び出し部分
    private String paramDeclare;//テスト対象メソッドパラメータ宣言部分
    private List<String> setParamList= new ArrayList<String>();	//テスト対象メソッドパラメータ設定処理部分
    
    private String paramText;	//テスト対象メソッド呼び出し部分のパラメータ
    private Object expected;	//期待値設定部分
    private String examine;		//検証部分
    private String testType;// ok,ng
    private List<String> importTxtList = new ArrayList<String>();//import文
    private ExceptionInfo exceptionInfo = new ExceptionInfo();

    
    
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
     * @return importTxtList
     */
    public List<String> getImportTxtList() {
        return importTxtList;
    }
    /**
     * @param importTxtList セットする importTxtList
     */
    public void setImportTxtList(List<String> importTxtList) {
        this.importTxtList = importTxtList;
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
     * インスタンス生成部分
     * @return mkinstance
     */
    public String getMkinstance() {
        return mkinstance;
    }
    /**
     * インスタンス生成部分
     * @param mkinstance セットする mkinstance
     */
    public void setMkinstance(String mkinstance) {
        this.mkinstance = mkinstance;
    }
    /**
     * @return examine
     */
    public String getExamine() {
        return examine;
    }
    /**
     * @param examine セットする examine
     */
    public void setExamine(String examine) {
        this.examine = examine;
    }
    /**
     * @return invoke
     */
    public String getInvoke() {
        return invoke;
    }
    /**
     * @param invoke セットする invoke
     */
    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
    /**
     * @return paramText
     */
    public String getParamText() {
        return paramText;
    }
    /**
     * @param paramText セットする paramText
     */
    public void setParamText(String paramText) {
        this.paramText = paramText;
    }
    /**
     * @return paramDeclare
     */
    public String getParamDeclare() {
        return paramDeclare;
    }
    /**
     * @param paramDeclare セットする paramDeclare
     */
    public void setParamDeclare(String paramDeclare) {
        this.paramDeclare = paramDeclare;
    }
    /**
     * @return expected
     */
    public Object getExpected() {
        return expected;
    }
    /**
     * @param expected セットする expected
     */
    public void setExpected(Object expected) {
        this.expected = expected;
    }
    /**
     * @return className
     */
    public String getClassName() {
        return className;
    }
    /**
     * @param className セットする className
     */
    public void setClassName(String className) {
        this.className = className;
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
    public void setReturnTypeName(String typeName) {
        this.returnTypeName = typeName;
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
    
    
}

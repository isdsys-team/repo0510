package make;

import java.util.ArrayList;
import java.util.List;

public class TestConfig {
    private String packageName;
    private String className;
    private List<String> importTxtList= new ArrayList<String>();
    private List<MethodInfo> methodInfoList = new ArrayList<MethodInfo>();
    
    public TestConfig(List<String> _importTxtList, List<MethodInfo> _methodInfoList, List<Param> _paramList, String _expected) {
        this.importTxtList= _importTxtList;
        this.methodInfoList = _methodInfoList;
    }

    public TestConfig(){
    }

    
    /**
     * @return packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName セットする packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
     * @return methodIndoList
     */
    public List<MethodInfo> getMethodInfoList() {
        return methodInfoList;
    }
    /**
     * @param methodIndoList セットする methodIndoList
     */
    public void setMethodInfoList(List<MethodInfo> _methodInfoList) {
        this.methodInfoList = _methodInfoList;
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

    
}

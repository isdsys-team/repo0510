package make;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class TargetClassInfo {

    private String packageName;
    private String className;
    private List<Method> methodList = new ArrayList<Method>();
    
    
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
     * @return methodList
     */
    public List<Method> getMethodList() {
        return methodList;
    }
    /**
     * @param methodList セットする methodList
     */
    public void setMethodList(List<Method> methodList) {
        this.methodList = methodList;
    }
 
    
}

package make;

public class Param {
    private String typeName;
    private String value;
    
    public Param(){}

    public Param(String _typeName, String _value) {
        this.typeName= _typeName;
        this.value= _value;
    }
    /**
     * @return typeName
     */
    public String getTypeName() {
        return typeName;
    }
    /**
     * @param typeName セットする typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    /**
     * @return value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value セットする value
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}

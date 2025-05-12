package make;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.exception.AppException;
import common.util.StringUtilExt;
import common.util.XmlUtil;

public class TestConfigData {
    static public final String INDENT = "    ";
    static public final String INDENT2 = INDENT + INDENT;

    private ArrayList<TestConfig> testConfigList = new ArrayList<TestConfig>();
    private static String testCodeConfigPath;

    
    /**
     * @return testCodeConfigPath
     */
    public static String getTestCodeConfigPath() {
        return testCodeConfigPath;
    }

    /**
     * @param testCodeConfigPath セットする testCodeConfigPath
     */
    public static void setTestCodeConfigPath(String _testCodeConfigPath) {
        TestConfigData.testCodeConfigPath = _testCodeConfigPath;
    }

    /**
     * @return testConfigList
     */
    public ArrayList<TestConfig> getTestConfigList() {
        return testConfigList;
    }

    /**
     * @param testConfigList セットする testConfigList
     */
    public void setTestConfigList(ArrayList<TestConfig> testConfigList) {
        this.testConfigList = testConfigList;
    }

    /**
     * 
     * @throws AppException
     */
    public void getConfig(String configPath) throws AppException {
        XmlUtil xmlUtil = null;
        TestConfig testConfig = new TestConfig();

//        List<TestConfig> testConfigList = new ArrayList<TestConfig>();

        try {
            xmlUtil = new XmlUtil(configPath);
        } catch (ParserConfigurationException e) {
           
            throw new AppException("テストコード定義情報取得に失敗しました。", e);
        } catch (SAXException e) {
            
            throw new AppException("テストコード定義情報取得に失敗しました。", e);
        } catch (IOException e) {
           
            throw new AppException("テストコード定義情報取得に失敗しました。", e);
        }
        Element root = xmlUtil.getDocument().getDocumentElement();
        NodeList rootChildList = root.getChildNodes();
        
        //テスト対象パッケージ名、テスト対象クラス名取得
        for (int i = 0; i < rootChildList.getLength(); i++) {
            Node node = (Node) rootChildList.item(i);
            String nodeName = node.getNodeName();
            if(nodeName.equals("TestCode")) {
                Element element = (Element)node;
                String pkgName = element.getAttribute("package");
                String className = element.getAttribute("class");
                testConfig.setPackageName(pkgName);
                testConfig.setClassName(className);
                break;
            }
        }
        NodeList testCodeList = root.getElementsByTagName("TestCode");

        // TestCodeタグ内容取得
        for (int i = 0; i < testCodeList.getLength(); i++) {
            Element element = (Element) testCodeList.item(i);
            // importTxtタグ内容取得
            List<String> importTxtList = new ArrayList<String>();
            NodeList importNodeTxtList = element.getElementsByTagName("importTxt");
            for (int j = 0; j < importNodeTxtList.getLength(); j++) {
                Element elemImport = (Element) importNodeTxtList.item(j);
                // importTxt取得
                String importTxt = elemImport.getTextContent();
                importTxtList.add(importTxt);
            }
            testConfig.setImportTxtList(importTxtList);

            // methodタグ内容取得（必須）
            List<MethodInfo> methodInfoList = new ArrayList<MethodInfo>();
            NodeList methodNodeList = element.getElementsByTagName("method");
            for (int j = 0; j < methodNodeList.getLength(); j++) {
                MethodInfo methodInfo = new MethodInfo();
                Element elemMethod = (Element) methodNodeList.item(j);
                // メソッド定義情報設定
                methodInfo = setMethodInfo(elemMethod, methodInfo);

                // setparamタグ内容取得(任意)
                List<String> setParamList = new ArrayList<String>();
                NodeList setParamNodeList = elemMethod.getElementsByTagName("setparam");
                getSetParamTagContent(setParamNodeList, setParamList);
                methodInfo.setSetParamList(setParamList);

                // paramタグ内容取得（必須）
                List<Param> paramList = new ArrayList<Param>();
                NodeList paramNodeList = elemMethod.getElementsByTagName("param");
                getParamTagContent(paramNodeList, paramList);
                methodInfo.setParamList(paramList);

                //exceptionタグ内容取得
                NodeList exceptionNodeList = elemMethod.getElementsByTagName("exception");
                ExceptionInfo expInfo = getExceptionTagContent(exceptionNodeList);
                methodInfo.setExceptionInfo(expInfo);

                methodInfoList.add(methodInfo);
                
            }
            testConfig.setMethodInfoList(methodInfoList);
            testConfigList.add(testConfig);
        }
    }

    /**
     * setparamタグ内容取得
     * 
     * @param setParamNodeList
     * @param setParamList
     * @throws AppException
     */
    private void getSetParamTagContent(NodeList setParamNodeList, List<String> setParamList) throws AppException {
        for (int k = 0; k < setParamNodeList.getLength(); k++) {
            Element elemSetParam = (Element) setParamNodeList.item(k);

            String setParam = elemSetParam.getTextContent();
            setParam =StringUtilExt.trimHanZen(setParam);
            
            if ((setParam == null) || (setParam.length() == 0)) {
                throw new AppException("メソッド定義情報(タグ：setParam、コンテンツが不正です。");
            }
            setParamList.add(setParam);
        }

    }

    /**
     * paramタグ内容取得
     * 
     * @param paramNodeList paramタグノードリスト
     * @param paramList     出力リスト
     * @throws AppException
     */
    private void getParamTagContent(NodeList paramNodeList, List<Param> paramList) throws AppException {
        for (int k = 0; k < paramNodeList.getLength(); k++) {
            Param param = new Param();
            Element elemParam = (Element) paramNodeList.item(k);
            String type = elemParam.getAttribute("type");
            String value = elemParam.getAttribute("value");
            if ((type == null) || (type.length() == 0)) {
                throw new AppException("メソッド定義情報(タグ：param、属性：type)が不正です。");
            }
            if ((value == null) || (value.length() == 0)) {
                throw new AppException("メソッド定義情報(タグ：param、属性：value)が不正です。");
            }
            param.setTypeName(type);
            if (type.equals("String")) {
                value = "\"" + value + "\"";
            }
            param.setValue(value);
            paramList.add(param);
        }

    }
    private ExceptionInfo getExceptionTagContent(NodeList exceptionNodeList) throws AppException {
        ExceptionInfo expInfo = null;
        for (int k = 0; k < exceptionNodeList.getLength(); k++) {
            expInfo = new ExceptionInfo();
            Element elemException = (Element) exceptionNodeList.item(k);
            String type = elemException.getAttribute("type");
            String msg = elemException.getAttribute("msg");
            if ((type == null) || (type.length() == 0)) {
                throw new AppException("メソッド定義情報(タグ：exception、属性：type)が不正です。");
            }
            if ((msg == null) || (msg.length() == 0)) {
                throw new AppException("メソッド定義情報(タグ：exception、属性：msg)が不正です。");
            }
            expInfo.setType(type);
            expInfo.setMessage(msg);
            break;
        }
        return expInfo;
    }

    private MethodInfo setMethodInfo(Element elemMethod, MethodInfo methodInfo) throws AppException {
//        TestConfig testConfig = new TestConfig();
        String methodName = elemMethod.getAttribute("methodName");
        if ((methodName == null) || (methodName.length() == 0)) {
            throw new AppException("メソッド定義情報(タグ：method、属性：methodName)が不正です。");
        }
        methodInfo.setMethodName(methodName);

        String isStatic = elemMethod.getAttribute("isStatic");
        if ((isStatic == null) || (isStatic.length() == 0)) {
            throw new AppException("メソッド定義情報(タグ：method、属性：isStatic)が不正です。");
        }
        if (isStatic.equals("true")) {
            methodInfo.setStatic(true);
        } else if (isStatic.equals("false")) {
            methodInfo.setStatic(false);
        }

        String returnTypeName = elemMethod.getAttribute("returnTypeName");
        if ((returnTypeName == null) || (returnTypeName.length() == 0)) {
            throw new AppException("メソッド定義情報(タグ：method、属性：returnTypeName)が不正です。");
        }
        methodInfo.setReturnTypeName(returnTypeName);

        String testType = elemMethod.getAttribute("testType");
        if ((testType == null) || (testType.length() == 0)) {
            throw new AppException("メソッド定義情報(タグ：method、属性：testType)が不正です。");
        }
        methodInfo.setTestType(testType);

        String expected = elemMethod.getAttribute("expected");
        if ((expected == null) || (expected.length() == 0)) {
            throw new AppException("メソッド定義情報(タグ：method、属性：expected)が不正です。");
        }
        methodInfo.setExpected(expected);

        return methodInfo;
    }
}

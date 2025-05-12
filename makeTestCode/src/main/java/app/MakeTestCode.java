package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.constant.SysConst;
import common.exception.AppException;
import common.util.FileUtil;
import make.EditInfo;
import make.ExceptionInfo;
import make.MethodInfo;
import make.Param;
import make.TargetClassInfo;
import make.TestConfig;
import make.TestConfigData;

public class MakeTestCode {
    static Logger logger = LoggerFactory.getLogger(MakeTestCode.class);

    /** 空文字列 */
    static public final String EMPTY = SysConst.EMPTY;
    static public final String SEMICOLON = SysConst.SEMICOLON;
    static public final String COMMA = SysConst.COMMA;
    static public final String PERIOD = SysConst.PERIOD;
    static public final String HAN_SPACE = SysConst.HAN_SPACE;
    static public final String CRLF = SysConst.CRLF;

//    private static String testCodeConfigPath;

    private TestConfigData testConfigData = new TestConfigData();

    /**
     * @return testConfigData
     */
    public TestConfigData getTestConfigData() {
        return testConfigData;
    }

    private String templateMethodPath = "template/templateMethod.txt";
    private String templateMainPath = "template/templateMain.txt";

    /**
     * @param info テスト対象クラス情報
     * @param info
     * @throws AppException
     * @throws ClassNotFoundException
     */
    public String make(TargetClassInfo classInfo) throws AppException{
        // 出力バッファ
        String mainBuff = EMPTY;

        ArrayList<EditInfo> editInfoList = new ArrayList<EditInfo>();

//        TestConfigData.setTestCodeConfigPath(testCodeConfigPath);
        testConfigData.getConfig(MakeTestCodeMain.appConfigMap.get("testCodeConfigPath"));

        for (TestConfig testConfig : testConfigData.getTestConfigList()) {
            // 編集情報設定
            for (MethodInfo methodInfo : testConfig.getMethodInfoList()) {
                EditInfo editInfo = new EditInfo();
                // クラス名設定
                editInfo.setClassName(testConfig.getClassName());
                // メソッド名設定
                editInfo.setMethodName(methodInfo.getMethodName());
                // 戻り値の型名を設定
                editInfo.setReturnTypeName(methodInfo.getReturnTypeName());
                // パラメータの型と値を設定
                ArrayList<Param> paramList = new ArrayList<Param>();
                paramList.addAll(0, methodInfo.getParamList());
                editInfo.setParamList(paramList);
                // スタティックフラグ設定
                editInfo.setStatic(methodInfo.isStatic());
                // 期待値設定
                editInfo.setExpected(methodInfo.getExpected());
                // パラメータ設定処理を設定
                ArrayList<String> setParamList = new ArrayList<String>();
                setParamList.addAll(0, methodInfo.getSetParamList());
                editInfo.setSetParamList(setParamList);
                // テスト種別
                editInfo.setTestType(methodInfo.getTestType());
                // exception情報
                editInfo.setExceptionInfo(methodInfo.getExceptionInfo());
                // リストに追加
                editInfoList.add(editInfo);
            }
            // import文
            ArrayList<String> importTxtList = new ArrayList<String>();
            importTxtList.addAll(0, testConfig.getImportTxtList());

            testConfig.setImportTxtList(importTxtList);
        }
        // テストコード編集
        mainBuff = editCode(testConfigData, classInfo, editInfoList);

        return mainBuff;
    }

    /**
     * テストコード編集
     * 
     * @param editInfoList 編集情報リスト
     * @throws AppException
     */
    private String editCode(TestConfigData testConfigData, TargetClassInfo classInfo, ArrayList<EditInfo> editInfoList)
            throws AppException {
//      
        // シュツリョクバッファ
        String mainBuff = EMPTY;
        String testMethodName = EMPTY;

        try {

            String templateMain = FileUtil.loadText(templateMainPath, "utf-8");
            String templateMethod = EMPTY;
            StringBuffer sbMethod = new StringBuffer();
            for (EditInfo editInfo : editInfoList) {

                Random rand = new Random();
                String suffix = String.valueOf(rand.nextInt(1000) + 100);
                testMethodName = "test" + StringUtils.capitalize(editInfo.getMethodName()) + "_"
                        + editInfo.getTestType() + "_" + suffix;

                logger.debug("MakeTestCode::editCode() testMethodName: " + testMethodName);

                // パラメータ宣言部編集
                // String param1;など
                editParamDeclare(classInfo, editInfo);

                templateMethod = FileUtil.loadText(templateMethodPath, "utf-8");
                // インスタンス生成部分
                editMkInstance(editInfo);

                boolean isExistsExcption = isExsitsException(editInfo);
                // テスト対象呼び出し部分
                editInvoke(editInfo, isExistsExcption);
                // 検証部分
                editInfo.setExamine(editExamine(editInfo, isExistsExcption));

                List<TestConfig> testConfigList = testConfigData.getTestConfigList();
                mainBuff = editImport(templateMain, editInfo, classInfo, testConfigList.get(0));

                // メソッド部分編集
                String methodBuff = editMethodPart(editInfo, templateMethod, testMethodName);
                sbMethod.append(methodBuff);
                logger.debug("sbMethod: " + sbMethod.toString());

            }
            ArrayList<TestConfig> testConfiglist = testConfigData.getTestConfigList();
            mainBuff = editMain(mainBuff, testConfiglist.get(0), sbMethod);

        } catch (IOException e) {
            throw new AppException("template ファイルの読み込みに失敗しました。", e);

        }
        return mainBuff;

    }

    private String editImport(String templateMain, EditInfo editInfo, TargetClassInfo classInfo,
            TestConfig testConfig) {
        String mainBuff = templateMain;

        // import 編集
        StringBuffer sbImport = new StringBuffer();

        for (String importTxt : testConfig.getImportTxtList()) {
            sbImport = sbImport.append(importTxt).append(SysConst.CRLF);
        }
        mainBuff = mainBuff.replaceAll("#import", sbImport.toString());
        return mainBuff;
    }

    private String editMain(String mainBuff, TestConfig testConfig, StringBuffer sbMethod) {

        mainBuff = mainBuff.replaceAll("#package", testConfig.getPackageName());
        mainBuff = mainBuff.replaceAll("#testClassName", testConfig.getClassName() + "Test");
        mainBuff = mainBuff.replaceAll("#testMethod", sbMethod.toString());

        return mainBuff;
    }

    /**
     * インスタンス生成部分の編集
     * 
     * @param editInfo
     */
    private void editMkInstance(EditInfo editInfo) {
        StringBuffer sbMkinstance = new StringBuffer();

        if (!editInfo.isStatic()) {
            sbMkinstance = sbMkinstance.append(editInfo.getClassName()).append(HAN_SPACE).append("obj = new ").append(editInfo.getClassName());
            sbMkinstance = sbMkinstance.append("()").append(SEMICOLON);
        }else {
            sbMkinstance = sbMkinstance.append(AppConst.DUMMY_LINE);
        }
        editInfo.setMkinstance(sbMkinstance.toString());
    }

    /**
     * テスト対象呼び出し部分の編集
     * 
     * @param editInfo
     */
    private void editInvoke(EditInfo editInfo, boolean isExistsException) {
        StringBuffer sbInvoke = new StringBuffer();

        // テスト対象呼び出し部分
        if (isExistsException) {// exception有りならば
            sbInvoke = sbInvoke.append("try {").append(SysConst.CRLF);
        }

        String typeName = editInfo.getReturnTypeName();
        if (editInfo.isStatic()) {
            if (isExistsException) {
                sbInvoke = sbInvoke.append(AppConst.INDENT2).append(AppConst.INDENT);
            }
            sbInvoke = sbInvoke.append(typeName).append(" rslt = ").append(editInfo.getClassName()).append(PERIOD)
                    .append(editInfo.getMethodName());
            sbInvoke = sbInvoke.append("(").append(editInfo.getParamText()).append(");");
        } else {
            sbInvoke = sbInvoke.append(typeName).append(" rslt = obj.").append(editInfo.getMethodName()).append("(");
            sbInvoke = sbInvoke.append(editInfo.getParamText()).append(");");
        }
        if (isExistsException) {
            sbInvoke = sbInvoke.append("{").append(SysConst.CRLF);
            sbInvoke = sbInvoke.append(AppConst.INDENT2).append("catch(").append(editInfo.getExceptionInfo().getType())
                    .append(" e) {");
        }
        editInfo.setInvoke(sbInvoke.toString());

    }

    /**
     * exception 存在判定
     * 
     * @param editInfo
     * @return
     */
    private boolean isExsitsException(EditInfo editInfo) {
        boolean isExists = false;
        ExceptionInfo expInfo = editInfo.getExceptionInfo();
        if (expInfo == null) {
            return isExists;
        }
        String type = expInfo.getType();

        if ((type != null) && (type.length() > 0)) {// exception有りならば
            isExists = true;
        }
        return isExists;
    }

    /**
     * 検証部分の編集
     * 
     * @param typeName 戻り値のデータ型
     * @param editInfo
     * @return
     */
    private String editExamine(EditInfo editInfo, boolean isExistsException) {
        String expected = EMPTY;
        String typeName = editInfo.getReturnTypeName();

        // 期待値設定部分
        if (typeName.equals("String")) {
            expected = typeName + HAN_SPACE + "expected = " + "\"" + (String) editInfo.getExpected() + "\"" + SEMICOLON
                    + CRLF;
        } else {
            expected = typeName + HAN_SPACE + "expected = " + (String) editInfo.getExpected() + SEMICOLON + CRLF;
        }
        // 検証部分
        StringBuffer sbExamine = new StringBuffer();
        
        if (editInfo.getReturnTypeName().equals("double")) {
            sbExamine = sbExamine.append(expected);
            if (isExistsException) {// exception有りならば
                sbExamine = sbExamine.append(AppConst.INDENT);
            }
            sbExamine = sbExamine.append(AppConst.INDENT2).append("assertEquals(expected , rslt, 0.0);");
        } else {
            sbExamine = sbExamine.append(AppConst.INDENT).append(expected);
            if (isExistsException) {// exception有りならば
                ExceptionInfo expInfo = editInfo.getExceptionInfo();
                sbExamine = sbExamine.append(AppConst.INDENT);
                sbExamine = sbExamine.append(AppConst.INDENT2).append("assertEquals(").append("\"").append(expInfo.getMessage())
                        .append("\"");
                sbExamine = sbExamine.append(", e.getMessage());");
            } else {
                sbExamine = sbExamine.append(AppConst.INDENT2).append("assertEquals(expected , rslt);");
            }
        }
        if (isExistsException) {// exception有りならば
            sbExamine = sbExamine.append(SysConst.CRLF).append(AppConst.INDENT2).append("}");
        }
        return sbExamine.toString();
    }

    /**
     * メソッド部分編集
     * 
     * @param templateMethod メソッド部分テンプレート
     * @param testMethodName テストコードメソッド名
     * @param mkinstance     インスタンス生成部分
     * @return 編集結果
     */
    private String editMethodPart(EditInfo editInfo, String templateMethod, String testMethodName) {

        String methodBuff = templateMethod;

        methodBuff = methodBuff.replaceAll("#testMethodName", testMethodName);

        methodBuff = methodBuff.replaceAll("#mkinstance", editInfo.getMkinstance());
        methodBuff = methodBuff.replaceAll("#paramDeclare", editInfo.getParamDeclare());

        StringBuffer sb = new StringBuffer();
        editInfo.getSetParamList();
        for (String setParam : editInfo.getSetParamList()) {
            sb = sb.append(setParam).append(SysConst.CRLF);
        }
        methodBuff = methodBuff.replaceAll("#setParam", sb.toString());

        methodBuff = methodBuff.replaceAll("#invoke", editInfo.getInvoke());

        methodBuff = methodBuff.replaceAll("#examine", editInfo.getExamine());

        return methodBuff;
    }

    /**
     * パラメータ宣言部編集
     * 
     * @return
     */
    private void editParamDeclare(TargetClassInfo classInfo, EditInfo info) {

        //
        // String param1;など
        StringBuffer sbParams = new StringBuffer();
//        ArrayList<String> list = new ArrayList<String>();
//        list = info.getParamTypeNameList();// パラメータのデータ型リスト

        String paramText = EMPTY;
        String initValue = EMPTY;
        List<Param> paramList = info.getParamList();

        int k = 0;
        for (Param param : paramList) {
//        for (int k = 0; k < params.length; k++) {
            StringBuffer sbParam = new StringBuffer();
            String typeName = param.getTypeName();
            initValue = param.getValue();
            if (initValue.equals("\"" + AppConst.NULL_MARK + "\"")) {
                initValue = "null";
            }
            if (initValue.equals("\"" + AppConst.EMPTY_MARK + "\"")) {
                initValue = "\"\"";
            }
            if (k > 0) {
                sbParam.append(AppConst.INDENT2);
            }
            sbParam.append(typeName).append(HAN_SPACE).append("param").append(String.valueOf(k + 1)).append("=")
                    .append(initValue).append(SEMICOLON).append(System.lineSeparator());
            sbParams.append(sbParam);
            if (paramText.length() == 0) {
                paramText = "param" + String.valueOf(k + 1);// param1

            } else {
                paramText = paramText + "," + "param" + String.valueOf(k + 1);// param1, param2,...,paramn
            }
            // 添え字更新
            k++;
        }
        info.setParamText(paramText);
        info.setParamDeclare(sbParams.toString());

        return;
    }

    /**
     * パラメータ宣言部以外の編集
     * 
     * @param classInfo
     * @param info
     */
//    private void editOtherPart(TargetClassInfo classInfo, EditInfo editInfo) {
//        if (editInfo.isStatic()) {
////          mkinstance = editInfo.getClassName()+ " "+ "obj = new "+editInfo.getClassName()+ "();";
//
//            // テスト対象呼び出し部分
//            String typeName = removePackage(editInfo.getTypeName(),"java.lang.") ;// パッケージ部分を取り除く
//            invoke = typeName + " rslt = " + classInfo.getClassName() + PERIOD + editInfo.getMethodName() + "(" + editInfo.getParamText() + ");";
//            // 検証部分
//            examine = "assertEquals(" + editInfo.getExpected() + ", rslt);";
//
//        } else {
//            // インスタンス生成部分
//            mkinstance = classInfo.getClassName() + " " + "obj = new " + classInfo.getClassName() + "();";
//            // テスト対象呼び出し部分
//            String typeName = removePackage(editInfo.getTypeName(),"java.lang.") ; // パッケージ部分を取り除く
//            invoke = typeName + " rslt = obj." + editInfo.getMethodName() + "(" + editInfo.getParamText() + ");";
//            // 検証部分
//            examine = "assertEquals(" + editInfo.getExpected() + ", rslt);";
//        }
//    }
    /**
     * 
     * @param typeName データ型名（パッケージふｋ
     * @return
     */
//    private String removePackage(String typeName) {
//        String rslt = typeName;
//
//        logger.debug("removePackage: typeName : " + typeName);
//
//        // "java.lang." を取り除く
//        String pkgName = "java.lang.";
//        if (typeName.startsWith(pkgName)) {
//            rslt = typeName.substring(pkgName.length(), typeName.length());
//        }
//        return rslt;
//
//    }
}

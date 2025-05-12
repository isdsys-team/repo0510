package app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.constant.SysConst;
import common.exception.AppException;
import common.util.FileUtil;
import common.util.PropertyUtil;
import make.TargetClassInfo;
import make.TestConfig;
import make.TestConfigData;

public class MakeTestCodeMain {
    static Logger logger = LoggerFactory.getLogger(MakeTestCodeMain.class);

//    private static String configFileName = "MakeTestCode.xml";
//    private static String configPath = "C:\\pleiades\\2022-03\\workspace\\makeTestCode\\src\\main\\resources\\MakeTestCode.xml";
//    private static String testCodeConfigPath;
    private static MakeTestCode mk = new MakeTestCode();
    private static TargetClassInfo info = new TargetClassInfo();

    private static PropertyUtil propUtil = new PropertyUtil();

    public static Map<String, String> appConfigMap = new HashMap<String, String>() ;
    public static Map<String, String> msgMap = new HashMap<String, String>() ;

    private static String logmsg ;
    /**
     * メイン処理
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ

        try {
//            String classPath = MakeTestCodeMain.class.getResource("MakeTestCodeMain.java").toString();
//            int idx = classPath.indexOf("MakeTestCodeMain.class");
//            String classDir = classPath.substring(0, idx);
            
            Path p1 = Paths.get("");
            Path p2 = p1.toAbsolutePath();

            System.out.println(p2.toString());
            
            getConfig();
            
            String mainBuff = mk.make(info);

//            mainBuff = adjustBuff(mainBuff);

            // 編集結果保存
            TestConfigData testConfigData=mk.getTestConfigData();
            ArrayList<TestConfig> testConfigList= testConfigData.getTestConfigList();
            TestConfig testConfig= testConfigList.get(0);
            String fpath = appConfigMap.get("outputDir") + System.getProperty("file.separator") + testConfig.getClassName() + "Test.java";
            //ダミー行削除
            //"@dummyLine
            mainBuff = removeDummyLine(mainBuff);
            FileUtil.saveText(fpath, mainBuff, false, "utf-8");

            logger.info(fpath +"を作成しました。");
            logger.info(MsgConst.NORMAL_APP_END);

        } catch (AppException e) {
            // TODO 自動生成された catch ブロック
            logger.error(e.getMessage() + getStackTrace(e));
            System.out.println(MsgConst.ERROR_APP_END);

        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            logger.error(e.getMessage() + getStackTrace(e));
            System.out.println(MsgConst.ERROR_APP_END);
        }
    }
    /**
     * dummyLineをバッファから削除。
     * @param mainBuff バッファ
     * @throws IOException
     * 
     */
    private static String removeDummyLine(String mainBuff) throws IOException {
        InputStream is = new ByteArrayInputStream(mainBuff.getBytes(StandardCharsets.UTF_8));

//        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader bfr = new BufferedReader(isr);

        StringBuffer sbwrite = new StringBuffer();
        String line = SysConst.EMPTY;
        while ((line = bfr.readLine()) != null) {
            if (line.indexOf(AppConst.DUMMY_LINE) < 0)  {
                sbwrite.append(line).append(SysConst.CRLF);
            }
        }
        bfr.close();
        isr.close();
        is.close();

        return sbwrite.toString();

    }

    /**
     * 定義情報取得
     * 
     * @throws AppException
     */
    private static void getConfig() throws AppException {
        //システム定義情報取得
        appConfigMap.put("outputDir", "");
        appConfigMap.put("testCodeConfigPath", "");
        String propPath = AppConst.APPCONFIG_PATH;
        getProperty(propPath, appConfigMap );
        
        //メッセージ定義情報取得
        msgMap.put("ERROR_FILE_IO", "");
        msgMap.put("ERROR_FILE_NOT_FOUND", "");
        msgMap.put("ERROR_PROPERTY", "");
        
        String msgPath = AppConst.MSGCONFIG_PATH;	;
        getProperty(msgPath, msgMap);

    }
    /**
     * プロパティ情報取得
     * @param fpath プロパティファイルのフルパス
     * @param propMap　プロパティのキー情報のみ設定されているマップ、本メソッドでピウロパティファイルから取得したプロパティ値を設定する
     * @throws AppException　アプリケーション例外
     */
    private static void getProperty(String propPath, Map<String,String> propMap)throws AppException{

        Properties prop = new Properties();
//        InputStream is;
        try {
//            is = new FileInputStream(propPath);
            prop = propUtil.loadByPath(propPath);

            //propMapに定義してあるキー値から、プロパティファイルを読み、プロパティ値を取得しpropMapに値を設定する
            for (Iterator<String> itr = propMap.keySet().iterator(); itr.hasNext();) {
                String key = itr.next();
                if(prop.getProperty(key)==null) {
                    logmsg = MessageFormat.format(MsgConst.ERROR_PROPERTY,key);
                    throw new AppException(logmsg);
                }
                else {
                    propMap.put(key, prop.getProperty(key));
                    logger.debug("app.MakeTestCodeMain::getProp() "+ "key= "+key + " value= "+prop.getProperty(key));
                }
            }
        } catch (FileNotFoundException e) {
            logmsg = MessageFormat.format(MsgConst.ERROR_FILE_NOT_FOUND, propPath);
            throw new AppException(logmsg);
            
        } catch (InvalidPropertiesFormatException e) {
            // TODO 自動生成された catch ブロック
            throw new AppException(MsgConst.ERROR_PROPERTY_FORMAT);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            throw new AppException(MsgConst.ERROR_FILE_IO);
        }
        
    }

    private static String getStackTrace(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append(System.lineSeparator());
        if (e.getCause() != null) {
            StackTraceElement[] elems = e.getCause().getStackTrace();
            for (int i = 1; i < elems.length; i++) {
                String tmp = elems[i].toString();
                sb.append(tmp).append(System.lineSeparator());
            }
        }
        return sb.toString();

    }
}

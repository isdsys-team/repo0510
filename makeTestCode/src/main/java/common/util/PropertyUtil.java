package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定義ファイルは、クラスパス定義とする<br>
 * <pre>
 * 説明：定義データを取得する際に、プロパティ名称を指定しなければならないため、プロパティ名称は、
 * 　　プログラム定数として定義する必要がある。
 * </pre>
 *
 * @author yuki
 *
 */
public class PropertyUtil {
    Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    /* プロパティ定義マップ　キー：ファイル名（拡張子なし）、値：Propertiesオブジェクト */
    private Hashtable<String, Properties> propMap = new Hashtable<String, Properties>();

    /**
     * 定義情報（複数）の取得<BR>
     * 　
     * <pre>
     * 【概要】定義名を指定して、定義情報（複数）を取得し、プロパティ定義マップ（propMap
     * 　　キー：定義名、値：Propertiesオブジェクト）に格納
     * </pre>
     *
     * @param confFilename　定義名称の定義ファイル名
     * @throws IOException
     * @throws InvalidPropertiesFormatException
     */
    public Hashtable<String, Properties> loadConfigByName(String confFilename)
            throws InvalidPropertiesFormatException, IOException {

        Properties propCfg = new Properties();

        InputStream stream = ClassLoader.getSystemResourceAsStream(confFilename);

        propCfg.loadFromXML(stream);
        stream.close();

        Enumeration<?> cfgnameList = propCfg.propertyNames();

        while (cfgnameList.hasMoreElements()) {
            String cfgkey = (String) cfgnameList.nextElement();
            String cfgname = propCfg.getProperty(cfgkey);

            Properties propwk = load(cfgname + ".xml");
            propMap.put(cfgname, propwk);
        }
        return propMap;
    }

    /**
     * 定義情報（単一）の取得<BR>
     *
     * <pre>
     * 【概要】定義情報（単一）の取得。定義ファイルが格納されるフォルダがクラスパスとして定義されていること
     * </pre>
     *
     * @param _confFilename
     *            定義情報ファイル名 例：Sample.xml
     * @throws IOException
     * @throws InvalidPropertiesFormatException
     */
    public Properties load(String confFilename) throws InvalidPropertiesFormatException,
            IOException {

        Properties prop = new Properties();

        InputStream stream = ClassLoader.getSystemResourceAsStream(confFilename);

        prop.loadFromXML(stream);
        stream.close();

        return prop;
    }
    /**
     * 定義情報（単一）の取得<BR>
     *
     * <pre>
     * 【概要】定義情報（単一）の取得。
     * </pre>
     *
     * @param _confPath 定義情報ファイルディレクトリ
     * @param _confFilename 定義情報ファイル名 例：Sample.xml
     * @throws IOException ファイルＩＯ例外
     * @throws InvalidPropertiesFormatException プロパティファイル書式不正
     */
    public Properties load(String _confPath, String _confFilename) throws InvalidPropertiesFormatException, IOException {
        Properties prop = new Properties();

        InputStream stream = new FileInputStream(_confPath + File.separator + _confFilename);

        prop.loadFromXML(stream);

        return prop ;
    }
    /**
     * 
     * @param _confPath　定義情報ファイルのパス
     * @return
     * @throws InvalidPropertiesFormatException
     * @throws IOException
     */
    public Properties loadByPath(String _confPath) throws InvalidPropertiesFormatException, IOException {
        Properties prop = new Properties();

        InputStream stream = new FileInputStream(_confPath );

        prop.loadFromXML(stream);

        return prop ;
    }
    
    /**
     * 複数プロパティ定義の定義データ取得
     * <pre>
     * 【概要】複数プロパティ定義の定義データ取得
     * </pre>
     * @param propName プロパティ名称
     * @param key プロパティのキー
     * @return 定義データ
     * @throws OtherAppException
     */
//    public String getProperty(String propName, String key) throws OtherAppException {
//
//        Properties prop = propMap.get(propName);
//
//        if (prop == null){
//            throw new OtherAppException("プロパティマップに、["+ propName + "] が定義されていません。");
//        }
//        return prop.getProperty(key);
//
//    }

}

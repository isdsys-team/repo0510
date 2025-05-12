package app;

public class AppConst {
    /**
     * 処理結果を保存する際に削除するダミー行のマーク
     */
    public static final String DUMMY_LINE = "@dummyLine";
    /**
     * xmlでnullを定義するためのマーク
     */
    public static final String NULL_MARK = "@null";
    /**
     * xmlで空文字を定義するためのマーク
     */
    public static final String EMPTY_MARK = "@empty";
    static public final String INDENT = "    ";
    static public final String INDENT2 = INDENT + INDENT;
    /** アプリケーション定義情報ファイルパス*/
    public static final String APPCONFIG_PATH="cfg/appConfig.xml";
    /** メッセージ定義情報ファイルパス*/
    public static final String MSGCONFIG_PATH="cfg/msgConfig.xml";


}

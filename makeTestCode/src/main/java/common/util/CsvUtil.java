package common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import common.constant.SysConst;

public class CsvUtil {

    private final static String COMMENT_MARK = "#";

    /**
     * CSVファイル読み込み
     *
     * @param fpath ファイルパス
     * @param encode エンコード
     * @throws IOException ファイルIO例外
     * @return 処理結果
     */
    public static List<ArrayList> readCsv(final String fpath, final String encode)
            throws IOException {
        return readCsv(fpath, encode, ",", false);

    }

    /**
     *
     * @param fpath ファイルパス
     * @param encode エンコード
     * @param sepa  セパレータ
     * @param dquoteFlg オンの場合、存在すれば、データ両端のダブルクォートを取り除く
     * @return 処理結果
     * @throws IOException  ファイルIO例外
     */
    public static List<ArrayList> readCsv(
            final String fpath,
            final String encode,
            final String sepa,
            final boolean dquoteFlg)
            throws IOException {

        ArrayList<ArrayList> rsltList = new ArrayList<ArrayList>();

        FileInputStream fis = new FileInputStream(fpath); // CSVデータファイル
        InputStreamReader isr = new InputStreamReader(fis, encode);
        BufferedReader br = new BufferedReader(isr);

        // 最終行まで読み込む
        String line = "";
        while ((line = br.readLine()) != null) {
            //ホワイトスペースのみの行は無視
            line = line.trim();

            int len = line.length();
            //空行は無視
            if (len < 1) {
                continue;
            }

            // 1行をデータの要素に分割
            String[] value = line.split(sepa, -1);

            ArrayList<String> columnList = new ArrayList();
            for (int i = 0; i < value.length; i++) {
                String buff = value[i];
                if (buff.length() > 0) {
                    if (dquoteFlg) {
                        if (buff.substring(0, 1).equals("\"")
                                && buff.substring(buff.length() - 1,
                                        buff.length()).equals("\"")) {
                            // 先頭と末尾のダブルクォートを取り除く
                            buff = buff.substring(1, buff.length() - 1);
                        }
                    }
                }
                // 1行の各要素をタブ区切りで表示
                columnList.add(buff);
            }
            rsltList.add(columnList);
        }
        br.close();
        isr.close();
        fis.close();

        return rsltList;
    }

    /**
     *
     * @param fpath
     * @param encode
     * @param sepa
     * @param dquoteFlg
     * @param commentFlg true:コメント行あり　　行先頭が、COMMENT_MARK("#")　はコメント行
     * @return
     * @throws IOException
     */
    public static List<ArrayList> readCsv(
            final String fpath,
            final String encode,
            final String sepa,
            final boolean dquoteFlg,
            final boolean commentFlg)
            throws IOException {

        ArrayList<ArrayList> rsltList = new ArrayList<ArrayList>();

        FileInputStream fis = new FileInputStream(fpath); // CSVデータファイル
        InputStreamReader isr = new InputStreamReader(fis, encode);
        BufferedReader br = new BufferedReader(isr);

        // 最終行まで読み込む
        String line = "";
        while ((line = br.readLine()) != null) {
            //ホワイトスペースのみの行は無視
            line = line.trim();

            int len = line.length();
            //空行は無視
            if (len < 1) {
                continue;
            }
            //コメント行は無視
            if (commentFlg && (line.startsWith(COMMENT_MARK))) {
                continue;
            }

            // 1行をデータの要素に分割
            String[] value = line.split(sepa, -1);

            ArrayList<String> columnList = new ArrayList();
            for (int i = 0; i < value.length; i++) {
                String buff = value[i];
                if (buff.length() > 0) {
                    if (dquoteFlg) {
                        if (buff.substring(0, 1).equals("\"")
                                && buff.substring(buff.length() - 1,
                                        buff.length()).equals("\"")) {
                            // 先頭と末尾のダブルクォートを取り除く
                            buff = buff.substring(1, buff.length() - 1);
                        }
                    }
                }
                // 1行の各要素をタブ区切りで表示
                columnList.add(buff);
            }
            rsltList.add(columnList);
        }
        br.close();
        isr.close();
        fis.close();

        return rsltList;
    }

    /**
     * オブジェクト形式のリストで戻り値を返す
     * CSV書式
     *  コメント行：行先頭が、"#"
     *  １行目　ヘッダ
     *  ２行目　データオブジェクトのフィールド名
     *
     * @param dtoClassName
     * @param fpath			ファイルパス
     * @param encode		エンコード
     * @param sepa			区切り文字
     * @param dquoteFlg		ダブルクォート有効フラグ true:ダブルクォート有効
     * @param commentFlg	コメント有効フラグ true:コメント行有効
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static List<Object> readCsvToObj(
            final String dtoname,
            final String fpath,
            final String encode,
            final String sepa,
            final boolean dquoteFlg,
            final boolean commentFlg
            ) throws IOException,
                ClassNotFoundException,
                InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                SecurityException,
                IllegalArgumentException,
                InvocationTargetException {

        ArrayList<Object> rsltList = new ArrayList<Object>();

        FileInputStream fis = new FileInputStream(fpath); // CSVデータファイル
        InputStreamReader isr = new InputStreamReader(fis, encode);
        BufferedReader br = new BufferedReader(isr);

        // ファイルを読み込む
        String[] colnames = null;
        int lineNo = 0;
        String line = "";
        while ((line = br.readLine()) != null) {
            //ホワイトスペースのみの行は無視
            line = line.trim();

            int len = line.length();
            //空行は無視
            if (len < 1) {
                continue;
            }
            //コメント行は無視
            String top = line.substring(0,1);
            if (commentFlg && (top.equals(COMMENT_MARK))) {
                continue;
            }
            lineNo++;

            if (lineNo == 1) {
                // 何もしない
                continue;
            } else if (lineNo == 2) {
                // フィールド名の1行をデータの要素に分割
                colnames = line.split(sepa, -1);
                //タイトル両端の ダブル・クオーテーションを取り除く
                bothDeleteDQ(colnames);
                continue;
            }

            // 1行をデータの要素に分割
            String[] values = line.split(sepa, -1);

            //データ両端の ダブル・クオーテーションを取り除く
            bothDeleteDQ(values);

            //オブジェクトに値を設定
            Class<?> clazz = Class.forName(dtoname);
            Object dtoobj = clazz.newInstance();

            //CSV定義の値を所定のオブジェクトのフィールドに設定
            for (int i = 0; i < values.length; i++) {
                String value = values[i];

                String colName = colnames[i];
                setValue(dtoobj, colName, value);
            }
            rsltList.add(dtoobj);

        }
        br.close();
        isr.close();
        fis.close();

        return rsltList;
    }

    /**
     * 両端のダブルクｫーテーション削除
     * @param titles 文字列の配列
     */
    static void bothDeleteDQ(String[] titles) {
        //タイトル両端の ダブル・クオーテーションを取り除く
        for (int i = 0; i < titles.length; i++) {
            String value = titles[i];
            if (titles[i].length() >= 2) {
                String left = titles[i].substring(0, 1);
                String right = titles[i].substring(titles[i].length() - 1, titles[i].length());

                if (left.equals("\"") && right.equals("\""))
                {
                    value = titles[i].substring(1, titles[i].length() - 1);
                }
            }
            titles[i] = value;
        }
    }

    /**
     * データオブジェクト設定
     * @param dto
     * @param colname
     * @param value
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    static void setValue(Object dto, String colname, Object value)
            throws NoSuchMethodException,
            SecurityException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {

        // メソッド名編集
        String methodName = "set" + colname.substring(0, 1).toUpperCase() + colname.substring(1, colname.length());
        {
            // メソッドオブジェクト取得
            Method method = dto.getClass().getMethod(methodName, String.class);
            // メソッド実行
            method.invoke(dto, value);
        }
    }

    /**
     * ＣＳＶ行編集
     * @param itemList 項目リスト
     * @param sepa セパレータ
     * @param dquoteFlg ダブルクウｫートフラグ true：データにダブルクォートを付加する
     * @return 編集結果
     */
    public static String editCsvLine(ArrayList<CsvItem> itemList, String sepa, boolean dquoteFlg) {

        StringBuilder lineSbf = new StringBuilder();

        for (int i = 0; i < itemList.size(); i++) {
            CsvItem item = itemList.get(i);

            switch (item.getDataType()) {
            case CsvConst.DT_STRING://データ型が、文字列
                if (dquoteFlg) {
                    //データにダブルクォートを付加する
                    item.setValue("\"" + item.getValue() + "\"");
                }
                break;
            case CsvConst.DT_DATE: //データ型が、日付
                if ((item.getDateValue() != null) && (item.getDateFormat() != null))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat(item.getDateFormat());
                    item.setValue(sdf.format(item.getDateValue()));
                } else {
                    //
                    throw new IllegalArgumentException("日付データ不正");
                }
                break;
            case CsvConst.DT_BIN: //データ型が、バイナリ

                break;

            }
            lineSbf.append(item.getValue());
            if (i < itemList.size() - 1) {
                lineSbf.append(sepa);
            }
        }
        lineSbf.append(SysConst.CRLF);

        return lineSbf.toString();
    }

}

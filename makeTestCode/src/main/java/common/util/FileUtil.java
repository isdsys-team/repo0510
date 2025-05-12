/*
 * 作成日: 2004/08/08
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ken
 *
 *         この生成されたコメントの挿入されるテンプレートを変更するため ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */

public class FileUtil {

    public static void saveBinary(final String fpath,
            final ArrayList<DataInfo> dataList,
            final boolean append) throws IOException, IllegalArgumentException {

        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("dataList is empty.");
        }
        // (1)DataOutputStreamオブジェクトの生成
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fpath,
                append));

        Iterator<DataInfo> itr = dataList.iterator();

        while (itr.hasNext()) {
            DataInfo info = itr.next();

            switch (info.dataType) {
            case DataInfo.TP_STRING://
                // Data Type is "String"
                writeString(out, info.stringData);
                break;
            case DataInfo.TP_BYTE_ARRAY://
                // Data Type is "byte Array"
                writeByteArray(out, info.bytes);
                break;

            case DataInfo.TP_SHORT:// }
                // Data Type is "short"
                out.writeShort(info.shortData);
                break;

            case DataInfo.TP_INT:// }
                // Data Type is "int"
                out.writeInt(info.intData);
                break;
            case DataInfo.TP_LONG:// }
                // Data Type is "long"
                out.writeLong(info.longData);
                break;
            default:
                throw new IllegalArgumentException("dataType is illegal.");
            }

        }
        out.close(); // (7)書き込みストリームのクローズ処理
    }

    /**
     * Write String Data
     *
     * @param out DataOutputStream オブジェクト
     * @param value 出力テキスト
     * @throws IOException File IO 例外
     */
    private static void writeString(final DataOutputStream out, final String value)
            throws IOException {

        byte[] bytes = value.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            out.writeByte(bytes[i]);
        }

    }
    /**
     *
     * @param out  DataOutputStream オブジェクト
     * @param bytes 出力データ
     * @throws IOException File IO 例外
     */
    private static void writeByteArray(final DataOutputStream out, final byte[] bytes)
            throws IOException {

        for (int i = 0; i < bytes.length; i++) {
            out.writeByte(bytes[i]);
        }
    }
    /**
     *
     * @param fpath ファイルパス
     * @return リードバッファ
     * @throws IOException File IO 例外
     */
    public static List<byte[]> readBytesList(final String fpath) throws IOException {

        BufferedInputStream fis = null;

        List<byte[]> readList = new ArrayList<byte[]>();

        // 入力元ファイル
        File file = new File(fpath);

        fis = new BufferedInputStream(new FileInputStream(file));

        int avail;
        // 読み込み可能なバイト数づつ読み込む
        while ((avail = fis.available()) > 0) {
            byte[] bytes = new byte[avail];
            fis.read(bytes);
            readList.add(bytes);
        }
        if (fis != null) {
            // ストリームは必ず finally で close します。
            fis.close();
        }
        return readList;
    }

    /**
     *
     * @param fpath ファイルパス
     * @param bytes 出力データ
     * @param append 追記フラグ
     * @throws IOException File IO 例外
     */
    public static void saveBytes(final String fpath, final byte[] bytes, final boolean append) throws IOException {
        BufferedOutputStream fis = null;

        // 出力先ファイル
        File file = new File(fpath);

        fis = new BufferedOutputStream(new FileOutputStream(file, append));
        fis.write(bytes);

        if (fis != null) {
            // ストリームは必ず finally で close します。
            fis.close();
        }
    }

    /**
     *
     * @param fpath ファイルパス
     * @param bytesList 出力データリスト
     * @param append 追記フラグ
     * @throws IOException ファイルIO例外
     */
    public static void saveBytesList(final String fpath, final List<byte[]> bytesList,
            final boolean append) throws IOException {

        BufferedOutputStream fis = null;

        // 出力先ファイル
        File file = new File(fpath);

        fis = new BufferedOutputStream(new FileOutputStream(file, append));

        Iterator<byte[]> itr = bytesList.iterator();
        while (itr.hasNext()) {
            fis.write(itr.next());
        }
        if (fis != null) {
            // ストリームは必ず finally で close します。
            fis.close();
        }
    }
    /**
     *
     * @param path ディレクトリパス
     * @param fileList ファイル名リスト
     */
    public static void getFileList(final String path, final ArrayList<String> fileList) {

        // ディレクトリ指定
        File dir = new File(path);

        // フルパスで取得
        File[] files1 = dir.listFiles();
        for (int i = 0; i < files1.length; i++) {
            File file = files1[i];
            if (files1[i].isFile()) {
                // ファイル名表示
                fileList.add(file.getAbsolutePath());
            } else if (files1[i].isDirectory()) {
                //
                getFileList(file.getAbsolutePath(), fileList);
            }
        }
        return;
    }
    /**
     *
     * @param path ファイルパス
     * @param fileList ファイル名リスト
     */
    public static void getFileListByFilename(final String path,
            final ArrayList<String> fileList) {

        // ディレクトリ指定
        File dir = new File(path);

        // フルパスで取得
        File[] files1 = dir.listFiles();
        for (int i = 0; i < files1.length; i++) {
            File file = files1[i];
            if (files1[i].isFile()) {
                // ファイル名表示
                // LogTrace.logout(3, file.getName());
                fileList.add(file.getName());
            } else if (files1[i].isDirectory()) {
                //
                getFileListByFilename(file.getAbsolutePath(), fileList);
            }
        }
        return;
    }

    /**
     * CSVファイル読み込み 【説明】カンマ空切りファイルやタブ区切りファイルをリードして、リスト形式で返す
     *
     * @param fpath
     *            ファイルパス
     * @param encode
     *            　エンコード（"Shift_JIS", "UTF-8" etc）
     * @param sepa
     *            セパレータ （"," or "\t" etc）
     * @param dquoteFlg
     *            オンの場合、データ両端のダブルクォートを取り除く
     * @return　読み込んだ内容
     * @throws IOException
     */
    // public static ArrayList<ArrayList> readCsvToList(String fpath, String
    // encode, String sepa, boolean dquoteFlg) throws IOException {
    // ArrayList<ArrayList> rsltList = new ArrayList<ArrayList>();
    //
    //
    // FileInputStream fis = new FileInputStream(fpath); // CSVデータファイル
    // InputStreamReader isr = new InputStreamReader(fis, encode);
    // BufferedReader br = new BufferedReader(isr);
    //
    // // 最終行まで読み込む
    // String line = "";
    // while ((line = br.readLine()) != null) {
    // //コメント行判定
    // if (line.length() > 0){
    // if (line.substring(0, 1).equals("#")){
    // continue ;
    // }
    // }
    //
    // // 1行をデータの要素に分割
    // StringTokenizer st = new StringTokenizer(line, sepa);
    //
    // ArrayList<String> columnList = new ArrayList();
    // while (st.hasMoreTokens()) {
    // // 1行の各要素をタブ区切りで表示
    // String buff = st.nextToken();
    // buff = buff.trim();
    //
    // if (buff.substring(0,1).equals("\"") &&
    // buff.substring(buff.length()-1,buff.length()).equals("\"")){
    // //先頭と末尾のダブルクォートを取り除く
    // buff = buff.substring(1, buff.length()-1);
    // }
    // columnList.add(buff);
    // }
    // rsltList.add(columnList);
    // }
    // br.close();
    // isr.close();
    // fis.close();
    //
    // return rsltList;
    // }

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
        return CsvUtil.readCsv(fpath, encode, ",", false);

    }


    /**
     * ファイル保存
     *
     * @param path ファイルパス
     * @param buff 保存するテキスト
     * @throws IOException ファイルIO例外
     */
    public static void saveText(final String path, final String buff) throws IOException {

        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        fos = new FileOutputStream(path);
        osr = new OutputStreamWriter(fos);
        bw = new BufferedWriter(osr);

        bw.append(buff);

        bw.close();
        osr.close();
        fos.close();

        return;
    }

    /**
     * ファイル保存
     *
     * @param path ファイルパス
     * @param buff 保存するテキスト
     * @param append 追加フラグ
     * @param encode 保存する際のエンコード
     * @throws IOException ファイルIO例外
     */
    public static void saveText(final String path, final String buff, final boolean append,
            final String encode) throws IOException {

        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        fos = new FileOutputStream(path, append);
        osr = new OutputStreamWriter(fos, encode);
        bw = new BufferedWriter(osr);

        if(append) {
            bw.append(buff);
        }
        else {
            bw.write(buff);
    }

        if (bw != null) {
            bw.close();
        }
        if (osr != null) {
            osr.close();
        }
        if (fos != null) {
            fos.close();
        }
        return;
    }
    /**
     * ファイル保存
     *
     * @param path ファイルパス
     * @param lineList 保存するテキストリスト
     * @param append 追加フラグ
     * @param encode 保存する際のエンコード
     * @throws IOException ファイルIO例外
     */
    public static void saveTextArray(final String path, ArrayList<String> lineList, final boolean append,
            final String encode) throws IOException {

        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        fos = new FileOutputStream(path, append);
        osr = new OutputStreamWriter(fos, encode);
        bw = new BufferedWriter(osr);

        for(String buff : lineList){
            bw.append(buff);
        }

        if (bw != null) {
            bw.close();
        }
        if (osr != null) {
            osr.close();
        }
        if (fos != null) {
            fos.close();
        }
        return;
    }

    /**
     * ファイル読み込み
     *
     * @param path ファイルパス
     * @return String形式の読み込んだテキスト
     * @throws IOException ファイルIO例外
     */
    public static String loadText(final String path) throws IOException {
        String buff = null;

        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader in = new BufferedReader(isr);

        StringBuffer sbf = new StringBuffer("");

        while ((buff = in.readLine()) != null) {
            sbf.append(buff);
            sbf.append("\r\n");
        }

        buff = sbf.toString();

        return buff;
    }

    /**
     * ファイル読み込み（リスト形式）
     *【概要】pathで指定されたファイルを読み込み、リスト形式の戻り値で結果を戻す。
     * @param path ファイルパス
     * @throws IOException ファイルIO例外
     * @return 読み込んだ内容（リスト形式）
     */
    public static ArrayList<String> loadTextToArray(final String path)
            throws IOException {

        String line = "";

        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader in = new BufferedReader(isr);

        ArrayList<String> list = new ArrayList<String>();
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        isr.close();
        fis.close();

        return list;
    }
    /**
     * ファイル読み込み（リスト形式）
     *【概要】pathで指定されたファイルを読み込み、リスト形式の出力パラメータで結果を戻す。
     * @param path ファイルパス
     * @param list 読み込んだ内容
     * @throws IOException ファイルIO例外
     */
    public static void loadTextToArray(final String path, List<String> list)
            throws IOException {

        String line = "";

        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader in = new BufferedReader(isr);

        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        isr.close();
        fis.close();
    }

    /**
     * ファイル読み込み（リスト形式）
     *
     * @param path ファイルパス
     * @param list 読み込んだテキストファイルの内容（リスト形式）
     * @param outEncode 出力するエンコード
     * @throws IOException  ファイルIO例外
     */
    public static void loadTextToArray(final String path, List<String> list, final String outEncode) throws IOException {

        String line = "";

        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader in = new BufferedReader(isr);

        while ((line = in.readLine()) != null) {
            String linewk = new String(line.getBytes(outEncode), outEncode);
            list.add(linewk);
        }
        in.close();
        isr.close();
        fis.close();
    }

    /**
     * ファイル読み込み（バイト配列形式）
     * 【概要】テキストファイルを読み込み、バイト配列形式で、戻り値として出力する
     * @param path ファイルパス
     * @return バイト配列形式の読み込みデータ
     */
    public static byte[] loadData(final String path) {
        byte[] bytes = null;

        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader in = new BufferedReader(isr);

            String buff = null;
            StringBuffer sbf = new StringBuffer("");

            while ((buff = in.readLine()) != null) {
                sbf.append(buff);
            }

            buff = sbf.toString();
            bytes = buff.getBytes();
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }

        return bytes;
    }

    /**
     * ファイル読み込み
     *
     * @param path ファイルパス
     * @param encode ファイルのエンコード
     * @return String形式の読み込んだデータ
     * @throws IOException ファイルIO例外
     */
    public static String loadText(final String path, final String encode)
            throws IOException {
        String buff = null;

        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, encode);
        BufferedReader in = new BufferedReader(isr);

        StringBuffer sbf = new StringBuffer("");

        while ((buff = in.readLine()) != null) {
            sbf.append(buff);
            sbf.append("\r\n");
        }

        buff = sbf.toString();

        return buff;
    }

    public static byte[] editByteArray(final short bin) {
        ByteBuffer buf = ByteBuffer.allocate(2);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putShort(bin);
        return buf.array();
    }

    public static byte[] editByteArray(final int bin) {
        ByteBuffer buf = ByteBuffer.allocate(4);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(bin);
        return buf.array();
    }

    public static byte[] editByteArray(final long bin) {
        ByteBuffer buf = ByteBuffer.allocate(8);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putLong(bin);
        return buf.array();
    }

}

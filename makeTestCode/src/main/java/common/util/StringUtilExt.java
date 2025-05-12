package common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
//import java.util.regex.Pattern;

/**
 *
 * タイトル: ユーティリティクラス<br>
 * 説明　　: ユーティリティクラス。
 *
 */
public class StringUtilExt {
      /**
       * 定数テーブル存在判定
       * @param tbl
       * @param txt
       * @return
       */
      static public boolean isExistsTbl(String tbl, String txt){

            if (txt.length() == 0){
                return false ;
            }
            for(int i=0; i < txt.length(); i++){
                String chr = txt.substring(i, i+1);
                if (tbl.indexOf(chr) < 0){
                    return false ;
                }
            }
            return true ;
      }
      static public boolean isKatakana(String txt){
            String tbl = "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワンヲ";
            tbl += "ァィゥェォャュョッ";
            tbl += "ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポー";
            return isExistsTbl(tbl, txt);
      }
        /**
         * 実数数値文字列判定
         * @param txt　対象文字列
         * @return　true:実数である、false:10進数数値文字列でない
         */
//		static public boolean isRealNumeric(String txt){
//			Integer.parseInt(txt)
//		}

      /**
       * 半角判定
       * @param txt
       * @return
       */
      static public boolean isAscii(java.lang.String txt)throws IllegalArgumentException{
        boolean rslt = true ;

        if ((txt ==null) || (txt.length()==0)){
            throw new IllegalArgumentException("パラメータがnullまたは空文字です。");
        }
        //バイト長取得
        if (txt.length() != txt.getBytes().length){
            rslt = false ;
        }
        return rslt ;
      }
    /**
     * アルファベット文字列判定
     * @param txt
     * @return　true:アルファベット文字列である、false:アルファベット文字列でない
     */
//    static public boolean isAlphaText(String txt){
//        return StringUtils.isAlpha(txt) ;
//    }
    /**
     * アルファベット+記号文字列判定
     * @param txt
     * @return
     */
    static public boolean isJavaIdentifier(String txt){
        String tbl = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" + "_$";

        if ((txt ==null) || (txt.length()==0)){
            throw new IllegalArgumentException("パラメータがnullまたは空文字です。");
        }

        if (txt.length() == 0){
            return false ;
        }
        for(int i=0; i < txt.length(); i++){
            String chr = txt.substring(i, i+1);
            if (tbl.indexOf(chr) < 0){
                return false ;
            }
        }
        return true ;
    }
    /**
     * 先頭文字を大文字変換
     * @param str　対象文字列
     * @return　変換した文字列
     */
//    static public String capitalize(java.lang.String str){
//        if ((str == null) || (str.length()==0)){
//            throw new IllegalArgumentException("引数が不正です。");
//        }
//        return str.substring(0,1).toUpperCase() + str.substring(1, str.length());
//    }
    /**
     * 先頭文字を小文字変換
     * @param str　対象文字列
     * @return　変換した文字列
     */
//    static public String uncapitalize(java.lang.String str){
//        if ((str == null) || (str.length()==0)){
//            throw new IllegalArgumentException("引数が不正です。");
//        }
//        return str.substring(0,1).toLowerCase() + str.substring(1, str.length());
//    }

    /**
     * 現在時刻文字列変換。<pre>
     * 【説明】
     * 現在時刻(System.currentTimeMillis())を時刻文字列("yyyyMMddHHmmss")
     * に変換する。
     * </pre>
     * @return　時刻文字列
     */
    static public String cnvSystimeToString(){
        //媒体入出力電文送信時刻の時刻文字列
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss:SSS");
        return sdf.format(dt);
    }
    /**
     * 現在時刻文字列変換。<pre>
     * 【説明】
     * 現在時刻(System.currentTimeMillis())を指定された時刻文字列書式(format)
     * にしたがって時刻文字列に変換する。
     * 書式 yyyy:西暦、MM:月、日付：dd、HH：時、mm：分、ss：秒、SSS：ミリ秒
     * formatの例. yyyy年MM月dd日 => 2013年01月05日
     * </pre>
     * @param format 出力する時刻文字列の書式
     * @return　時刻文字列
     */
    static public String cnvSystimeToString(String format){
        if ((format ==null) || (format.length()==0)){
            throw new IllegalArgumentException("パラメータがnullまたは空文字です。");
        }

        //媒体入出力電文送信時刻の時刻文字列
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat (format);
        return sdf.format(dt);
    }
    /**
     * 電文リストファイル名の時刻文字列取得。<pre>
     * 【説明】
     * 電文リストファイル名の時刻文字列を取得する。
     * </pre>
     * @return　時刻文字列
     *
     */
    static public String getListFileTime(){
        //電文リストファイル名の時刻文字列
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat ("yyMMddHHmmss");
        return sdf.format(dt);
    }

    /**
     * 文字列分割処理。<pre>
     * 【説明】
     * 指定された文字列を指定された単位文字列長で分割する。
     * </pre>
     * @param unitLength 単位文字列長
     * @param text 分割対象文字列
     * @return 文字列リスト
     */
//    static public LinkedList splitString(int unitLength, String text){
//        if (unitLength <= 0){
//            throw new IllegalArgumentException("\"unitLength\" is illegal. \"unitLength\" is " + unitLength + ".");
//        }
//        if (text == null){
//            throw new IllegalArgumentException("\"text\" is null.");
//        }
//        if (text.length() <= 0){
//            throw new IllegalArgumentException("\"text\" is empty String.");
//        }
//        //sendMaxSize		readDataList
//        int div = text.length() / unitLength +
//                (((text.length() % unitLength) == 0) ? 0 : 1) ;
//
//        int pos = 0;
//        LinkedList dataList = new LinkedList();
//        for(int i=0 ; i < div; i++){
//            String s = "" ;
//            if (text.length() < (pos + unitLength)){
//                s = text.substring(pos, text.length());
//            }else{
//                s = text.substring(pos, pos + unitLength);
//            }
//            dataList.add(s);
//            pos += unitLength ;
//        }
//        return dataList ;
//    }
    /**
     * 同一名重複チェック。<pre>
     * 【説明】
     * リストのテキストに重複があるかチェックしインデックスを戻す。
     * </pre>
     * @param srcList　文字列リスト
     * @return　インデックス
     *
     */
    static public HashMap isExistsSameString(List<String> srcList){
        if ((srcList ==null) || (srcList.size()==0)){
            throw new IllegalArgumentException("パラメータがnullまたは０件です。");
        }

        HashMap map = new HashMap();

        if (srcList.size() < 2)
            return map ;

        ArrayList<String> dstList = new ArrayList<String>();
        dstList = (ArrayList<String>)srcList ;

        for(int i=0; i < srcList.size() - 1; i++){
            String src = (String)srcList.get(i);
            /* チェック */
            for(int j=i+1; j < srcList.size(); j++){
                String dst = (String)dstList.get(j);
                if (src.equals(dst)){
                    ArrayList idxList = new ArrayList();
                    ArrayList list = (ArrayList)map.get(src);

                    if (list == null){
                        idxList.add(new Integer(i)) ;
                        idxList.add(new Integer(j)) ;
                        //重複インデックスを登録
                        map.put(src, idxList);
                    }else{
                        if (!list.contains(new Integer(i))){
                            idxList.add(new Integer(i)) ;
                        }
                        if (!list.contains(new Integer(j))){
                            idxList.add(new Integer(j)) ;
                        }
                        //キーに対応するインデックスリストを更新
                        list.addAll(idxList);
                        map.put(src, list);
                    }

                    break;
                }
            }
        }
        return map ;
    }
    /**
     * 文字列=>Date変換。<pre>
     * 【説明】
     * 指定された日付文字列をDate型に変換する。
     * </pre>
     * @param date 日付文字列(形式は、"YYYYMMDD"で、年＝1900年以上、月＝01～12）
     * @return Dateオブジェクト
     *
     */
    static public Date stringToDate(String date){
        if ((date ==null) || (date.length()==0)){
            throw new IllegalArgumentException("パラメータがnullまたは空文字です。");
        }

        if (!isDateString(date)){
            return null ;
        }
        int year 	= (new Integer(date.substring(0,4))).intValue();
        int month	= (new Integer(date.substring(4,6))).intValue();
        int day  	= (new Integer(date.substring(6,8))).intValue();


        Calendar cal = Calendar.getInstance(new Locale("ja", "JAPAN"));
        cal.set(year, month-1, day, 0, 0, 0);
        Date dt = cal.getTime();

        return dt;

    }
    /**
     * 日付文字列妥当性判定。<pre>
     * 【説明】
     * 指定された日付文字列が妥当か判定する。
     * </pre>
     * @param date 日付文字列(形式は、"YYYYMMDD"で、年＝1900年以上、月＝01～12）
     * @return 判定結果
     *
     */
    static public boolean isDateString(String date){
        final int[] dayArray = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };
        if ((date ==null) || (date.length()==0)){
            throw new IllegalArgumentException("パラメータがnullまたは空文字です。");
        }

        if (date.length() < 8){
            throw new IllegalArgumentException("日付文字列長が不正。") ;
        }

        int year 	= (new Integer(date.substring(0,4))).intValue();
        int month	= (new Integer(date.substring(4,6))).intValue();
        int day  	= (new Integer(date.substring(6,8))).intValue();

        if (year < 1900){
            throw new IllegalArgumentException("年の値が不正（日付＝" + date + "）。") ;
        }
        if ((month <= 0) || (12 < month)){
            throw new IllegalArgumentException("月の値が不正（日付＝" + date + "）。") ;
        }
        if (day <= 0){
            throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
        }
        if (month != 2){
            if (day > dayArray[month - 1]){
                throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
            }
        }else{
            if (isUruYear(year)){
                if (day > 29){
                    throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
                }
            }else{
                if (day > 28){
                    throw new IllegalArgumentException("日の値が不正（日付＝" + date + "）。") ;
                }
            }
        }
        return true ;
    }
    /**
     * うるう年判定。<pre>
     * 【説明】
     * 指定された年のうるう年判定を行う。
     * 判定条件
     * (1)西暦が400で割り切れる年はうるう年である。
     * (2)400で割り切れない場合、西暦が100で割り切れる年はうるう年ではない。
     * (3)100で割り切れない場合、西暦が4で割り切れる年はうるう年である。
     * (4)4で割り切れない場合、うるう年ではない。
     * </pre>
     * @param year　1900年以上とする
     * @return　判定結果
     *
     */
    static public boolean isUruYear(int year){
        if (year < 1900){
            return false ;
        }
        // (4)4で割り切れない場合、うるう年ではない。
        if ((year%4) != 0){
            return false ;
        }
        boolean rtn = true ;
        if ((year%400) != 0){
            // (2)400で割り切れない場合、西暦が100で割り切れる年はうるう年ではない。
            if ((year%100) ==0){
                rtn = false ;
            }else{
                // (3)100で割り切れない場合、西暦が4で割り切れる年はうるう年である。
                if ((year%4) == 0){
                    rtn = true  ;
                }else{
                    rtn = false ;
                }
            }
        }else{
            // (1)西暦が400で割り切れる年はうるう年である。
            rtn = true ;
        }
        return rtn ;
    }
    /**
     * 日付文字列形式変換（年2桁=>4桁）。<pre>
     * 日付文字列形式をYYMMDDから、YYYYMMDDに変換する。
     * </pre>
     * @param srcDate　日付文字列(形式は、"YYMMDD"で、年＝1900年以上、月＝01～12）　
     * @return　変換後の日付文字列
     *
     */
    static public String cnvDateYYMMDDtoYYYYMMDD(String srcDate){
        if (srcDate.length() != 6){
            throw new IllegalArgumentException("日付文字列長が不正。") ;
        }
        String dstDate = "";

        int year = (new Integer(srcDate.substring(0, 2))).intValue();
        year += 1900 ;
        dstDate = String.valueOf(year) + srcDate.substring(2, srcDate.length());

        //妥当性チェック
        stringToDate(dstDate);

        return dstDate ;
    }
    /**
     * 半角数値文字列to全角数値文字列変換<pre>
     * 半角数値文字列を全角数値文字列に変換します。
     * @param hantxt 変換元文字列
     * @return 変換後文字列
     */
      public static String hanNumToZen(String hantxt) {
          final String hantbl = "0123456789";
          final String zentbl = "０１２３４５６７８９";

          StringBuffer sbf = new StringBuffer();

          for(int i=0; i < hantxt.length();i++){
              int idx = hantbl.indexOf(hantxt.substring(i, i+1));
              sbf.append(zentbl.substring(idx, idx+1));
          }
          return sbf.toString();
      }
      /**
       * 全角数値文字列To半角数値文字列変換<pre>
       * 全角数値文字列を半角数値文字列に変換します。
       * </pre>
       * @param zentxt 変換元文字列
       * @return 変換後文字列
       */
      public static String zenNumToHan(String zentxt) {
          final String hantbl = "0123456789";
          final String zentbl = "０１２３４５６７８９";

          StringBuffer sbf = new StringBuffer();

          for(int i=0; i < zentxt.length();i++){
              int idx = zentbl.indexOf(zentxt.substring(i, i+1));
              sbf.append(hantbl.substring(idx, idx+1));
          }
          return sbf.toString();
      }
      /**
       * 全角スペースのトリム<pre>
       * 対象文字列から、両端の全角スペースを取り除く。
       * </pre>
       * @param txt　トリム対象文字列
       * @return　トリム実施後の文字列
       */
      public static String trimzen(String txt){
            String regex1 = "^　*";		//先頭の全角スペースが一致
            String regex2 = "　*$";		//末尾の全角スペースが一致

            //一挙に
            return txt.replaceAll(regex1, "").replaceAll(regex2, "");
      }
      /**
       * 半角スペース及び全角スペーストリム<pre>
       * 対象文字列から、両端の半角スペース及び全角スペースを取り除く。
       * </pre>
       * @param txt　トリム対象文字列
       * @return　トリム実施後の文字列
       */
      public static String trimHanZen(String orgStr){
            char[] value = orgStr.toCharArray();
            int len = value.length;
            int st = 0;
            char[] val = value;

            if ((orgStr ==null) || (orgStr.length()==0)){
                throw new IllegalArgumentException("パラメータがnullまたは空文字です。");
            }

            while ((st < len) && (val[st] <= ' ' || val[st] == '　')) {
                st++;
            }
            while ((st < len) && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
                len--;
            }
            return ((st>0) || (len<value.length)) ? orgStr.substring(st,len):orgStr;
      }
      /**
       * ホワイトスペース判定
       * @param txt　対象の1文字文字列
       * @return　判定結果
       */
      public static boolean isWhiteSpace(String txt){
          String template = " \t";

          if (template.indexOf(txt) == -1){
              return false ;
          }
          return true ;
      }
      /**
       * 西暦to和暦変換
       * @param srcCal 変換元の西暦
       * @return HashMap "TITLE":元号名, "YEAR":和暦の年
       */
//      public static HashMap<String, String> cnvYearSeirekiToWareki(Calendar srcCal){
//          MyDate srcDate = new MyDate(srcCal.get(Calendar.YEAR), srcCal.get(Calendar.MONTH), srcCal.get(Calendar.DAY_OF_MONTH));
//
//          return cnvYearSeirekiToWareki(srcDate);
//
//      }
        /**
         * <p>[概 要] 西暦日（Date） ⇒ 和暦(GGGGyy年MM月dd日)変換</p>
         * <p>[詳 細] </p>
         * <p>[備 考] </p>
         * @param  date 日付（Date型）
         * @return 和暦（GGGGyy年MM月dd日）
         */
      public static String cnvSeirekiToWareki(Date date){

          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          
          if (cal.get(Calendar.YEAR)< 1868) {
              throw new IllegalArgumentException("日付は明治以降を指定してください。");
          }
            String result = null;
            Locale locale = new Locale("ja", "JP", "JP");
            DateFormat warekiFormat = new SimpleDateFormat("GGGGyy年MM月dd日", locale);
            if(Objects.nonNull(date)) {
                result = warekiFormat.format(date);
            }
            return result;
      }
      /**
       * 和暦にフォーマットした現在日時を西暦にパースする
       * 
       * @param dateStr
       * @return
     * @throws ParseException 
       */
      public static String cnvWarekiToSeireki(String dateStr) throws ParseException{
          //ロケールを指定してCalendarインスタンスを取得
          Locale locale = new Locale("ja", "JP", "JP");
          Calendar calendar = Calendar.getInstance(locale);

          //calendar.getTime()で現在日時を取得して和暦にフォーマットする
          DateFormat japaseseFormat = new SimpleDateFormat("GGGGy年M月d日", locale);
    
        calendar.setLenient(false);
        Date date = japaseseFormat.parse(dateStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = dateFormat.format(date);

        return  strDate;
    }
      /**
       * 対象日付範囲内判定<pre>
       * 開始日付 <=　対象日付　<　終了日付　を判定する
       * </pre>
       * @param sd　開始日付
       * @param ed　終了日付
       * @param td　対象日付
       * @return　true:範囲内、false：範囲外
       */
//      public static boolean isDateOnArea(MyDate sd, MyDate ed, MyDate td){
//
//          if ((sd.year == td.year) && (sd.month == td.month) && (sd.day == td.day)){
//              //開始日付が対象日付と等しい場合
//              return true ;
//          }
//          if (compareDate(sd, td) > 0 && (compareDate(ed, td) < 0)){
//              return true ;
//
//          }
//          return false ;
//      }
      /**
       *
       * @param sd　開始日付
       * @param td　対象日付
       * @return 開始日付<対象日付:1,開始日付>対象日付:-1,開始日付=対象日付:0
       */
//      private static int compareDate(MyDate sd, MyDate td){
//
//          if (sd.year < td.year ){
//              return 1 ;
//          }else if (sd.year > td.year){
//              return -1 ;
//          }else{
//              //yearが等しい場合
//              if (sd.month < td.month){
//                  return 1 ;
//              }else if (sd.month > td.month){
//                  return -1 ;
//              }else{
//                  //year, month が等しい場合
//                  if (sd.day < td.day){
//                      return 1 ;
//                  }else if (sd.day > td.day){
//                      return -1 ;
//                  }
//              }
//          }
//          return 0 ;
//      }
      /**
       * Integer判定
       * <pre>
       * 【概要】
       * </pre>
       * @param txt
       * @return
       */
      static public boolean isInteger(String txt){
          try{
              Integer.parseInt( txt );
          }
          catch(NumberFormatException ex){
//			  System.out.println(txt + " is not Integer.");
              return false ;
          }
//		  System.out.println(txt + " is Integer.");
          return true ;
      }
      /**
       * Double判定
       * <pre>
       * 【概要】
       * </pre>
       * @param txt
       * @return
       */
      static public boolean isDouble(String txt){
          try{
              Double.parseDouble( txt );
          }
          catch(NumberFormatException ex){
//			  System.out.println(txt + " is not Double.");
              return false ;
          }
//		  System.out.println(txt + " is Double.");
          return true ;
      }
}

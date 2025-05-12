package common.exception;


/**
 * タイトル：日付指定フォーマット不正例外<pre>
 * 説明　　：日付変換のフォーマットエラー時に返す例外
 *
 * </pre>
 */
 public class AppException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -4148341505275939712L;

	/** エラー情報*/
	private ErrorInfo errorInfo = new ErrorInfo();

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 */
	public AppException(){
	}

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aMsg メッセージを指定する。
	 */
	public AppException(String aMsg){
		super(aMsg);
	}
	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aMsg メッセージを指定する。
	 */
	public AppException(ErrorInfo info){
		errorInfo = info ;
	}

	/**
	 * @return errorInfo
	 */
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	/**
	 * @param errorInfo セットする errorInfo
	 */
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aMsg メッセージを指定する。
	 * @param aCause 元となった例外を指定する。
	 */
	public AppException(String aMsg, Exception aCause){
		super(aMsg, aCause);
	}

	/**
	 * コンストラクタ。<pre>
	 * 【説明】
	 * インスタンスを作成する。
	 * </pre>
	 * @param aCause 元となった例外を指定する。
	 */
	public AppException(Exception aCause){
		super(aCause);
	}
}

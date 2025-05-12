package common.util;

public class DataInfo {

	/** 文字列 */
	public static final int TP_STRING 		= 1 ;
	/** BYTE配列 */
	public static final int TP_BYTE_ARRAY 	= TP_STRING + 1 ;
	/** short */
	public static final int TP_SHORT 		= TP_BYTE_ARRAY + 1 ;
	/** int */
	public static final int TP_INT 			= TP_SHORT + 1 ;
	/** long */
	public static final int TP_LONG 		= TP_INT + 1 ;

	/**  byte length */
	public int len ;
	/**  Java Data Type ex.String, short */
	public int dataType ;

	/**  */
	public String stringData ;

	/**  short Data value */
	public short shortData ;
	/**  int Data value */
	public int intData ;

	/**  long Data value */
	public long longData ;

	/** Data value */
	public byte[] bytes ;

	public DataInfo(int len, int dataType, String value){
		this.len = len ;
		this.dataType = dataType ;
		this.stringData = value ;
	}
	public DataInfo(int len, int dataType, short shortData){
		this.len = len ;
		this.dataType = dataType ;
		this.shortData = shortData ;
	}
	public DataInfo(int len, int dataType, int intData){
		this.len = len ;
		this.dataType = dataType ;
		this.intData = intData ;
	}
	public DataInfo(int len, int dataType, long longData){
		this.len = len ;
		this.dataType = dataType ;
		this.longData = longData ;
	}
	public DataInfo(int len, int dataType, byte[] srcBytes){
		this.len = len ;
		this.dataType = dataType ;

		this.bytes = new byte[srcBytes.length];
		System.arraycopy(srcBytes, 0, this.bytes, 0, srcBytes.length);

	}
}

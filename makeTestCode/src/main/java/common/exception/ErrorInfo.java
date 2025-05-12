package common.exception;

import java.util.ArrayList;

public class ErrorInfo {
	/** メッセージコード*/
	private String msgcode ;
	/** メッセ－ジ付加情報 */
	private ArrayList<String> addList = new ArrayList<String>();

	/**
	 * @return msgcode
	 */
	public String getMsgcode() {
		return msgcode;
	}
	/**
	 * @param msgcode セットする msgcode
	 */
	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}
	/**
	 * @return addList
	 */
	public ArrayList<String> getAddList() {
		return addList;
	}
	/**
	 * @param addList セットする addList
	 */
	public void setAddList(ArrayList<String> addList) {
		this.addList = addList;
	}


}

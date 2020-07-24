/**
 *
 */
package dto;

/**
 * @author bx0045
 * 中身のないclass
 * (後々削除する可能性あり)
 */
public abstract class BaseDto {
//	/**
//	 * レコードID
//	 */
//	protected int ID;
//	/**
//	 * データベースアクセスオブジェクト
//	 */
//	protected BaseDao dao;

//	public BaseDto() {
//
//	}
//
//	/**
//	 * レコードIDを設定
//	 * @param ID レコードID(数値)
//	 */
//	public void setID(int ID) {
//		if (0 < ID) {
//			this.ID = ID;
//		}
//	}
//
//	/**
//	 * レコードIDを設定
//	 * @param ID レコードID(文字列)
//	 */
//	public void setID(String ID) {
//		int intID = -1;
//		try {
//			intID = Integer.parseInt(ID);
//		} catch (NumberFormatException e) {
//
//		}
//		setID(intID);
//	}
//
//	/**
//	 * レコードIDを取得
//	 * @return レコードID
//	 */
//	public int getID() {
//		return ID;
//	}
//
////	/**
//	 * 対応したテーブルを操作するDAOクラスを取得する
//	 *
//	 * @return 対応したDAO
//	 */
//	protected abstract BaseDao getDao();
//
//	/**
//	 * オブジェクトのフィールドをコピーする
//	 *
//	 * @param bean コピーするオブジェクト
//	 */
//	protected abstract void copy(BaseDto bean);

//	/**
//	 * IDフィールドの情報を使用しレコードを取得する
//	 * @return 取得結果 成功true 失敗 false
//	 */
//	public boolean select() {
//		BaseDto bean =  getDao().selectById(ID);
//		if (!isNull(bean)) {
//			copy(bean);
//		}
//		return !isNull(bean);
//	}
//
//	/**
//	 * フィールド情報を使用し更新処理を行う
//	 * @return 更新結果 成功true 失敗 false
//	 */
//	public boolean update() {
//		return getDao().update(this);
//	}
//
//	/**
//	 * フィールド情報を使用し登録処理を行う
//	 * @return 登録結果 成功true 失敗 false
//	 */
//	public boolean insert() {
//		return getDao().insert(this);
//	}
//
//	/**
//	 * フィールド情報を使用し削除処理を行う
//	 * @return 削除結果 成功true 失敗 false
//	 */
//	public boolean delete() {
//		return getDao().delete(this);
//	}
//
//	/**
//	 * DB操作時に発生したExceptionを取得する
//	 * @return 発生した例外
//	 */
//	public StockManagementException getException() {
//		return dao.getException();
//	}
//
//	/**
//	 * DB操作時にExceptionの発生を取得する
//	 * @return true発生
//	 */
//	public boolean isError(){
//		return dao.isError();
//	}

}

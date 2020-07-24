package dto;

import java.util.Date;

/**
 * user_tableから情報を引き出す際に、
 * データの入れ物として使用する
 */
public class UserDto  extends BaseDto{

	private String user_id;
	private String user_pass;
	private String nickname;
	private Date created_at;
	private Date update_at;

	/**
	 * デフォルトコンストラクタ
	 */
	public UserDto() {
		super();
	};

	/**
	 * user_tableからデータを取り出す際に使用する
	 * @param user_id ユーザID
	 * @param user_pass ユーザパス
	 * @param nickname ニックネーム
	 * @param created_at データ作成日
	 * @param update_at データ更新日
	 */
	public UserDto(String user_id, String user_pass, String nickname, Date created_at, Date update_at) {
		super();
		this.user_id = user_id;
		this.user_pass = user_pass;
		this.nickname = nickname;
		this.created_at = created_at;
		this.update_at = update_at;
	}

	/**
	 * ユーザID情報を取得する
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * ユーザIDをセットする
	 * @param user_id ユーザID
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * ユーザパスワードを取得する
	 * @return user_pass
	 */
	public String getUser_pass() {
		return user_pass;
	}
	/**
	 * ユーザパスワードをセットする
	 * @param user_pass ユーザパスワード
	 */
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	/**
	 * ニックネーム情報の取得
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * ニックネームをフィールドにセットする
	 * @param nickname ニックネーム
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * データ作成日時情報を取得
	 * @return created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at 作成日をセットする
	 */
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	/**
	 * データの更新日情報をゲット
	 * @return update_at
	 */
	public Date getUpdate_at() {
		return update_at;
	}
	/**
	 * @param update_at 更新日情報をセットする
	 */
	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}


}

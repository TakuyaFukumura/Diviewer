package dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * possession_table用の入れ物
 * 所持データに加えてティッカーシンボルも格納できる
 */
public class PossessionDto extends BaseDto{
	private String user_id;
	private int ticker_id;
	private String ticker_symbol;
	private BigDecimal unit;
	private BigDecimal average_unit_cost;
	private Date created_at;
	private Date update_at;

	/**
	 * デフォルトコンストラクタ
	 */
	public PossessionDto() {
		super();
	}

	/**
	 * コンストラクタ
	 * 所持情報を入れられる
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param unit 保有数量
	 * @param average_unit_cost 平均取得単価
	 * @param created_at データ作成日
	 * @param update_at データ更新日
	 */
	public PossessionDto(String user_id, int ticker_id, BigDecimal unit, BigDecimal average_unit_cost, Date created_at,
			Date update_at) {
		super();
		this.user_id = user_id;
		this.ticker_id = ticker_id;
		this.unit = unit;
		this.average_unit_cost = average_unit_cost;
		this.created_at = created_at;
		this.update_at = update_at;
	}
	/**
	 * コンストラクタ
	 * 所持情報＋ティッカーシンボルを入れられる
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param ticker_symbol ティッカーシンボル
	 * @param unit 保有数量
	 * @param average_unit_cost 平均取得単価
	 * @param created_at データ作成日
	 * @param update_at データ更新日
	 */
	public PossessionDto(String user_id, int ticker_id, String ticker_symbol, BigDecimal unit, BigDecimal average_unit_cost, Date created_at,
			Date update_at) {
		super();
		this.user_id = user_id;
		this.ticker_id = ticker_id;
		this.ticker_symbol = ticker_symbol;
		this.unit = unit;
		this.average_unit_cost = average_unit_cost;
		this.created_at = created_at;
		this.update_at = update_at;
	}


	/**
	 * ユーザIDを返す
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * ユーザIDをフィールドに入れる
	 * @param user_id セットする user_id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * ティッカーIDを返す
	 * @return ticker_id
	 */
	public int getTicker_id() {
		return ticker_id;
	}

	/**
	 * ティッカーIDをフィールドに入れる
	 * @param ticker_id セットする ticker_id
	 */
	public void setTicker_id(int ticker_id) {
		this.ticker_id = ticker_id;
	}

	/**
	 * 口数(保有数量)を返す
	 * @return unit
	 */
	public BigDecimal getUnit() {
		return unit;
	}

	/**
	 * 保有数量をフィールドに入れる
	 * @param unit セットする unit
	 */
	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	/**
	 * 平均取得単価を返す
	 * @return average_unit_cost
	 */
	public BigDecimal getAverage_unit_cost() {
		return average_unit_cost;
	}

	/**
	 * 平均取得単価をフィールドに入れる
	 * @param average_unit_cost セットする average_unit_cost
	 */
	public void setAverage_unit_cost(BigDecimal average_unit_cost) {
		this.average_unit_cost = average_unit_cost;
	}

	/**
	 * データ作成日を返す
	 * @return created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}

	/**
	 * データ作成日をフィールドに入れる
	 * 現状、使用していない2020/06/21
	 * @param created_at セットする created_at
	 */
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	/**
	 * データ更新日を返す
	 * @return update_at
	 */
	public Date getUpdate_at() {
		return update_at;
	}

	/**
	 * データ更新日をフィールドに入れる
	 * @param update_at セットする update_at
	 */
	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}

	/**
	 * ティッカーシンボルを返す
	 * @return ticker_symbol
	 */
	public String getTicker_symbol() {
		return ticker_symbol;
	}

	/**
	 * ティッカーシンボルをフィールドに入れる
	 * @param ticker_symbol ティッカーシンボル
	 */
	public void setTicker_symbol(String ticker_symbol) {
		this.ticker_symbol = ticker_symbol;
	}

}

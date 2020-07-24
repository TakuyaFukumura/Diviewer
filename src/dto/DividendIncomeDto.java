/**
 *
 */
package dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bx0045
 * 配当データ受取用のDTO
 */
public class DividendIncomeDto extends BaseDto{
	private int dividend_income_id;
	private String user_id;
	private int ticker_id;
	private String ticker_symbol;
	private Date receipt_date;
	private BigDecimal aftertax_income;
	private Date created_at;
	private Date update_at;

	/**
	 * デフォルトコンストラクタ
	 */
	public DividendIncomeDto() {
		super();
	}


	/**
	 * コンストラクタ
	 * 配当情報を格納する
	 * @param dividend_income_id インカムID
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param receipt_date 受領日
	 * @param aftertax_income 税引き後受領額
	 * @param created_at データ作成日
	 * @param update_at データ更新日
	 */
	public DividendIncomeDto(int dividend_income_id, String user_id, int ticker_id, Date receipt_date,
			BigDecimal aftertax_income, Date created_at, Date update_at) {
		super();
		this.dividend_income_id = dividend_income_id;
		this.user_id = user_id;
		this.ticker_id = ticker_id;
		this.receipt_date = receipt_date;
		this.aftertax_income = aftertax_income;
		this.created_at = created_at;
		this.update_at = update_at;
	}

	/**
	 * コンストラクタ
	 * 配当情報＋ティッカーシンボルを格納する
	 * @param dividend_income_id インカムID
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param ticker_symbol ティッカーシンボル
	 * @param receipt_date 受領日
	 * @param aftertax_income 税引き後受領額
	 * @param created_at データ作成日
	 * @param update_at データ更新日
	 */
	public DividendIncomeDto(int dividend_income_id, String user_id,
			int ticker_id,String ticker_symbol, Date receipt_date,
			BigDecimal aftertax_income, Date created_at, Date update_at) {
		super();
		this.dividend_income_id = dividend_income_id;
		this.user_id = user_id;
		this.ticker_id = ticker_id;
		this.ticker_symbol = ticker_symbol;
		this.receipt_date = receipt_date;
		this.aftertax_income = aftertax_income;
		this.created_at = created_at;
		this.update_at = update_at;
	}

	/**
	 * インカムゲインIDを返す
	 * @return dividend_income_id
	 */
	public int getDividend_income_id() {
		return dividend_income_id;
	}

	/**
	 * @param dividend_income_id セットする dividend_income_id
	 */
	public void setDividend_income_id(int dividend_income_id) {
		this.dividend_income_id = dividend_income_id;
	}

	/**
	 * ユーザIDを返す
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
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
	 * @param ticker_id セットする ticker_id
	 */
	public void setTicker_id(int ticker_id) {
		this.ticker_id = ticker_id;
	}

	/**
	 * 配当受領日を返す
	 * @return receipt_date
	 */
	public Date getReceipt_date() {
		return receipt_date;
	}

	/**
	 * @param receipt_date セットする receipt_date
	 */
	public void setReceipt_date(Date receipt_date) {
		this.receipt_date = receipt_date;
	}

	/**
	 * 税引き後配当受取額を返す
	 * @return aftertax_income
	 */
	public BigDecimal getAftertax_income() {
		return aftertax_income;
	}

	/**
	 * @param aftertax_income セットする aftertax_income
	 */
	public void setAftertax_income(BigDecimal aftertax_income) {
		this.aftertax_income = aftertax_income;
	}

	/**
	 * データ作成日を返す
	 * @return created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}

	/**
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
	 * @param ticker_symbol セットする ticker_symbol
	 */
	public void setTicker_symbol(String ticker_symbol) {
		this.ticker_symbol = ticker_symbol;
	}

}

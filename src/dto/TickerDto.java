/**
 *
 */
package dto;

/**
 * ticker_tableから情報を引き出す際に、
 * 入れ物として使用する
 * @author bx0045
 */
public class TickerDto extends BaseDto{

	private int ticker_id;
	private String ticker_symbol;

	/**
	 * デフォルトコンストラクタ
	 */
	public TickerDto() {
		super();
	}

	/**
	 * コンストラクタ
	 * ティッカーのIDとシンボル情報を格納する
	 * @param ticker_id ティッカーID
	 * @param ticker_symbol ティッカーシンボル
	 */
	public TickerDto(int ticker_id, String ticker_symbol) {
		super();
		this.ticker_id = ticker_id;
		this.ticker_symbol = ticker_symbol;
	}

	/**
	 * ティッカーID情報を返す
	 * @return ticker_id
	 */
	public int getTicker_id() {
		return ticker_id;
	}

	/**
	 * ティッカーIDをフィールドに入れる
	 * @param ticker_id ティッカーID
	 */
	public void setTicker_id(int ticker_id) {
		this.ticker_id = ticker_id;
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

/**
 *
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TickerDto;

/**
 * @author bx0045
 * ティッカー情報の取得編集を行うクラス
 */
public class TickerDao extends BasisDao{
	private TickerDto tickerDto = new TickerDto();
	private List<TickerDto> tickerList = new ArrayList<>();

	/**
	 * CSVからティッカー情報をテーブルに追加する
	 * @return 成功true 失敗false
	 */
	public boolean insert(String ticker_id, String ticker_symbol ) {
		flag = false;
		int tickerId = Integer.parseInt(ticker_id);
		sql = "INSERT INTO ticker_table VALUES ( ?, ? )";
		if (openConnection()) {
			try {
				pstmt.setInt(1, tickerId);
				pstmt.setString(2, ticker_symbol);
				if (executeUpdate() == 1) {
					flag = true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return flag;
	}

	/**
	 * ティッカーテーブルの全件取得
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功:情報格納したDTO 失敗時null
	 * 複数権取得するためDTOのリストで対応しないといけない
	 */
	public List<TickerDto> getTickerAll() {
		tickerList = new ArrayList<>();
		sql = "SELECT ticker_id,ticker_symbol FROM ticker_table";
		if (openConnection()) {
	        try{
	        	executeQuery();
	    	} catch (SQLException e) {
	    		printSQLException(e);
	    	}finally {
	    		closeConnection();
	    	}
	    }
		return tickerList;
	}

	/**
	 * ティッカーシンボルを削除する
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功true 失敗false
	 */
	public boolean delete(String ticker_symbol) {
		flag = false; //検索して存在しなければtrue
		sql = "DELETE FROM ticker_table WHERE ticker_symbol = ? ";
		if (openConnection()) {
			try {
				pstmt.setString(1, ticker_symbol);
				if (executeUpdate() == 1) {
					flag = true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return flag;
	}

	/**
	 * ティッカーテーブル初期化
	 * @return 成功true 失敗false
	 */
	public boolean delete() {
		sql = "DELETE FROM ticker_table ";
		return deleteAll();
	}


	/**
	 * ティッカー情報をテーブルに追加する
	 * ティッカーIDは自動生成される。
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功true 失敗false
	 */
	public boolean insert(String ticker_symbol) {
		flag = false;
		sql = "INSERT INTO ticker_table (ticker_symbol ) VALUES ( ? )";
		if (openConnection()) {
			try {
				pstmt.setString(1, ticker_symbol);
				if (executeUpdate() == 1) {
					flag = true;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return flag;
	}


	/**
	 * ティッカーシンボルから該当データを取得する
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功:情報格納したDTO 失敗時null
	 */
	public TickerDto getTickerldBySymbol(String ticker_symbol) {
		tickerList = new ArrayList<>();
		sql = "SELECT ticker_id,ticker_symbol FROM "
				+ "ticker_table WHERE ticker_symbol = ?" ;
		if (openConnection()) {
			try {
				pstmt.setString(1, ticker_symbol);
				executeQuery();
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return tickerDto;
	}

	/**
	 * ティッカーシンボルから該当データを取得する
	 * @param ticker_id ティッカーID
	 * @return ティッカーID 失敗時null
	 */
	public TickerDto getTickerSymbolById(int ticker_id) {
		tickerList = new ArrayList<>();
		sql = "SELECT ticker_id,ticker_symbol FROM "
				+ "ticker_table WHERE ticker_id = ? ";
		if (openConnection()) {
			try {
				pstmt.setInt(1, ticker_id);
				executeQuery();
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return tickerDto;
	}

	/**
	 * SQL実行結果から値を取得する
	 * @param rs SQL実行結果
	 */
	@Override
	public void convertReserSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			tickerDto = new TickerDto();
			tickerDto.setTicker_id(rs.getInt("ticker_id"));
			tickerDto.setTicker_symbol(rs.getString("ticker_symbol"));
			tickerList.add(tickerDto);
		}
	}



}

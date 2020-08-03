/**
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TickerDto;

/**
 * @author bx0045
 * ティッカー情報の取得編集を行うクラス
 */
public class TickerDao extends BaseDao{
	String getTickerldBySymbol_sql = "select * from ticker_table "
			+ " where ticker_symbol = ? ";

	/**
	 * ティッカーテーブルの全件取得
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功:情報格納したDTO 失敗時null
	 * 複数権取得するためDTOのリストで対応しないといけない
	 */
	public List<TickerDto> getTickerAll() {
		List<TickerDto> tickerList = new ArrayList<>();
		String sql = "select * from ticker_table";
		try{
	    	Class.forName(CLASSNAME_ORACLE_DRIVER);
	    	Connection conn = DriverManager.getConnection(URL_ORACLE, USERNAME_ORACLE, PASSWORD_ORACLE);
	    	PreparedStatement ps = conn.prepareStatement(sql);
	        try{
	        	ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	            	TickerDto tickerDto = new TickerDto();
	            	tickerDto.setTicker_id(rs.getInt("ticker_id"));
	            	tickerDto.setTicker_symbol(rs.getString("ticker_symbol"));
	    			tickerList.add(tickerDto);
	            }
	    	} catch (Exception e) {
	    			e.printStackTrace();
	    	}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	closeConnection();
	    }
		return tickerList;
	}

	/**
	 * ティッカーシンボルを削除する
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功true 失敗false
	 */
	public boolean delete(String ticker_symbol) {
		boolean flag = false; //検索して存在しなければtrue
		String sql = "DELETE FROM ticker_table "
				+ "WHERE ticker_symbol = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, ticker_symbol);
				if (executeComonUpdate() == 1) {
					flag = true;
				}
			} catch (SQLException e) {

			}finally{
				closeConnection();
			}
		}
		return flag;
	}


	/**
	 * ティッカー情報をテーブルに追加する
	 * ティッカーIDは自動生成される。
	 * @param ticker_symbol ティッカーシンボル
	 * @return 成功true 失敗false
	 */
	public boolean insert(String ticker_symbol) {
		boolean flag = false;
		String sql = "INSERT INTO ticker_table (ticker_symbol ) VALUES ( ? )";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, ticker_symbol);
				if (executeComonUpdate() == 1) {
					flag = true;
				}
			} catch (Exception e) {

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
		TickerDto tickerDto = null;
		list = null;
		String sql = getTickerldBySymbol_sql;
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, ticker_symbol);
				if (executeComonQuery()) {
					if (list.size() == 1) {
						tickerDto = (TickerDto) list.get(0);
					}
				}
			} catch (SQLException e) {

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
		TickerDto tickerDto = null;
		list = null;
		String sql = "select * from ticker_table "
				+ " where ticker_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setInt(1, ticker_id);
				if (executeComonQuery()) {
					if (list.size() == 1) {
						tickerDto = (TickerDto) list.get(0);
					}
				}
			} catch (SQLException e) {

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
			TickerDto tickerDto = new TickerDto();
			tickerDto.setTicker_id(rs.getInt("ticker_id"));
			tickerDto.setTicker_symbol(rs.getString("ticker_symbol"));
			list.add(tickerDto);
		}
	}

}

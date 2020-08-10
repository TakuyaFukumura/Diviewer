/**
 *
 */
package dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DividendIncomeDto;

/**
 * @author fukumura
 * 配当情報の取得編集を行う
 */
public class DividendIncomeDao extends BasisDao{
	private DividendIncomeDto dividendIncomeDto = new DividendIncomeDto();
	private List<DividendIncomeDto> dividendIncomeList = new ArrayList<>();

	/**
	 * CSVからデータを登録
	 * @param dividend_income_id インカムID
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param receipt_date 受領日
	 * @param aftertax_income 税引き後受領額
	 * @param created_at 作成日
	 * @param update_at 更新日
	 * @return 成功true 失敗false
	 */
	public boolean insert(String dividend_income_id, String user_id,
			String ticker_id,String receipt_date, String aftertax_income,
			String created_at, String update_at) {
		int dividendIncomeId = Integer.parseInt(dividend_income_id);
		int tickerId = Integer.parseInt(ticker_id);
		BigDecimal aftertaxIncome = new BigDecimal(aftertax_income);
		flag = false;
		sql = "INSERT INTO dividend_income_table "
				+ "VALUES ( ?, ?, ?,  TO_DATE( ?, 'YYYY-MM-DD'),"
				+ " ?,  TO_DATE( ?, 'YYYY-MM-DD'),  TO_DATE( ?, 'YYYY-MM-DD'))";
		if (openConnection()) {
			try {
				pstmt.setInt(1, dividendIncomeId);
				pstmt.setString(2, user_id);
				pstmt.setInt(3, tickerId);
				pstmt.setString(4, receipt_date);
				pstmt.setBigDecimal(5, aftertaxIncome);
				pstmt.setString(6, created_at);
				pstmt.setString(7, update_at);
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
	 * dividend_income_table全件取得
	 * @return 全dividendIncome情報
	 */
	public List<DividendIncomeDto> getDividendIncomeAll() {
		dividendIncomeList = new ArrayList<>();
		sql = "SELECT * FROM dividend_income_table ";
		if (openConnection()) {
	        try{
	        	executeQuery();
	    	} catch (SQLException e) {
	    		printSQLException(e);
	    	}finally {
	    		closeConnection();
	    	}
	    }
		return dividendIncomeList;
	}

	/**
	 * 指定したユーザかつ年月の配当合計額を取得取得する
	 * @param user_id どのユーザのデータを参照するか
	 * @param year 取得したい年
	 * @param month 取得したい月
	 * @return 合計額
	 */
	public BigDecimal getSumIncomeByIdAndMonth(String user_id,
			int year, int month ) {
		BigDecimal sum = new BigDecimal("0");
		String start = null;
		String end = null;
		start = month < 10 ? "" + year + "-0" + month + "-01" : "" + year + "-" + month + "-01"; //三項演算子
		month++;
		if(month == 13) {
			end = "" + (year+1) + "-01-01";
		}else {
			end = month < 10 ? "" + year + "-0" + month + "-01" : "" + year + "-" + month + "-01";
		}
		sql = "SELECT SUM(aftertax_income)AS sum_income "
				+ "FROM dividend_income_table "
				+ "WHERE receipt_date  >= TO_DATE( ?, 'YYYY-MM-DD') "
				+ "AND receipt_date  < TO_DATE( ?, 'YYYY-MM-DD') "
				+ "AND user_id = ? ";
		if (openConnection()) {
			try {
				pstmt.setString(1, start);
				pstmt.setString(2, end);
				pstmt.setString(3, user_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					if(rs.getBigDecimal("sum_income") != null) {
						sum = (rs.getBigDecimal("sum_income"));
					}
				}
			} catch (SQLException e) {
				printSQLException(e);
			}finally{
				closeConnection();
			}
		}
		return sum;
	}


	/**
	 * データを更新する処理
	 * @param dividend_income_id ｲﾝｶﾑｹﾞｲﾝID
	 * @param aftertax_income 税引き後受領額
	 * @param receipt_date 受領日
	 * @return 成功true 失敗時false
	 */
	public boolean update(int dividend_income_id,
			BigDecimal aftertax_income, String receipt_date) {
		flag = false;
		sql = " UPDATE dividend_income_table "
				+ "SET aftertax_income =  ?, "
				+ "receipt_date = TO_DATE( ?, 'YYYY-MM-DD'), update_at = sysdate "
				+ "WHERE  dividend_income_id = ? ";
		if (openConnection()) {
			try {
				pstmt.setBigDecimal(1, aftertax_income);
				pstmt.setString(2, receipt_date);
				pstmt.setInt(3, dividend_income_id);
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
	 * 所持シンボルを削除する際に呼び出すメソッド
	 * シンボルの持つ配当情報を全件削除する
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @return 成功true 失敗false
	 */
	public boolean deleteWithSymbol(String user_id, int ticker_id ) {
		flag = false;
		sql = "DELETE FROM dividend_income_table "
				+ "WHERE user_id = ? "
				+ "AND ticker_id = ? ";
		if (openConnection()) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setInt(2, ticker_id);
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
	 * 配当情報の削除を実行する
	 * @param dividend_income_id 配当ID
	 * @return 成功true 失敗false
	 */
	public boolean delete(int dividend_income_id) {
		flag = false;
		sql = "DELETE FROM dividend_income_table "
				+ "WHERE dividend_income_id = ? ";
		if (openConnection()) {
			try {
				pstmt.setInt(1, dividend_income_id);
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
	 * 配当情報をすべて削除する
	 * @return 成功true 失敗false
	 */
	public boolean delete() {
		sql = "DELETE FROM dividend_income_table ";
		return deleteAll();
	}

	/**
	 * 配当情報の取得
	 * @param dividend_income_id 配当ID
	 * @return 配当DTO
	 */
	public DividendIncomeDto getDataById(int dividend_income_id) {
		sql = "SELECT * FROM dividend_income_table "
				+ "WHERE dividend_income_id = ? ";
		if (openConnection()) {
			try {
				pstmt.setInt(1, dividend_income_id);
			    executeQuery();
			} catch (SQLException e) {
				printSQLException(e);
			}finally {
			    closeConnection();
			}
		}
		return dividendIncomeDto;
	}


	/**
	 * データを登録
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param receipt_date 受領日
	 * @param aftertax_income 税引き後受領額
	 * @return 成功true 失敗false
	 */
	public boolean insert(String user_id, int ticker_id,
			String receipt_date, BigDecimal aftertax_income) {
		flag = false;
		sql = "INSERT INTO dividend_income_table "
				+ "(user_id, ticker_id, "
				+ "receipt_date, aftertax_income, "
				+ "created_at, update_at) "
				+ "VALUES ( ?, ?, TO_DATE( ?, 'YYYY-MM-DD'), ?, sysdate, sysdate)";
		if (openConnection()) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setInt(2, ticker_id);
				pstmt.setString(3, receipt_date);
				pstmt.setBigDecimal(4, aftertax_income);
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
	 * インカム最新情報の10件取得
	 * ティッカーIDで内部結合したものを返す
	 * @param user_id ユーザID
	 * @return リスト形式のインカムDTO
	 */
	public List<DividendIncomeDto> findNewIncom(String user_id) {
		dividendIncomeList = new ArrayList<>();
        sql = "SELECT * FROM("
        		+ "SELECT ticker_symbol,aftertax_income,receipt_date "
        		+ "FROM dividend_income_table "
        		+ "INNER JOIN ticker_table USING (ticker_id) "
        		+ "WHERE user_id = ? ORDER BY receipt_date DESC) "
        		+ "WHERE rownum <= 10";
        if (openConnection()) {
	        try{
	        	pstmt.setString(1, user_id);
	        	ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	DividendIncomeDto incomeDto = new DividendIncomeDto();
	    			incomeDto.setTicker_symbol(rs.getString("ticker_symbol"));
	    			incomeDto.setReceipt_date(rs.getDate("receipt_date"));
	    			incomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
	    			dividendIncomeList.add(incomeDto);
	            }
	    	} catch (SQLException e) {
	    		printSQLException(e);
	    	}finally {
	    		closeConnection();
	    	}
	    }
        return dividendIncomeList;
    }

	/**
	 * 配当情報の全件取得
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @return 配当DTOのリスト
	 */
	public List<DividendIncomeDto> findIncomList(String user_id, int ticker_id) {
		dividendIncomeList = new ArrayList<>();
        sql = "SELECT * FROM "
        		+ "dividend_income_table "
        		+ "WHERE ticker_id = ? "
        		+ "AND user_id = ? "
        		+ "ORDER BY receipt_date DESC";
        if (openConnection()) {
	        try{
	        	pstmt.setInt(1, ticker_id);
	        	pstmt.setString(2, user_id);
	        	executeQuery();
	    	} catch (SQLException e) {
	    		printSQLException(e);
	    	}finally {
	    		closeConnection();
	    	}
	    }
        return dividendIncomeList;
    }



	/**
	 * SQL実行結果から情報を取得する
	 * @param rs SQL実行結果
	 */
	@Override
	public void convertReserSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			dividendIncomeDto = new DividendIncomeDto();
			dividendIncomeDto.setDividend_income_id(rs.getInt("dividend_income_id"));
			dividendIncomeDto.setUser_id(rs.getString("user_id"));
			dividendIncomeDto.setTicker_id(rs.getInt("ticker_id"));
			dividendIncomeDto.setReceipt_date(rs.getDate("receipt_date"));
			dividendIncomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
			dividendIncomeDto.setCreated_at(rs.getDate("created_at"));
			dividendIncomeDto.setUpdate_at(rs.getDate("update_at"));
			dividendIncomeList.add(dividendIncomeDto);
		}
	}
}

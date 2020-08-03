/**
 *
 */
package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DividendIncomeDto;

/**
 * @author fukumura
 * 配当情報の取得編集を行う
 */
public class DividendIncomeDao extends BaseDao{
	/**
	 * dividend_income_table全件取得
	 * @return 全dividendIncome情報
	 */
	public List<DividendIncomeDto> getDividendIncomeAll() {
		list = null;
		List<DividendIncomeDto> dividendIncomeList = new ArrayList<>();
		String sql = "SELECT * FROM dividend_income_table ";
		if (createConnection(sql)) { //DB接続処理
			try {
				ResultSet rs;
				rs = pstmt.executeQuery(); //データベースを検索するメソッドSELECT用
				while (rs.next()) {
					DividendIncomeDto dividendIncomeDto = new DividendIncomeDto();
					dividendIncomeDto.setDividend_income_id(rs.getInt("dividend_income_id"));
					dividendIncomeDto.setUser_id(rs.getString("user_id"));
					dividendIncomeDto.setTicker_id(rs.getInt("ticker_id"));
					dividendIncomeDto.setReceipt_date(rs.getDate("receipt_date"));
					dividendIncomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
					dividendIncomeDto.setCreated_at(rs.getDate("created_at"));
					dividendIncomeDto.setUpdate_at(rs.getDate("update_at"));
					dividendIncomeList.add(dividendIncomeDto);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				closeConnection(); //DB切断処理
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
	public BigDecimal getSumIncomeByIdAndMonth(String user_id, int year, int month ) {
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
		String sql = "SELECT SUM(aftertax_income)AS sum_income FROM dividend_income_table "
				+ "WHERE receipt_date  >= TO_DATE( ?, 'YYYY-MM-DD') "
				+ "AND receipt_date  < TO_DATE( ?, 'YYYY-MM-DD') AND user_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, start);
				pstmt.setString(2, end);
				pstmt.setString(3, user_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
					if(rs.getBigDecimal("sum_income") != null)
						sum = (rs.getBigDecimal("sum_income"));
			} catch (Exception e) {

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
	public boolean update(int dividend_income_id, BigDecimal aftertax_income, String receipt_date) {
		boolean flag = false;
		String sql = " UPDATE dividend_income_table "
				+ "SET aftertax_income =  ?, "
				+ "receipt_date = TO_DATE( ?, 'YYYY-MM-DD'), update_at = sysdate "
				+ "WHERE  dividend_income_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setBigDecimal(1, aftertax_income);
				pstmt.setString(2, receipt_date);
				pstmt.setInt(3, dividend_income_id);
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
	 * 所持シンボルを削除する際に呼び出すメソッド
	 * シンボルの持つ配当情報を全件削除する
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @return 成功true 失敗false
	 */
	public boolean deleteWithSymbol(String user_id, int ticker_id ) {
		boolean flag = false; //検索して存在しなければtrue
		String sql = "DELETE FROM dividend_income_table "
				+ "WHERE user_id = ? "
				+ "AND ticker_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setInt(2, ticker_id);
				if (executeComonUpdate() == 1) { //ここが問題
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
	 * 配当情報の削除を実行する
	 * @param dividend_income_id 配当ID
	 * @return 成功true 失敗false
	 */
	public boolean delete(int dividend_income_id) {
		boolean flag = false; //検索して存在しなければtrue
		String sql = "DELETE FROM dividend_income_table "
				+ "WHERE dividend_income_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setInt(1, dividend_income_id);
				if (executeComonUpdate() == 1) { //ここが問題
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
	 * 配当情報の取得
	 * @param dividend_income_id 配当ID
	 * @return 配当DTO
	 */
	public DividendIncomeDto getDataById(int dividend_income_id) {
		DividendIncomeDto incomeDto = null;
		list = null;
		String sql = "SELECT * FROM dividend_income_table "
				+ "where dividend_income_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setInt(1, dividend_income_id);

				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					incomeDto = new DividendIncomeDto();
					incomeDto.setDividend_income_id(rs.getInt("dividend_income_id"));
	    			incomeDto.setUser_id(rs.getString("user_id"));
	    			incomeDto.setTicker_id(rs.getInt("ticker_id"));
	    			incomeDto.setReceipt_date(rs.getDate("receipt_date"));
	    			incomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
	    			incomeDto.setCreated_at(rs.getDate("created_at"));
	    			incomeDto.setUpdate_at(rs.getDate("update_at"));
				}
			} catch (Exception e) {

			}finally{
				closeConnection();
			}
		}
		return incomeDto;
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
		boolean flag = false;
		String sql = "INSERT INTO dividend_income_table "
				+ "(user_id, ticker_id, "
				+ "receipt_date, aftertax_income, "
				+ "created_at, update_at) "
				+ "VALUES ( ?, ?, TO_DATE( ?, 'YYYY-MM-DD'), ?, sysdate, sysdate)";

		if (createConnection(sql)) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setInt(2, ticker_id);
				pstmt.setString(3, receipt_date);
				pstmt.setBigDecimal(4, aftertax_income);

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
	 * インカム最新情報の10件取得
	 * ティッカーIDで内部結合したものを返す
	 * @param user_id ユーザID
	 * @return リスト形式のインカムDTO
	 */
	public List<DividendIncomeDto> findNewIncom(String user_id) {
        List<DividendIncomeDto> incomeList = new ArrayList<>();
        String sql = "SELECT * FROM("
        		+ "SELECT ticker_symbol,aftertax_income,receipt_date FROM "
        		+ "dividend_income_table INNER JOIN ticker_table USING (ticker_id) "
        		+ "WHERE user_id = ? ORDER BY receipt_date DESC) WHERE rownum <= 10";
	    try{
	    	Class.forName(CLASSNAME_ORACLE_DRIVER);
	    	Connection conn = DriverManager.getConnection(URL_ORACLE, USERNAME_ORACLE, PASSWORD_ORACLE);
	    	PreparedStatement ps = conn.prepareStatement(sql);
	        try{
	        	ps.setString(1, user_id);
	        	ResultSet rs = ps.executeQuery();
	            while (rs.next()) { //2020061215:50エラー記録rs.nextがfalse 原因はDBの参照にあった
	            	DividendIncomeDto incomeDto = new DividendIncomeDto();
	    			incomeDto.setTicker_symbol(rs.getString("ticker_symbol"));
	    			incomeDto.setReceipt_date(rs.getDate("receipt_date"));
	    			incomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
	    			incomeList.add(incomeDto);
	            }
	    	} catch (Exception e) {
	    			e.printStackTrace();
	    	}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    }
        return incomeList;
    }

	/**
	 * 配当情報の全件取得
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @return 配当DTOのリスト
	 */
	public List<DividendIncomeDto> findIncomList(String user_id, int ticker_id) {
        List<DividendIncomeDto> incomeList = new ArrayList<>();
        String sql = "SELECT * FROM "
        		+ "dividend_income_table "
        		+ "WHERE ticker_id = ? "
        		+ "AND user_id = ? "
        		+ "ORDER BY receipt_date DESC";
	    try{
	    	Class.forName(CLASSNAME_ORACLE_DRIVER);
	    	Connection conn = DriverManager.getConnection(URL_ORACLE, USERNAME_ORACLE, PASSWORD_ORACLE);
	    	PreparedStatement ps = conn.prepareStatement(sql);
	        try{
	        	ps.setInt(1, ticker_id);
	        	ps.setString(2, user_id);
	        	ResultSet rs = ps.executeQuery();
	        	incomeList = convertReserSet(rs, incomeList);
	    	} catch (Exception e) {
	    			e.printStackTrace();
	    	}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    }
        return incomeList;
    }



	/**
	 * SQL実行結果から情報を取得する
	 * @param rs SQL実行結果
	 */
	@Override
	public void convertReserSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			DividendIncomeDto incomeDto = new DividendIncomeDto();
			incomeDto.setDividend_income_id(rs.getInt("dividend_income_id"));
			incomeDto.setUser_id(rs.getString("user_id"));
			incomeDto.setTicker_id(rs.getInt("ticker_id"));
			//incomeDto.setTicker_symbol(rs.getString("ticker_symbol"));
			incomeDto.setReceipt_date(rs.getDate("receipt_date"));
			incomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
			incomeDto.setCreated_at(rs.getDate("created_at"));
			incomeDto.setUpdate_at(rs.getDate("update_at"));
			list.add(incomeDto);
		}
	}

	/**
	 * SQL実行結果から情報を取得する
	 * @param rs SQL実行結果
	 * @param incomeList データ格納用のリスト
	 * @return DBから取り出したデータのリスト
	 * @throws java.sql.SQLException エラー
	 */
	public List<DividendIncomeDto> convertReserSet(ResultSet rs, List<DividendIncomeDto> incomeList) throws SQLException {
		while (rs.next()) {
        	DividendIncomeDto incomeDto = new DividendIncomeDto();
			incomeDto.setDividend_income_id(rs.getInt("dividend_income_id"));
			incomeDto.setUser_id(rs.getString("user_id"));
			incomeDto.setTicker_id(rs.getInt("ticker_id"));
			incomeDto.setReceipt_date(rs.getDate("receipt_date"));
			incomeDto.setAftertax_income(rs.getBigDecimal("aftertax_income"));
			incomeDto.setCreated_at(rs.getDate("created_at"));
			incomeDto.setUpdate_at(rs.getDate("update_at"));
			incomeList.add(incomeDto);
        }
		return incomeList;
	}

}

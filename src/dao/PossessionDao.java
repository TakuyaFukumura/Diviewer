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

import dto.PossessionDto;

/**
 * 所持データの取得編集を行う
 * @author bx0045
 */
public class PossessionDao extends BaseDao{

	/**
	 * ティッカーシンボルの曖昧検索
	 * 条件に一致するデータをリストで返す
	 * @param user_id ユーザID
	 * @param ticker_symbol ティッカーシンボル
	 * @return DBから取り出したデータを格納したリスト
	 */
	public List<PossessionDto> searchPossession(String user_id, String ticker_symbol) {
        List<PossessionDto> possessionList = new ArrayList<>();
        ticker_symbol = "%" + ticker_symbol + "%";
        String sql = "SELECT * FROM possession_table INNER JOIN ticker_table USING (ticker_id) "
        		+ "WHERE ticker_symbol LIKE ? AND user_id = ? ORDER BY update_at DESC";
	    try{
	    	Class.forName(CLASSNAME_ORACLE_DRIVER);
	    	Connection conn = DriverManager.getConnection(URL_ORACLE, USERNAME_ORACLE, PASSWORD_ORACLE);
	    	PreparedStatement ps = conn.prepareStatement(sql);
	        try{
	        	ps.setString(1, ticker_symbol);
	        	ps.setString(2, user_id);
	        	ResultSet rs = ps.executeQuery();
	        	possessionList = convertReserSet(rs, possessionList);
	    	} catch (Exception e) {
	    			e.printStackTrace();
	    	}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    }
        return possessionList;
    }


	/**
	 * @param ticker_symbol ティッカーシンボル
	 * @param user_id ユーザID
	 * @return 成功true 失敗false
	 */
	public boolean delete(String ticker_symbol, String user_id) {
		boolean flag = false; //検索して存在しなければtrue
		String sql = "DELETE FROM possession_table "
				+ "WHERE user_id = ? "
				+ "AND ticker_id = "
				+ "(SELECT ticker_id FROM ticker_table WHERE ticker_symbol = ? )";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setString(2, ticker_symbol);
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
	 * データを更新
	 * @param user_id ユーザID
	 * @param old_ticker_id 旧ティッカーID
	 * @param ticker_symbol ティッカーシンボル
	 * @param unit 保有数量
	 * @param average_unit_cost 平均取得単価
	 * @param created_at データ作成日
	 *            更新に使うデータ群
	 * @return 成功true 失敗時false
	 */
	public boolean update(String user_id, int old_ticker_id, String ticker_symbol,
			BigDecimal unit, BigDecimal average_unit_cost,
			String created_at) {
		boolean flag = false;
		String sql = " UPDATE possession_table "
				+ "SET ticker_id = (SELECT ticker_id FROM ticker_table WHERE ticker_symbol = ?), "
				+ "unit = ?, "
				+ "average_unit_cost = ? , "
				+ "created_at = TO_DATE( ?, 'YYYY-MM-DD'), update_at = sysdate "
				+ "WHERE  user_id = ? "
				+ "AND ticker_id = ? ";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, ticker_symbol);
				pstmt.setBigDecimal(2, unit);
				pstmt.setBigDecimal(3, average_unit_cost);
				pstmt.setString(4, created_at);
				pstmt.setString(5, user_id);
				pstmt.setInt(6, old_ticker_id);
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




	//所持データインサート（必要情報：ユーザID、ティッカーID、口数、取得単価のみ、日付はsysdate）
	/**
	 * データを登録
	 * @param user_id ユーザID
	 * @param ticker_id ティッカーID
	 * @param unit 保有数量
	 * @param average_unit_cost 平均取得単価
	 * @return 成功true 失敗false
	 */
	public boolean insert(String user_id, int ticker_id,
			BigDecimal unit, BigDecimal average_unit_cost) {
		boolean flag = false;
		String sql = "INSERT INTO possession_table "
				+ "values ( ?, ?, ?, ?, sysdate, sysdate )";
		if (createConnection(sql)) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setInt(2, ticker_id);
				pstmt.setBigDecimal(3, unit);
				pstmt.setBigDecimal(4, average_unit_cost);

				if (executeComonUpdate() == 1) { //ここが問題
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
	 * ユーザIDとシンボルから所持データを取り出す
	 * @param ticker_symbol ティッカーシンボル
	 * @param user_id ユーザID
	 * @return 所持データ 失敗時null
	 */
	public PossessionDto getPossessionBySymbolId(String ticker_symbol, String user_id) {
		PossessionDto possessionDto = null;
		list = null;
		String sql = "SELECT * FROM possession_table "
				+ "INNER JOIN ticker_table USING (ticker_id) "
				+ "where user_id = ? "
				+ " AND ticker_symbol = ? ";

		if (createConnection(sql)) {
			try {
				pstmt.setString(1, user_id);
				pstmt.setString(2, ticker_symbol);

				if (executeComonQuery()) {//ここfalse
					if (list.size() == 1) {
						possessionDto = (PossessionDto) list.get(0);
					}
				}
			} catch (Exception e) {

			}finally{
				closeConnection();
			}
		}
		return possessionDto;
	}

	/**
	 * SQL実行結果からデータを取得する
	 */
	@Override
	public void convertReserSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			PossessionDto possessionDto = new PossessionDto();
			possessionDto.setUser_id(rs.getString("user_id"));
			possessionDto.setTicker_id(rs.getInt("ticker_id"));
			possessionDto.setTicker_symbol(rs.getString("ticker_symbol"));
			possessionDto.setUnit(rs.getBigDecimal("unit"));
			possessionDto.setAverage_unit_cost(rs.getBigDecimal("average_unit_cost"));
			possessionDto.setCreated_at(rs.getDate("created_at"));
			possessionDto.setUpdate_at(rs.getDate("update_at"));
			list.add(possessionDto);
		}
	}

	/**
	 * SQL実行結果からデータを取得する
	 * @param rs SQL実行結果
	 * @param possessionList 情報格納用のリスト
	 * @throws java.sql.SQLException SQLエラー
	 * @return DBから取り出したデータを格納したリスト
	 */
	public List<PossessionDto> convertReserSet(ResultSet rs, List<PossessionDto> possessionList) throws SQLException {
		while (rs.next()) {
			PossessionDto possessionDto = new PossessionDto();
			possessionDto.setUser_id(rs.getString("user_id"));
			possessionDto.setTicker_id(rs.getInt("ticker_id"));
			possessionDto.setTicker_symbol(rs.getString("ticker_symbol"));
			possessionDto.setUnit(rs.getBigDecimal("unit"));
			possessionDto.setAverage_unit_cost(rs.getBigDecimal("average_unit_cost"));
			possessionDto.setCreated_at(rs.getDate("created_at"));
			possessionDto.setUpdate_at(rs.getDate("update_at"));
			possessionList.add(possessionDto);
		}
		return possessionList;
	}
}

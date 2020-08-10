/**
 *
 */
package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import model.CsvModel;

/**
 * @author fukumura
 *
 */
public abstract class BaseTest {
	protected static CsvModel cm = new CsvModel();
	protected static boolean flag = false;

	@BeforeAll
	static void beforeAll() {
		backupAllCSV();
		readeTestCSV();
	}

	/**
	 * テスト前にバックアップ作成
	 * @return 成功true 失敗false
	 */
	static boolean backupAllCSV() {
		flag = cm.writeDividendIncomeCSV("test/dividend_income_table_bk.csv");
		if(flag) flag = cm.writeTickerCSV("test/ticker_table_bk.csv");
		if(flag) flag = cm.writeUserCSV("test/user_table_bk.csv");
		if(flag) flag = cm.writePossessionCSV("test/possession_table_bk.csv");
		return flag;
	}
	/**
	 * テストに使うDBを用意する
	 * @return 成功true 失敗false
	 */
	public static boolean readeTestCSV() {
		flag = cm.initializationTable();
		if(flag) flag = cm.readeTickerCSV("test/ticker_table.csv");
		if(flag) flag = cm.readeUserCSV("test/user_table.csv");
		if(flag) flag = cm.readePossessionCSV("test/possession_table.csv");
		if(flag) flag = cm.readeIncomeCSV("test/dividend_income_table.csv");
		return flag;
	}
	/**
	 * バックアップからロードしてDB復元
	 * @return 成功true 失敗false
	 */
	@AfterAll
	static void readeBackupCSV() {
		cm.initializationTable();
		cm.readeTickerCSV("test/ticker_table_bk.csv");
		cm.readeUserCSV("test/user_table_bk.csv");
		cm.readePossessionCSV("test/possession_table_bk.csv");
		cm.readeIncomeCSV("test/dividend_income_table_bk.csv");
	}
}

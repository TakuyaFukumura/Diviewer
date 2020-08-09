/**
 *
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import dao.DividendIncomeDao;
import dao.PossessionDao;
import dao.TickerDao;
import dao.UserDao;
import dto.DividendIncomeDto;
import dto.PossessionDto;
import dto.TickerDto;
import dto.UserDto;

/**
 * @author fukumura
 * CSVファイルの入出力処理を纏めたクラス
 */
public class CsvModel {
	private List<TickerDto> tickerList = new ArrayList<>();
	private List<UserDto> userList = new ArrayList<>();
	private List<PossessionDto> possessionList = new ArrayList<>();
	private List<DividendIncomeDto> dividendIncomeList = new ArrayList<>();
	private TickerDao tickerDao = new TickerDao();
	private UserDao userDao = new UserDao();
	private PossessionDao possessionDao = new PossessionDao();
	private DividendIncomeDao dividendIncomeDao = new DividendIncomeDao();
	private boolean flag = false;
	private String filePath = "";
	private String line = "";
	private Pattern p = Pattern.compile(",");

	/**
	 * 全テーブルをCSV出力していく
	 * パスはデフォ設定を使用する
	 * @return 成功true 失敗false
	 */
	public boolean outputAllCSV() {
		flag = writeDividendIncomeCSV(null);
		if(flag) flag = writeTickerCSV(null);
		if(flag) flag = writeUserCSV(null);
		if(flag) flag = writePossessionCSV(null);
		return flag;
	}

	/**
	 * 順番を考えつつ（INCOME→PO→TICKER→USER）
	 * テーブルを初期化してCSV入力していく デフォ設定
	 * @return 成功true 失敗false
	 */
	public boolean inputAllCSV() {
		flag = initializationTable(); //table初期化
		if(flag) flag = readeTickerCSV(null); //CSVから読み込む
		if(flag) flag = readeUserCSV(null);
		if(flag) flag = readePossessionCSV(null);
		if(flag) flag = readeIncomeCSV(null);
		return flag; //TODO 途中で処理止まったらどうする？
	}
	/**
	 * 順番を考えつつ（INCOME→PO→TICKER→USER）
	 * テーブルを初期化する
	 * @return 成功true 失敗false
	 */
	public boolean initializationTable() {
		flag = dividendIncomeDao.delete();
		if(flag) flag = possessionDao.delete();
		if(flag) flag = tickerDao.delete();
		if(flag) flag = userDao.delete();
		return flag;
	}

	/**
	 * user_table情報をCSV入力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean readeUserCSV(String csvFileName) {
		flag = false;
		setFilePath("user_table.csv", csvFileName);
		try (var reader = new BufferedReader(new FileReader(new File(filePath)))) {
			line = reader.readLine();
			while((line = reader.readLine()) != null) {
			    String[] result = p.split(line);//2行目からコンマで分解 for (int i=0; i<result.length; i++) System.out.print("[" + result[i] + "]");System.out.println("");
			    userDao.insert(result[0],result[1],
			    		result[2],result[3],result[4]);
			}
			flag = true; //成功か失敗か
		} catch (FileNotFoundException e) { // "C:\\tmp\\samplefile.txt" is not exists
			e.printStackTrace();
		} catch (IOException e) { // failed to read file
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ticker_table情報をCSV入力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean readeTickerCSV(String csvFileName) {
		flag = false;
		setFilePath("ticker_table.csv", csvFileName);
		try (var reader = new BufferedReader(new FileReader(new File(filePath)))) {
			line = reader.readLine();
			while((line = reader.readLine()) != null) {
			    String[] result = p.split(line);//2行目からコンマで分解 for (int i=0; i<result.length; i++) System.out.print("[" + result[i] + "]");System.out.println("");
			    tickerDao.insert(result[0],result[1]);
			}
			flag = true; //成功か失敗か
		} catch (FileNotFoundException e) { // "C:\\tmp\\samplefile.txt" is not exists
			e.printStackTrace();
		} catch (IOException e) { // failed to read file
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * possession_table情報をCSV入力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean readePossessionCSV(String csvFileName) {
		flag = false;
		setFilePath("possession_table.csv", csvFileName);
		try (var reader = new BufferedReader(new FileReader(new File(filePath)))) {
			line = reader.readLine();
			while((line = reader.readLine()) != null) {
			    String[] result = p.split(line);//2行目からコンマで分解 for (int i=0; i<result.length; i++) System.out.print("[" + result[i] + "]");System.out.println("");
			    possessionDao.insert(result[0],
			    		result[1],result[2],result[3],
			    		result[4],result[5]);//取得データをDBにインサート
			}
			flag = true; //成功か失敗か
		} catch (FileNotFoundException e) { // "C:\\tmp\\samplefile.txt" is not exists
			e.printStackTrace();
		} catch (IOException e) { // failed to read file
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * dividend_income_table情報をCSV入力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean readeIncomeCSV(String csvFileName) {
		flag = false;
		setFilePath("dividend_income_table.csv", csvFileName);
		try (var reader = new BufferedReader(new FileReader(new File(filePath)))) {
			line = reader.readLine();
			while((line = reader.readLine()) != null) {
			    String[] result = p.split(line);//2行目からコンマで分解 for (int i=0; i<result.length; i++) System.out.print("[" + result[i] + "]");System.out.println("");
			    dividendIncomeDao.insert(result[0],
			    		result[1],result[2],result[3],
			    		result[4],result[5],result[6]);//取得データをDBにインサート
			}
			flag = true; //成功か失敗か
		} catch (FileNotFoundException e) { // "C:\\tmp\\samplefile.txt" is not exists
			e.printStackTrace();
		} catch (IOException e) { // failed to read file
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * dividend_income_table情報をCSV出力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean writeDividendIncomeCSV(String csvFileName) {
		flag = false;
		setFilePath("dividend_income_table.csv", csvFileName);
		dividendIncomeList = dividendIncomeDao.getDividendIncomeAll();
		try {
            FileWriter fw = new FileWriter(filePath);
            fw.write("dividend_income_id,user_id,ticker_id,receipt_date,aftertax_income,created_at,update_at");
            for (DividendIncomeDto tmp: dividendIncomeList) {
            	String dividend_income_id = String.valueOf(tmp.getDividend_income_id());
            	String ticker_id = String.valueOf(tmp.getTicker_id());
            	String receipt_date = ConvDateToStr(tmp.getReceipt_date());
            	String created_at = ConvDateToStr(tmp.getCreated_at());
            	String update_at = ConvDateToStr(tmp.getUpdate_at());
            	fw.write("\n");
            	fw.write(dividend_income_id);
            	fw.write(",");
            	fw.write(tmp.getUser_id());
            	fw.write(",");
            	fw.write(ticker_id);
            	fw.write(",");
            	fw.write(receipt_date);
            	fw.write(",");
            	fw.write(tmp.getAftertax_income().toString());
            	fw.write(",");
            	fw.write(created_at);
            	fw.write(",");
            	fw.write(update_at);
            }
            fw.close();
            flag = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return flag;
	}
	/**
	 * possession_table情報をCSV出力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean writePossessionCSV(String csvFileName) {
		flag = false;
		setFilePath("possession_table.csv", csvFileName);
		possessionList = possessionDao.getPossessionAll();
		try {
            FileWriter fw = new FileWriter(filePath);
            fw.write("user_id,ticker_id,unit,average_unit_cost,created_at,update_at");
            for (PossessionDto tmp: possessionList) {
            	String ticker_id = String.valueOf(tmp.getTicker_id());
            	String created_at = ConvDateToStr(tmp.getCreated_at());
            	String update_at = ConvDateToStr(tmp.getUpdate_at());
            	fw.write("\n");
            	fw.write(tmp.getUser_id());
            	fw.write(",");
            	fw.write(ticker_id);
            	fw.write(",");
            	fw.write(tmp.getUnit().toString());
            	fw.write(",");
            	fw.write(tmp.getAverage_unit_cost().toString());
            	fw.write(",");
            	fw.write(created_at);
            	fw.write(",");
            	fw.write(update_at);
            }
            fw.close();
            flag = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return flag;
	}
	/**
	 * user_table情報をCSV出力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean writeUserCSV(String csvFileName) {
		flag = false;
		setFilePath("user_table.csv", csvFileName);
		userList = userDao.getUserAll();
		try {
            FileWriter fw = new FileWriter(filePath);
            fw.write("user_id,user_pass,nickname,created_at,update_at");
            for (UserDto tmp: userList) {
            	String created_at = ConvDateToStr(tmp.getCreated_at());
            	String update_at = ConvDateToStr(tmp.getUpdate_at());
            	fw.write("\n");
            	fw.write(tmp.getUser_id());
            	fw.write(",");
            	fw.write(tmp.getUser_pass());
            	fw.write(",");
            	fw.write(tmp.getNickname());
            	fw.write(",");
            	fw.write(created_at);
            	fw.write(",");
            	fw.write(update_at);
            }
            fw.close();
            flag = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return flag;
	}
	/**
	 * ticker_table情報をCSV出力する
	 * CSVファイル名はデフォルト値を設定したうえで変数化してある
	 * @param csvFileName 読み込むファイル名 default Null
	 * @return 成功true 失敗false
	 */
	public boolean writeTickerCSV(String csvFileName) {
		flag = false;
		setFilePath("ticker_table.csv", csvFileName);
		tickerList = tickerDao.getTickerAll();
		try {
            FileWriter fw = new FileWriter(filePath);
            fw.write("ticker_id,ticker_symbol");
            for (TickerDto tmp: tickerList) {
            	String ticker_id = String.valueOf(tmp.getTicker_id());
            	fw.write("\n");
            	fw.write(ticker_id);
            	fw.write(",");
            	fw.write(tmp.getTicker_symbol());
            }
            fw.close();
            flag = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return flag;
	}

	public void setFilePath(String fileName, String csvFileName) {
		if(csvFileName != null) fileName = csvFileName;
		filePath = "WebContent/csv/" + fileName;
	}

	public String ConvDateToStr(Date date) {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return str;
	}
}

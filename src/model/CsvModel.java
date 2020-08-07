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
	private String filename = "";
	private String line = "";
	private Pattern p = Pattern.compile(",");

	/**
	 * possession_table情報をCSV入力する
	 * @return 削除成功true 削除失敗false
	 */
	public boolean inputPossessionCSV() {
		flag = false;
		possessionDao.delete(); //DB初期化
		filename = "WebContent/csv/possession_table.csv";
		try (var reader = new BufferedReader(new FileReader(new File(filename)))) {
			line = reader.readLine();
			while((line = reader.readLine()) != null) {
			    String[] result = p.split(line);//2行目からコンマで分解 for (int i=0; i<result.length; i++) System.out.print("[" + result[i] + "]");System.out.println("");
			    possessionDao.csvInsert(result[0],
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
	 * @return 削除成功true 削除失敗false
	 */
	public boolean inputIncomeCSV() {
		flag = false;
		dividendIncomeDao.delete(); //DB初期化
		filename = "WebContent/csv/dividend_income_table.csv";
		try (var reader = new BufferedReader(new FileReader(new File(filename)))) {
			line = reader.readLine();
			while((line = reader.readLine()) != null) {
			    String[] result = p.split(line);//2行目からコンマで分解 for (int i=0; i<result.length; i++) System.out.print("[" + result[i] + "]");System.out.println("");
			    dividendIncomeDao.csvInsert(result[0],
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
	 * @return 削除成功true 削除失敗false
	 */
	public boolean outputDividendIncomeCSV() {
		flag = false;
		dividendIncomeList = dividendIncomeDao.getDividendIncomeAll();
		try {
            FileWriter fw = new FileWriter("WebContent/csv/dividend_income_table.csv");
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
	 * @return 削除成功true 削除失敗false
	 */
	public boolean outputPossessionCSV() {
		flag = false;
		possessionList = possessionDao.getPossessionAll();
		try {
            FileWriter fw = new FileWriter("WebContent/csv/possession_table.csv");
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
	 * @return 削除成功true 削除失敗false
	 */
	public boolean outputUserCSV() {
		flag = false;
		userList = userDao.getUserAll();
		try {
            FileWriter fw = new FileWriter("WebContent/csv/user_table.csv");
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
	 * @return 削除成功true 削除失敗false
	 */
	public boolean outputTickerCSV() {
		flag = false;
		tickerList = tickerDao.getTickerAll();
		try {
            FileWriter fw = new FileWriter("WebContent/csv/ticker_table.csv");
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

	public String ConvDateToStr(Date date) {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return str;
	}
}

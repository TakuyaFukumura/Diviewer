/**
 *
 */
package model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.TickerDao;
import dao.UserDao;
import dto.TickerDto;
import dto.UserDto;

/**
 * @author fukumura
 * CSVファイルの入出力処理を纏めたクラス
 */
public class CsvModel {
	private List<TickerDto> tickerList = new ArrayList<>();
	private List<UserDto> userList = new ArrayList<>();
	private TickerDao tickerDao = new TickerDao();
	private UserDao userDao = new UserDao();
	private boolean flag = false;
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

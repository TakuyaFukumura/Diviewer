# Diviewer
インカムゲイン管理アプリのプロトタイプ
インカムゲインを視覚化したい

#使用SQL
CREATE TABLE user_table
 (
	user_id VARCHAR2(15) NOT NULL,
	user_pass VARCHAR2(15 CHAR) NOT NULL,
	nickname VARCHAR2(15 CHAR) NOT NULL UNIQUE,
	created_at DATE default  SYSDATE,
	update_at DATE default SYSDATE NOT NULL,
	PRIMARY KEY(user_id)
 );

create sequence SEQ_ticker_id
  increment by 1
  start with 1
  maxvalue 99999
  minvalue 1
  nocycle
  cache 3
  order;

INSERT INTO user_table VALUES
('fukumura', 'takuya045A', '46kumaBC', sysdate, sysdate);

CREATE TABLE ticker_table
 (
	ticker_id NUMBER(5) default SEQ_ticker_id.nextval PRIMARY KEY,
	ticker_symbol VARCHAR2(15 CHAR) NOT NULL
 );


CREATE TABLE possession_table
 (
	user_id VARCHAR2(15) NOT NULL,
	ticker_id  NUMBER(5) NOT NULL,
	unit NUMBER(10) default 0 NOT NULL,
	average_unit_cost  NUMBER(10, 2) default 0 NOT NULL,
	created_at DATE default  SYSDATE,
	update_at DATE default SYSDATE NOT NULL,
	PRIMARY KEY(user_id, ticker_id),
	FOREIGN KEY(user_id) REFERENCES user_table(user_id),
	FOREIGN KEY(ticker_id) REFERENCES ticker_table(ticker_id)
 );


create sequence SEQ_dividend_income_id
  increment by 1
  start with 1
  maxvalue 99999
  minvalue 1
  nocycle
  cache 3
  order;


CREATE TABLE dividend_income_table
 (
	dividend_income_id NUMBER(5) default SEQ_dividend_income_id.nextval PRIMARY KEY,
	user_id VARCHAR2(15) NOT NULL,
	ticker_id  NUMBER(5) NOT NULL,
	receipt_date DATE default SYSDATE NOT NULL,
	aftertax_income NUMBER(10, 2) default 0 NOT NULL,
	created_at DATE default  SYSDATE,
	update_at DATE default SYSDATE NOT NULL,
	FOREIGN KEY(user_id) REFERENCES user_table(user_id),
	FOREIGN KEY(ticker_id) REFERENCES ticker_table(ticker_id)
 );



INSERT INTO ticker_table (ticker_symbol ) VALUES ( 'PFFD');
INSERT INTO ticker_table (ticker_symbol ) VALUES ( 'SPDY');

INSERT INTO possession_table VALUES ('fukumura', 1,1, 35.59, sysdate, sysdate);
INSERT INTO possession_table VALUES ('fukumura', 2,1, 30.30, sysdate, sysdate);

INSERT INTO dividend_income_table (user_id, ticker_id, receipt_date,
 aftertax_income, created_at,
  update_at) VALUES ('fukumura', 1,sysdate, 0.2, sysdate, sysdate);

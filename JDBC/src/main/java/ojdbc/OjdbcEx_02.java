package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OjdbcEx_02 {

	//OJDBC드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
		
	// DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "SCOTT";
	private static final String PASSWORRD = "1234";
	
	// OJDBC 관련 객체
	private static Connection conn = null;	// DB 연결 객체
	private static Statement st = null; 	// SQL 수행 객체
	private static ResultSet rs = null; 	// 결과 집합 객체
	
	// main 시작
	public static void main(String[] args) {
		
		// ----- 드라이버 로드 -----
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// -------  SQL작성  -------
		
		// sql1에 테이블 생성 쿼리 저장
		String sql1= "";
		sql1 += "CREATE TABLE userTest(";
		sql1 += "	idx NUMBER CONSTRAINT pk_user_test PRIMARY KEY";
		sql1 += "	, name VARCHAR2(50) NOT NULL";
		sql1 += "	, phone VARCHAR2(50) NOT NULL";
		sql1 += ")";

		// sql2에 시퀸스 생성 쿼리 저장
		String sql2= "";
		sql2 += "CREATE Sequence seq_usertest";
		sql2 += "		INCREMENT BY 1";
		sql2 += "		START WITH 1";
		
		try {
			// DB연결
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORRD);

			// SQL 수행
			st = conn.createStatement(); // Statement 객체 얻기
			/////////////////////////////////////////////////////////
			// ---------- usertest테이블 생성하기 -------------------
			/////////////////////////////////////////////////////////
			rs = st.executeQuery("SELECT count(*) cnt FROM user_tables WHERE table_name = upper('usertest')");
			// 조회된 첫 번째 행 참조하기
			rs.next();

			// 조회결과의 첫번째 컬럼값 얻어오기
//			if ( rs.getInt(1)>0 ) {}
			
			// 조회결과의 "cnt" 컬럼값 얻어오기
			if ( rs.getInt("cnt")>0 ) {
				//userTest테이블 삭제하기
				st.execute("DROP TABLE usertest");
				
			}
			
			// 테이블 생성하기
			st.execute(sql1);
			
			// ---------- usertest테이블 생성하기 끝 -------------------

			
			/////////////////////////////////////////////////////////
			// ---------- seq_usertest 시퀸스 생성하기 -------------------
			/////////////////////////////////////////////////////////
			
			rs = st.executeQuery("SELECT count(*) cnt FROM user_sequences WHERE sequence_name = upper('seq_usertest')");

			// 조회된 첫 번째 행 참조하기
			rs.next();
			
			// seq_usertest시퀀스가 이미 존재하는지 확인
			if ( rs.getInt("cnt") > 0 ) {
				// -> 이미 있으면 삭제
				st.execute("DROP sequence seq_usertest");
			}
			
			// seq_usertest 시퀀스 생성
			st.execute(sql2);

			// ---------- seq_usertest 시퀸스 생성하기 끝 -------------------

			
			/////////////////////////////////////////////////////////
			//---------최종 확인 메세지 출력------------
			/////////////////////////////////////////////////////////
			
			// usertest테이블이 이미 존재하는지 확인
			rs = st.executeQuery("SELECT count(*) cnt FROM user_tables WHERE table_name = upper('usertest')");

			// 조회된 첫 번째 행 참조하기
			rs.next();

			// 이미 usrtest테이블이 존재할 경우
			if ( rs.getInt("cnt")>0 ) {
				System.out.println("userTest생성완료!");
			}
			
			// seq_usertest시퀸스가 존재하는지 확인
			rs = st.executeQuery("SELECT count(*) cnt FROM user_sequences WHERE sequence_name = upper('seq_usertest')");

			// 조회된 첫 번째 행 참조하기
			rs.next();
			
			// seq_usertest시퀀스가 이미 존재하는지 확인
			if ( rs.getInt("cnt") > 0 ) {
				System.out.println("seq_userTest생성완료!");
			}
			
			//---------최종 확인 메세지 출력 끝------------

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { //연결을 종료하고 자원을 해제하는 과정을 위한 finally문
			// 5. 연결 종료
			
			// 역순으로 import한 것들을 제거해줘야한다(서로 엮여 있기 때문에 처음부터 하면 문제가 생김)
			
			try {
				if(rs!=null && !rs.isClosed()) rs.close();
				//rs!=null(rs가 null이 아니면서) && !rs.isClosed() (닫힌적이 없을경우)
				//rs.close( rs를 닫아라)
				
				if(st!=null && !st.isClosed()) st.close();
				//st!=null(st가 null이 아니면서) && !st.isClosed() (닫힌적이 없을 경우)
				// st.close() (st를 닫아라)
				
				if(conn!=null && !conn.isClosed()) conn.close();
				//conn!=null(conn이 null이 아니면서) && !conn.isClosed() (닫힌적이 없을경우)
				//conn.close( conn을 닫아라)
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
	}
}

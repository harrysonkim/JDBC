package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// JDBC 라이브러리 사용방법

// +++++ 1. JDBC드라이버(driver) 로드 +++++
// +++++ 2. DB접속(연결, Connection) +++++
// +++++ 3. SQL 쿼리 수행 +++++
// +++++ 4. 결과값 처리 +++++
// +++++ 5. 연결 종료 +++++

public class OjdbcEx_01 {

	public static void main(String[] args) {
		
////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// +++++++++++ 1. JDBC드라이버(driver) 로드 +++++++++++
		// 붙여넣은 ojdbc6.jar파일에 OracleDriver.Class를 이용하여 JDBC로드를 하는데,
		// OracleDriver.class는 byte코드로 되어 있어서 new로 호출 할 수 없다.
		// 그래서 자바에는 Class라는 클래스가 전체적인 클래스의 관리를 하기에
		// Class.forname("클래스명")으로 호출하여 바이트 코드도 호출 가능

		// *OracleDriver.class의 경로
		// Libraries => Web App Libraries => ojdb6.jar => 
		// oracle => jdbc => driver => OracleDriver.class 파일이 존재
		
		try {
			
			//Class.forName("클래스명"); => 클래스의 이름으로 호출(경로까지)
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		////////////////////////////////////////////
		// ===== OJDBC 사용에 필요한 변수들 ===== //
		////////////////////////////////////////////
		// * import 자동 등록 단축키 : ctrl + shift + o
		
		// java.sql.Connection를 import => 2. DB접속에 사용
		Connection conn = null; // DB연결 객페 ( 접속 객체)
		
		// java.sql.Statement를 import => 3. SQL쿼리 수행에 사용
		Statement st = null; // SQL구문 저장 및 SQL구문 수행 객체
		
		// java.sql.ResultSet를 import => 4. 결과값처리에 사용
		ResultSet rs = null; // 조회 결과를 반환하는 객체
		
////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// +++++++++++ 2. DB접속(연결, Connection) +++++++++++5
		
		try {
//			conn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
			conn = DriverManager.getConnection(
					// @접속할 컴퓨터 : 프로그램 PORT : SID(DB이름 : Express Edition)
					"jdbc:oracle:thin:@localhost:1521:xe",
					// 사용할 DB계정
					"scott",
					// 사용할 DB계정의 비밀번호
					"1234");
			
////////////////////////////////////////////////////////////////////////////////////////////////////////

			// +++++ 3. SQL 쿼리 수행 +++++

			// 3-1. SQL쿼리를 저장/수행하는 객체 생성
			st = conn.createStatement();
		
			
			// 3-2. SQL쿼리를 수행 및 결과 저장
			
			// 쿼리 수행에 사용할 객체.executeQuery("쿼리");
			// 	* SELECT구문 사용시 executeQuery()메소드 사용
			
			// 결과값을 rs에 저장
			rs = st.executeQuery("SELECT * FROM emp ORDER BY empno");

////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// +++++ 4. 결과값 처리 +++++
			
			// SELECT 쿼리의 수행 결과( 결과 집합, ResultSet )
			// java.sql.ResultSet 객체로 반환받아 저장
			
			// ResultSet객체는 조회 결과 중에서 한 행씩 참조할 수 있다
			
			// ResultSet객체.next() 메소드
			// 	=> 조회 결과를 순차적으로 한 행씩 옮겨가며 참조
			// 	=> 메소드를 실행할 때마다 ResultSet객체가 다음 행을 참조
			
			//  => true반환		- 조회할 행이 존재할 때
			//  => false반환	- 더 이상 조회할 행이 없을 때
			
			// 조회 결과를 처음부터 끝까지 한 행씩 반복
			while( rs.next() ) {
				
				//////////////////// enpno컬럼 출력하기 //////////////////////
				// rs.getInt(1)		  => 첫번째 행(empno)을 불러온다 
				// rs.getInt("empno") => empno을 불러온다
//				System.out.println(rs.getInt(1) + ", " + rs.getInt("empno"));
				///////////////////////////////////////////////////////////////
				
				/////////////// emp테이블의 모든 정보 출력하기 ///////////////
//				System.out.print(rs.getInt("empno") + "," );
//				System.out.print(rs.getString("ename")  + "," );
//				System.out.print(rs.getString("job")  + "," );
//				System.out.print(rs.getInt("mgr")  + "," );
//				
//				System.out.print(rs.getDate("hiredate")  + "," );
//				System.out.print(rs.getDouble("sal")  + "," );
//				System.out.print(rs.getDouble("comm")  + "," );
//				System.out.println(rs.getInt("deptno") );
				///////////////////////////////////////////////////////////////
				
				/////////////// emp테이블의 모든 정보 String형으로 출력하기 ///////////////
				System.out.print(rs.getString("empno") + ":" );
				System.out.print(rs.getString("ename")  + ":" );
				System.out.print(rs.getString("job")  + ":" );
				System.out.print(rs.getString("mgr")  + ":" );
				
				System.out.print(rs.getString("hiredate")  + ":" );
				System.out.print(rs.getString("sal")  + ":" );
				System.out.print(rs.getString("comm")  + ":" );
				System.out.println(rs.getString("deptno") );
				///////////////////////////////////////////////////////////////

			} // while( rs.next() ) 끝
			
////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}finally {
			
			// +++++ 5. 연결 종료 +++++
			// 열었던 자원의 반대순서로 close
			
			try {
				
				if( rs!=null && !rs.isClosed() )	rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {

				if( st!=null && !st.isClosed() )	st.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {

				if( conn!=null && !conn.isClosed() )	conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} // finally 끝
	} // main 끝
} // class 끝

package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OjdbcEx_04 {

	// OJDBC드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "SCOTT";
	private static final String PASSWORRD = "1234";

	// OJDBC 관련 객체
	private static Connection conn = null; // DB 연결 객체
	private static Statement st = null; // SQL 수행 객체 (SQL인젝션에 취약)
	private static PreparedStatement ps = null; // SQL 수행 객체(입력될 데이터를 ?(물음표)로 표시)
	private static ResultSet rs = null; // 결과 집합 객체

	public static void main(String[] args) {

		// 드라이버 로드
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 조회할 job입력받기
		Scanner sc = new Scanner(System.in);
		System.out.println("조회할 job입력 : ");
		String job = sc.nextLine();

		// SQL 작성
		String sql1 = "";
		sql1 += "SELECT * FROM emp";
		sql1 += "	WHERE upper(job) LIKE '%' || upper(?) || '%'";
		sql1 += "ORDER BY empno";

//		System.out.println(sql1);

		try {
			// DB연결
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORRD);
			// SQL 수행 객체
//			st = conn.createStatement();
//			rs = st.executeQuery(sql1);

			// Statement객체를 생성할 때에는 SQL 구문을 입력하지 않는다
			// 추후에 구문을 실행(execute)할 때 입력한다

			// PreparedStatement객체를 생성하면서 SQL구문을 "미리" 입력해야 한다
			ps = conn.prepareStatement(sql1);

			// SQL구문의 첫 번째 ? 에 job변수 값을 채워 넣는다
			ps.setString(1, job);

			// SQL구문 수행
			rs = ps.executeQuery();

			// SQL 조회 결과 처리
			while (rs.next()) {
				System.out.print(rs.getString("empno") + "\t");
				System.out.print(rs.getString("ename") + "\t");
				System.out.println(rs.getString("job"));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally { // 연결을 종료하고 자원을 해제하는 과정을 위한 finally문

			// 역순으로 import한 것들을 제거해줘야한다(서로 엮여 있기 때문에 처음부터 하면 문제가 생김)

			try {
				if (rs != null && !rs.isClosed())
					rs.close();
				// rs!=null(rs가 null이 아니면서) && !rs.isClosed() (닫힌적이 없을경우)
				// rs.close( rs를 닫아라)

				if (ps != null && !ps.isClosed())
					ps.close();
				// st!=null(st가 null이 아니면서) && !st.isClosed() (닫힌적이 없을 경우)
				// st.close() (st를 닫아라)

				if (conn != null && !conn.isClosed())
					conn.close();
				// conn!=null(conn이 null이 아니면서) && !conn.isClosed() (닫힌적이 없을경우)
				// conn.close( conn을 닫아라)

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}

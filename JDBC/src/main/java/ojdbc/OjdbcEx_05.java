package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Emp;

public class OjdbcEx_05 {

	// OJDBC드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "SCOTT";
	private static final String PASSWORD = "1234";

	// OJDBC 관련 객체
	private static Connection conn = null; // DB 연결 객체
	private static PreparedStatement ps = null; // SQL 수행 객체(입력될 데이터를 ?(물음표)로 표시)
	private static ResultSet rs = null; // 결과 집합 객체

	public static void main(String[] args) {

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 조회할 부서 번호 입력받기
		Scanner sc = new Scanner(System.in);
		System.out.println("조회할 부서 번호 입력 : ");
		int deptno = sc.nextInt();
//		int deptno = new Scanner(System.in).nextInt(); // 일회용 코드

		String sql = "";
		sql += "SELECT * FROM emp";
		sql += "	WHERE deptno = ?";
		sql += "ORDER BY empno";

		// 조회결과를 저장할 리스트 객체
		List<Emp> list = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// SQL 수행 객체
			ps = conn.prepareStatement(sql);

			// ? 파라미터를 채우기
			ps.setInt(1, deptno);

			// SQL수행 및 결과 저장
			rs = ps.executeQuery();

			// 코드 결과 처리
			while (rs.next()) {

				// 조회 결과의 각 행을 데이터를 저장할 객체
				Emp emp = new Emp();

				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setMgr(rs.getInt("mgr"));

				emp.setHiredate(rs.getDate("hiredate"));
				emp.setSal(rs.getDouble("sal"));
				emp.setComm(rs.getDouble("comm"));
				emp.setDeptno(rs.getInt("deptno"));

				// 행 데이터를 리스트에 추가하기
				list.add(emp);

			}

			// 전체 행 데이터 출력
			for (Emp e : list) {
				System.out.println(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 연결을 종료하고 자원을 해제하는 과정을 위한 finally문

			// 역순으로 import한 것들을 제거해줘야한다(서로 엮여 있기 때문에 처음부터 하면 문제가 생김)

			try {
				// rs!=null(rs가 null이 아니면서) && !rs.isClosed() (닫힌적이 없을경우)
				// rs.close( rs를 닫아라)
				if (rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				// st!=null(st가 null이 아니면서) && !st.isClosed() (닫힌적이 없을 경우)
				// st.close() (st를 닫아라)
				if (ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				// conn!=null(conn이 null이 아니면서) && !conn.isClosed() (닫힌적이 없을경우)
				// conn.close( conn을 닫아라)
				if (conn != null && !conn.isClosed()) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
}

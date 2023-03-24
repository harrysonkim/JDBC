package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.face.EmpDao;
import dto.Emp;

public class EmpDaoImpl implements EmpDao {

	// OJDBC 드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "1234";

	// ODJBC 관련 객체
	private static Connection conn = null; // DB 연결 객체
	private static PreparedStatement ps = null; // SQL 수행 객체
	private static ResultSet rs = null; // 결과 집합 객체

	@Override
	public List<Emp> selectAll() {

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String sql = "";
		sql += "SELECT * FROM emp";
		sql += " ORDER BY empno";

		List<Emp> list = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

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

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 최종 조회 반환
		return list;
	}

	@Override
	public List<Emp> selectByDeptno(int deptno){
		
		String sql = "";
		sql += "SELECT * FROM emp";
		sql += " WHERE deptno = ?";
		sql += " ORDER BY empno";

		// 조회결과를 저장할 emp_list 생성
		List<Emp> list = new ArrayList<>();

		try {

			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, deptno);
			
			rs = ps.executeQuery();

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

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 최종 조회 반환
		return list;
		
	}

	@Override
	public int insert(Emp insertData) {

		String sql = "";
		sql += "INSERT INTO emp";
		sql += "( empno, ename, deptno )";
		sql += " VALUES( ?,?,?)";
		
		// 수행 결과 저장 변수
		// SQL코드에 영향 받은 행 수를 저장
		int res = 0;
		
		try {

			// SQL 수행 객체
			ps = conn.prepareStatement(sql);

			// ? 파라미터 채우기
			ps.setInt(1, insertData.getEmpno());
			ps.setString(2, insertData.getEname());
			ps.setInt(3, insertData.getDeptno());
			
			// SQL 수행
			res = ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		
		} finally {

			try {
				if (ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 최종 결과 반환
		return res;
		
	}

	@Override
	public Emp selectByEmp(Emp insertData) {

		String sql = "";
		sql += "SELECT * FROM emp";
		sql += " WHERE empno = ?";
		
		// 조회 결과를 저장할 객체
		Emp result = null;
		
		try {

			// SQL 수행 객체
			ps = conn.prepareStatement(sql);
			ps.setInt(1, insertData.getEmpno());
			
			// SQL 수행 결과 저장
			rs = ps.executeQuery();

			while (rs.next()) {

				// 조회 결과의 각 행을 데이터를 저장할 객체
				result = new Emp();

				result.setEmpno(rs.getInt("empno"));
				result.setEname(rs.getString("ename"));
				result.setJob(rs.getString("job"));
				result.setMgr(rs.getInt("mgr"));

				result.setHiredate(rs.getDate("hiredate"));
				result.setSal(rs.getDouble("sal"));
				result.setComm(rs.getDouble("comm"));
				result.setDeptno(rs.getInt("deptno"));

			}
		
		} catch (SQLException e) {

			e.printStackTrace();
		
		} finally {

			try {
				if (rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 최종 결과 반환
		return result;
		
	}

	@Override
	public Emp selectByEmpno(int empno) {

		String sql = "";
		sql += "SELECT * FROM emp";
		sql += " WHERE empno = ?";
		// 조회 결과를 저장할 객체
		Emp result = null;
		
		try {

			// SQL 수행 객체
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empno);
			
			// SQL 수행 결과 저장
			rs = ps.executeQuery();

			while (rs.next()) {

				// 조회 결과의 각 행을 데이터를 저장할 객체
				result = new Emp();

				result.setEmpno(rs.getInt("empno"));
				result.setEname(rs.getString("ename"));
				result.setJob(rs.getString("job"));
				result.setMgr(rs.getInt("mgr"));

				result.setHiredate(rs.getDate("hiredate"));
				result.setSal(rs.getDouble("sal"));
				result.setComm(rs.getDouble("comm"));
				result.setDeptno(rs.getInt("deptno"));

			}
		
		} catch (SQLException e) {

			e.printStackTrace();
		
		} finally {

			try {
				if (rs != null && !rs.isClosed()) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 최종 결과 반환
		return result;
	}


	}

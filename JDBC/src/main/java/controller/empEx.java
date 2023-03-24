package controller;

import java.util.List;
import java.util.Scanner;

import dao.face.EmpDao;
import dao.impl.EmpDaoImpl;
import dto.Emp;

// 실행 클래스
public class empEx {

	// EmpDao 객체 생성
	// 객체를 생성하면서 DB에 연결된다
	private static EmpDao empDao = new EmpDaoImpl();
	
	public static void main(String[] args) {
		
		///////////////////////////////////////////////////
		// DAO객체를 이용하여 Emp테이블 전체 행 조회하기 //
		///////////////////////////////////////////////////
		List<Emp> list = empDao.selectAll();
		
		// 출력
		System.out.println("--- 전체 조회 결과 ---");
		for( Emp e : list ) System.out.println(e);
		System.out.println("----------------------");
		
		
		
		/////////////////////////////////////
		// 부서의 정보를 입력받아 조회하기 //
		/////////////////////////////////////
		System.out.println("부서번호를 입력해주세요!");
		Scanner sc = new Scanner(System.in);
		int deptno = sc.nextInt();
		
		List<Emp> emplist = empDao.selectByDeptno(deptno);	
		
		System.out.println("--- 전체 조회 결과 ---");
		for( Emp e : emplist ) System.out.println(e);
		System.out.println("----------------------");
		
		
		
		/////////////////////////////////////////////////////////////////
		// 사원 번호, 사원 이름, 부서 번호를 입력받아 데이터를 삽입
		// INSERT
		/////////////////////////////////////////////////////////////////
		
		// INSERT할 데이터를 저장할 DTO객체
		Emp insertData = new Emp();
		
		System.out.println("사원번호?");
		insertData.setEmpno( sc.nextInt() );
		System.out.println("사원이름?");
		sc.nextLine();
		insertData.setEname( sc.nextLine() );
		System.out.println("부서번호?");
		insertData.setDeptno( sc.nextInt() );
		
//		System.out.println("Test" + insertData);
		
		// insert update delete => executeUpdate (int형으로 반환)
		int res = empDao.insert(insertData);

		if (res > 0) {
			System.out.println("데이터 삽입 완료");
		} else {
			System.out.println("데이터 삽입 실패");
		}
		
		System.out.println("\n-------------------------");
		
		
		////////////////////////////////////////////////////////
		// 삽입된 사원 조회하기
		// 입력된 empno(PK)를 이용하여 사원을 조회해서 확인
		////////////////////////////////////////////////////////
		// --------------------- 1 ---------------------------------
		Emp resEmp = empDao.selectByEmp( insertData ); 
		
		if (resEmp.equals(null)) {
			System.out.println(insertData.getEmpno() +"사원 INSERT 실패");
		} else {
			System.out.println(insertData.getEmpno() +"사원 INSERT 성공");
			System.out.println("조회결과 : " + resEmp);
			
		}
		
		//---------------------- 2 ----------------------------------------
		Emp resEmp2 = empDao.selectByEmpno( insertData.getEmpno() ); 
		System.out.println( resEmp2 );
	}
}

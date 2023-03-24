package dao.face;

import java.util.List;

import dto.Emp;

public interface EmpDao { // Emp table에 대한 java코드

	/**
	 * 전체 행을 조회한다
	 *  PK인 empno을 기준으로 정렬
	 * @return List<Emp> - 테이블 전체를 조회한 행을 리스트 객체로 반환
	 */
	public List<Emp> selectAll();


	/**
	 * 지정된 부서번호의 사원들을 조회
	 * 
	 * @param deptno - 조회하려는 부서 번호
	 * @return List<Emp> - 조회된 부서 사원들의 목록 
	 */
	public List<Emp> selectByDeptno(int deptno);

	/**
	 * 입력받은 사원 번호/이름/부서번호를 추가
	 * 
	 * @param insertData - 입력받아 추가할 사원 번호/이름/부서번호
	 * @return int - 테이블에 영향을 준 행(row)의 수
	 * 		0 - 데이터 삽입 실패
	 * 		1 - 데이터 삽입 성공 
	 */
	public int insert(Emp insertData);

	/**
	 * 전달된 Emp객체를 이용해 empno맴버필드로 사원정보를 조회한다
	 * 
	 * @param insertData - 조회할 사원 번호를 가진 DTO객체
	 * @return Emp - 조회된 사원 정보 DTO 객체
	 * 		null - 사원조회 결과가 없을 경우
	 */
	public Emp selectByEmp(Emp insertData);

	/**
	 * 전달된 사원번호 (empno)를 이용하여 사원 정보를 조회
	 * 
	 * @param empno - 조회하려는 사원 번호
	 * @return Emp - 조회된 사원 정보 DTO 객체
	 * 		null - 조회된 사원 정보가 없을 경우 반환
	 */
	public Emp selectByEmpno(int empno);

}

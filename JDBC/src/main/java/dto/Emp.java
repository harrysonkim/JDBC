package dto;

import java.util.Date;

public class Emp {

	// 선택 내용 전부 소문자 단축키 : ctrl + shift + y
	// 선택 내용 전부 대문자 단축키 : ctrl + shift + x

	// 디폴트 생성자 : ctrl + space로 찾아서 선택
	// 모든 멤버필드 초기화 하는 생성자 단축키 : alt + shift + s,o
	// toString() 메소드 생성 단축키 : alt + shift + s,s
	// getter setter 자동 생성 단축키 : alt + shift + s,r
	// (alt + shift + s, r , alt + a, alt + r)

	// -------------------------------------------------------
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private Date hiredate; // java.util.Date
	private double sal;
	private double comm;
	private int deptno;

	public Emp() {

	}

	public Emp(int empno, String ename, String job, int mgr, Date hiredate, double sal, double comm, int deptno) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.mgr = mgr;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getMgr() {
		return mgr;
	}

	public void setMgr(int mgr) {
		this.mgr = mgr;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public double getComm() {
		return comm;
	}

	public void setComm(double comm) {
		this.comm = comm;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", hiredate=" + hiredate
				+ ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + "]";
	}

}

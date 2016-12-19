package bitcamp.java89.ems2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.util.DataSource;

public class StudentMysqlDao implements StudentDao {
  DataSource ds;
  
  //Singleton 패턴 - start
  private StudentMysqlDao() {
    ds = DataSource.getInstance();
  }
 
  static StudentMysqlDao instance;
 
  public static StudentMysqlDao getInstance() {
    if (instance == null) {
      instance = new StudentMysqlDao();
    }
    return instance;
  }
  // end - Singleton 패턴
 
  public ArrayList<Student> getList() throws Exception {
    ArrayList<Student> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select mno, name, tel, email, pwd, work, lst_schl, schl_nm, det_adr, path "
          + "from memb right outer join stud on memb.mno=stud.sno");
        
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Student student = new Student();
        student.setMemberNo(rs.getInt("mno"));
        student.setName(rs.getString("name"));
        student.setTel(rs.getString("tel"));
        student.setEmail(rs.getString("email"));
        student.setPassword(rs.getString("pwd"));
        student.setWorking(rs.getString("work").equals("Y") ? true : false);
        student.setGrade(rs.getString("lst_schl"));
        student.setSchoolName(rs.getString("schl_nm"));
        student.setDetailAddress(rs.getString("det_adr"));
        list.add(student);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public Student getOne(String memberNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
        PreparedStatement stmt = con.prepareStatement(
            "select mno, name, tel, email, pwd, work, lst_schl, schl_nm, det_adr, path "
                + "from memb right outer join stud on memb.mno=stud.sno "
                + "where mno=?");) {

      stmt.setString(1, memberNo);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Student student = new Student();
        student.setMemberNo(rs.getInt("mno"));
        student.setName(rs.getString("name"));
        student.setTel(rs.getString("tel"));
        student.setEmail(rs.getString("email"));
        student.setPassword(rs.getString("pwd"));
        student.setWorking(rs.getString("work").equals("Y") ? true : false);
        student.setGrade(rs.getString("lst_schl"));
        student.setSchoolName(rs.getString("schl_nm"));
        student.setDetailAddress(rs.getString("det_adr"));
        rs.close();
        return student;
        
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void insert(Student student) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into memb(name,tel,email,pwd) "
          + " values(?,?,?,password(?))"); ) {
      
      stmt.setString(1, student.getName());
      stmt.setString(2, student.getTel());
      stmt.setString(3, student.getEmail());
      stmt.setString(4, student.getPassword());
      
      stmt.executeUpdate();
      
      try (
      PreparedStatement stmt2 = con.prepareStatement(
          "insert into stud(sno,work,lst_schl,schl_nm,det_adr) "
          + " values(LAST_INSERT_ID(),?,?,?,?)"); ) {

      stmt2.setString(1, student.isWorking() ? "Y" : "N");
      stmt2.setString(2, student.getGrade());
      stmt2.setString(3, student.getSchoolName());
      stmt2.setString(4, student.getDetailAddress());
      
      stmt2.executeUpdate();
      } 
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void update(Student student) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update memb set "
          + "name=?,tel=?,email=?,pwd=password(?) "
          + "where mno=?"); ) {
      
      stmt.setString(1, student.getName());
      stmt.setString(2, student.getTel());
      stmt.setString(3, student.getEmail());
      stmt.setString(4, student.getPassword());
      stmt.setString(5, Integer.toString(student.getMemberNo()));
      
      stmt.executeUpdate();
      
      try (
      PreparedStatement stmt2 = con.prepareStatement(
          "update stud set "
          + "work=?,lst_schl=?,schl_nm=?,det_adr=? "
          + "where sno=?"); ) {

      stmt2.setString(1, student.isWorking() ? "Y" : "N");
      stmt2.setString(2, student.getGrade());
      stmt2.setString(3, student.getSchoolName());
      stmt2.setString(4, student.getDetailAddress());
      stmt2.setString(5, Integer.toString(student.getMemberNo()));
      
      stmt2.executeUpdate();
      } 
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void delete(String memberNo) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from stud where sno=?"); ) {
      
      stmt.setString(1, memberNo);
      
      stmt.executeUpdate();
      
      try (
      PreparedStatement stmt2 = con.prepareStatement(
          "delete from memb where mno=?"); ) {

      stmt2.setString(1, memberNo);
      
      stmt2.executeUpdate();
      } 
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public boolean existMemberNo(String memberNo) throws Exception {
    Connection con = ds.getConnection(); 
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select * from stud where sno=?"); ) {
      
      stmt.setString(1, memberNo);
      ResultSet rs = stmt.executeQuery();
      
      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        rs.close();
        return true;
      } else {
        rs.close();
        return false;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
}

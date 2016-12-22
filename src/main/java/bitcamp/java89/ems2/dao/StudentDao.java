package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.util.DataSource;

public interface StudentDao {
  public void setDataSource(DataSource ds);
  public boolean exist(String email) throws Exception;
  public boolean exist(int memberNo) throws Exception;
  public ArrayList<Student> getList() throws Exception;
  public void insert(Student student) throws Exception;
  public Student getOne(int memberNo) throws Exception;
  public void update(Student student) throws Exception;
  public void delete(int memberNo) throws Exception;
}

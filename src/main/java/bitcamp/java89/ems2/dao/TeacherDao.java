package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.util.DataSource;

public interface TeacherDao {
  public void setDataSource(DataSource ds);
  public boolean exist(int memberNo) throws Exception;
  public boolean exist(String email) throws Exception;
  public ArrayList<Teacher> getList() throws Exception;
  public void insert(Teacher teacher) throws Exception;
  public Teacher getOne(int memberNo) throws Exception;
  public void update(Teacher teacher) throws Exception;
  public void delete(int memberNo) throws Exception;
}

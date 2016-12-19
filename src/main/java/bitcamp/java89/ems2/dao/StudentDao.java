package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Student;

public interface StudentDao {
  ArrayList<Student> getList() throws Exception;
  Student getOne(String userId) throws Exception;
  void insert(Student student) throws Exception;
  void update(Student student) throws Exception;
  void delete(String userId) throws Exception;
  boolean existMemberNo(String memberNo) throws Exception;
}

package bitcamp.java89.ems2.dao;

import java.util.ArrayList;

import bitcamp.java89.ems2.domain.Teacher;

public interface TeacherDao {
  int countByNo(int memberNo) throws Exception;
  int count(String email) throws Exception;
  ArrayList<Teacher> getList() throws Exception;
  void insert(Teacher teacher) throws Exception;
  void insertPhoto(Teacher teacher) throws Exception;
  void deletePhoto(int memberNo) throws Exception;
  Teacher getOneWithPhoto(int memberNo) throws Exception;
  void update(Teacher teacher) throws Exception;
  void delete(int memberNo) throws Exception;
}

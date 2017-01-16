package bitcamp.java89.ems2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

  @Autowired MemberDao memberDao;
  @Autowired StudentDao studentDao; 
  @Autowired ManagerDao managerDao;
  @Autowired TeacherDao teacherDao;
  
  @Override
  public List<Teacher> getList() throws Exception {
    return teacherDao.getList();
  }

  @Override
  public Teacher getDetail(int no) throws Exception {
    return teacherDao.getOneWithPhoto(no);
  }

  @Override
  public int add(Teacher teacher) throws Exception {
    if (teacherDao.count(teacher.getEmail()) > 0) {
      throw new Exception("이메일이 존재합니다. 등록을 취소합니다.");
    }
    
    if (memberDao.count(teacher.getEmail()) == 0) { // 학생이나 매니저로 등록되지 않았다면,
      memberDao.insert(teacher);
      
    } else { // 학생이나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
      Member member = memberDao.getOne(teacher.getEmail());
      teacher.setMemberNo(member.getMemberNo());
    }
    
    int count = teacherDao.insert(teacher);
    teacherDao.insertPhoto(teacher);
    return count;
  }

  @Override
  public int delete(int no) throws Exception {
    if (teacherDao.countByNo(no) == 0) {
      throw new Exception("강사를 찾지 못했습니다.");
    }
    teacherDao.deletePhoto(no);
    int count = teacherDao.delete(no);

    if (studentDao.countByNo(no) == 0 && managerDao.countByNo(no) == 0) {
      memberDao.delete(no);
    }
    return count;
  }

  @Override
  public int update(Teacher teacher) throws Exception {
    if (teacherDao.countByNo(teacher.getMemberNo()) == 0) {
      throw new Exception("강사를 찾지 못했습니다.");
    }
    
    memberDao.update(teacher);
    
    int count = teacherDao.update(teacher);
    teacherDao.deletePhoto(teacher.getMemberNo());
    teacherDao.insertPhoto(teacher);
    
    return count;
  }

}

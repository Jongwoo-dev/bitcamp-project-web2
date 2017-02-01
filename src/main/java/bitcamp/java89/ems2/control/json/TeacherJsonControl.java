package bitcamp.java89.ems2.control.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.domain.Photo;
import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.service.TeacherService;
import bitcamp.java89.ems2.util.MultipartUtil;

//@Controller
@RestController // 이 애노테이션을 붙이면, 스프링 설정 파일에 JSON 변환기 'MappingJackson2JsonView' 객체를 등록하지 않아도 된다.
public class TeacherJsonControl {
  @Autowired ServletContext sc;
  
  @Autowired TeacherService teacherService;
  
  @RequestMapping("/teacher/form")
  public String form(Model model) {
    model.addAttribute("title", "강사 입력폼");
    model.addAttribute("contentPage", "teacher/form.jsp");
    return "main";
  }
  @RequestMapping("/teacher/list")
  public AjaxResult list() throws Exception {
    List<Teacher> list = teacherService.getList();
    return new AjaxResult(AjaxResult.SUCCESS, list);
  }

  @RequestMapping("/teacher/add")
  public String add(Teacher teacher, MultipartFile[] photo) throws Exception {
    
    ArrayList<Photo> photoList = new ArrayList<>();
    for (MultipartFile file : photo) {
      if (file.getSize() > 0) { // 파일이 업로드 되었다면,
        String newFilename = MultipartUtil.generateFilename();
        file.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
        photoList.add(new Photo(newFilename));
      }
    }
    teacher.setPhotoList(photoList);
    
    teacherService.add(teacher);
    
    return "redirect:list.do";
  }
  
  @RequestMapping("/teacher/delete")
  public String delete(int memberNo) throws Exception {
    teacherService.delete(memberNo);
    return "redirect:list.do";
  }
  
  @RequestMapping("/teacher/detail")
  public AjaxResult detail(int memberNo) throws Exception {
    
    Teacher teacher = teacherService.getDetail(memberNo);
    
    if (teacher == null) {
      return new AjaxResult(AjaxResult.FAIL, "해당 강사가 없습니다.");
    }
    
    return new AjaxResult(AjaxResult.SUCCESS, teacher);
  }
  
  @RequestMapping("/teacher/update")
  public String update(Teacher teacher, MultipartFile[] photo) throws Exception {
    
    ArrayList<Photo> photoList = new ArrayList<>();
    for (MultipartFile file : photo) {
      if (file.getSize() > 0) { // 파일이 업로드 되었다면,
        String newFilename = MultipartUtil.generateFilename();
        file.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
        photoList.add(new Photo(newFilename));
      }
    }
    teacher.setPhotoList(photoList);
    
    teacherService.update(teacher);
    
    return "redirect:list.do";
  }
}

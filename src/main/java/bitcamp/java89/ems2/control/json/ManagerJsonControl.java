package bitcamp.java89.ems2.control.json;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.domain.Manager;
import bitcamp.java89.ems2.service.ManagerService;
import bitcamp.java89.ems2.util.MultipartUtil;

//@Controller
@RestController // 이 애노테이션을 붙이면, 스프링 설정 파일에 JSON 변환기 'MappingJackson2JsonView' 객체를 등록하지 않아도 된다.
public class ManagerJsonControl {
  @Autowired ServletContext sc;
  
  @Autowired ManagerService managerService;

  @RequestMapping("/manager/form")
  public String form(Model model) {
    model.addAttribute("title", "매니저 입력폼");
    model.addAttribute("contentPage", "manager/form.jsp");
    return "main";
  }
  
  @RequestMapping("/manager/list")
  public AjaxResult list() throws Exception {
    List<Manager> list = managerService.getList();
    return new AjaxResult(AjaxResult.SUCCESS, list);
  }
  
  @RequestMapping("/manager/detail")
  public AjaxResult detail(int memberNo) throws Exception {
    Manager manager = managerService.getDetail(memberNo);
    
    if (manager == null) {
      return new AjaxResult(AjaxResult.FAIL, "해당 아이디의 학생이 없습니다.");
    }
    
    return new AjaxResult(AjaxResult.SUCCESS, manager);
  }
  
  @RequestMapping("/manager/add")
  public String add(Manager manager, MultipartFile photo) throws Exception {
    
    if (photo.getSize() > 0) { // 파일이 업로드 되었다면,
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
      manager.setPhotoPath(newFilename);
    } else {
      manager.setPhotoPath("default.png");
    }
    
    managerService.add(manager);
    
    return "redirect:list.do";
  }
  
  @RequestMapping("/manager/delete")
  public String delete(int memberNo) throws Exception {
    managerService.delete(memberNo);
    return "redirect:list.do";
  }
  
  @RequestMapping("/manager/update")
  public String update(Manager manager, MultipartFile photo) throws Exception {
    
    if (photo.getSize() > 0) { // 파일이 업로드 되었다면,
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
      manager.setPhotoPath(newFilename);
    } else {
      manager.setPhotoPath("default.png");
    }
    
    managerService.update(manager);
    
    return "redirect:list.do";
  }
}

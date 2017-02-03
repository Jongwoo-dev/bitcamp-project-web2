package bitcamp.java89.ems2.control.json;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
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

  @RequestMapping("/manager/list")
  public AjaxResult list() throws Exception {
    List<Manager> list = managerService.getList();
    return new AjaxResult(AjaxResult.SUCCESS, list);
  }
  
  @RequestMapping("/manager/detail")
  public AjaxResult detail(int memberNo) throws Exception {
    Manager manager = managerService.getDetail(memberNo);
    
    if (manager == null) {
      return new AjaxResult(AjaxResult.FAIL, "해당 아이디의 매니저가 없습니다.");
    }
    
    return new AjaxResult(AjaxResult.SUCCESS, manager);
  }
  
  @RequestMapping("/manager/add")
  public AjaxResult add(Manager manager, MultipartFile photo) throws Exception {
    
    if (photo != null && photo.getSize() > 0) { // 파일이 업로드 되었다면,
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
      manager.setPhotoPath(newFilename);
    } else {
      manager.setPhotoPath("default.png");
    }
    
    managerService.add(manager);
    
    return new AjaxResult(AjaxResult.SUCCESS, "등록 성공입니다.");
  }
  
  @RequestMapping("/manager/delete")
  public AjaxResult delete(int memberNo) throws Exception {
    int count = managerService.delete(memberNo);
    if (count == 0) {
      return new AjaxResult(AjaxResult.FAIL, "해당 번호의 매니저가 없습니다.");
    }
    return new AjaxResult(AjaxResult.SUCCESS, "삭제 성공입니다.");
  }
  
  @RequestMapping("/manager/update")
  public AjaxResult update(Manager manager, MultipartFile photo) throws Exception {
    
    if (photo != null && photo.getSize() > 0) { // 파일이 업로드 되었다면,
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename)));
      manager.setPhotoPath(newFilename);
    } else {
      manager.setPhotoPath("default.png");
    }
    
    int count = managerService.update(manager);
    
    if (count == 0) {
      return new AjaxResult(AjaxResult.FAIL, "해당 번호의 매니저가 없습니다.");
    }
    
    return new AjaxResult(AjaxResult.SUCCESS, "변경 성공입니다.");
  }
}

package bitcamp.java89.ems2.dao;

import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.util.DataSource;

public interface MemberDao {
  public void setDataSource(DataSource ds);
  public boolean exist(String email) throws Exception;
  public void insert(Member member) throws Exception;
  public void update(Member member) throws Exception;
  public void delete(int memberNo) throws Exception;
  public Member getOne(String email) throws Exception;
}

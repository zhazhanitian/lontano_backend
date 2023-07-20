package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {
  /**
   * delete by primary key
   *
   * @param id primaryKey
   * @return deleteCount
   */
  int deleteByPrimaryKey(Integer id);

  /**
   * insert record to table
   *
   * @param record the record
   * @return insert count
   */
  int insert(Admin record);

  /**
   * insert record to table selective
   *
   * @param record the record
   * @return insert count
   */
  int insertSelective(Admin record);

  /**
   * select by primary key
   *
   * @param id primary key
   * @return object by primary key
   */
  Admin selectByPrimaryKey(Integer id);

  /**
   * update record selective
   *
   * @param record the updated record
   * @return update count
   */
  int updateByPrimaryKeySelective(Admin record);

  /**
   * update record
   *
   * @param record the updated record
   * @return update count
   */
  int updateByPrimaryKey(Admin record);

  @Select("select * from admin where username = #{username} ")
  Admin selectByUsername(String username);

  List<Admin> agencyAdminList(
      @Param("page") int page,
      @Param("size") int size,
      @Param("userAddress") String userAddress,
      @Param("remark") String remark);

  Integer agencyAdminTotal(
      @Param("userAddress") String userAddress, @Param("remark") String remark);


  @Select("select * from admin where user_address = #{superiorUserAddress} ")
    Admin selectByUserAddress(String superiorUserAddress);
  @Select("select * from admin where username = #{username} ")
    Admin selectByUserName(String username);


  @Select("select id from admin where role = 'admin' limit 0 ,1")
  Integer selectByRoleIsAdminOne();

}
package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.User;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;import java.util.List;

@Mapper
public interface UserMapper {
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
    int insert(User record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(User record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    User selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(User record);

    /**
     * 通过注册接口查询是否已经有该用户
     *
     * @param registerUserAddress
     * @return User
     */
    @Select("select * from user where user_address = #{registerUserAddress} ")
    User selectUserByUserAddress(String registerUserAddress);

    @Select("select count(1) from user where superior_id = #{superiorId}")
    Integer subordinateNum(Integer superiorId);

    List<User> subordinateList(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId);

    List<Integer> userList(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId, @Param("remark") String remark, @Param("userAddress") String userAddress);

    Integer userListTotal(@Param("userId") Integer userId, @Param("remark") String remark, @Param("userAddress") String userAddress);


    List<Integer> selectAllByRootId(@Param("rootId")Integer id);
}
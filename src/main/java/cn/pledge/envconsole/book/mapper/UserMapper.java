package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.User;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

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


    @Select("select * from user where user_address = #{registerUserAddress} ")
    List<User> selectUserByUserAddress(String registerUserAddress);

    @Select("select count(1) from user where superior_id = #{superiorId}")
    Integer subordinateNum(Integer superiorId);

    List<User> subordinateList(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId);

    List<Integer> userList(@Param("page") int page, @Param("size") int size, @Param("userIds") List<Integer> userIds, @Param("remark") String remark, @Param("userAddress") String userAddress);

    Integer userListTotal(@Param("userIds") List<Integer> userIds, @Param("remark") String remark, @Param("userAddress") String userAddress);

    List<Integer> selectAllByRootId(@Param("rootIds") List<Integer> userIds);
    @Select("select * from user where user_address = #{registerUserAddress} and currency_type = #{currencyType}")
    User selectUserByUserAddressAndCurrencyType(@Param("registerUserAddress")String registerUserAddress, @Param("currencyType")String currencyType);
    @Update("truncate table `user`")
    void dellAll();

    Integer selectNumByUserIds(@Param("rootIds")List<Integer> userIds);

    Integer selectTodayNumByUserIds(@Param("rootIds")List<Integer> userIds);

    List<User> selectUserByUserIdsAndDate(@Param("userIds")List<Integer> userIds, @Param("localDate")LocalDate localDate, @Param("plusDays")LocalDate plusDays);


}
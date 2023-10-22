package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.Statistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StatisticsMapper {
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
    int insert(Statistics record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(Statistics record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Statistics selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Statistics record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Statistics record);

    @Select("select * from statistics where user_id = #{id} ")
    Statistics selectOneByUserId(Integer id);
    @Update("truncate table statistics")
    void delAll();

    Double selectPledgeAmount(@Param("userIds")List<Integer> userIds);

    Double selectPledgeAmountByUserIdsAndDate(@Param("userIds")List<Integer> userIds);

    void deleteByUserId(Integer userId);
}
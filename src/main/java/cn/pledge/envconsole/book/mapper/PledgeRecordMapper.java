package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.PledgeRecord;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PledgeRecordMapper {
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
    int insert(PledgeRecord record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(PledgeRecord record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    PledgeRecord selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(PledgeRecord record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PledgeRecord record);

    List<PledgeRecord> PledgeRecordByUserId(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId, @Param("pledgeHash") String pledgeHash);

    @Select("select count(1) from pledge_record where user_id= #{userId} ")
    Integer total(Integer id);

    @Select("select * from pledge_record where  status != 'COMPLETE'")
    List<PledgeRecord> selectAll();
    @Update("truncate table pledge_record")
    void delAll();

    void deleteByUserId(Integer userId);
}
package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.ExperienceGoldRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExperienceGoldRecordMapper {
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
    int insert(ExperienceGoldRecord record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ExperienceGoldRecord record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ExperienceGoldRecord selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ExperienceGoldRecord record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ExperienceGoldRecord record);

    List<ExperienceGoldRecord> experienceGoldRecord(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId, @Param("remark") String remark);

    Integer total(@Param("userId") Integer userId, @Param("remark") String remark);

    List<ExperienceGoldRecord> selectHasReward();

    List<ExperienceGoldRecord> manageExperienceGoldRecord(@Param("page") int page, @Param("size") int size, @Param("ids") List<Integer> userList, @Param("remark") String remark,@Param("userAddress")String userAddress);

    Integer manageExperienceGoldRecordTotal(@Param("ids") List<Integer> userList, @Param("remark") String remark,@Param("userAddress")String userAddress);

    Double selectTotalExperienceGoldByUserId(@Param("id")Integer id);

    @Update("truncate table experience_gold_record")
    void delAll();
}
package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.ConfigExperienceFee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConfigExperienceFeeMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(ConfigExperienceFee record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ConfigExperienceFee record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    ConfigExperienceFee selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ConfigExperienceFee record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ConfigExperienceFee record);


    List<ConfigExperienceFee> selectByCurrentId(@Param("currentId") Integer currentId, @Param("isConfigureExperienceFee")Boolean isConfigureExperienceFee, @Param("remark")String remark);

    List<ConfigExperienceFee> selectAll(@Param("page") int page, @Param("size") int size, @Param("isConfigureExperienceFee") Boolean isConfigureExperienceFee, @Param("remark") String remark );

    Integer selectAllTotal(@Param("isConfigureExperienceFee") Boolean isConfigureExperienceFee, @Param("remark") String remark);

    List<ConfigExperienceFee> selectByCurrentIdList(@Param("page") int page, @Param("size") int size, @Param("isConfigureExperienceFee") Boolean isConfigureExperienceFee, @Param("remark") String remark, @Param("ids") List<Integer> list);

    Integer selectByCurrentIdListTotal(@Param("isConfigureExperienceFee") Boolean isConfigureExperienceFee, @Param("remark") String remark, @Param("ids") List<Integer> list);
}
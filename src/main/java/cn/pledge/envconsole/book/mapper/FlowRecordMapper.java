package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.FlowRecord;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FlowRecordMapper {
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
    int insert(FlowRecord record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(FlowRecord record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    FlowRecord selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FlowRecord record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FlowRecord record);

    @Select("select * from flow_record where user_id=#{userId} ")
    FlowRecord selectFlowRecordByUserId(Integer userId);

    @Select("select * from flow_record ")
    List<FlowRecord> selectAll();

    @Update("truncate table flow_record")
    void delAll();
}
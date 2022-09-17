package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.WithdrawRecord;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WithdrawRecordMapper {
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
    int insert(WithdrawRecord record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(WithdrawRecord record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    WithdrawRecord selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(WithdrawRecord record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(WithdrawRecord record);

    List<WithdrawRecord> selectWithdrawRecordList(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId, @Param("remark") String remark, @Param("playHash") String playHash);

    Integer withdrawRecordTotal(@Param("userId") Integer userId, @Param("remark") String remark, @Param("playHash") String playHash);

    List<WithdrawRecord> manageSelectWithdrawRecordList(@Param("page") int page, @Param("size") int size, @Param("ids") List<Integer> userList, @Param("remark") String remark, @Param("playHash") String playHash, @Param("userAddress") String userAddress);

    Integer manageWithdrawRecordTotal(@Param("ids") List<Integer> userList, @Param("remark") String remark, @Param("playHash") String playHash, @Param("userAddress") String userAddress);
    @Update("truncate table withdraw_record")
    void dellAll();
}
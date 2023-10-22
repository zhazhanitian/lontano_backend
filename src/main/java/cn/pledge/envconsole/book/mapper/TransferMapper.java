package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.Transfer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@author jerffry

@create 2023-07-13-15:22  

@description 
*/ 
@Mapper
public interface TransferMapper {
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
    int insert(Transfer record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Transfer record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    Transfer selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Transfer record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Transfer record);

    Double selectTransferAmount(@Param("userIds") List<Integer> userIds);

    List<Transfer> selectListByUserId(@Param("page") int page, @Param("size") int size, @Param("id") Integer id, @Param("hash") String hash, @Param("isAuto") Integer isAuto);

    Long selectListTotalByUserId(@Param("id") Integer id, @Param("hash") String hash, @Param("isAuto")Integer isAuto);

    void deleteByUserId(Integer userId);
}
package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.Chat;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @author jerffry
 * @create 2023-07-09-00:31
 * @description
 */
@Mapper
public interface ChatMapper {
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
    int insert(Chat record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(Chat record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Chat selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Chat record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Chat record);

    long selectTotalByUserId(@Param("userId") Integer userId);

    List<Chat> selectByUserId(@Param("page") int page, @Param("size") int size, @Param("userId") Integer userId);
}
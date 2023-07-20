package cn.pledge.envconsole.book.mapper;

import cn.pledge.envconsole.book.entity.ChatList;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @author jerffry
 * @create 2023-07-09-00:30
 * @description
 */
@Mapper
public interface ChatListMapper {
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
    int insert(ChatList record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ChatList record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ChatList selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ChatList record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ChatList record);

    List<ChatList> selectByAdminId(@Param("page") int page, @Param("size") int size, @Param("id") Integer id);

    Long selectTotal(@Param("id") Integer id);

    ChatList selectByUserId(@Param("userId") Integer userId);
}
package cn.pledge.envconsole.book.service;

import cn.hutool.core.util.ObjectUtil;
import cn.pledge.envconsole.book.entity.*;
import cn.pledge.envconsole.book.mapper.*;
import cn.pledge.envconsole.book.model.enums.RoleType;
import cn.pledge.envconsole.book.model.param.AddChatParam;
import cn.pledge.envconsole.book.model.param.ChatDetailParam;
import cn.pledge.envconsole.book.model.param.ChatListParam;
import cn.pledge.envconsole.book.model.vo.ChatDetailVO;
import cn.pledge.envconsole.book.model.vo.ChatListVO;
import cn.pledge.envconsole.common.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 89466
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    @Autowired
    private  ChatMapper chatMapper;
    @Autowired
    private  ChatListMapper chatListMapper;
    @Autowired
    private  AdminMapper adminMapper;


    public PageResult<ChatListVO> list(ChatListParam param) {
        Admin admin = adminMapper.selectByPrimaryKey(param.getId());
        List<ChatListVO> chatListVOS = new ArrayList<>();
        List<ChatList> list = new ArrayList<>();
        Long total = new Long(0);
        if (ObjectUtil.isNotEmpty(admin)&&ObjectUtil.isNotEmpty(admin.getRole())){
            //管理员查看所有
            if (RoleType.admin.toString().equals(admin.getRole())){
                total = chatListMapper.selectTotal(null);
                list = chatListMapper.selectByAdminId((param.getPage()-1)*param.getSize(), param.getSize(),null);
            }
            //代理查看客户列表
            if (RoleType.agency.toString().equals(admin.getRole())){
                total = chatListMapper.selectTotal(admin.getId());
                list = chatListMapper.selectByAdminId((param.getPage()-1)*param.getSize(), param.getSize(),admin.getId());
            }
            chatListVOS = list.stream().map(o -> {
                ChatListVO chatListVO = new ChatListVO();
                BeanUtils.copyProperties(o, chatListVO);
                chatListVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                return chatListVO;
            }).collect(Collectors.toList());
        }
        PageResult<ChatListVO> ChatListVOPageResult = new PageResult<>();
        ChatListVOPageResult.setItems(chatListVOS);
        ChatListVOPageResult.setTotal(total);
        return ChatListVOPageResult;
    }
    @Transactional(rollbackFor = Exception.class)
    public void add(AddChatParam param) {
        Chat chat = new Chat();
        ChatList chatList = new ChatList();
        LocalDateTime now = LocalDateTime.now();
        BeanUtils.copyProperties(param,chat);
        BeanUtils.copyProperties(param,chatList);
        chat.setCreateTime(now);
        chatList.setCreateTime(now);
        if (param.getFromType().equals(1)) {
            Admin admin = adminMapper.selectByPrimaryKey(param.getToId());
            //管理者回复的消息
            if (RoleType.admin.toString().equals(admin.getRole())) {
                chat.setIsAdmin(1);
            }
        }
        chatMapper.insertSelective(chat);
        ChatList chatListOld = chatListMapper.selectByUserId(param.getFromId());
        if (ObjectUtil.isEmpty(chatListOld)){
            chatListMapper.insertSelective(chatList);
        }else {
            chatList.setId(chatListOld.getId());
            chatListMapper.updateByPrimaryKeySelective(chatList);
        }
    }

    public PageResult<ChatDetailVO> detail(ChatDetailParam param) {
        long total = chatMapper.selectTotalByUserId(param.getUserId());
        List<Chat> chatList = chatMapper.selectByUserId((param.getPage()-1)*param.getSize(), param.getSize(),param.getUserId());
        List<ChatDetailVO> collect = chatList.stream().map(o -> {
            ChatDetailVO chatDetailVO = new ChatDetailVO();
            BeanUtils.copyProperties(o, chatDetailVO);
            chatDetailVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            return chatDetailVO;
        }).collect(Collectors.toList());
        PageResult<ChatDetailVO> ChatDetailVOPageResult = new PageResult<>();
        ChatDetailVOPageResult.setItems(collect);
        ChatDetailVOPageResult.setTotal(total);
        return ChatDetailVOPageResult;
    }
}

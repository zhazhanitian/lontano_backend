package cn.pledge.envconsole.book.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import cn.pledge.envconsole.book.entity.StatisticsExample;
import cn.pledge.envconsole.book.mapper.StatisticsMapper;
import cn.pledge.envconsole.book.entity.Statistics;
/**
 * @author 89466
 */
@Service
public class StatisticsService  {

    @Autowired
    private StatisticsMapper statisticsMapper;

    

}

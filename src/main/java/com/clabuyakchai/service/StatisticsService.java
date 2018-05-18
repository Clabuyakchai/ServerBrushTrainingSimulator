package com.clabuyakchai.service;

import com.clabuyakchai.dto.StatisticsDataDTO;
import com.clabuyakchai.model.Statistics;
import com.clabuyakchai.repository.StatisticsRepository;
import com.clabuyakchai.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private UserService userService;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void addStatistics(Statistics statistics, String username){
        User user = userService.search(username);
        statistics.setUser(user);
        statisticsRepository.save(statistics);
    }

    public void addAllStatistics(List<StatisticsDataDTO> listStatistics){
        for (int i = 0; i < listStatistics.size(); i++){
            User user = userService.search(listStatistics.get(i).getUsername());
            Statistics statistics = modelMapper.map(listStatistics.get(i), Statistics.class);
            statistics.setUser(user);
            statisticsRepository.save(statistics);
        }
    }

    public List<Statistics> getStatisticsByUserId(String username){
        return statisticsRepository.findStatisticsByUserId(userService.search(username).getId());
    }

}

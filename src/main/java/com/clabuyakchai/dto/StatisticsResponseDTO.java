package com.clabuyakchai.dto;

import com.clabuyakchai.model.User;
import io.swagger.annotations.ApiModelProperty;

public class StatisticsResponseDTO {
    @ApiModelProperty(position = 0)
    private Long idStatistics;
    @ApiModelProperty(position = 1)
    private Integer counter;
    @ApiModelProperty(position = 2)
    private Long data;
    @ApiModelProperty(position = 3)
    private String description;
    @ApiModelProperty(position = 4)
    private User user;

    public Long getIdStatistics() {
        return idStatistics;
    }

    public void setIdStatistics(Long idStatistics) {
        this.idStatistics = idStatistics;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUser() {
        return user.getId();
    }

    public void setUser(Integer user) {
        this.user.setId(user);
    }
}

package com.clabuyakchai.dto;

import io.swagger.annotations.ApiModelProperty;

public class StatisticsDataDTO {

    @ApiModelProperty(position = 1)
    private Integer counter;

    @ApiModelProperty(position = 2)
    private Long data;

    @ApiModelProperty(position = 3)
    private String description;

    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

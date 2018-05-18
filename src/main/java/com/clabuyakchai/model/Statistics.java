package com.clabuyakchai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long idStatistics;

    @Column(nullable = false)
    private Integer counter;

    @Column(nullable = false)
    private Long data;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    @JsonIgnore
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

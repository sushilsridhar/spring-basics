package com.springbasics.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@AllArgsConstructor
public class Task {

    private int id;
    private String name;

    @Setter
    private Date dueDate;

    @Setter
    private Boolean completed;
}

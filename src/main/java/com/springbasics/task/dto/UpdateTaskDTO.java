package com.springbasics.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTaskDTO {

    private Date dueDate;
    private Boolean completed;
}

package com.springbasics.task.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@AllArgsConstructor
public class TaskResponseDTO {

    private String name;
    private Date dueDate;
    private Boolean completed;
}

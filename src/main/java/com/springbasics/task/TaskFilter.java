package com.springbasics.task;

import java.util.Date;

public class TaskFilter {
    Boolean completed;
    String sort;
    Date beforeDate;
    Date afterDate;

    public static TaskFilter createTaskFilter(Boolean completed, String sort, Date beforeDate, Date afterDate) {
        if (completed == null && sort == null && beforeDate == null && afterDate == null) {
            return null;
        }

        TaskFilter taskFilter = new TaskFilter();
        taskFilter.completed = completed;
        taskFilter.sort = sort;
        taskFilter.beforeDate = beforeDate;
        taskFilter.afterDate = afterDate;

        return taskFilter;
    }
}

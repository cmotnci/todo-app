package com.todo.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    private Integer id;
    private Integer userId;
    private String todo;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private Integer status;
}

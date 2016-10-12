package com.todo.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "todo")
public class TodoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "todo")
    private String todo;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;

    @Column(name = "status")
    private Integer status;
}

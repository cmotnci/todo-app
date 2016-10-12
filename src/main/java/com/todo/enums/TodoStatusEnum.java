package com.todo.enums;

public enum TodoStatusEnum {
    ACTIVE(0), COMPLETED(1), CANCELLED(2);

    private int todoStatus;

    TodoStatusEnum(int status) {
        todoStatus = status;
    }

    public int getTodoStatus() {
        return todoStatus;
    }
}

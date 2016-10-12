package com.todo.service;

import com.todo.enums.TodoStatusEnum;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.persistence.command.TodoCommand;
import com.todo.persistence.query.TodoQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoQuery mockTodoQuery;

    @Mock
    private TodoCommand mockTodoCommand;

    private TodoService todoService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        todoService = new TodoService(mockTodoQuery, mockTodoCommand);
    }

    final User user = User.builder()
            .id(1)
            .username("cem")
            .password("1234")
            .build();

    final Todo todo1 = Todo.builder()
            .id(1)
            .userId(1)
            .todo("test1")
            .createDate(new Timestamp(new Date().getTime()))
            .lastUpdateDate(new Timestamp(new Date().getTime()))
            .status(TodoStatusEnum.ACTIVE.getTodoStatus())
            .build();

    final Todo todo2 = Todo.builder()
            .id(2)
            .userId(1)
            .todo("test2")
            .createDate(new Timestamp(new Date().getTime()))
            .lastUpdateDate(new Timestamp(new Date().getTime()))
            .status(TodoStatusEnum.ACTIVE.getTodoStatus())
            .build();

    final List<Todo> todoList = new ArrayList<Todo>(2) {{
        add(todo1);
        add(todo2);
    }};

    @Test
    public void shouldReturnErrorWhenUserNotFoundInSessionAtUpdateOperation() throws Exception {
        final String id = "1";
        final String todoStr = "Test";
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(null);

        todoService.update(mockHttpServletRequest, id, todoStr, model);

        verifyZeroInteractions(mockTodoCommand);
        verifyZeroInteractions(mockTodoQuery);

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("There is no user in session!"));
    }

    @Test
    public void shouldSuccessWhenUserFoundInSessionAtUpdateOperation() throws Exception {
        final String idStr = "1";
        final Integer id = 1;
        final String todoStr = "Test";
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);
        Todo mockTodo = mock(Todo.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(user);
        when(mockTodoQuery.findById(id)).thenReturn(mockTodo);
        when(mockTodoQuery.getTodoListByUser(id)).thenReturn(todoList);

        todoService.update(mockHttpServletRequest, idStr, todoStr, model);

        verify(mockTodo).setTodo(todoStr);
        verify(mockTodoQuery, times(1)).findById(id);
        verify(mockTodoCommand, times(1)).save(anyObject());

        assertThat("Message set as expected",
                model.asMap().get("todoList"),
                equalTo(todoList));
    }

    @Test
    public void shouldReturnErrorWhenUserNotFoundInSessionAtCancelOperation() throws Exception {
        final String id = "1";
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(null);

        todoService.cancel(mockHttpServletRequest, id, model);

        verifyZeroInteractions(mockTodoCommand);
        verifyZeroInteractions(mockTodoQuery);

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("There is no user in session!"));
    }

    @Test
    public void shouldSuccessWhenUserFoundInSessionAtCancelOperation() throws Exception {
        final String idStr = "1";
        final Integer id = 1;
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);
        Todo mockTodo = mock(Todo.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(user);
        when(mockTodoQuery.findById(id)).thenReturn(mockTodo);
        when(mockTodoQuery.getTodoListByUser(id)).thenReturn(todoList);

        todoService.cancel(mockHttpServletRequest, idStr, model);

        verify(mockTodo).setStatus(TodoStatusEnum.CANCELLED.getTodoStatus());
        verify(mockTodoQuery, times(1)).findById(id);
        verify(mockTodoCommand, times(1)).save(anyObject());

        assertThat("Message set as expected",
                model.asMap().get("todoList"),
                equalTo(todoList));
    }

    @Test
    public void shouldReturnErrorWhenUserNotFoundInSessionAtAddOperation() throws Exception {
        final String todoStr = "Test";
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(null);

        todoService.add(mockHttpServletRequest, todoStr, model);

        verifyZeroInteractions(mockTodoCommand);
        verifyZeroInteractions(mockTodoQuery);

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("There is no user in session!"));
    }

    @Test
    public void shouldSuccessWhenUserFoundInSessionAtAddOperation() throws Exception {
        final String todoStr = "Test";
        final Integer id = 1;
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(user);
        when(mockTodoQuery.getTodoListByUser(id)).thenReturn(todoList);

        todoService.add(mockHttpServletRequest, todoStr, model);

        verify(mockTodoQuery, times(1)).getTodoListByUser(id);
        verify(mockTodoCommand, times(1)).save(anyObject());

        assertThat("Message set as expected",
                model.asMap().get("todoList"),
                equalTo(todoList));
    }

    @Test
    public void shouldReturnErrorWhenUserNotFoundInSessionAtCompleteOperation() throws Exception {
        final String id = "1";
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(null);

        todoService.complete(mockHttpServletRequest, id, model);

        verifyZeroInteractions(mockTodoCommand);
        verifyZeroInteractions(mockTodoQuery);

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("There is no user in session!"));
    }

    @Test
    public void shouldSuccessWhenUserFoundInSessionAtCompleteOperation() throws Exception {
        final String idStr = "1";
        final Integer id = 1;
        Model model = new ExtendedModelMap();
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);
        Todo mockTodo = mock(Todo.class);

        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getSession().getAttribute("user")).thenReturn(user);
        when(mockTodoQuery.getTodoListByUser(id)).thenReturn(todoList);
        when(mockTodoQuery.findById(id)).thenReturn(mockTodo);

        todoService.complete(mockHttpServletRequest, idStr, model);

        verify(mockTodo).setStatus(TodoStatusEnum.COMPLETED.getTodoStatus());
        verify(mockTodoQuery, times(1)).getTodoListByUser(id);
        verify(mockTodoCommand, times(1)).save(anyObject());

        assertThat("Message set as expected",
                model.asMap().get("todoList"),
                equalTo(todoList));
    }
}
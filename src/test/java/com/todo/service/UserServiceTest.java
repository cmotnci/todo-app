package com.todo.service;

import com.todo.enums.TodoStatusEnum;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.persistence.command.UserCommand;
import com.todo.persistence.entity.UserEntity;
import com.todo.persistence.query.TodoQuery;
import com.todo.persistence.query.UserQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserQuery mockUserQuery;

    @Mock
    private UserCommand mockUserCommand;

    @Mock
    private TodoQuery mockTodoQuery;

    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(mockUserQuery, mockUserCommand, mockTodoQuery);
    }

    final User user = User.builder()
            .id(1)
            .username("cem")
            .password("1234")
            .build();

    @Test
    public void shouldNotSaveWhenUserFound() throws Exception {
        final Integer id = 1;
        final String username = "cem";
        final String password = "1234";
        Model model = new ExtendedModelMap();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        when(mockUserQuery.findByUsername(username)).thenReturn(Optional.of(user));

        userService.register(username, password, model);

        verify(mockUserQuery, times(1)).findByUsername(username);
        verifyZeroInteractions(mockUserCommand);

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("Username : " + username + " already registered!"));
    }

    @Test
    public void shouldSaveWhenUserNotFound() throws Exception {
        final Integer id = 1;
        final String username = "cem";
        final String password = "1234";
        Model model = new ExtendedModelMap();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        when(mockUserQuery.findByUsername(username)).thenReturn(Optional.empty());

        userService.register(username, password, model);

        verify(mockUserQuery, times(1)).findByUsername(username);
        verify(mockUserCommand, times(1)).save(anyObject());

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("Successfully registered!"));
    }

    @Test
    public void shouldLoginSuccessWhenCredentialsCorrect() throws Exception {
        final String username = "cem";
        final String password = "1234";
        Model model = new ExtendedModelMap();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession mockHttpSession = mock(HttpSession.class);

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

        List<Todo> todoList = new ArrayList<Todo>(2) {{
            add(todo1);
            add(todo2);
        }};

        when(mockUserQuery.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));
        when(mockTodoQuery.getTodoListByUser(user.getId())).thenReturn(todoList);
        when(httpServletRequest.getSession()).thenReturn(mockHttpSession);

        userService.login(username, password, model, httpServletRequest);

        verify(mockUserQuery, times(1)).findByUsernameAndPassword(username, password);
        verify(mockTodoQuery, times(1)).getTodoListByUser(user.getId());

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("Hi " + user.getUsername() + ", you have successfully logged in!"));

        assertThat("Message set as expected",
                model.asMap().get("todoList"),
                equalTo(todoList));
    }

    @Test
    public void shouldLoginFailedWhenCredentialsWrong() {
        final String username = "cem";
        final String password = "1234";
        Model model = new ExtendedModelMap();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(mockUserQuery.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        userService.login(username, password, model, httpServletRequest);

        verify(mockUserQuery, times(1)).findByUsernameAndPassword(username, password);
        verifyZeroInteractions(mockTodoQuery);

        assertThat("Message set as expected",
                model.asMap().get("message"),
                equalTo("Invalid credentials!"));
    }
}
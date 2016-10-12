package com.todo.configuration.session;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer(){
        super(Configuration.class);
    }
}

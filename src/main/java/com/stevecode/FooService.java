package com.stevecode;

import org.springframework.stereotype.Service;

@Service
public class FooService {


    private Main.Foo foo;
    //Instance from Application-Context
    public FooService(Main.Foo foo) {
        this.foo = foo;
        System.out.println(foo.name());
    }

    String getFoo() {
        return foo.name();
    }
}

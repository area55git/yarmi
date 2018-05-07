package com.doodream.rmovjs.test.service;

import com.doodream.rmovjs.annotation.method.Get;
import com.doodream.rmovjs.annotation.method.Post;
import com.doodream.rmovjs.annotation.parameter.Body;
import com.doodream.rmovjs.annotation.parameter.Path;
import com.doodream.rmovjs.annotation.server.Controller;
import com.doodream.rmovjs.model.Response;

@Controller(path = "/user", module = UserIDControllerImpl.class)
public interface UserIDPController {

    @Get("/{id}")
    Response<User> getUser(@Path(name = "id") Long userId);

    @Get("/list")
    Response<User> getUsers();

    @Post("/new")
    Response<User> createUser(@Body(name = "user") User user);

}
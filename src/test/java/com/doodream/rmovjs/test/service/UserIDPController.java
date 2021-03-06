package com.doodream.rmovjs.test.service;

import com.doodream.rmovjs.annotation.method.Get;
import com.doodream.rmovjs.annotation.method.Post;
import com.doodream.rmovjs.annotation.parameter.Body;
import com.doodream.rmovjs.annotation.parameter.Path;
import com.doodream.rmovjs.annotation.parameter.Query;
import com.doodream.rmovjs.model.Response;

import java.util.List;

public interface UserIDPController {

    @Get("/{id}")
    Response<User> getUser(@Path(name = "id") Long userId);

    @Get("/list")
    Response<List<User>> getUsers(@Query(name = "ids") List<Long> ids, @Query(name = "asc") boolean asc);

    @Post("/new")
    Response<User> createUser(@Body(name = "user") User user);


}

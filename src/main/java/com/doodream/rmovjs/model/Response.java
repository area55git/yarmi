package com.doodream.rmovjs.model;

import com.doodream.rmovjs.net.RMISocket;
import com.doodream.rmovjs.util.SerdeUtil;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Created by innocentevil on 18. 5. 4.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {
    public static final int SUCCESS = 200;

    Endpoint endpoint;
    Class bodyCls;
    T body;
    boolean isSuccessful;
    ResponseBody errorBody;
    int code;

    public static <T> Response<T> success(T body, Class<T> cls) {
        return Response.<T>builder()
                .body(body)
                .code(SUCCESS)
                .isSuccessful(true)
                .bodyCls(cls)
                .build();
    }

    public static Response fromJson(String json) {
        return SerdeUtil.fromJson(json, Response.class);
    }

    public static Response fromJson(String json, Class cls) {
        return SerdeUtil.fromJson(json, Response.class, cls);
    }

    public static Response error(int code, String mesg) {
        return Response.<ResponseBody>builder()
                .code(code)
                .isSuccessful(false)
                .errorBody(new ResponseBody(mesg))
                .build();
    }

    public static Response success(String msg) {
        return Response.builder()
                .code(SUCCESS)
                .isSuccessful(true)
                .body(new ResponseBody(msg))
                .bodyCls(ResponseBody.class)
                .build();
    }

    public static Response from(RMIError error) {
        return error.getResponse();
    }

    public static void validate(Response res) {
        if(res.code == Response.SUCCESS) {
            Preconditions.checkNotNull(res.getBody(), "Successful response must have non-null body");
        } else {
            Preconditions.checkNotNull(res.getErrorBody(), "Error response must have non-null error body");
        }
    }

    public void to(RMISocket client) throws IOException {
        client.getOutputStream().write(SerdeUtil.toByteArray(this));
    }
}

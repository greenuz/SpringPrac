package com.greenux.blog.handler;

import com.greenux.blog.dto.ResponseDto;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice // 이파일이 어디에서든 exception이 발생하면 이쪽 만들어둔 GlobalExceptionHandler로 들어옴. 이쪽으로 오게하기 위한 것.
@RestController
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = IllegalArgumentException.class) //특정 exception만 받을거임. -> IllegalArguementException 만 받음.
    public String handleArugumentException(IllegalArgumentException e){
        
        return "<h1>" + e.getMessage() + "</h1>";
    }

    //다른 exception을 받고 싶어.
    @ExceptionHandler(value = EmptyResultDataAccessException.class) //특정 exception만 받을거임. -> EmptyResultDataAccessException 만 받음.
    public String EmptyResultDataAccessException(EmptyResultDataAccessException e){    
        return "<h1>" + e.getMessage()+"다른 exception" + "</h1>";
    }

    //UserApiController의 ResponseDto Exception 처리
    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleReponseDtoException(Exception e){
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}

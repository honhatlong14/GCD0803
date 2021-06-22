package com.example.app_dev.controller;

import com.example.app_dev.service.exception.NotFoundPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends AbstractErrorController {
    private static final String ERROR_PATH=  "/error";

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * Just catching the {@linkplain NotFoundPageException} exceptions and render
     * the 404.jsp error page.
     */
    @ExceptionHandler(NotFoundPageException.class)
    public String notFound() {
        System.err.println("NOT FOUND");
        return "404";
    }

    /**
     * Responsible for handling all errors and throw especial exceptions
     * for some HTTP status codes. Otherwise, it will return a map that
     * ultimately will be converted to a json error.
     */
    @RequestMapping(ERROR_PATH)
    public String handleErrors(HttpServletRequest request) throws NotFoundPageException {
        HttpStatus status = getStatus(request);

        if (status.equals(HttpStatus.NOT_FOUND)) {
            throw new NotFoundPageException("Not Found Page");
        }

        return getErrorPath();
    }

//    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

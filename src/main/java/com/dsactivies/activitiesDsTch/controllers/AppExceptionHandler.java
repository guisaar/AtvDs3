package com.dsactivies.activitiesDsTch.controllers;

import com.dsactivies.activitiesDsTch.Services.FIleService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(FIleService.FileStorageException.class)
    public ModelAndView handleException(FIleService.FileStorageException exception, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMsg());
        mav.setViewName("event/error");
        return mav;
    }
}

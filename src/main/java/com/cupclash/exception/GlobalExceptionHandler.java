package com.cupclash.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Bad match ID, bad prediction ID, invalid bracket slot, etc.
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorTitle",   "Invalid Request");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("statusCode",   400);
        return "error";
    }

    // User navigates to a URL that doesn't exist (e.g. /game/999)
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNotFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("errorTitle",   "Page Not Found");
        model.addAttribute("errorMessage", "The page you're looking for doesn't exist.");
        model.addAttribute("statusCode",   404);
        return "error";
    }

    // Anything unexpected — database down, null pointer, etc.
    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("errorTitle",   "Something Went Wrong");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        model.addAttribute("statusCode",   500);
        return "error";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.ai.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Абс0лютный Н0ль
 */
@RestController
public class BaseController {

    protected ModelAndView template(String baseName, Model model, HttpStatus status, String viewName) {
        model.addAttribute("viewName", viewName);

        return new ModelAndView(baseName, status);
    }

    protected ModelAndView OK(String baseName, Model model, String viewName) {
        return template(baseName, model, HttpStatus.OK, viewName);
    }

    protected ModelAndView OK(Model model, String viewName) {
        return OK("main", model, viewName);
    }

    protected ModelAndView NOT_FOUND(String baseName, Model model, String viewName) {
        return template(baseName, model, HttpStatus.NOT_FOUND, viewName);
    }

    protected ModelAndView NOT_FOUND(Model model, String viewName) {
        return OK("main", model, viewName);
    }

    protected ModelAndView BAD_GATEWAY(String baseName, Model model, String viewName) {
        return template(baseName, model, HttpStatus.BAD_GATEWAY, viewName);
    }

    protected ModelAndView BAD_GATEWAY(Model model, String viewName) {
        return OK("main", model, viewName);
    }

    protected ModelAndView BAD_REQUEST(String baseName, Model model, String viewName) {
        return template(baseName, model, HttpStatus.BAD_REQUEST, viewName);
    }

    protected ModelAndView BAD_REQUEST(Model model, String viewName) {
        return OK("main", model, viewName);
    }

    protected ModelAndView Redirect(String redirectUrl) {
        return new ModelAndView("redirect:" + redirectUrl);
    }
}

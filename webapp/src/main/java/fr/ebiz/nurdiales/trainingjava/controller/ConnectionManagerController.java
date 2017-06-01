package fr.ebiz.nurdiales.trainingjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ConnectionManagerController {
    private static final String LOGIN = "/login";

    @RequestMapping(value = {LOGIN}, method = RequestMethod.GET)
    protected String doGet(ModelMap model, @RequestParam Map<String, String> request) {
        return "." + LOGIN;
    }

    @RequestMapping(value = {LOGIN}, method = RequestMethod.POST)
    protected String doPost(ModelMap model, @RequestParam Map<String, String> request) {
        return "." + LOGIN;
    }
}

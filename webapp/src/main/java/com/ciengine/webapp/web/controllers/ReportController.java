package com.ciengine.webapp.web.controllers;

import com.ciengine.common.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle requests from users and return templates.
 */
@Controller
public class ReportController {

    @Autowired
    private RestTemplate restTemplate;

    private final String restUrl;

    @Autowired
    public ReportController(@Value("${rest.service.url}") String restUrl) {
        this.restUrl = restUrl;
    }

    /**
     * Get reports page.
     *
     * @return reports page
     */
    @RequestMapping(value = "/selectmodules", method = RequestMethod.GET)
    public String reportsPage(Model model) {
//        model.addAttribute("name", "Evg");
        List<ModuleItem> list = new ArrayList<>();
        list.add(createModule("A"));
        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        //model.addAttribute("modules", list);
        return "selectmodules";
    }

    /**
     * Get reports page.
     *
     * @return reports page
     */
    @RequestMapping(value = "/selectbranches", method = RequestMethod.POST)
    public String postReportsPage(@ModelAttribute("modulesForm") ModulesForm modulesForm) {
//        model.addAttribute("name", "Evg");
//        List<Module> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        //model.addAttribute("modules", list);
        return "selectbranches";
    }

    private ModuleItem createModule(String a) {
        ModuleItem module = new ModuleItem();
        module.setName(a);
        return module;
    }

//
//    @ModelAttribute("allSeedStarters")
//    public List<Module> populateSeedStarters() {
//        List<Module> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        list.add(createModule("v"));
//        return list;
//    }

    @ModelAttribute(value = "newModulesForm")
    public ModulesForm newModulesForm()
    {
        ModulesForm modulesForm = new ModulesForm();
        List<ModuleItem> list = new ArrayList<>();
        list.add(createModule("A"));
        list.add(createModule("b"));
        list.add(createModule("v"));
        modulesForm.setModules(list);
        return modulesForm;
    }
}

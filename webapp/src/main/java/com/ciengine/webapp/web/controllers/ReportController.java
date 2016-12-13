package com.ciengine.webapp.web.controllers;

import com.ciengine.common.Module;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
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
//        List<ModuleItem> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        ModulesForm modulesForm = new ModulesForm();
        List<ModuleItem> list = new ArrayList<>();
        list.add(createModule("A"));
        list.add(createModule("b"));
        list.add(createModule("v"));
        modulesForm.setModules(list);
        model.addAttribute("modulesForm", modulesForm);
        return "selectmodules";
    }

    /**
     * Get reports page.
     *
     * @return reports page
     */
    @RequestMapping(value = "/selectbranches", method = RequestMethod.POST)
    public String postReportsPage(@ModelAttribute("modulesForm") ModulesForm modulesForm, Model model) {
//        model.addAttribute("name", "Evg");
//        List<Module> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        //model.addAttribute("modules", list);
        List<String> brabchesFrom = new ArrayList<>();
        brabchesFrom.add("develop");
        brabchesFrom.add("future/6.4");
        List<String> brabchesTo = new ArrayList<>();
        brabchesTo.add("release/6.3");
        brabchesTo.add("release/6.4");

        List<ModuleItem> list = new ArrayList<>();
        for (ModuleItem moduleItem : modulesForm.getModules()) {
            if (moduleItem.isEnabled()) {
                list.add(moduleItem);
                moduleItem.setBranchesFrom(brabchesFrom);
                moduleItem.setBranchesTo(brabchesTo);
            }
        }
        modulesForm.setModules(list);
        model.addAttribute("modulesForm", modulesForm);
        return "selectbranches";
    }

    @RequestMapping(value = "/acceptmodules", method = RequestMethod.POST)
    public String acceptmodules(@ModelAttribute("modulesForm") ModulesForm modulesForm, Model model) {
//        model.addAttribute("name", "Evg");
//        List<Module> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        //model.addAttribute("modules", list);
        for (ModuleItem moduleItem : modulesForm.getModules()) {
            moduleItem.setCodeChanged(Math.random() > 0.5d ? "yes":"no");
            if ("yes".equals(moduleItem.getCodeChanged())) {
                moduleItem.setEnabled(true);
            }
        }
        model.addAttribute("modulesForm", modulesForm);
        return "acceptmodules";
    }

 @RequestMapping(value = "/selectversions", method = RequestMethod.POST)
    public String selectversions(@ModelAttribute("modulesForm") ModulesForm modulesForm, Model model) {
//        model.addAttribute("name", "Evg");
//        List<Module> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        //model.addAttribute("modules", list);
//        for (ModuleItem moduleItem : modulesForm.getModules()) {
//            moduleItem.setCodeChanged(Math.random() > 0.5d ? "yes":"no");
//            if ("yes".equals(moduleItem.getCodeChanged())) {
//                moduleItem.setEnabled(true);
//            }
//        }
     List<ModuleItem> list = new ArrayList<>();
     for (ModuleItem moduleItem : modulesForm.getModules()) {
         if (moduleItem.isEnabled()) {
             list.add(moduleItem);

         }
     }
     modulesForm.setModules(list);
        model.addAttribute("modulesForm", modulesForm);
     List<String> allTypes = new ArrayList<>();
     allTypes.add("RC");
     allTypes.add("M");
        model.addAttribute("allTypes", allTypes);
        return "selectversions";
    }

@RequestMapping(value = "/selectversionsfinal", method = RequestMethod.POST)
    public String selectversionsfinal(@ModelAttribute("modulesForm") ModulesForm modulesForm, Model model) {
    for (ModuleItem moduleItem : modulesForm.getModules()) {
        moduleItem.setVersion(moduleItem.getNumericversion() + "-" + moduleItem.getMilestonetype() + "123");
    }
    model.addAttribute("modulesForm", modulesForm);
        return "selectversionsfinal";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@ModelAttribute("modulesForm") ModulesForm modulesForm, Model model) {
// todo submit via rest call
        return "submit";
    }

    private ModuleItem createModule(String a) {
        ModuleItem module = new ModuleItem();
        module.setName(a);
        List<String> brabchesFrom = new ArrayList<>();
        brabchesFrom.add("develop");
        brabchesFrom.add("future/6.4");
        module.setBranchesFrom(brabchesFrom);
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

//    @ModelAttribute(value = "newModulesForm")
//    public ModulesForm newModulesForm()
//    {
//        ModulesForm modulesForm = new ModulesForm();
//        List<ModuleItem> list = new ArrayList<>();
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        list.add(createModule("v"));
//        modulesForm.setModules(list);
//        return modulesForm;
//    }
}

package com.ciengine.webapp.web.controllers;

import com.ciengine.common.CIEngineClient;
import com.ciengine.common.Module;
import com.ciengine.common.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle requests from users and return templates.
 */
@Controller
public class ReleaseController {

    @Autowired
    private CIEngineClient ciEngineClient;

    private final String restUrl;

    @Autowired
    public ReleaseController(@Value("${rest.service.url}") String restUrl) {
        this.restUrl = restUrl;
    }

    /**
     * Get reports page.
     *
     * @return reports page
     */
    @RequestMapping(value = "/selectmodules", method = RequestMethod.GET)
    public String selectmodules(Model model) {
        FindModulesRequest findModulesRequest = new FindModulesRequest();
        FindModulesResponse findModulesResponse = ciEngineClient.findModules(restUrl, findModulesRequest);
        List<ModuleItem> list = new ArrayList<>();
        for (Module module : findModulesResponse.getModules()) {
            list.add(createModule(module.getName()));
        }
//        model.addAttribute("name", "Evg");
//
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
        ModulesForm modulesForm = new ModulesForm();

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
    public String selectbranches(@ModelAttribute("modulesForm") ModulesForm modulesForm, Model model) {
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
        List<Release> releaseList = new ArrayList<>();
        SubmitReleasesRequest submitReleasesRequest = new SubmitReleasesRequest();
        for (ModuleItem moduleItem : modulesForm.getModules()) {
            Release release = new Release();
            release.setName(moduleItem.getName());
            release.setBrancheFrom(moduleItem.getBrancheFrom());
            release.setBrancheTo(moduleItem.getBrancheTo());
            release.setVersion(moduleItem.getVersion());
            releaseList.add(release);
        }
        submitReleasesRequest.setReleaseList(releaseList);
        ciEngineClient.submitReleases(restUrl, submitReleasesRequest);
        return "submit";
    }


    @RequestMapping(value = "/builds", method = RequestMethod.GET)
    public String builds(Model model) {
        FindBuildsRequest findBuildsRequest = new FindBuildsRequest();
        ciEngineClient.findBuilds(restUrl, findBuildsRequest);
        FindBuildsResponse findBuildsResponse = ciEngineClient.findBuilds(restUrl, findBuildsRequest);
        List<Build> builds = new ArrayList<>();
        for (Build build : findBuildsResponse.getBuildList()) {
            builds.add(build);
        }
//        model.addAttribute("name", "Evg");
//
//        list.add(createModule("A"));
//        list.add(createModule("b"));
//        model.addAttribute("greeting", list);
//        ModulesForm modulesForm = new ModulesForm();
//
//        modulesForm.setModules(list);
        model.addAttribute("builds", builds);
        return "builds";
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

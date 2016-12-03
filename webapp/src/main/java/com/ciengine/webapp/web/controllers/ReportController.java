package com.ciengine.webapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String reportsPage() {
        return "reports";
    }

    /**
     * Get report.
     *
     * @param model      model
     * @param parameters report parameters
     * @return html with required data or html with error status and message
     */
//    @RequestMapping(value = "/report", method = RequestMethod.POST)
//    public String getReport(Model model, @RequestBody final ReportParams parameters) {
//        HttpEntity<ReportParams> request = new HttpEntity<>(parameters);
//        ResponseEntity entity = restTemplate.postForEntity(restUrl + "/reports/report",
//                request, ReportResponse.class);
//        if (entity.getStatusCode() == HttpStatus.OK) {
//            ReportResponse report = (ReportResponse) entity.getBody();
//            List<ProductViewsResponse> productViewsList = report.getProductViewsList();
//            model.addAttribute("products", productViewsList);
//            return "results :: resultsList";
//        } else {
//            ControllerResponse response = (ControllerResponse) entity.getBody();
//            model.addAttribute("status", entity.getStatusCode());
//            model.addAttribute("reason", response.getMessage());
//            return "errorMessage";
//        }
//    }

}

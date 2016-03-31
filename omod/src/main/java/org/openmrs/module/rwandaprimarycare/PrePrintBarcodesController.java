package org.openmrs.module.rwandaprimarycare;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/module/rwandaprimarycare/prePrintBarcodes")
public class PrePrintBarcodesController {

    protected static final Log log = LogFactory.getLog(HomepageController.class);
    
    @RequestMapping(method = RequestMethod.GET)
    public void getRequest(ModelMap model){
        //just show the page
    }
}

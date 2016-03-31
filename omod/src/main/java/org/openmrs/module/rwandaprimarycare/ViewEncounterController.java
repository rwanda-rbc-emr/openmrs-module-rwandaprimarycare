package org.openmrs.module.rwandaprimarycare;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewEncounterController {

    @RequestMapping("/module/rwandaprimarycare/encounter")
    public String viewEncounter(
            @RequestParam("encounterId") int encounterId,
            @RequestParam(required=false, value="returnUrl") String returnUrl,
            ModelMap model) throws PrimaryCareException {
        
    	//LK: Need to ensure that all primary care methods only throw a PrimaryCareException
    	//So that errors will be directed to a touch screen error page
    	try{
	        Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
	        model.addAttribute(encounter);
	        model.addAttribute(encounter.getPatient());
	        model.addAttribute("returnUrl", returnUrl);
    	} catch(Exception e)
    	{
    		throw new PrimaryCareException(e);
    	} 
        
        return "/module/rwandaprimarycare/encounter";
    }

    @RequestMapping("/module/rwandaprimarycare/deleteEncounter")
    public String deleteEncounter(
            @RequestParam("encounterId") int encounterId,
            @RequestParam(required = false, value = "returnUrl") String returnUrl) throws PrimaryCareException {
    	//LK: Need to ensure that all primary care methods only throw a PrimaryCareException
    	//So that errors will be directed to a touch screen error page
    	try{
	    	Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
	        Integer patientId = encounter.getPatientId();
	        Context.getEncounterService().voidEncounter(encounter, "N/A");
	        if (!StringUtils.hasText(returnUrl))
	            returnUrl = "/module/rwandaprimarycare/patient.form?patientId=" + patientId;
	        return "redirect:" + returnUrl;
    	} catch(Exception e)
    	{
    		throw new PrimaryCareException(e);
    	} 
    }
}

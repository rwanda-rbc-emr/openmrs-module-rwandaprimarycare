package org.openmrs.module.rwandaprimarycare;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptNumeric;
import org.openmrs.Encounter;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.mohappointment.model.Appointment;
import org.openmrs.module.mohappointment.utils.AppointmentUtil;
import org.openmrs.patient.IdentifierValidator;

public class PrimaryCareUtil {

    protected final static Log log = LogFactory.getLog(PrimaryCareUtil.class);
    
    public static Obs newObs(Patient patient, Concept c, Date obsDate, Location location){
        Obs ret = new Obs();
        ret.setCreator(Context.getAuthenticatedUser());
        ret.setDateCreated(new Date());
        ret.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
        ret.setVoided(false);
        ret.setObsDatetime(obsDate);
        ret.setConcept(c);
        ret.setLocation(location);
        return ret;
    }
    
    public static PersonAttribute newPersonAttribute(PersonAttributeType pat, String value, Patient patient){
        PersonAttribute pa = new PersonAttribute(pat, value);
        pa.setPerson(Context.getPersonService().getPerson(patient.getPatientId()));
        pa.setCreator(Context.getAuthenticatedUser());
        pa.setDateCreated(new Date());
        pa.setVoided(false);
        return pa;
    }
    
    //Get the idgen identifier source for the primary identifier type
    public static IdentifierSource getPrimaryIdentifierTypeSource() {
        IdentifierSource is = null;
        PatientIdentifierType pit = PrimaryCareBusinessLogic.getPrimaryPatientIdentiferType();
        List<IdentifierSource> isss = Context.getService(IdentifierSourceService.class).getAllIdentifierSources(false);
        
        for (IdentifierSource iss : isss){
            if (iss.getIdentifierType().getPatientIdentifierTypeId().equals(pit.getPatientIdentifierTypeId())){
                return iss;
            }   
        }
        throw new RuntimeException("Unable to load valid identifier source");
    }
    
    
    /**
     * 
     * Returns the patientIdentifierType to use for the national ID number
     * 
     * @return
     */
    public static PatientIdentifierType getNationalIdIdentifierType(){
            PatientIdentifierType ret = null;
            String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_NATIONAL_ID_TYPE);
            if (st != null && !st.equals("")){
                ret = Context.getPatientService().getPatientIdentifierTypeByName(st);
            }
            return ret;
    }
    
    /**
     * 
     * Returns the patientIdentifierType to use for the national ID number
     * 
     * @return
     */
    public static PersonAttributeType getHealthCenterAttributeType(){
            PersonAttributeType ret = null;
            String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_HEALTH_CENTER_ATTRIBUTE_TYPE);
            if (st != null && !st.equals("")){
                ret = Context.getPersonService().getPersonAttributeTypeByName(st);
            }
            return ret;
    }
    
    /**
     * 
     * Returns the patientIdentifierType to use for the national ID number
     * 
     * @return
     */
    public static Concept getMothersNameConcept(){
            Concept ret = null;
            String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_MOTHERS_NAME_CONCEPT);
            if (st != null && !st.equals("")){
                try {
                    ret = Context.getConceptService().getConcept(Integer.valueOf(st));
                } catch (Exception ex){log.info("Unable to load concept for mother's name.  Returning null");}
            }
            return ret;
    }
    
    /**
     * 
     * Returns the patientIdentifierType to use for the national ID number
     * 
     * @return
     */
    public static Concept getFathersNameConcept(){
            Concept ret = null;
            String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_FATHERS_NAME_CONCEPT);
            if (st != null && !st.equals("")){
                try {
                    ret = Context.getConceptService().getConcept(Integer.valueOf(st));
                } catch (Exception ex){log.info("Unable to load concept for mother's name.  Returning null");}
            }
            return ret;
    }  
    
    public static Concept getInsuranceTypeConcept(){
        Concept ret = null;
        String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_INSURANCE_TYPE);
        if (st != null && !st.equals("")){
            try {
                ret = Context.getConceptService().getConcept(Integer.valueOf(st));
            } catch (Exception ex){log.info("Unable to load concept for Rwanda Insurance Type.  Returning null");}
        }
        return ret;
    }
    
    public static Concept getInsuranceNumberConcept(){
        Concept ret = null;
        String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_INSURANCE_NUMBER);
        if (st != null && !st.equals("")){
            try {
                ret = Context.getConceptService().getConcept(Integer.valueOf(st));
            } catch (Exception ex){log.info("Unable to load concept for Rwanda Insurance Number.  Returning null");}
        }
        return ret;
    }

    public static Concept getInsuranceCoverageStartDateConcept(){
        Concept ret = null;
        String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_INSURANCE_COVERAGE_START_DATE);
        if (st != null && !st.equals("")){
            try {
                ret = Context.getConceptService().getConcept(Integer.valueOf(st));
            } catch (Exception ex){log.info("Unable to load concept for Rwanda Insurance Coverage StartDate.  Returning null");}
        }
        return ret;
    }
    
    public static Concept getInsuranceExpirationDateConcept(){
        Concept ret = null;
        String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_INSURANCE_EXPIRATION_DATE);
        if (st != null && !st.equals("")){
            try {
                ret = Context.getConceptService().getConcept(Integer.valueOf(st));
            } catch (Exception ex){log.info("Unable to load concept for Rwanda Insurance Expiration Date.  Returning null");}
        }
        return ret;
    }
    
    /**
     * Returns a 3 digit string which represents a health facility in Rwanda
     * 
     * You can query the PBF database for FOSA codes
     * 
     * @return location code as String
     */
    public static String getPrimaryCareLocationCode(){ 
    	String ret = evaluatePrimaryCareLocationCode();
    	if(!StringUtils.isNumeric(ret))
    		throw new RuntimeException("The registration.defaultLocationCode global property is not set correctly.  Please use only digits.");
    	return ret;
    }

    /**
     * This 1) looks for a userLocation in user's volatile data, which is set by logging in through the touchscreen app
     * (this depends on the list of locations (by name and prefixes) GP
     * 2) returns the number as a String in the registration default user location GP
     * 
     * @return location code as String
     */
    private static String evaluatePrimaryCareLocationCode(){
        Object myLocationObj =  Context.getVolatileUserData("userLocation");
        if (myLocationObj == null){
            String ret = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_DEFAULT_LOCATION_CODE);
            if (ret != null && !ret.equals(""))
                return ret;
            else
                throw new RuntimeException("Please set a default health center location prefix");
        }    
        //TODO:  next location check should be through the GP
        Location myLocation = (Location) myLocationObj;
        String str = null;
        str = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_RWANDA_LOCATION_CODE);
        if (str.contains("|")){   //multiple locations 
            for (StringTokenizer st = new StringTokenizer(str, "|"); st.hasMoreTokens(); ) {
                String s = st.nextToken().trim();
                if (s.contains(":")){
                    String[] stArr = s.split(":");
                    if (stArr[0].equals(myLocation.getName()))
                        return stArr[1];
                } else {
                    throw new RuntimeException("The registration.rwandaLocationCode global property is not set correctly.  Please use the format <<location_Name>>:MOH_ID| etc...");
                }
            }                
        } else {   //single 
            if (str.contains(":")){
                String[] stArr = str.split(":");
                if (stArr[0].equals(myLocation.getName()))
                    return stArr[1];
            } else {
                return str;
            }
        }
        //LK: if the location code is not found in the global properties use the default location code (as this is preferential to throwing
        //	  an exception, though we would expect each location to be set up within the global properties.
        String ret = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_DEFAULT_LOCATION_CODE);
        if (ret != null && !ret.equals(""))
        {
            return ret;
        }
        else
        {
            throw new RuntimeException("The registration.rwandaLocationCode global property is not set correctly.  Unable to return a location code. Also unable to retrieve the default location code from registration.defaultLocationCode");
        }
    }
    
    
    public static Obs getMostRecentObs(Patient p, Concept concept){
        List<Obs> o = Context.getObsService().getObservationsByPersonAndConcept(p, concept);
        
        Obs ob = null;
        if(o != null){
		//find the most recent value
			for(Obs obs:o){
				 if (ob == null
	                     || obs.getObsDatetime().compareTo(ob.getObsDatetime()) > 0){
					 ob = obs;
				 }
			}
        }
		return ob;
    }
    
    public static boolean doesEncounterContainObsWithConcept(Encounter enc, Concept c){
        try{
            for (Obs oTmp : enc.getObs()){
                if (oTmp.getConcept().getConceptId().equals(c.getConceptId()))
                    return true;
            }
        } catch (Exception ex){
            return false;
        }
        return false;
    }
    
    public static Concept getServiceRequestedConcept(){
        Concept ret = null;
        String st = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_SERVICE_REQUESTED_CONCEPT);
        if (st != null && !st.equals("")){
            try {
                ret = Context.getConceptService().getConcept(Integer.valueOf(st));
            } catch (Exception ex){log.info("Unable to load concept for primary care service requested.  Returning null");}
        }
        return ret;
    }
//    
//    public List<Concept> getSelectiveRequestedConcepts(){
//    	List<Concept> concepts = new ArrayList<Concept>();
//    	
//    	for(ConceptAnswer answer: getServiceRequestedConcept().getAnswers()){
//    		
//    	}
//    	
//    	return concepts;
//    }
    
    public static boolean isIdentifierStringAValidIdentifier(String identifierStr, Location location){
        IdentifierSourceService iss = Context.getService(IdentifierSourceService.class);
        PatientIdentifierType pit = PrimaryCareBusinessLogic.getPrimaryPatientIdentiferType();
        
        //if source is a sequential generator, verify the length:
        try {
            IdentifierSource is = iss.getAutoGenerationOption(pit).getSource();
            if (is instanceof SequentialIdentifierGenerator){
                SequentialIdentifierGenerator sig = (SequentialIdentifierGenerator) is;
                if (identifierStr.length() != sig.getLength().intValue())
                    return false;
            }
        } catch (Exception ex){
            log.error("PrimaryCareUtil.isIdentifierStringAValidIdentifier isn't working for the following reason: ");
            ex.printStackTrace(System.out); 
        }
        
        String validator = pit.getValidator();
        IdentifierValidator iv = Context.getPatientService().getIdentifierValidator(validator);
        
        
        
        if (validator != null)
            return iv.isValid(identifierStr);
        else
            return true;
    }
    
    /**
     * 
     * returns the 3-digit location code from the list of location codes GP
     * 
     * @param myLocation
     * @return
     */
    public static Location getPrimaryCareLocationFromCodeList(String locationCode){
        String str = null;
        str = Context.getAdministrationService().getGlobalProperty(PrimaryCareConstants.GLOBAL_PROPERTY_RWANDA_LOCATION_CODE);
        if (str.contains("|")){   //multiple locations 
            for (StringTokenizer st = new StringTokenizer(str, "|"); st.hasMoreTokens(); ) {
                String s = st.nextToken().trim();
                if (s.contains(":")){
                    String[] stArr = s.split(":");
                    if (stArr[1].equals(locationCode))
                        return Context.getLocationService().getLocation(stArr[0]);
                } else {
                    throw new RuntimeException("The registration.rwandaLocationCode global property is not set correctly.  Please use the format <<location_Name>>:MOH_ID| etc...");
                }
            }                
        } else {   //single 
            if (str.contains(":")){
                String[] stArr = str.split(":");
                if (stArr[1].equals(locationCode))
                    return Context.getLocationService().getLocation(stArr[0]);
            } 
        }
        return null;
    }
    
    public static String calculateBMI(Patient patient, List<Obs> obsToCreate) {
	    
    	String bmiAsString = null;
    	
    	//firstly find out if someone has entered a weight for the patient today
    	Concept weight = PrimaryCareBusinessLogic.getWeightConcept();
    	Concept height = PrimaryCareBusinessLogic.getHeightConcept();
    	
    	String weightStr = null;
    	Obs heightOb = null;
    	
    	for(Obs observation:obsToCreate){
    		if(observation.getConcept().equals(weight))
    		{
    			weightStr = observation.getValueAsString(Context.getLocale());
    		}
    		
    		if(observation.getConcept().equals(height))
    		{
    			heightOb = observation;
    		}
    	}
    	
    	if(weightStr != null)
    	{
    		Double heightDbl = null;
    		
    		if(heightOb == null)
    		{
    			//Person person = Context.getPersonService().
    			//if a height hasn't been entered now we need to see if
    			//a height has been entered previously
    			Obs mostRecentHeightOb = getMostRecentHeightObservation(patient);
    			
    			//need to check the age of the most recent height observation
    			//if the age was taken when the patient was below 20 years of age then is should not be
    			//used
    			if (mostRecentHeightOb != null && getAgeAtObservation(patient, mostRecentHeightOb) > 20)
        			{
        				heightOb = mostRecentHeightOb;
        			}
    			
    		}
    		
    		if(heightOb != null)
    		{
    			ConceptNumeric heightNumberic = Context.getConceptService().getConceptNumeric(height.getConceptId());
    			ConceptNumeric weightNumberic = Context.getConceptService().getConceptNumeric(weight.getConceptId());
    			
    			double weightInKg = Double.parseDouble(weightStr);
                double heightInM = Double.parseDouble(heightOb.getValueAsString(Context.getLocale()));
                
                if (weightNumberic.getUnits().equals("lb"))
                {
                    weightInKg = weightInKg * 0.45359237;
                }
                
                if (heightNumberic.getUnits().equals("cm"))
                {
                    heightInM = heightInM / 100;
                }
                else if (heightNumberic.getUnits().equals("in"))
                {
                    heightInM = heightInM * 0.0254;
                }
                
                
                double bmi = weightInKg / (heightInM * heightInM);
                String temp = "" + bmi;
                bmiAsString = temp.substring(0, temp.indexOf('.') + 2);
    		}
    	}
    	
	    return bmiAsString;
    }
    
    public static int getAgeAtObservation(Patient patient, Obs observation)
    {
    	Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(patient.getBirthdate());
		
		Calendar obsDate = Calendar.getInstance();
		obsDate.setTime(observation.getObsDatetime());
		
		int age = obsDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) -1;
		
		return age;
    }
    
    public static Obs getMostRecentHeightObservation(Patient patient)
    {
    	Concept height = PrimaryCareBusinessLogic.getHeightConcept();
    	
    	return getMostRecentObs(patient, height);
    }
    
    
    
    //The following are Rwanda national ID specific as of July 29, 2010
    ////////////////////////////////////////////////////////////////////////////////////////
    public static String getIdNumFromNationalId(String nationalIdLong) {
        if (nationalIdLong.length() >= 21)
            return nationalIdLong.substring(0, 21);
        else
            throw new RuntimeException("National ID Number must be at least 21 digits. Current length is " + nationalIdLong+".  You may need to reconfigure your scanner");
    }
    
    public static Integer getDOBYearFromNationalId(String nationalIdLong){
        if (nationalIdLong.length() >= 21)
            return Integer.valueOf(nationalIdLong.substring(2, 6));
        else
            throw new RuntimeException("National ID Number must be at least 21 digits. Current length is " + nationalIdLong);
    }
    
    public static String getGivenNameFromNationalId(String nationalIdLong){
        if (nationalIdLong.length() >= 84){
            return nationalIdLong.substring(31, 31+25).trim();
        } else {
            return "";
        }
    }
    public static String getFamilyNameFromNationalId(String nationalIdLong){
        if (nationalIdLong.length() >= 84){
            return nationalIdLong.substring(31+25, 31+50).trim();
        } else {
            return "";
        }
    }
 
    public static String getGenderFromNationalId(String nationalIdLong){
        if (nationalIdLong.length() >= 21){
          //TODO:  figure this out once we figure out how to parse:
          return "";
        } else {
            throw new RuntimeException("National ID Number must be at least 21 digits. Current length is " + nationalIdLong);
        }    
    }
    
    public static Patient setupParentNames(Patient patient,String mothersName, String fathersName){
        PersonService ps = Context.getPersonService();
        if (mothersName != null && !mothersName.equals("")){
        	PersonAttributeType attributeType = ps.getPersonAttributeTypeByName(PrimaryCareConstants.MOTHER_NAME_ATTRIBUTE_TYPE);
        	   PersonAttribute attribute = new PersonAttribute(attributeType, "");
        	   attribute.setValue(mothersName);
        	   patient.addAttribute(attribute);
        }
        if (fathersName != null && !fathersName.equals("")){
        	PersonAttributeType attributeType = ps.getPersonAttributeTypeByName(PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE);
        	//if we don't have the father's name attribute type, we create it.
        	  if (attributeType == null) {
        		  attributeType = new PersonAttributeType();
        		  attributeType.setName(PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE);
        		  attributeType.setFormat("java.lang.String");
        		  attributeType.setDescription("First or last name of this person's father");
                  ps.savePersonAttributeType(attributeType);
                  log.info("Created New Person Attribute: "+PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE);
              } else {
                  log.info("Person Attribute: "+ PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE +"already exists");
              }
        	   PersonAttribute attribute = new PersonAttribute(attributeType, "");
               attribute.setValue(fathersName);
               patient.addAttribute(attribute);
        }
        return patient;
    }
    
    public static boolean hasParentsNamesAttributes(Patient patient){
    	PersonAttribute mumNameAttribute = patient.getAttribute(Context.getPersonService().getPersonAttributeTypeByName(PrimaryCareConstants.MOTHER_NAME_ATTRIBUTE_TYPE));
    	PersonAttribute dadNameAttribute = patient.getAttribute(Context.getPersonService().getPersonAttributeTypeByName(PrimaryCareConstants.FATHER_NAME_ATTRIBUTE_TYPE));
    	if(mumNameAttribute !=null && dadNameAttribute !=null)
    		return true;
    	else
    		return false;
    }
    
    /**
	 * Creates waiting appointment in different services
	 * 
	 * @param patient
	 */
	public static void createWaitingAppointment(Person provider, Encounter encounter, Obs obs, HttpSession session, Concept serviceConcept) {
		Appointment waitingAppointment = new Appointment();
		// Setting appointment attributes
		waitingAppointment.setAppointmentDate(new Date());
		waitingAppointment.setAttended(false);
		waitingAppointment.setVoided(false);
		waitingAppointment.setCreatedDate(new Date());
		waitingAppointment.setCreator(Context.getAuthenticatedUser());
		waitingAppointment.setProvider(provider);
		waitingAppointment.setEncounter(encounter);
		waitingAppointment.setReason(obs);
		waitingAppointment.setNote("This is a waiting patient to the Consultation");
		waitingAppointment.setPatient(encounter.getPatient());
		waitingAppointment.setLocation(PrimaryCareBusinessLogic.getLocationLoggedIn(session));
		waitingAppointment.setService(AppointmentUtil.getServiceByConcept(serviceConcept));
		
		log.info("<<<<<<<<<____Service Concept ID__"+getServiceRequestedConcept()+"__SERVICE NAME:____"+AppointmentUtil.getServiceByConcept(getServiceRequestedConcept())+"_________>>>>>>>");
		
		AppointmentUtil.saveWaitingAppointment(waitingAppointment);
	}

	/**
	 * Gets the a list of categorized group of Requested Primary Care services
	 * 
	 * @return List<Concept>
	 */
	public static  List<Concept> getPrimaryCareServices() {
		List<Concept> selectedConcepts=new ArrayList<Concept>();
		GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(PrimaryCareConstants.GLOBAL_PROPERTY_SERVICE_REQUESTED_CONCEPT_ANSWERS);
		String[] conceptIds = gp.getPropertyValue().split(",");
		
		for (String conceptIdstr: conceptIds) {
			
			Concept cpt=Context.getConceptService().getConcept(Integer.valueOf(conceptIdstr)) ;
			selectedConcepts.add(cpt);
		}
		
		return selectedConcepts;
	}
}

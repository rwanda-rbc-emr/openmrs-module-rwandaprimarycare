package org.openmrs.module.rwandaprimarycare;

import org.openmrs.EncounterType;
import org.openmrs.Privilege;

public class PrimaryCareConstants {
    
    public static EncounterType ENCOUNTER_TYPE_REGISTRATION;
    public static EncounterType ENCOUNTER_TYPE_VITALS;
    public static EncounterType ENCOUNTER_TYPE_DIAGNOSIS;
    public static Privilege PRINT_BARCODE_OFFLINE_PRIVILEGE;
    public static int ageRange = 10;
    public static final String GLOBAL_PROPERTY_PRIMARY_IDENTIFIER_TYPE = "registration.primaryIdentifierType";
    public static final String GLOBAL_PROPERTY_OTHER_IDENTIFIER_TYPES = "registration.otherIdentifierTypes";
    public static final String GLOBAL_PROPERTY_BAR_CODE_COUNT = "registration.barCodeCount";
    //TODO:  the location code architecture is wrong -- all location codes come from module
    //TODO:  this needs to correspond to a single default location
    //TODO:  registration clerk can override this in module.
    public static final String GLOBAL_PROPERTY_RWANDA_LOCATION_CODE = "registration.rwandaLocationCodes";
    public static final String GLOBAL_PROPERTY_DEFAULT_LOCATION_CODE = "registration.defaultLocationCode";
    public static final String GLOBAL_PROPERTY_INSURANCE_TYPE = "registration.insuranceTypeConcept";
    public static final String GLOBAL_PROPERTY_INSURANCE_NUMBER = "registration.insuranceNumberConcept";
    
    
	public static final String GLOBAL_PROPERTY_INSURANCE_COVERAGE_START_DATE = "registration.insuranceCoverageStartDateConcept";
	public static final String GLOBAL_PROPERTY_INSURANCE_EXPIRATION_DATE = "registration.insuranceExpirationDateConcept";
	
	
    public static final String GLOBAL_PROPERTY_NATIONAL_ID_TYPE = "registration.nationalIdType";
    public static final String SESSION_ATTRIBUTE_WORKSTATION_LOCATION = "primaryCareWorkstationLocation";
    public static final String GLOBAL_PROPERTY_HEALTH_CENTER_ATTRIBUTE_TYPE = "registration.healthCenterPersonAttribute";
    public static final String GLOBAL_PROPERTY_INSURANCE_TYPE_ANSWERS = "registration.insuranceTypeConceptAnswers";
    public static final String GLOBAL_PROPERTY_MOTHERS_NAME_CONCEPT = "registration.mothersNameConceptId";
    public static final String GLOBAL_PROPERTY_FATHERS_NAME_CONCEPT = "registration.fathersNameConceptId";
    public static final String GLOBAL_PROPERTY_SERVICE_REQUESTED_CONCEPT = "registration.serviceRequestedConcept";
    public final static String GLOBAL_PROPERTY_PARENT_TO_CHILD_RELATIONSHIP_TYPE = "registration.parentChildRelationshipTypeId";
    public final static String GLOBAL_PROPERTY_RESTRICT_BY_HEALTH_CENTER = "registration.restrictSearchByHealthCenter";
    public static final String MOTHER_NAME_ATTRIBUTE_TYPE = "Mother's name";
    public static final String FATHER_NAME_ATTRIBUTE_TYPE = "Father's name";
	public static final String GLOBAL_PROPERTY_SERVICE_REQUESTED_CONCEPT_ANSWERS = "registration.serviceRequestedConceptAnswers";
    
}

<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="resources/touchscreenHeader.jsp"%>

<!-- note: controller is PatientDashboardController-->
<table><tr><Td>
	
	<%@ include file="patientResultsTableWhite.jspf"%>
	
	<div style="float: left; background-color: #f0f0a0; border: 1px black solid; padding: 10px">
		<h1><spring:message code="rwandaprimarycare.touchscreen.printbarcodequestion"/></h1>
		<c:set var="yes"><spring:message code="rwandaprimarycare.touchscreen.yes"/></c:set>
		<c:set var="no"><spring:message code="rwandaprimarycare.touchscreen.no"/></c:set>
		<touchscreen:button label="${yes}" onClick="window.open('barCode.form?patientId=${patient.patientId}&multiple=true'); window.location = 'patient.form?patientId=${patient.patientId}&serviceRequested=0';" cssClass="green"/>
		<touchscreen:button label="${no}" href="patient.form?patientId=${patient.patientId}&serviceRequested=0" cssClass="green"/>
	</div>
</Td></tr></table>
<%@ include file="resources/touchscreenFooter.jsp"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="resources/touchscreenHeader.jsp"%>
<openmrs:htmlInclude file="/moduleResources/rwandaprimarycare/addresshierarchyrwanda.js" />

<c:if test="${results == null}">
	<form method="get">
		<input type="hidden" name="addIdentifier" value="${param.addIdentifier}"/>
		<table>
		<tr>
			<td>Rwandan Name</td>
			<c:set var="rwandanName"><spring:message code='rwandaprimarycare.touchscreen.rwandanName' /></c:set>
			<td><touchscreen:textInput required="false"  label="${rwandanName}" name="RWNAME" fieldType="upper" value="${search}" allowFreeText="true" ajaxURL="findPatientByNameAjax.form?searchType=RWNAME&search="/></td>
		</tr>
		<tr>
			<td>French/Ango Name</td>
			<c:set var="christianName"><spring:message code='rwandaprimarycare.touchscreen.christianName' /></c:set>
			<td><touchscreen:textInput required="false" label="${christianName}" name="FANAME" value="${search}" allowFreeText="true" ajaxURL="findPatientByNameAjax.form?searchType=FANAME&search="/></td>
		</tr>
			
		<tr>
			<td>Gender</td> 
			<td>
				<c:set var="gender"><spring:message code='rwandaprimarycare.touchscreen.gender' /></c:set>
				<c:set var="male"><spring:message code='rwandaprimarycare.touchscreen.male' /></c:set>
				<c:set var="female"><spring:message code='rwandaprimarycare.touchscreen.female' /></c:set>
				<select optional="true" name="GENDER" label="${gender}" helpText="${gender}">
					<option value="M">${male}</option>
					<option value="F">${female}</option>
				</select>
			</td>
		</tr>	
		<tr>

			<td>Age</td>
			<c:set var="age"><spring:message code='rwandaprimarycare.touchscreen.age' /></c:set>
			<td><touchscreen:numberInput required="true" label="${age}" name="AGE" value="${search}" min="0" max="150"/></td>

			
		</tr>	
		<!--<tr>
			<td>Mother's Rwandan Name</td>
			<td><touchscreen:textInput required="false" label="Mother's Rwandan Name" name="MRWNAME" value="${search}" allowFreeText="true" ajaxURL="findPatientByNameAjax.form?searchType=MRWNAME&search="/></td>
		</tr>	
		<tr>
			<td>Father's Rwandan Name</td>
			<td><touchscreen:textInput required="false" label="Father's Rwandan Name" name="FATHERSRWNAME" value="${search}" allowFreeText="true" ajaxURL="findPatientByNameAjax.form?searchType=FATHERSRWNAME&search="/></td>
		</tr>	-->	
		<!--  <tr>
			<td>Current Umudugudu</td>
			<c:set var="umuduguduStr"><spring:message code='rwandaprimarycare.touchscreen.umudugudu' /></c:set>
			<td><touchscreen:textInput required="false" label="${umuduguduStr}" name="UMUDUGUDU" value="${search}" allowFreeText="true" ajaxURL="findPatientByNameAjax.form?searchType=UMUDUGUDU&search=" javascriptAction="updateAddressHierarchyCache()"/></td>
		</tr>	-->		
		<tr>
			<td>Submit</td>
			<c:set var="searchStr"><spring:message code='rwandaprimarycare.touchscreen.search' /></c:set>
			<td><input type="submit" value="${searchStr}"/></td>
		</tr>	
		</table>				
	</form>
</c:if>

<c:if test="${results != null}">
	<touchscreen:patientList patients="${results}" maxResults="5" separator="" href="findPatientByNameConfirm.list" showAllIds="true"/>
	<br/>
	<c:url var="createHref" value="createNewPatient.form">
		<c:param name="addIdentifier" value="${addIdentifier}"/>
		<c:param name="givenName" value="${param.FANAME}"/>
		<c:param name="familyName" value="${param.RWNAME}"/>
		<c:param name="gender" value="${param.GENDER}"/>
		<c:param name="birthdate_day" value="${param.BIRTHDATE_DAY}"/>
		<c:param name="birthdate_month" value="${param.BIRTHDATE_MONTH}"/>
		<c:param name="birthdate_year" value="${param.BIRTHDATE_YEAR}"/>
		<c:param name="age" value="${param.AGE}"/>
		<c:param name="mothersName" value="${param.MRWNAME}"/>
		<c:param name="fathersName" value="${param.FATHERSRWNAME}"/>
		<c:param name="country" value="${param.COUNTRY}"/>
		<c:param name="province" value="${param.PROVINCE}"/>
		<c:param name="district" value="${param.DISTRICT}"/>
		<c:param name="sector" value="${param.SECTOR}"/>
		<c:param name="cell" value="${param.CELL}"/>
		<c:param name="address1" value="${param.UMUDUGUDU}"/>
	</c:url>
	
	<c:set var="nf"><spring:message code='rwandaprimarycare.touchscreen.notFoundNewPatient' /></c:set>
	<touchscreen:button label="${nf} ${fn:toUpperCase(param.RWNAME)} ${param.FANAME}" href="${createHref}"/>
</c:if>

<%@ include file="resources/touchscreenFooter.jsp"%>  

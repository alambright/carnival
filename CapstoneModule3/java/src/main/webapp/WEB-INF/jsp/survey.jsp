<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />


<body id="formbody">

	
	<div class="formcontainer">
	<div class="formcontent">
		<h2>National Park Survey</h2>
		<br/>
			<c:url var="submit" value="/submitSurvey" />
			<form:form modelAttribute="survey" method="POST" action="${ submit }">


				<form:label class="formtext" path="parkCode">Favorite National Park:</form:label>
				<form:select path="parkCode" class="formbox">
					<form:option value="">----Select a Park----</form:option>
					<c:forEach var="park" items="${ parks }">
						<form:option value="${ park.parkCode }">
							<c:out value="${ park.parkName }" />
						</form:option>
					</c:forEach>
					
				</form:select>
				<form:errors path="parkCode" cssClass="error" />
				<br />


				<div>
					<form:label class="formtext" path="emailAddress">Email Address:</form:label>
					<form:input class="formbox" path="emailAddress" type="text"
						placeholder="email Address" />
					<form:errors path="emailAddress" cssClass="error" />
				</div>

				<label class="formtext" for="state">State</label>
				<form:select path="state" class="formbox">
					<form:option value="">----Select a State----</form:option>
					<form:option value="Alabama">Alabama</form:option>
					<form:option value="Alaska">Alaska</form:option>
					<form:option value="Arizona">Arizona</form:option>
					<form:option value="Arkansas">Arkansas</form:option>
					<form:option value="California">California</form:option>
					<form:option value="Colorado">Colorado</form:option>
					<form:option value="Connecticut">Connecticut</form:option>
					<form:option value="Delaware">Delaware</form:option>
					<form:option value="District of Columbia">District of Columbia</form:option>
					<form:option value="Florida">Florida</form:option>
					<form:option value="Georgia">Georgia</form:option>
					<form:option value="Guam">Guam</form:option>
					<form:option value="Hawaii">Hawaii</form:option>
					<form:option value="Idaho">Idaho</form:option>
					<form:option value="Illinois">Illinois</form:option>
					<form:option value="Indiana">Indiana</form:option>
					<form:option value="Iowa">Iowa</form:option>
					<form:option value="Kansas">Kansas</form:option>
					<form:option value="Kentucky">Kentucky</form:option>
					<form:option value="Louisiana">Louisiana</form:option>
					<form:option value="Maine">Maine</form:option>
					<form:option value="Maryland">Maryland</form:option>
					<form:option value="Massachusetts">Massachusetts</form:option>
					<form:option value="Michigan">Michigan</form:option>
					<form:option value="Minnesota">Minnesota</form:option>
					<form:option value="Mississippi">Mississippi</form:option>
					<form:option value="Missouri">Missouri</form:option>
					<form:option value="Montana">Montana</form:option>
					<form:option value="Nebraska">Nebraska</form:option>
					<form:option value="Nevada">Nevada</form:option>
					<form:option value="New Hampshire">New Hampshire</form:option>
					<form:option value="New Jersey">New Jersey</form:option>
					<form:option value="New Mexico">New Mexico</form:option>
					<form:option value="New York">New York</form:option>
					<form:option value="North Carolina">North Carolina</form:option>
					<form:option value="North Dakota">North Dakota</form:option>
					<form:option value="Northern Marianas Islands">Northern Marianas Islands</form:option>
					<form:option value="Ohio">Ohio</form:option>
					<form:option value="Oklahoma">Oklahoma</form:option>
					<form:option value="Oregon">Oregon</form:option>
					<form:option value="Pennsylvania">Pennsylvania</form:option>
					<form:option value="Puerto Rico">Puerto Rico</form:option>
					<form:option value="Rhode Island">Rhode Island</form:option>
					<form:option value="South Carolina">South Carolina</form:option>
					<form:option value="South Dakota">South Dakota</form:option>
					<form:option value="Tennessee">Tennessee</form:option>
					<form:option value="Texas">Texas</form:option>
					<form:option value="Utah">Utah</form:option>
					<form:option value="Vermont">Vermont</form:option>
					<form:option value="Virginia">Virginia</form:option>
					<form:option value="Virgin Islands">Virgin Islands</form:option>
					<form:option value="Washington">Washington</form:option>
					<form:option value="West Virginia">West Virginia</form:option>
					<form:option value="Wisconsin">Wisconsin</form:option>
					<form:option value="Wyoming">Wyoming</form:option>
				</form:select>
				<form:errors path="state" cssClass="error" />

				<br />
				
				<label class="formtext" for="activityLevel">Your Activity Level:  </label>
					<form:radiobutton path="activityLevel" value="inactive" />Inactive
					<form:radiobutton path="activityLevel" value="sedentary" />Sedentary
					<form:radiobutton path="activityLevel" value="active" />Active
					<form:radiobutton path="activityLevel" value="extremely active" />Extremely Active
				<form:errors path="activityLevel" cssClass="error" />
				
				<br />

				<input type="submit" value="Submit!" class="formbox formtext" />
			</form:form>
		</div>
		
	</div>
</body>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />
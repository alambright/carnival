<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:import url="/WEB-INF/jsp/common/header.jsp" />


<body>

	<div class="container-fluid">
	
	<h1 class="fav-header"> Favorite National Parks</h1>
		<div class="content">
			<div class="row justify-content-center">
			<c:set var="count" value="${ 1 }"/>
				<c:forEach var="park" items="${ favoriteParks }">
				
				<div class="col-sm-1 home-row">
		
				<p class="rank">#<c:out value="${count }"/></p>
				
				<c:set var="count" value="${ count+1 }"/>
				</div>
				
					
					<div class="col-sm-6 home-row">
					<c:url var="detailsLink" value="details?code=${ park.parkCode }" />
						<div class="parkName"><a href="${ detailsLink }">
							<c:out value="${ park.parkName }" /></a>
							</div>
						<p class="parkInfo">
							Location:
							<c:out value="${ park.state }" />
						</p>
						<p class="parkInfo">
							<c:out value="${ park.surveyCount }" />
							National Park Geek<c:out value="${ park.surveyCount > 1 ? 's' : '' }"/> voted
							<c:out value="${ park.parkName }" />
							as their favorite!
						</p>
						<p  class="parkDescription">
							<c:out value="${ park.parkDescription }" />
						</p>
						<div class="icon">
								<a href="${ detailsLink }">
								<i class="fa fa-tree"></i> Learn More</a>
							</div>
					</div>
					<div class="col-sm-5 home-row">
						<c:url var="parkImg"
							value="img/parks/${ park.parkCode.toLowerCase() }.jpg" />
						
						<%-- <c:set var="code" value="${ park.parkCode }" /> --%>
						<a href="${ detailsLink }"><img class="park-image"
							src="${ parkImg }" /></a>
					</div>

				</c:forEach>
			</div>
		</div>

	</div>



</body>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />
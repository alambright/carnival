<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />


<body>

	<div class="container-fluid">
		<div class="content">

			<div class="row justify-content-center">
				<div class="col-sm-10">
					<h1 id="parkName">
						<c:out value="${ park.parkName }" />
					</h1>
					<br />
					<p id="parkQuote">
						"
						<c:out value="${ park.inspirationalQuote }" />
						" -
						<c:out value="${ park.inspirationalQuoteSource }" />
					</p>
					<br />


					<c:url var="parkImg"
						value="img/parks/${ park.parkCode.toLowerCase() }.jpg" />
					<img class="detailImg" src="${ parkImg }" /> <br />

				</div>
			</div>

		<div class="row justify-content-center">
				<div class="col-sm-5 containDescription">


					<p class="parkDescription">
						<c:out value="${ park.parkDescription }" />
					</p>
					<br />


				</div>
				<!-- col div -->

				<div class="col-sm-5">


					<table>
						<tr>
							<th>Park Details</th>
							<th></th>
						</tr>
						<tr>
							<td>Location:</td>
							<td><c:out value="${ park.state }" /></td>
						</tr>
						<tr>
							<td>Acreage</td>
							<td><c:out value="${ park.acreage }" /></td>
						</tr>
						<tr>
							<td>Elevation</td>
							<td><c:out value="${ park.elevationInFeet }" /></td>
						</tr>
						<tr>
							<td>Miles of Trails</td>
							<td><c:out value="${ park.milesOfTrail }" /></td>
						</tr>
						<tr>
							<td>Number of campsites:</td>
							<td><c:out value="${ park.numberOfCampsites }" /></td>
						</tr>
						<tr>
							<td>Climate:</td>
							<td><c:out value="${ park.climate }" /></td>
						</tr>
						<tr>
							<td>Founded in:</td>
							<td><c:out value="${ park.yearFounded }" /></td>
						</tr>
						<tr>
							<td>Annual Visitors:</td>
							<td><c:out value="${ park.annualVisitorCount }" /></td>
						</tr>
						<tr>
							<td>Number of animal Species:</td>
							<td><c:out value="${ park.numberOfAnimalSpecies }" /></td>
						</tr>
						<tr>
							<td>Entry Fee:</td>
							<td>$<c:out value="${ park.entryFee }" /></td>
						</tr>


					</table>


				</div>
				<!-- col div -->
			</div>
			<!-- row div -->


			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="row justify-content-center">

						<div class="col-sm-8">


							

							<c:set var="isCelcius" value="${ sessionScope.inCelcius }" />
							<c:url var="celciusFormAction"
								value="details?code=${ park.parkCode }"></c:url>
						
							<h2 class="weather-header">Five Day Weather Forcast</h2>

							<form action="${ celciusFormAction }" method="POST">
								<input type="radio" name="chooseCelcius" value="${ false }"
									<c:out value="${ isCelcius ? '' : 'checked' }"/>> 
									<p class="radio-text">Fahrenheit</p>
								<input type="radio" name="chooseCelcius" value="${ true }"
									<c:out value="${ isCelcius ? 'checked' : '' }"/>>
									<p class="radio-text">Celcius</p>
								<input class="formbox" type="submit" value="Convert Temperature" />
							</form>

						</div>
						<!-- col div -->
					</div>
					<!-- row div -->
				</div>
				<!-- panel heading -->

				<div class="panel-body">
					<div class="row justify-content-center">


						<c:forEach var="weather" items="${ weathers }">

							<div class="col-sm-2">

								<div class="weathertile">
									<h3>
										<c:out value="${ weather.dayOfWeek }" />
									</h3>
									<p class="forecast-text">
										<c:out value="${ weather.forecast.toUpperCase() }" />
									</p>
								
									<c:url var="weatherImg"
										value="img/weather/${ weather.weatherImgName }.png" />
									<img id="weatherImage" src="${ weatherImg }" />
									<p id="weatherParkName">
										<c:out value="${ park.parkName }" />
									</p>
									<p class="high">
										High:
										<c:out value="${ isCelcius ? weather.highInC : weather.high }" />
										<c:out value="${ isCelcius ? '° C' : '° F' }" />
									</p>
									<p class="low">
										Low:
										<c:out value="${ isCelcius ? weather.lowInC : weather.low }" />
										<c:out value="${ isCelcius ? '° C' : '° F' }" />
									</p>
									

									<div class="weatherPrep">
										<p>
											<c:out value="${ weather.forecastMessage }" />
										</p>
										<p>
											<c:out value="${ weather.temperatureMesage }" />
										</p>
									</div>
								</div>
								<!-- weather tile -->

							</div>
							<!--  col div -->
						</c:forEach>



					</div>
					<!-- row div -->
				</div>
				<!-- panel body -->

			</div>
			<!-- panel -->
		</div>
		<!--  content -->
	</div>
	<!--  container fluid -->
</body>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />
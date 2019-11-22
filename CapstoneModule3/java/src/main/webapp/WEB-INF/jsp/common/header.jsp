<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
        crossorigin="anonymous">
<!-- !! -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!-- !! -->



<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<c:url value="/css/site.css" var="cssHref" />
<link rel="stylesheet" href="${ cssHref }">
<meta charset="ISO-8859-1">
<title>National Park Enthusiast</title>
</head>
<header>
<c:url var="homeUrl" value="/" />
<c:url var="surveyUrl" value="survey" />
<c:url var="favoritesUrl" value="favorites" />

<c:url var="parkLogo" value="img/logo.png" />
<img id="logo" src="${ parkLogo }" />


<nav class="navbar navbar-inverse">
	
		<ul class="nav navbar-nav">
			<li><a class="navLink" href="${ homeUrl }">Home Page</a></li>
			<li><a class="navLink" href="${ surveyUrl }">Survey</a></li>
			<li><a class="navLink" href="${ favoritesUrl }">Top Parks</a></li>
		</ul>
	
</nav>
</header>


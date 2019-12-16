<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Anya
  Date: 13.12.2019
  Time: 2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="users" type="java.util.List<epam.entity.User>" scope="request"/>
<jsp:useBean id="tours" type="java.util.List<epam.entity.Tour>" scope="request"/>
<jsp:useBean id="orders" type="java.util.List<epam.entity.Order>" scope="request"/>
<jsp:useBean id="hotels" type="java.util.List<epam.entity.Hotel>" scope="request"/>
<jsp:useBean id="countries" type="java.util.List<epam.entity.Country>" scope="request"/>

<html>
<head>
    <title>Main</title>
</head>
<body>

<table border="1" width="40%" cellpadding="5">
    <caption>USERS</caption>
    <tr>
        <th>Name</th>
        <th>Password hash</th>
        <th>Passport</th>
        <th>Phone number</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.name}</td>
            <td>${user.passwordHash}</td>
            <td>${user.passport}</td>
            <td>${user.phoneNumber}</td>
        </tr>
    </c:forEach>
</table>

<table border="1" width="80%" cellpadding="5">
    <caption>TOURS</caption>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Departure</th>
        <th>Arrival</th>
        <th>Transport</th>
        <th>Type</th>
        <th>Price</th>
        <th>Image</th>
        <th>Countries</th>
        <th>Hotels</th>
    </tr>
    <c:forEach var="tour" items="${tours}">
        <tr>
            <td>${tour.name}</td>
            <td>${tour.description}</td>
            <td>${tour.departureTime}</td>
            <td>${tour.arrivalTime}</td>
            <td>${tour.transport}</td>
            <td>${tour.type}</td>
            <td>${tour.price}</td>
            <td>${tour.image}</td>
            <td>${tour.countryNames}</td>
            <td>${tour.hotelIds}</td>
        </tr>
    </c:forEach>
</table>

<table border="1" width="40%" cellpadding="5">
    <caption>ORDERS</caption>
    <tr>
        <th>ID</th>
        <th>User passport</th>
        <th>Tour id</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.userPassport}</td>
            <td>${order.tourId}</td>
        </tr>
    </c:forEach>
</table>

<table border="1" width="40%" cellpadding="5">
    <caption>HOTELS</caption>
    <tr>
        <th>Name</th>
        <th>Country</th>
        <th>Description</th>
        <th>Stars</th>
        <th>Web-site</th>
        <th>Address</th>
    </tr>
    <c:forEach var="hotel" items="${hotels}">
        <tr>
            <td>${hotel.name}</td>
            <td>${hotel.countryName}</td>
            <td>${hotel.description}</td>
            <td>${hotel.stars}</td>
            <td>${hotel.webSite}</td>
            <td>${hotel.address}</td>
        </tr>
    </c:forEach>
</table>


<table border="1" width="40%" cellpadding="5">
    <caption>Countries</caption>
    <tr>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <c:forEach var="country" items="${countries}">
        <tr>
            <td>${country.name}</td>
            <td>${country.description}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>

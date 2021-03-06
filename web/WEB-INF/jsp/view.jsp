<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.StringSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
        <h2>${type.title}</h2>
        <c:choose>
            <c:when test="${type == 'PERSONAL' || type =='OBJECTIVE'}">
                <%=((StringSection) section).getDescription()%>
            </c:when>
            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                    <ul>
                        <li>${item}</li>
                    </ul>
                </c:forEach>
            </c:when>
            <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                <c:forEach var="listOrganizations"
                           items="<%=((OrganizationSection) section).getListOrganizations()%>">
                    <c:forEach var="position" items="${listOrganizations.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <table>
                            <c:choose>
                                <c:when test="${empty listOrganizations.homePage.url}">
                                    <h3 style="text-align: center">${listOrganizations.homePage.name}</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3 style="text-align: center"><a href="${listOrganizations.homePage.url}">${listOrganizations.homePage.name}</a></h3>
                                </c:otherwise>
                            </c:choose>
                            <tr>
                                <td width="170"
                                    style="vertical-align: top"><%=DateUtil.format(position.getStartDate())%>
                                    - <%=DateUtil.format(position.getEndDate())%>
                                </td>
                                <td><b>${position.title}</b><br>${position.description}</td>
                            </tr>
                        </table>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

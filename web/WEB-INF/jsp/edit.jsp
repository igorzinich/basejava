<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required
                       pattern="[А-Яа-яa-zA-Z\s]{2,}"></dd>
        </dl>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=40 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type == 'PERSONAL' || type =='OBJECTIVE'}">
                    <input type="text" name="${type.name()}" size="108" value="<%=section%>">
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <textarea name="${type.name()}" cols="100"
                              rows="10"><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="organization"
                               items="<%=((OrganizationSection) section).getListOrganizations()%>" varStatus="counter">
                        <dl>
                            <dt>Наименование организации</dt>
                            <dd><input type="text" name="${type}" size="81" value="${organization.homePage.name}">
                        </dl>

                        <dl>
                            <dt>Сайт организации</dt>
                            <dd><input type="text" name="${type}url" size="81" value="${organization.homePage.url}">
                        </dl>
                        <br>

                        <div style="margin-left: 40px">
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                                <dl>
                                    <dt>Должность</dt>
                                    <dd><input type="text" name="${type}${counter.index}title" size="75"
                                               value="${position.title}">
                                </dl>

                                <dl>
                                    <dt>Дата начала</dt>
                                    <dd><input type="text" name="${type}${counter.index}startDate" size="75"
                                               value="<%=DateUtil.format(position.getStartDate())%>"
                                               placeholder="MM-YYYY">
                                </dl>

                                <dl>
                                    <dt>Дата окончания</dt>
                                    <dd><input type="text" name="${type}${counter.index}endDate" size="75"
                                               value="<%=DateUtil.format(position.getEndDate())%>"
                                               placeholder="MM-YYYY">
                                </dl>

                                <dl>
                                    <dt>Описание</dt>
                                    <dd><textarea name="${type}${counter.index}description" cols="70"
                                                  rows="5">${position.description}</textarea>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
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
<jsp:include page="fragments/header.jsp"></jsp:include>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <h3>Контакты:</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=HtmlUtil.ContactsToHtml(contactEntry.getKey(), contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
            <tr>
                <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((TextSection) section).getDescription()%>
                            </h3> <hr>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((TextSection) section).getDescription()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT'|| type=='QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getInfoList()%>">
                                    <li>${item}</li>
                                </c:forEach>

                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE'|| type=='EDUCATION'}">
                    <tr>
                        <td colspan="2">

                                <c:forEach var="orgs" items="<%=((OrganizationSection) section).getOrganizations()%>">
                                    <c:choose>
                                        <c:when test="${not empty orgs.homepage.url}">
                                            <a href="${orgs.homepage.url}">${orgs.homepage.name}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <h3>${orgs.homepage.name}</h3>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:forEach var="position" items="${orgs.positions}">
                                        <jsp:useBean id="position"
                                                     type="ru.javawebinar.basejava.model.Organization.Position"/>
                                        <tr>
                                        <td width="15%" style="vertical-align: top" ><%=DateUtil.dateForJsp(position.getStartDate())%> - <%=DateUtil.dateForJsp(position.getEndDate())%></td>
                                            <td><b>${position.title}</b><br>${position.description}</td>

                                        </tr>
                                    </c:forEach>

                                </c:forEach>

                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <%--</p>--%>
</section>

<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
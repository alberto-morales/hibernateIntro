<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="eu.albertomorales.hibernateIntro.model.Person"%>
<%@page import="eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Welcome</title>
</head>
<%
	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
  pageContext.setAttribute("myMessage", context.getBean("message"));

  GenericDAO<Person,Integer> personaDAO = (GenericDAO<Person,Integer>)context.getBean("personDAO");
  Integer numberOfPeople = personaDAO.getAll().size();
  pageContext.setAttribute("nPeople", ""+numberOfPeople);
%>

<c:out value="${myMessage.text}"/>

<body>
It's up & running, there are <c:out value="${nPeople}"/> people.
</body>
</html>
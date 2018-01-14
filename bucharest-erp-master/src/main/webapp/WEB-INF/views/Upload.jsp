<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
     "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring MVC - Hibernate File Upload to Database Demo</title>
</head>
<body>


 

	<div>
		<form:form method="POST" enctype="multipart/form-data"
			action="/bucharest-project/partners/upload-doc2"
			modelAttribute="FileForm">
			<form:input type="file" path="file" multiple="multiple" />
			<form:form method="POST" modelAttribute="DocumentForm">
				<table>
					<tr>
						<td>Document number</td>
						<td><form:input type="input" name="docNumber" path="docNumber" /></td>
					</tr>
					<tr>
						<td>Document name</td>
						<td><form:input type="input" name="docName" path="docName"/></td>
					</tr>
					<tr>
						<td>Master agreement</td>
						<td><form:input type="input" name="master" path="master" /></td>
					</tr>
					<tr>
						<td>Expiration Date</td>
						<td><form:input type="input" name="expDate" path="expDate" /></td>
					</tr>
					<tr>
						<td>Tags</td>
						<td><form:input type="input" name="tags" path="tags" /></td>
					</tr>
					<tr>
						<td>Comments</td>
						<td><form:input type="input" name="comment" path="comment" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Upload"></td>
					</tr>
					<input type="hidden" name="partnerId" value="3"></input>
				</table>

				<form:input style="visibility: hidden;" type="text"
					placeholder="created By" value="asdasd" path="createdBy"
					name="createdBy" id="createdBy" />
				<br></br>
				<form:input type="text" placeholder="created Date"
					style="visibility: hidden;" value="1993-03-03" path="createdDate"
					name="createdDate" id="createdDate" />
				<br></br>
				<form:input type="text" placeholder="updated By"
					style="visibility: hidden;" value="asdasd" path="updatedBy"
					name="updatedBy" id="updatedBy" />
				<br></br>
				<form:input type="text" placeholder="updated Date"
					style="visibility: hidden;" value="1993-03-03" path="updatedDate"
					name="updatedDate" id="updatedDate" />
				<br></br>
			</form:form>
		</form:form>

	</div>
	
	<a href="/gellallfiles">Get List Files</a>
	
	
	
	
</body>
</html>

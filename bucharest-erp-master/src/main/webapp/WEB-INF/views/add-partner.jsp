<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<c:import url="/WEB-INF/views/head.jsp" />
<script
	src="<c:url value="/resources/jQuery/jquery/jquery.placeholder.label.min.js" />"></script>

<title>Add ${type}</title>
<script>
	var partnerType = "${type}";
	var partnerTypeSingular = partnerType.substring(0, partnerType.length - 1);
	$(".partner-type").html(partnerTypeSingular);
</script>
</head>
<body>
	<!-- HEADER -->
	<c:import url="/WEB-INF/views/header.jsp" />
	<div class="content-wrapper toggled" id="wrapper">
		<!-- Sidebar menu -->
		<c:import url="/WEB-INF/views/sidebarMenu.jsp" />
		<!-- CONTAINER -->
		<div class="container-fluid main-container">
			<div class="row output-head ">
				<button id="backButton" class="back-btn text-color"><i
					class="fa fa-arrow-left" aria-hidden="true"></i> Back</button>
				<div class="page-title text-color">
					Add new <span class="partner-type"></span>
				</div>
			</div>
			<form:form class="register-form box"
				action="partners-list?type=${type}" method="POST"
				modelAttribute="addPartnerForm">
				<div class="row">
					<div class="col-md-4">
							<form:input type="text" class="form-control form-control-danger"
								maxlength="50" path="name" name="name" id="name"
								placeholder="*Name" />
							<div class="form-control-feedback">*Only letters, digits, "/" , "-"
								and "." are allowed</div>
					</div>
					<div class="col-md-4">
							<form:input type="text" class="form-control form-control-danger"
								placeholder="*Contact person" path="contactPerson"
								name="contactPerson" id="contactPerson" maxlength="50" />
							<div class="form-control-feedback">*Only letters, and "-" are allowed</div>
					</div>
					<div class="col-md-4">
							<form:input type="text" class="form-control form-control-danger"
								placeholder="J" path="jCode" name="jCode" id="jCode"
								maxlength="50" />
							<div class="form-control-feedback">*Only digits, letters and "/" are allowed</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
							<form:input type="text" class="form-control form-control-danger"
								placeholder="*VAT Number" maxlength="20" path="vatNumber"
								name="vatNumber" id="vatNumber" />
							<div class="form-control-feedback">*Only letters and digits are allowed</div>
					</div>
					<div class="col-md-4">
							<form:input type="text"
								class="form-control form-control-danger"
								placeholder="*Phone Number" path="phoneNumber"
								name="phoneNumber" id="phoneNumber" maxlength="50" />
							<div class="form-control-feedback">*Only digits are allowed</div>
					</div>
					<div class="col-md-4">
								<form:input type="text" class="form-control form-control-danger"
									placeholder="tags" path="tags" name="tags" id="tags"
									maxlength="100" />
								<div class="form-control-feedback">*Only letters, digits and any non word characters are allowed</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
								<form:input type="text" class="form-control form-control-danger"
									placeholder="*Address" path="address" name="address"
									id="address" maxlength="100" />
								<div class="form-control-feedback">*Only letters, digits and folowing charactetrs : "(.,'":@;/-#)" are allowed</div>
						</div>
						<div class="col-md-8">
								<form:textarea class="form-control form-control-danger"
									placeholder="Comments" path="comment" name="comment"
									id="comment" maxlength="200" rows="3" />
								<div class="form-control-feedback">*Only letters, digits and any non word characters are allowed</div>
						</div>
					</div>
					<button class="btn btn-primary" value="Register">
						Add <span class="partner-type"></span>
					</button>
					<div class="mandatory-fields">*Mandatory fields</div>
			</form:form>
		</div>
	</div>

	<script src="<c:url value="/resources/scripts/scripts.js" />"></script>
	<script src="<c:url value="/resources/scripts/inputsValidation.js" />"></script>
	<script>
	var partnerTypeSingular = partnerType.substring(0, partnerType.length - 1);
	$(".partner-type").html(partnerTypeSingular);
	</script>
</body>

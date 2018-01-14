<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Clients</title>
<c:import url="/WEB-INF/views/head.jsp" />
<script src="<c:url value="/resources/scripts/inputsValidation.js" />"></script>
		<script src="<c:url value="/resources/scripts/scripts.js" />"></script>
<link href="<c:url value="/resources/datatables/datatables.min.css" />" rel="stylesheet">
<script
	src="<c:url value="/resources/jQuery/jquery/jquery.placeholder.label.min.js" />"></script>
	
</head>
<script>
	var partnerType = "${type}";
</script>
<c:choose>
	  <c:when test="${partnerId}"><script>var partnerId = "${partnerId}";</script></c:when>
	  <c:otherwise><script>var partnerId = "";</script></c:otherwise>
</c:choose>
<body>
	<!-- HEADER -->
	<c:import url="/WEB-INF/views/header.jsp" />
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/partners.js" />"></script>
	<div class="content-wrapper toggled" id="wrapper">

		<!-- Sidebar menu -->
		<c:import url="/WEB-INF/views/sidebarMenu.jsp" />

		<!-- CONTAINER -->
		<div class="container-fluid main-container">
			<div class="row output-head ">
				<button id="backButton" class="back-btn text-color"><i
					class="fa fa-arrow-left" aria-hidden="true"></i> Back</button>
				<div class="page-title text-color">
					<span class="capitalize">${type}</span> list
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 col-md-12">
					<div class="box">
						<table id="partners-list"
							class="table table-striped table-bordered" cellspacing="0"
							width="100%">
							<thead>
								<tr>
									<th>Name</th>
									<th>VAT number</th>
									<th>J</th>
									<th>Contact person</th>
									<th>Phone number</th>
									<th class="head-action-holder"></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<div class="modal fade" id="partner-modal" role="dialog">
				<div class="modal-dialog modal-lg modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title"></h4>
						</div>
						<div class="modal-body">
							<div class="register-form box" id="editPartnerInputs">
								<div class="row">
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger" placeholder="*Name"
											maxlength="50" name="name" id="name" />
											<div class="form-control-feedback">*Only letters, digits, "/" , "-"
								and "." are allowed</div>
									</div>
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger"
											placeholder="*Contact person" name="contactPerson"
											id="contactPerson" maxlength="50" />
											<div class="form-control-feedback">*Only letters, and "-" are allowed</div>
									</div>
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger" placeholder="tags"
											name="tags" id="tags" maxlength="100" />
											<div class="form-control-feedback">*Only letters, digits and any non word characters are allowed</div>
											
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger"
											placeholder="*VAT Number" maxlength="20" name="vatNumber"
											id="vatNumber" />
											<div class="form-control-feedback">*Only letters and digits are allowed</div>
									</div>
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger"
											placeholder="*Phone Number" name="phoneNumber"
											id="phoneNumber" maxlength="50" />
											<div class="form-control-feedback">*Only digits are allowed</div>
									</div>
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger" placeholder="J"
											name="jCode" id="jCode" maxlength="50" />
											<div class="form-control-feedback">*Only digits, letters and "/" are allowed</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger" placeholder="*Address"
											name="address" id="address" maxlength="100" />
											<div class="form-control-feedback">*Only letters, digits and folowing charactetrs : "(.,'":@;/-#)" are allowed</div>
									</div>
									<div class="col-md-8">
										<textarea class="form-control form-control-danger" placeholder="Comments"
											name="comment" id="comment" maxlength="200" rows="3"></textarea>
											<div class="form-control-feedback">*Only letters, digits and any non word characters are allowed</div>
									</div>
								</div>
								<div class="mandatory-fields">*Mandatory fields</div>

							</div>
							<div class='text-center delete-content'>
								You are about to delete "<span class='partnerName'></span>". Are
								you sure?
							</div>
						</div>
						<div class="modal-footer">
							<button id="update-button" class="btn btn-info" value="Register">
								<i class="fa fa-pencil" aria-hidden="true"></i> Update
							</button>
							<button id="delete-partner-button" type="button" class="btn btn-danger">
								<i class="fa fa-trash-o" aria-hidden="true"></i> Yes, delete
							</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Cancel</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/dt-includes.jsp" />
		
</body>
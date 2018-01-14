<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

<title>View client Info</title>
<link href="<c:url value="/resources/datatables/datatables.min.css" />"
	rel="stylesheet">
<c:import url="/WEB-INF/views/head.jsp" />
<link
	href="<c:url value="/resources/jQuery/bootstrap/css/datepicker.css" />"
	rel="stylesheet">
<script type="text/javascript"
	src="<c:url value="/resources/jQuery/jquery/fileUpload.js" />"></script>
<script
	src="<c:url value="/resources/jQuery/jquery/jquery.placeholder.label.js" />"></script>
<script src="<c:url value="/resources/scripts/scripts.js" />"></script>
<script src="<c:url value="/resources/scripts/inputsValidation.js" />"></script>
<script
	src="<c:url value="/resources/jQuery/bootstrap/js/datepicker.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/partners.js" />"></script>
</head>

<body>
	<script>
		var partnerType = "${partnerType}";
		var partnerId = "${partner.partnerId}";
	</script>
	<!-- HEADER -->
	<c:import url="/WEB-INF/views/header.jsp" />

	<div class="content-wrapper toggled" id="wrapper">

		<!-- Sidebar menu -->
		<c:import url="/WEB-INF/views/sidebarMenu.jsp" />

		<!-- CONTAINER -->
		<div class="container-fluid main-container">
			<div class="row output-head ">
				<button id="backButton" class="back-btn text-color">
					<i class="fa fa-arrow-left" aria-hidden="true"></i> Back
				</button>
				<div class="page-title text-color">
					View <span class="partner-type"></span> info
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 col-md-3">
					<div class="box">
						<table class="table table-sm table-bordered table-hover">
							<p class="box-titles">
								<span class="uppercase partner-type"></span> DETAILS
							</p>
							<tbody>
								<tr>
									<th scope="row">Name</th>
									<td>${partner.name}</td>
								</tr>
								<tr>
									<th scope="row">Contact Person</th>
									<td>${partner.contactPerson}</td>
								</tr>
								<tr>
									<th scope="row">Phone Number</th>
									<td>${partner.phoneNumber}</td>
								</tr>
								<tr>
									<th scope="row">VAT Number</th>
									<td>${partner.vatNumber}</td>
								</tr>
								<tr>
									<th scope="row">Address</th>
									<td>${partner.address}</td>
								</tr>
								<tr>
									<th scope="row">J</th>
									<td>${partner.jCode}</td>
								</tr>
								<tr>
									<th scope="row">Tags</th>
									<td>${partner.tags}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="box">
						<p class="box-titles">COMMENTS</p>
						<p class="text-justify">${partner.comment}</p>
					</div>
				</div>
				<div class="col-sm-6 col-md-9">
					<div class="box">
						<p class="box-titles">
							<span class="uppercase partner-type"></span> DOCUMENTS
						</p>
						<table id="partner-documents"
							class="table table-striped table-bordered table-hover"
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th>Document Name</th>
									<th>Document Number</th>
									<th>Master</th>
									<th>Reminder</th>
									<th class="head-action-holder"></th>
								</tr>
							</thead>

						</table>
					</div>
				</div>
			</div>
			<div class="modal fade" id="documents-modal" role="dialog">
				<div class="modal-dialog modal-lg modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title"></h4>
						</div>
						<div class="modal-body">
							<div class="register-form box" id="addEditDocument">
								<div class="row">
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger"
											placeholder="*Document number" maxlength="50"
											name="docNumber" id="docNumber" path="docNumber" />
										<div class="form-control-feedback">
											Only letters, digits <b>"-./#"</b> are allowed
										</div>
									</div>
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger"
											placeholder="*Document Name" name="docName" id="docName"
											maxlength="50" path="docName" />
										<div class="form-control-feedback">
											Only letters, digits, and <b>"-/('"#)"</b> are allowed
										</div>
									</div>
									<div class="col-md-4">
										<input type="text" class="form-control form-control-danger"
											placeholder="Master Agreement" name="master" id="master"
											path="master" maxlength="100" />
										<div class="form-control-feedback">
											Only letters, digits and <b>"-(#/.)"</b> are allowed
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<label class="to-fix"
											style="position: absolute; cursor: initial; color: rgb(137, 137, 137); font-size: 16px; margin-left: 7px; padding-left: 5px; padding-right: 5px; background-color: rgb(255, 255, 255); margin-top: 0px;">Expiration
											Date</label> <input type="text"
											class="form-control form-control-danger datePickerInput"
											data-toggle="datepicker" maxlength="20" name="expDate"
											id="expDate" path="expDate" readonly='true' />
										<div class="form-control-feedback">Please set the expiration date</div>
									</div>
									<div class="col-md-4">
										<label class="to-fix"
											style="position: absolute; cursor: initial; color: rgb(137, 137, 137); font-size: 16px; margin-left: 7px; padding-left: 5px; padding-right: 5px; margin-top: 0px; background-color: rgb(255, 255, 255);">Reminder
											Date</label> <input type="text"
											class="form-control form-control-danger"
											data-toggle="datepicker" name="reminderDate"
											id="reminderDate" maxlength="20" path="reminderDate"
											readonly='true' />
										<div class="form-control-feedback">Only letters, "/" ,
											"-" and "." are allowed</div>
									</div>
									<div class="col-md-4">
										<input class="form-control form-control-danger"
											placeholder="Tags" name="tags" id="tags" maxlength="50"
											rows="3" path="tags"/>
										<div class="form-control-feedback">*Only letters, digits
											and any non word characters are allowed</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-8">
										<textarea class="form-control form-control-danger"
											placeholder="Comments" name="comment" id="comment"
											maxlength="200" rows="3" path="comment"></textarea>
										<div class="form-control-feedback">*Only letters, digits
											and any non word characters are allowed</div>
									</div>
									<div class="col-md-4">
									<input type="text" class="form-control form-control-danger"
											placeholder="Created Date" name="createdDate"
											id="createdDate" disabled maxlength="50" path="createdDate"
											readonly='true' />
										<div class="form-control-feedback">Only letters, "/" ,
											"-" and "." are allowed</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="uploadSection">
											  <button id="triggerInputFile" class="btn btn-primary uploadFileButton">
												<i class="fa fa-upload" aria-hidden="true"></i> Upload
											  </button>
											<input type="text"
												class="form-control docUploadDisplayName form-control-danger"
												disabled id="displayFileName" />
												<span></span>
											<button id="download-file" class="btn btn-outline-primary" type="button" title="Download document">
													<i class="fa fa-download" aria-hidden="true"></i> 
											</button>
											<div class="form-control-feedback">*Upload a file in any of the following formats: csv, txt, pdf , jpg, png, xls, doc, docx, odt, zip, rar, 7z</div>
										</div>
										<input id="fileName" class="upload" type="file" path="file"
											name="file" multiple="multiple" />
									</div>

								</div> 
								<input type="hidden" name="partnerId" value="${partnerId}" id="partnerId"  />
								<div class="mandatory-fields">*Mandatory fields</div>
							</div>
							<div class='text-center delete-doc'>
								You are about to delete "<span class='docName'></span>"
								document. Are you sure?
							</div>
						</div>
						<div class="modal-footer">
						<div class="loading"></div>
							<button class="btn btn-info" id="add-button" value="Add-edit">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> Add new
								document
							</button>
							<button class="btn btn-info" id="edit-button" value="Add-edit">
								<i class="fa fa-pencil" aria-hidden="true"></i> Edit document
							</button>
							<button id="delete-document-button" class="btn btn-danger"
								value="Register">
								<i class="fa fa-trash-o" aria-hidden="true"></i> Yes, Delete
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
</html>
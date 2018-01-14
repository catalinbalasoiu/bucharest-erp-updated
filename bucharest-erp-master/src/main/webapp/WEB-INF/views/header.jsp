
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top"
	id="mainNav">
	<div class="row">
		<div class="col-3">
			<div id="menu-button">
				<i class="fa fa-bars" aria-hidden="true"></i>
			</div>
		</div>
		<div class="col-6 text-center">
			<a class="navbar-brand" href="/bucharest-project"> <img
				class="ag-logo" src="<c:url value="/resources/imgs/ag-logo2.png" />" />
			</a>
		</div>
		<div class="col-md-3 col-12">
			<div class="username">
			<span>Hello Username</span> | <a href="#"> Log Out <i
				class="fa fa-sign-out" aria-hidden="true"></i></a>
				</div>
		</div>
	</div>
</nav>
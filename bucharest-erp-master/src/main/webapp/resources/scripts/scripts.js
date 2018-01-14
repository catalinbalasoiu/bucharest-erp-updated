$(document).ready(function() {
	//Open close the sidebar menu on click
	$("#menu-button").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
	
	//Hide the sidebar menu when window width is smaller than 1000px
	if($(window).innerWidth() <= 1000) {
		$("#wrapper").toggleClass("toggled");
	} else {
		//Hide the sidebar menu on certain pages
		var hideSideBarMenuPageList = {
				viewPartnerInfo : "view-partner-info",
		}
		for (item in hideSideBarMenuPageList) {
			if(window.location.href.search(hideSideBarMenuPageList[item]) !== -1) {
				$("#wrapper").toggleClass("toggled");
			}
		}
	}

	$('#backButton').on('click', function() {
	    window.history.back();
	})
});
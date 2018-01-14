$(document).ready(function() {
	//Implement the placeholder label floating
	$('input[placeholder], textarea[placeholder]').placeholderLabel();
	
	$('#addPartnerForm').on('submit', function(event){
    	event.preventDefault();
    	var passed = !inputsForAddingPartnersValidation();
    	if(passed) {
    		if(checkIfJCodeExists() && checkIfVatNumberExists()){
    			this.submit();
    		}
    	} else {
    		console.log("some validation failed");
    	}
    });
    
	 function checkIfVatNumberExists() {
		 var vatNumberFound;
		$.ajax({
         type: 'POST',
         url: 'verify-vatNumber',
         async: false,
         data: {vatNumber: $('#vatNumber').val() },
         success: function(availableVatNumbers) {
         	if(availableVatNumbers) {
         		//jCode already exists
         		$('#vatNumber').parent().addClass('has-danger');
				$('#vatNumber').css("border-color", "#d9534f");
				$('#vatNumber').next().html("*This VAT Number was alreay added.");
				$('#vatNumber').next().show();
				vatNumberFound =  false;
         	} else {
         		//JCode does not exist - adding document to database
         		$('#jCode').parent().removeClass('has-danger');
					$('#vatNumber').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#vatNumber').next().html("*Only letters and digits are allowed");
					$('#vatNumber').next().hide();
					vatNumberFound =  true;
         	}
         }
     });
		return vatNumberFound;
	 }
	function checkIfJCodeExists(){
		var jCodeFound;
		$.ajax({
            type: 'POST',
            url: 'verify-jCode',
            async: false,
            data: {jCode: $('#jCode').val() },
            success: function(availableJCodes) {
            	if(availableJCodes) {
            		//jCode already exists
            		$('#jCode').parent().addClass('has-danger');
					$('#jCode').css("border-color", "#d9534f");
					$('#jCode').next().html("*This J Code was alreay added.");
					$('#jCode').next().show();
					jCodeFound =  false;
            	} else {
            		//JCode does not exist - adding document to database
            		$('#jCode').parent().removeClass('has-danger');
					$('#jCode').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#jCode').next().html("*Only digits, letters and '/' are allowed");
					$('#jCode').next().hide();
            		jCodeFound =  true;
            	}
            }
        });
		return jCodeFound;
	}
	
	window.inputsForAddingPartnersValidation = function() {
		var notPassed = 0;
		for (item in partnersInputs) {
		   	if(!partnersInputs[item]()) {
		   		notPassed  += 1;
		   	}
		}
		return notPassed;
	}
	
	
	var partnersInputs = {
			name : function() {
				var regex = /^[^\s][\w\s-./]+$/g;
				var inputValue = $('#name').val();
				if(regex.test($('#name').val()) && inputValue.replace(/\s/g, '').length) {
					$('#name').parent().removeClass('has-danger');
					$('#name').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#name').next().hide();
					return true;
				} else {
					$('#name').parent().addClass('has-danger');
					$('#name').css("border-color", "#d9534f");
					$('#name').next().show();
					return false;
				}
			},
			contactPerson : function() {
				var regex = /^[^\s][a-zA-Z\s-]+$/g;
				var inputValue = $('#contactPerson').val();
				if(regex.test($('#contactPerson').val()) && inputValue.replace(/\s/g, '').length) {
					$('#contactPerson').parent().removeClass('has-danger');
					$('#contactPerson').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#contactPerson').next().hide();
					return true;
				} else {
					$('#contactPerson').parent().addClass('has-danger');
					$('#contactPerson').css("border-color", "#d9534f");
					$('#contactPerson').next().show();
					return false;
				}
			},
			jCode : function() {
				var regex = /^[\w\s/]*$/g;
				console.log($('#jCode').next())
				$('#jCode').next().html("*Only digits, letters and '/' are allowed");
				if(regex.test($('#jCode').val())) {
					$('#jCode').parent().removeClass('has-danger');
					$('#jCode').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#jCode').next().hide();
					return true;
				} else {
					$('#jCode').parent().addClass('has-danger');
					$('#jCode').css("border-color", "#d9534f");
					$('#jCode').next().show();
					return false;
				}
			},
			vatNumber : function() {
				var regex = /^[^\s][\w\s]+$/g;
				var inputValue = $('#vatNumber').val();
				$('#vatNumber').next().html("*Only letters and digits are allowed");
				if(regex.test($('#vatNumber').val()) && inputValue.replace(/\s/g, '').length) {
					$('#vatNumber').parent().removeClass('has-danger');
					$('#vatNumber').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#vatNumber').next().hide();
					return true;
				} else {
					$('#vatNumber').parent().addClass('has-danger');
					$('#vatNumber').css("border-color", "#d9534f");
					$('#vatNumber').next().show();
					return false;
				}
			},
			phoneNumber : function () {
				var regex = /^[^\sa-zA-Z][\d\s]*$/g;
				var inputValue = $('#phoneNumber').val();
				if(regex.test($('#phoneNumber').val()) && inputValue.replace(/\s/g, '').length) {
					$('#phoneNumber').parent().removeClass('has-danger');
					$('#phoneNumber').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#phoneNumber').next().hide();
					return true;
				} else {
					$('#phoneNumber').parent().addClass('has-danger');
					$('#phoneNumber').css("border-color", "#d9534f");
					$('#phoneNumber').next().show();
					return false;
				}
			},
			address : function() {
				var regex = /^[^\s][\w-\s(.,'":@;/#-(]+$/g;
				var inputValue = $('#address').val();
				if(regex.test($('#address').val()) && inputValue.replace(/\s/g, '').length) {
					$('#address').parent().removeClass('has-danger');
					$('#address').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#address').next().hide();
					return true;
				} else {
					$('#address').parent().addClass('has-danger');
					$('#address').css("border-color", "#d9534f");
					$('#address').next().show();
					return false;
				}
			},
			comment : function (){
				var regex = /^[\w-\s\W]*$/g;
				if (regex.test($('#comment').val())) {
					$('#comment').parent().removeClass('has-danger');
					$('#comment').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#comment').next().hide();
					return true;
				} else {
					$('#comment').parent().addClass('has-danger');
					$('#comment').css("border-color", "#d9534f");
					$('#comment').next().show();
					return false;
				}
			},
			tags : function () {
				var regex = /^[\w-\s\W]*$/g;
				if (regex.test($('#tags').val())) {
					$('#tags').parent().removeClass('has-danger');
					$('#tags').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#tags').next().hide();
					return true;
				} else {
					$('#tags').parent().addClass('has-danger');
					$('#tags').css("border-color", "#d9534f");
					$('#tags').next().show();
					return false;
				}
			}
	}
	
	//Documents inputs validation 
	window.inputsForAddingDocumentsValidation = function() {
		var notPassed = 0;
		for (item in documentsInputs) {
		   	if(!documentsInputs[item]()) {
		   		notPassed  += 1;
		   	}
		}
		return notPassed;
	}
	
	var documentsInputs = {
			docNumber : function() {
				var regex = /^[^\s][\w\s-./#]*$/g;
				var inputValue = $('#docNumber').val();
				if (regex.test($('#docNumber').val()) && inputValue.replace(/\s/g, '').length) {
					$('#docNumber').parent().removeClass('has-danger');
					$('#docNumber').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#docNumber').next().hide();
					return true;
				} else {
					$('#docNumber').parent().addClass('has-danger');
					$('#docNumber').css("border-color", "#d9534f");
					$('#docNumber').next().show();
					return false;
				}
			},
			docName : function() {
				var regex = /^[^\s][\w\s-"/'(#)]+$/g;
				var inputValue = $('#docName').val();
				if (regex.test($('#docName').val()) && inputValue.replace(/\s/g, '').length) {
					$('#docName').parent().removeClass('has-danger');
					$('#docName').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#docName').next().hide();
					return true;
				} else {
					$('#docName').parent().addClass('has-danger');
					$('#docName').css("border-color", "#d9534f");
					$('#docName').next().show();
					return false;
				}
			},
			master : function() {
				var regex = /^[\w\s-(#\/.)]*$/g;
				var inputValue = $('#master').val();
				$('#master').next().html("Only letters, digits and <b>\"-(#/.)\"</b> are allowed");
				if (regex.test($('#master').val())) {
					$('#master').parent().removeClass('has-danger');
					$('#master').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#master').next().hide();
					return true;
				} else {
					$('#master').parent().addClass('has-danger');
					$('#master').css("border-color", "#d9534f");
					$('#master').next().show();
					return false;
				}
			},
			comment : function (){
				var regex = /^[\w-\s\W]*$/g;
				if (regex.test($('#comment').val())) {
					$('#comment').parent().removeClass('has-danger');
					$('#comment').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#comment').next().hide();
					return true;
				} else {
					$('#comment').parent().addClass('has-danger');
					$('#comment').css("border-color", "#d9534f");
					$('#comment').next().show();
					return false;
				}
			},
			tags : function () {
				var regex = /^[\w-\s\W]*$/g;
				if (regex.test($('#tags').val())) {
					$('#tags').parent().removeClass('has-danger');
					$('#tags').css('border-color', 'rgba(0, 0, 0, 0.15)');
					$('#tags').next().hide();
					return true;
				} else {
					$('#tags').parent().addClass('has-danger');
					$('#tags').css("border-color", "#d9534f");
					$('#tags').next().show();
					return false;
				}
			},
			file: function() {
		        var validFileExtensions = [".csv", ".txt", ".pdf", ".jpg", ".png", ".xls", ".doc", ".docx", ".odt", ".zip", ".rar", ".7z"];
		        var fileName = $('#displayFileName').val();
		        if (fileName.length > 0) {
		            var foundFileExt = false;
		            for (var j = 0; j < validFileExtensions.length; j++) {
		                var curentExtension = validFileExtensions[j];
		                if (fileName.substr(fileName.length - curentExtension.length, curentExtension.length).toLowerCase() == curentExtension.toLowerCase()) {
		                    foundFileExt = true;
		                    break;
		                }
		            }
		            if (!foundFileExt) {
		            	$('#fileName').prev().addClass('has-danger');
		            	$('#fileName').prev().children(".form-control-feedback").show();
		                return false;
		            } else {
		            	$('#fileName').prev().removeClass('has-danger');
		            	$('#fileName').prev().children(".form-control-feedback").hide();
		               return true;
		            }
		        } else {
		        	$('#fileName').prev().addClass('has-danger');
		        	$('#fileName').prev().children(".form-control-feedback").show();
		        	return false;
		        }
			}
		
		}

});
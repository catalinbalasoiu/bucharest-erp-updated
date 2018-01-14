$(document).ready(function() {
	var partnerTypeSingular = partnerType.substring(0, partnerType.length - 1);
	$(".partner-type").html(partnerTypeSingular);
	var docJsonUrl = "get-docs-call?partnerId=" + partnerId;
	
	
	var urlToParse = location.search;  
	var result = parseQueryString(urlToParse); 
	if (result['doc-id']) {
		$.ajax({
			type: 'GET',
			url: docJsonUrl,
			success: function(data) {
				var theData = JSON.parse(data).data;
				for(var i=0; i<theData.length; i++) {
					for( item in theData[i]) {
						if(theData[i][item] === parseInt(result['doc-id'])) {
					           addEditDocument("Edit", theData[i]);
					            populateFormWithData(theData[i]);
					            $('#documents-modal').modal("show");
					    	}
						}
					}
				}
		});
	} else {
		// no doc-id variable present in url
	}
	
	// Initialization of client documents table
    var documents_table = $('#partner-documents').DataTable({
        "responsive": true,
        "language": {
            "emptyTable": '<div class="spinner">' +
                '<div class="rect1"></div>' +
                '<div class="rect2"></div>' +
                '<div class="rect3"></div>' +
                '<div class="rect4"></div>' +
                '<div class="rect5"></div>' +
                '</div>'
        },
        "order": [],
        "aaSorting": [],
        "ajax": docJsonUrl,
        "columns": [{
            "data": "docName"
        },
        {
            "data": "docNumber"
        },
        {
            "data": "master"
        },
        {
            "data": "reminderDate"
        },
        {
            "data": ""
        }
    ],
        "columnDefs": [{
            "targets": -1,
            "data": null,
            "defaultContent": "<div class='text-center action-buttons'>" +
                "<i class='fa fa-trash delete' aria-hidden='true'></i>" +
                "<i class='fa fa-download download' aria-hidden='true'></i>" +
                "</div>"
        }],
        "dom": 'Blftip',
        "buttons": [{
                "text": "<i class='fa fa-plus' aria-hidden='true'></i> Add new document",
                "action": function(e, dt, button, config) {
                	deleteFormValues();
                	addEditDocument("Add new");
                	 $('#documents-modal').modal("show");
                },
                "titleAttr": "Add new document"
            },
            {
            	"extend": 'csv',
            	"text" : "<i class='fa fa-file-text-o' aria-hidden='true'></i> CSV",
            	"titleAttr": "Export as CSV"
            }
        ],
        "createdRow": function( row, data, dataIndex ) {
          }
          
    });
    
    //Listener for the responsive version due to datatables dom changing
    $('#partner-documents tbody').on('click', 'tr.child td.child ul li:not(:last-child)', function(event) {
    	var docData = documents_table.row($(this)).data();
    	addEditDocument("Edit", docData);
        populateFormWithData(docData);
        $('#documents-modal').modal("show");
    });
    
    
    $('#partner-documents tbody').on('click', 'tr td:not(:last-child)', function(event) {
    	if(!($(window).innerWidth() <= 957 && $(this).attr('tabindex') === '0')) {
    		var docData = documents_table.row($(this)).data();
            addEditDocument("Edit", docData);
            populateFormWithData(docData);
            $('#documents-modal').modal("show");
    	}
        
    });
    
    $('#triggerInputFile').on('click', function() {
    	$('#fileName').click();
    });
    
    $("#fileName").change(function () {
    	$('#displayFileName').val($('#fileName').val().replace(/C:\\fakepath\\/i, ''));
	});
    
    $('#partner-documents tbody').on('click', '.delete', function() {
    	var data;
    	if($(window).innerWidth() <= 957 ) {
    		data = documents_table.row($(this)).data();
    	} else {
    		data = documents_table.row($(this).parents('tr')).data();
    	}
    	$('#documents-modal .modal-header .modal-title').html('<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> Delete document');
        $('#addEditDocument').hide();
        $('#add-button').hide();
        $('.delete-doc').show();
        $('.delete-doc').attr('docId', data.docId);
        $('.delete-doc').attr('reminderId', data.reminderId);
        $('.docName').html(data.docName);
        $('#edit-button').hide();
        $('#delete-document-button').show();
        $('.loading').hide();
        $('#documents-modal').modal("show");
    });
    
    /* ==== Trigger for deleting a document === */
    $("#delete-document-button").on('click', function() {
        	$.ajax({
                type: 'POST',
                url: 'delete-document',
                beforeSend:function() {
                	$('.loading').show();
                },
                data: {
                    'docId': parseInt($('.delete-doc').attr('docId')),
                    'reminderId': parseInt($('.delete-doc').attr('reminderId'))
                },
                success: function() {
                	documents_table.ajax.reload(function ( json ) {
                    	$('#documents-modal').modal("hide");
                    	$('.loading').hide();
                    });
                }
            });
        });
    
    /* ==== Triggers for downloading a document === */
    $('#partner-documents tbody').on('click', '.download', function() {
    	var data;
    	if($(window).innerWidth() <= 957 ) {
    		data = documents_table.row($(this)).data();
    	} else {
    		data = documents_table.row($(this).parents('tr')).data();
    	}
        window.location.href="download-file?docId=" + data.docId;  
    });
    
    $('#download-file').on('click', function() {
    	 window.location.href="download-file?docId=" + $('#addEditDocument').attr("docId");
    });
	
	$('#add-button').on('click', function() {
		var passed = !window.inputsForAddingDocumentsValidation();
		if(passed) {
			if(validateMaster()) {
				submitAddNewDocForm();
			} else {
				$('.loading').hide();
				console.log("master validation failed");
			}
		} else {
			console.log("some validation failed");
		}
	});
	
	/* ==== Trigger for updating a document === */
	$('#edit-button').on('click', function() {
		var passed = !window.inputsForAddingDocumentsValidation();
    	if(passed) {
    		submitEditDocForm();
    	} else {
    		console.log("some validation failed");
    	}
    });
	
	
	
	function addEditDocument(action, data) {
		$('#documents-modal .modal-header .modal-title').html(action + ' document');
        $('#addEditDocument').show();
        $('.loading').hide();
        $('.delete-doc').hide();
       
        if(action === "Edit") {
        	$('#add-edit-document').attr('action', 'edit-document?type=' +partnerType + '&partnerId=' + partnerId);
        	$('#add-button').hide();
        	$('#edit-button').show();
        	$('#download-file').show();
        	$('#createdDate').prev().show();
        	$('#createdDate').show();
        	$('#addEditDocument').attr("docId", data.docId);
        	$('#addEditDocument').attr("reminderId", data.reminderId);
        } else {
        	$('#add-edit-document').attr('action', 'add-document?type=' +partnerType + '&partnerId=' + partnerId);
        	$('#add-button').show();
        	$('#edit-button').hide();
        	$('#download-file').hide();
        	$('#createdDate').prev().hide();
        	$('#createdDate').hide();
        }
        $('#delete-document-button').hide();
	}
	
	 function getDocumentFormData(fileDocName) {
	    	var data = {
	    			partnerId: parseInt(partnerId),
	    			docName : $('#docName').val(),
	    			docNumber : $('#docNumber').val(),
	    			fileName : fileDocName,
	    			master: $('#master').val(),
	    			expDate : $('#expDate').val(),
	    			tags : $('#tags').val(),
	    			comment : $('#comment').val(),
	    			reminderDate : $('#reminderDate').val()
	    	}
	    	return data;
	    }
	 
	    function submitAddNewDocForm() {
	    	$('#fileName').simpleUpload("upload-file", {
				start: function(file){
					console.log("upload started");
				},
				success: function(fileName) {
					var inputsData = getDocumentFormData(fileName);
					$.ajax({
			        	url:"add-document",
			        	headers: { 
			                'Accept': 'application/json',
			                'Content-Type': 'application/json' 
			            },
			        	type: "POST",
						data: JSON.stringify(inputsData),
						success: function(docId) {
							documents_table.ajax.reload(function ( json ) {
		                    	$('#documents-modal').modal("hide");
		                    });
						},
						error: function(error){
							// upload failed
							$('.loading').hide();
							console.log("upload error: " + error.name + ": " + error.message);
						}
					});
				}
	    	});
	    }
	    
	    function editDocumentFormData(docId, fileDocName, reminderId, isNewDoc) {
	    	var data = {
	    			docId: docId,
	    			reminderId: parseInt(reminderId),
	    			docName : $('#docName').val(),
	    			docNumber : $('#docNumber').val(),
	    			master: $('#master').val(),
	    			expDate : $('#expDate').val(),
	    			fileName : fileDocName,
	    			tags : $('#tags').val(),
	    			comment : $('#comment').val(),
	    			reminderDate: $('#reminderDate').val(),
	    			isNewDoc : "False" 
	    	}
	    	if(isNewDoc) data.isNewDoc = "True";
	    	return data;
	    }
	    
		function submitEditDocForm(){
			$('.loading').show();
			//Verify if a file was added on the file input
			if($('#fileName').val()) {
		    	$('#fileName').simpleUpload("upload-file", {
		    		start: function(file){
						console.log("upload started");
					},
					success: function(fileName) {
						var inputsData = editDocumentFormData($('#addEditDocument').attr('docId'), fileName ,$('#addEditDocument').attr('reminderId'), "True");
						console.log("upload successful!");
						$.ajax({
				        	url:"edit-document",
				        	headers: { 
				                'Accept': 'application/json',
				                'Content-Type': 'application/json' 
				            },
				        	type: "POST",
							data: JSON.stringify(inputsData),
							success: function() {
								documents_table.ajax.reload(function ( json ) {
			                    	$('#documents-modal').modal("hide");
			                    });
							},
					error: function(error){
						console.log("upload error: " + error.name + ": " + error.message);
					}
							});
						}
		    	});
		    	} else {
		    		var inputsData = editDocumentFormData($('#addEditDocument').attr('docId'), $('#displayFileName').val(),$('#addEditDocument').attr('reminderId'));
		    		$.ajax({
			        	url:"edit-document",
			        	headers: { 
			                'Accept': 'application/json',
			                'Content-Type': 'application/json' 
			            },
			        	type: "POST",
						data: JSON.stringify(inputsData),
						success: function() {
							documents_table.ajax.reload(function ( json ) {
		                    	$('#documents-modal').modal("hide");
		                    });
						}
			        });
		    	}
			}
		
		function validateMaster () {
			$('.loading').show();
			var docNrAndMasterAreEqual = $('#docNumber').val() === $('#master').val();
			if(docNrAndMasterAreEqual) {
				//check if document nummber and master are equal 
				var masterFound;
				$.ajax({
	                type: 'POST',
	                url: 'verify-master',
	                async: false,
	                data: {master: $('#master').val()},
	                success: function(availableMasters) {
	                	if(availableMasters) {
	                		//master was already added
	                		$('#master').parent().addClass('has-danger');
	    					$('#master').css("border-color", "#d9534f");
	    					$('#master').next().html("This master was alreay added.");
	    					$('#master').next().show();
	                		masterFound = false;
	                	} else {
	                		//master does not exist - adding document to database
	                		$('#master').parent().removeClass('has-danger');
	    					$('#master').css('border-color', 'rgba(0, 0, 0, 0.15)');
	    					$('#master').next().html("Only letters, digits and <b>\"-(#/.)\"</b> are allowed");
	    					$('#master').next().hide();
	                		masterFound = true;
	                	}
	                }
	            })
	            return masterFound;
				//check if document nummber and master are not equal 
			} else {
				// If master input is empty, check if the document was already added
				// because if the master is empty it will get the document number value
				if($('#master').val() === "") {
					var masterFound;
					$.ajax({
		                type: 'POST',
		                url: 'verify-master',
		                async: false,
		                beforeSend: function() {
		                	$('.loading').show();
		                },
		                data: {master: $('#docNumber').val()},
		                success: function(availableMasters) {
		                	if(availableMasters) {
		                		$('#docNumber').parent().addClass('has-danger');
		    					$('#docNumber').css("border-color", "#d9534f");
		    					$('#docNumber').next().html("This document was alreay added.");
		    					$('#docNumber').next().show();
		                		masterFound = false;
		                	} else {
		                		$('#docNumber').parent().removeClass('has-danger');
		    					$('#docNumber').css('border-color', 'rgba(0, 0, 0, 0.15)');
		    					$('#docNumber').next().html("Only letters, digits and <b>\"-(#/.)\"</b> are allowed");
		    					$('#docNumber').next().hide();
		                		masterFound = true;
		                	}
		                }
		            });
					return masterFound;
					// If master value is different from the document value go ahead and verify 
					// that the document has a master to be attached to
				} else {
					var masterFound;
					$.ajax({
		                type: 'POST',
		                url: 'verify-master',
		                async: false,
		                beforeSend: function() {
		                	$('.loading').show();
		                },
		                data: {master: $('#master').val()},
		                success: function(availableMasters) {
		                	if(availableMasters) {
		                		// the document will be attached to the master 
		                		$('#master').parent().removeClass('has-danger');
		    					$('#master').css('border-color', 'rgba(0, 0, 0, 0.15)');
		    					$('#master').next().html("Only letters, digits and <b>\"-(#/.)\"</b> are allowed");
		    					$('#master').next().hide();
		                		masterFound = true;
		                	} else {
		                		// the document added has no master to be attached to
		                		$('#master').parent().addClass('has-danger');
		    					$('#master').css("border-color", "#d9534f");
		    					$('#master').next().html("This master does not exist. Please Add it first");
		    					$('#master').next().show();
		                		masterFound = false;
		                	}
		                }
		            });
					return masterFound;
				}
			}
			
		}
/*    ======================================================  Partners table  ======================================================    */			

    // Initialization of the clients datatable
    var jsonUrl = "get-partners-call?type=" + partnerType;
    var partners_table = $('#partners-list').DataTable({
        "responsive": true,
        "language": {
            "emptyTable": '<div class="spinner">' +
                '<div class="rect1"></div>' +
                '<div class="rect2"></div>' +
                '<div class="rect3"></div>' +
                '<div class="rect4"></div>' +
                '<div class="rect5"></div>' +
                '</div>'
        },
        "ajax": jsonUrl,
        "columns": [{
                "data": "name"
            },
            {
                "data": "vatNumber"
            },
            {
                "data": "jCode"
            },
            {
                "data": "contactPerson"
            },
            {
                "data": "phoneNumber"
            },
            {
                "data": ""
            }
        ],
        "columnDefs": [{
            "targets": -1,
            "data": null,
            "defaultContent": "<div class='text-center action-buttons'>" +
                "<i class='fa fa-pencil-square edit' aria-hidden='true' ></i>" +
                "<i class='fa fa-trash delete' aria-hidden='true'></i>" +
                "</div>"
        }],
        "dom": 'Blftip',
        "buttons": [{
                "text": "<i class='fa fa-plus' aria-hidden='true'></i> Add new " + partnerTypeSingular,
                "action": function(e, dt, button, config) {
                    window.location.href = "add-partner?type=" + partnerType;
                },
                "titleAttr": "Add new Partner"
            },
            {
            	"extend": 'csv',
            	"text" : "<i class='fa fa-file-text-o' aria-hidden='true'></i> CSV",
            	"titleAttr": "Export as CSV"
            }
        ]
    });

 
    // Catch the edit icon button on each partner in the table
    $('#partners-list tbody').on('click', '.edit', function() {
        var data = partners_table.row($(this).parents('tr')).data();
        // Show modal content for editing partner
        $('#partner-modal .modal-header .modal-title').html('<i class="fa fa-pencil" aria-hidden="true"></i> Edit ' + partnerTypeSingular);
        $('#editPartnerInputs').show();
        $('.delete-content').hide();
        $('#delete-partner-button').hide();
        $('#partner-modal').attr('partnerid', data.partnerId);
        $('#update-button').show();
        populateFormWithData(data);
        $('#partner-modal').modal("show");
    });
    
    /* ==== Trigger for updating a partner === */
    $('#update-button').on("click", function() {
    	var passed = !window.inputsForAddingPartnersValidation();
    	if(passed) {
    		submitEditPartnerForm();
    	} else {
    		console.log("some validation failed");
    	}
    });
    
    // Catch the delete icon button on each partner in the table
    $('#partners-list tbody').on('click', '.delete', function() {
    	var data = partners_table.row($(this).parents('tr')).data();
        // Show modal content for delete partner
        $('#partner-modal .modal-header .modal-title').html('<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> Delete ' + partnerTypeSingular);
        $('#editPartnerInputs').hide();
        $('.delete-content').show();
        $('#partner-modal .modal-body .partnerName').html(data.name);
        $('#delete-partner-button').show();
        $('#partner-modal').attr('partnerid', data.partnerId);
        $('#update-button').hide();
        $('#partner-modal').modal("show");
       
    });
    

    $('[data-toggle="datepicker"]').datepicker({
      autoHide: true,
      zIndex: 2048
    });

    /* ==== Trigger for deleting a partner === */
    $('#delete-partner-button').on("click", function() {
    	var partnerId = $('#partner-modal').attr('partnerid')
        $.ajax({
            type: 'POST',
            url: 'delete-partner',
            data: {
                'partnerId': partnerId
            },
            success: function() {
                partners_table.ajax.reload();
                $('#partner-modal').modal("hide");
            }
        });
    })
    
    $('#partners-list tbody').on( 'click', 'td', function () {
        if(partners_table.cell( this ).data().search("fa-pencil-square edit") === -1) {
        	var partnerId = partners_table.row($(this).parents('tr')).data().partnerId;
        	var urlPath = 'view-partner-info?type=' + partnerType + '&partnerId=' + partnerId;
    		document.location.href = urlPath;
        }
    });
    
    function submitEditPartnerForm() {
    	var inputsData = renderPartnerFormData($('#partner-modal').attr('partnerid'));
        $.ajax({
        	url:"update-partner",
        	headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
        	type: "POST",
			data: JSON.stringify(inputsData),
            success: function() {
               partners_table.ajax.reload();
                $('#partner-modal').modal("hide");
            }
        })
    }
    
    function renderPartnerFormData(partnerId) {
    	var data = {
    			partnerId: partnerId,
    			name : $('#name').val(),
    			vatNumber : $('#vatNumber').val(),
    			contactPerson: $('#contactPerson').val(),
    			jCode : $('#jCode').val(),
    			phoneNumber : $('#phoneNumber').val(),
    			tags : $('#tags').val(),
    			comment : $('#comment').val(),
    			address : $('#address').val()
    	}
    	return data;
    }
    
    function populateFormWithData(rowData) {
		 deleteFormValues();
	        for (item in rowData) {
	            if(item === "comment"){
	            	/* $('textarea#' + item).trigger( "click" ); */
	            	$('textarea#' + item).prev().css("margin-top", "0px");
	            	$('textarea#' + item).val(rowData[item]);
	            }else if(item === "fileName"){
	            	$('input#' + item).prev().css("margin-top", "0px")
	            	$('#displayFileName').val(rowData[item]);
	            }else {
	            	$('input#' + item).prev().css("margin-top", "0px");
	            	$('input#' + item).val(rowData[item]);
	            }
	        }
	    }
	 
	 function deleteFormValues() {
	       $('input.form-control').each(function(index, item){
	    	   $(item).val("");
	    	   $(item).parent().removeClass('has-danger');
	    	   $(item).css('border-color', 'rgba(0, 0, 0, 0.15)');
	    	   $(item).next().hide();
	       })
	       $('textarea.form-control').val("");
	       $('textarea.form-control').prev().css("margin-top", "18px");
	       $('input.form-control').prev().css("margin-top", "18px");
	       $('#triggerInputFile').css('margin-top', '0px');
	     
	       // TODO: fix this
	  	 $('label.to-fix').css('margin-top','0px')
	    }
 
	   $('#expDate').datepicker({
	    	format: "MM/dd/yyyy",
	        autoHide: true,
	        zIndex: 2048,
	        
	      });
	    $('#reminder').datepicker({
	        autoHide: true,
	        zIndex: 2048,
	        
	      });	
				
		function parseQueryString (url) {
			var urlParams = {};
			url.replace(
					new RegExp("([^?=&]+)(=([^&]*))?", "g"),
					function($0, $1, $2, $3) {
						urlParams[$1] = $3;
					}
			);
			
			return urlParams;
		}
		
		
});
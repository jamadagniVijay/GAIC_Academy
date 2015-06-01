$(document).ready(function() {
	/*var indexTable  = $('#indexPage,#example').DataTable( {
		"ajax": 'data.txt',
		"paging": false,
		"bInfo":false,
		"columnDefs": [ {
			"targets": 3,
			"data": null,
			"defaultContent": "<a href=\"\"\" style=\" text-decoration:underline\">Request</a>"
		} ]
	} );*/
	$('input, textarea').placeholder();
	//TABLES IN ALL PAGES
	var indexTable = null;
	var myRequests = null;
	var myActiveTrain = null;
	var allTrain = null;
	var createdTrainings = null;
	var reqForTrain = null;
	var getAllTrainings = null;
	
	var cancelTrainRequestId = null;
	var trainingName = null;

	//DESCRIPTION OF ALL TABLES
	indexTable = $('#indexPage').DataTable({
		"sAjaxSource": "academyServices/myServices/getRelaventTrainings/" + empCode,
		"sAjaxDataProp": "",
		"paging": false,
		"bInfo": false,
		"sEmptyTable": "No Rows",
		"columnDefs": [{
			"aTargets": [4],
			"data": null
		}],
		"aoColumns": [{
			"mData": "Category",
			'sWidth': '25%'
		}, {
			"mData": "Name",
			'sWidth': '45%'
		}, {
			"mData": "Coordinators"
		}, {
			"mRender": function(data, type, full) {
				//console.log(Boolean(full['InProgress']));
				var isInProgress = full['InProgress'] === "true";
				var isRequested = full['IsRequested'] === "true";
				var isAttendee = full['IsAttendee'] === "true";

				if (isRequested) {
					if (isInProgress) {
						//MsgBox "Show Not mapped"
						result = '<span class="label label-danger">Pending</span>';
					} else {
						//MsgBox "Show Requested"
						result = '<span class="label label-warning">Requested</span>';
					}
				} else if (isAttendee) {
					if (isInProgress) {
						//MsgBox "Show mapped"
						result = '<span class="label label-success">Attendee</span>';

					} else {
						//MsgBox "Show Attendee"
						result = '<span class="label label-info">On Going</span>';
					}
				} else if (isInProgress) {
					//MsgBox "Show InProgress"
					result = '<span class="label label-info">On Going</span>';
				} else {
					//MsgBox "Show Request"
					result = '<a href="academyServices/myServices/requestTrainId/' + full['TrainId'] + '"><code>Request</code></a>';
				}

				return result;
			}
		}, {
			"mData": "InProgress",
			"visible": false
		}, {
			"mData": "IsRequested",
			"visible": false
		}, {
			"mData": "IsAttendee",
			"visible": false
		}, {
			"mData": "TrainId",
			"visible": false
		}],
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {

		}
	});


	/*$('#indexPage tbody').on( 'click', 'tr', function () {
	    var rowData = indexTable.row( this ).data();
	    console.log($("td:eq(3)", this).text()); 
	 } );*/


	myRequests = $('#myRequests').DataTable({
		"paging": false,
		"bInfo": false,
		"bSort": false,
		"aaSorting": [],
		"scrollY": "170px",
		"scrollCollapse": true,
		"sEmptyTable": "No Requests yet",
		"sAjaxSource": "academyServices/myServices/getMyRequests/" + empCode,
		"sAjaxDataProp": "",
		"autoWidth": false,
		"aoColumns": [{
			"mData": "myTrainings",
			'sWidth': '90%'
		},
		{
			"mRender": function(data, type, full) {
				return '<span class="glyphicon glyphicon-remove removeRequest" aria-hidden="true" title="click to cancel"></span>'
			}
		}],
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			// Cell click
			$('td span', nRow).on('click', function() {
				cancelTrainRequestId = aData['trainId'];
				trainingName = aData['myTrainings'];
			});

		}
	});
	$('#myRequests').on('click', 'span', function(e) {
		e.preventDefault();
		console.log("this is the trainingId "+cancelTrainRequestId);
		$.ajax({
			type: 'get',
			cache: false,
			url: "academyServices/myServices/cancelMyRequestedTraining/"+cancelTrainRequestId,
			success: function(data) {
				//console.log(data);
				if (data == "sesssionTimeOut") {
					displaySessionTimeOut();

				} else {
					displayToast("Cancelled : " + trainingName);
					indexTable.ajax.reload();
					myRequests.ajax.reload();
				}
			}
		});
		
		});
	myActiveTrain = $('#myActiveTrain').DataTable({
		"paging": false,
		"bInfo": false,
		"bSort": false,
		"aaSorting": [],
		"scrollY": "145px",
		"sEmptyTable": "No trainings scheduled",
		"scrollCollapse": true,
		"sAjaxSource": "academyServices/myServices/getMyActive/" + empCode,
		"sAjaxDataProp": "",
		"aoColumns": [{
			"mData": "name"
		}]
	});

	allTrain = $('#getAllActiveTrainings').DataTable({
		"paging": false,
		"bInfo": false,
		"sAjaxSource": "academyServices/myServices/getAllActiveTrainings",
		"sAjaxDataProp": "",
		"sEmptyTable": "No Trainings",
		"aoColumns": [{
			"mData": "name"
		}, {
			"mData": "coordinators"
		}, {
			"mData": "startDt"
		}, {
			"mData": "endDt"
		}, {
			"mData": "trainers"
		}, {
			"mData": "enroll_pending"
		}]
	});
	var TrainId = null;
	var initalCellValue = null;
	defaultEditable = {
			tooltip: 'Click to edit',
			type: 'text',
			cssclass: 'paddingMore',
			onblur: 'submit'
	};

	createdTrainings = $('#createdTrainings').dataTable({
		"paging": false,
		"bInfo": false,
		"sEmptyTable": "The trainings are yet to be created",
		/*"bSort": false,
		"aaSorting": [],*/
		"sAjaxSource": "academyServices/myServices/getAllTrainings",
		"sAjaxDataProp": "",
		"columnDefs": [{
			"aTargets": [3],
			"data": null
		}],
		"aoColumns": [{
			"mData": "Category"
		}, {
			"mData": "Name"
		}, {
			"mData": "Coordinators"
		}, {
			"mRender": function(data, type, full) {
				return '<a href="" class="deleteRow">Delete</a>'
				/*academyServices/myServices/deleteThisTraining?trainId='+full['TrainId']+'" class="table-action-deletelink*/
				;
			}
		}, {
			"mData": "TrainId",
			"visible": false
		}],
		"fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			// Cell click
			$('td', nRow).on('click', function() {
				TrainId = aData['TrainId'];
				initalCellValue = $(this).text();
			});

		}
	});
	$('#createNewBtn').on('click', function(e) {
		e.preventDefault();
		var txtCategory = $('#category').val();
		var txtTraining = $('#training').val();
		var txtCoordinators = $('#coordinator').val();
		var fieldsAreValid = validateField(txtCategory);

		fieldsAreValid = validateField(txtTraining) && fieldsAreValid;

		fieldsAreValid = validateField(txtCoordinators) && fieldsAreValid;


		//alert(txtCategory+" "+txtTraining+" "+txtCoordinators);
		//alert(fieldsAreValid);

		if (fieldsAreValid) {
			//console.log("academyServices/myServices/createThisTraining?txtCategory="+txtCategory+"&txtTraining="+txtTraining+"&txtCoordinators="+txtCoordinators);
			$.ajax({
				type: 'get',
				url: "academyServices/myServices/createThisTraining?txtCategory=" + txtCategory + "&txtTraining=" + txtTraining + "&txtCoordinators=" + txtCoordinators,
				success: function(data) {
					console.log(data);
					if (data == "sesssionTimeOut") {
						displaySessionTimeOut();

					} else {
						if (data == "insertedTraining") {
							//displayToast("Created the training "+txtTraining);
							swal({
								title: "Created!",
								html: "<h3 ><span class='label label-default'>Training<span></h3><br/><span class='label label-success'>" + txtTraining + "</span>",
								type: "success",
								timer: 2000,
								showConfirmButton: false
							});
							$('#category').val('');
							$('#training').val('');
							$('#coordinator').val('');
							createdTrainings.api().ajax.reload();
						} else {
							alert('Please check/correct "Category" or "Training" or "Coordinator"');
						}
					}
				}
			});

		} else {
			alert('Please check/correct "Category" or "Training" or "Coordinator"');
		}
	});

	createdTrainings.makeEditable({
		sUpdateURL: function(value, settings) {
			var sentObject = {};
			var columnPosition = createdTrainings.fnGetPosition(this)[1];
			var columnId = createdTrainings.fnGetPosition(this)[2];
			var sColumnTitle = createdTrainings.fnSettings().aoColumns[columnId].sTitle;
			sentObject["TrainId"] = TrainId;
			sentObject["columnId"] = columnId;
			sentObject["sColumnTitle"] = sColumnTitle;
			sentObject["initalCellValue"] = initalCellValue;
			sentObject["changedValue"] = value;
			$.ajax({
				type: "POST",
				url: "academyServices/myServices/editThisTraining",
				data: "sentObj=" + JSON.stringify(sentObject),
				success: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus, errorThrown);
					if (jqXHR === "sesssionTimeOut") {
						//Session has Expired,redirect to first page
						displaySessionTimeOut();
					} else {
						//Other Exceptions/Errors
						displayToast("edited " + sColumnTitle + " from " + initalCellValue + " to " + value);
						console.log(jqXHR);
					}
				}
			});
			return value;
		},
		"aoColumns": [
		              $.extend({}, defaultEditable),
		              $.extend({}, defaultEditable),
		              $.extend({}, defaultEditable),
		              null
		              ]
	});

	$('#createdTrainings').on('click', 'a.deleteRow', function(e) {
		e.preventDefault();

		console.log(TrainId);
		var thisRowText = jQuery.makeArray($(this).parent().parent().find('td'));
		swal({
			title: "Are you sure to delete the training?",
			html: "<h3><span class='label label-default'>Training</span></h3>" + thisRowText[1].innerHTML + "<br\><h3><span class='label label-default'>Corodinated by</span></h3>" + thisRowText[2].innerHTML,
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#DD6B55",
			confirmButtonText: "Yes, delete it!",
			closeOnConfirm: true
		},
		function() {
			var sentObject = {}
			sentObject["TrainId"] = TrainId;
			$.ajax({
				type: "POST",
				url: "academyServices/myServices/deleteThisTraining",
				data: "sentObj=" + JSON.stringify(sentObject),
				success: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus, errorThrown);
					if (jqXHR === "sesssionTimeOut") {
						//Session has Expired,redirect to first page
						/*swal("Sorry session timed out", " left me too long ")*/
						displaySessionTimeOut();
					} else {
						//Other Exceptions/Errors
						swal({
							title: "Deleted!",
							html: "<h3 ><span class='label label-default'>Training<span></h3><br/><span class='label label-success'>" + thisRowText[1].innerHTML + "</span>",
							type: "success",
							timer: 2000,
							showConfirmButton: false
						})
						//displayToast("Deleted training :"+thisRowText[1].innerHTML);
						createdTrainings.api().ajax.reload();
						console.log(jqXHR);
					}
				}
			})

		});

	});
	//click handling functions
	$('#indexPage,#createdTrainings,#manageAllActiveTrainings').on('click', 'tbody td a', function(e) {
		e.preventDefault();
	});


	$("#indexPage tbody").delegate("td", "click", function() {
		var requestHref = $("a:eq(0)", this).attr('href');
		var thisRowText = jQuery.makeArray($(this).parent().find('td'));
		if (!(typeof requestHref == "undefined")) {
			$(this).html('<span class="label label-warning">Requested</span>');
			$.ajax({
				type: "POST",
				url: requestHref,
				success: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus, errorThrown);
					if (jqXHR === "sesssionTimeOut") {
						//Session has Expired,redirect to first page
						/*swal("Sorry session timed out", " left me too long ")*/
						displaySessionTimeOut();
					} else {

						/*swal({  title: "Requested",
							html:true,
							text: "<h4 ><span class='label label-success'>"+thisRowText[1].innerHTML+"<span></h4>",   
							type: "success",
							timer: 3000,
							showConfirmButton: true

						})*/
						displayToast("Requested : " + thisRowText[1].innerHTML);
						indexTable.ajax.reload();
						myRequests.ajax.reload();
					}
				}
			})
		}
	});
	allTrain = $('#manageAllActiveTrainings').DataTable({
		"paging": false,
		"bInfo": false,
		"sAjaxSource": "academyServices/myServices/getAllActiveTrainings",
		"sAjaxDataProp": "",
		"sEmptyTable": "No Trainings",
		"aoColumns": [{
			"mData": "name"
		}, {
			"mData": "coordinators"
		}, {
			"mData": "startDt"
		}, {
			"mData": "endDt"
		}, {
			"mData": "trainers"
		}, {
			"mData": "enroll_pending"
		}, {
			"mRender": function(data, type, full) {
				TrainId = full['trainId'];
				if (full['isCancelled'] == 'false') {
					return '<a href="" class="deleteRow">Cancel</a><span class="TrainId" style="display:none">'+TrainId+'</span>';
				}
				if (full['isCancelled']) {
					return '<a href="" class="deleteRow">Reschedule</a><span class="TrainId" style="display:none">'+TrainId+'</span>';
				}
			}
		}, ]
	});

	$('#manageAllActiveTrainings tbody').on('click', 'a', function() {
		var canOrSec = $(this).text();
		var thisRowText = jQuery.makeArray($(this).parent().parent().find('td'));		

		var sentObject = {};

		var startDate = thisRowText[2].innerHTML.split(' ')[0]+'&#32;'+thisRowText[2].innerHTML.split(' ')[1];

		var endDate =   thisRowText[3].innerHTML.split(' ')[0]+'&#32;'+thisRowText[3].innerHTML.split(' ')[1];

		sentObject['TrainId'] = $(this).parent().find('.TrainId').text();
		console.log($(this).parent().find('.TrainId').text());
		switch (canOrSec) {
		case 'Cancel':{

			sentObject['StartDt'] = startDate.replace('&#32;', ' ');
			sentObject['EndDt'] = endDate.replace('&#32;', ' ');
			sentObject['isCancelled'] = 'true';
			manageSchedule(sentObject, canOrSec,thisRowText);
			break;
		}
		case 'Reschedule':{
			swal({
				title : 'Reschedule Training',
				html : 	'<div class="col-lg-6 addTrainings"><input id="startDate" type="text" class="form-control custom-form-control" placeholder="Start Date" value='+startDate+'></div>'+
				'<div class="col-lg-6 addTrainings"><input id="endDate" type="text" class="form-control custom-form-control" placeholder="End Date" value='+endDate+'></div>',
				showCancelButton : true,
				closeOnConfirm : false
			}, function(isConfirm) {
				if(isConfirm)
					{
						swal.disableButtons();
						if (($('#startDate').val() === false)||($('#endDate').val() === false)) 
							return false;
						if (($('#startDate').val() === "")||($('#endDate').val() === "")) {
							swal.showInputError("You need to write something!");    
							return false   
						}
						
						sentObject['StartDt'] = $('#startDate').val();
						sentObject['EndDt'] = $('#endDate').val();
						sentObject['isCancelled'] = 'false';
						
						manageSchedule(sentObject,canOrSec, thisRowText);
					}
				
			});

			break;
		}
		default:{break;}
		break;
		}
		console.log(sentObject);
		


	});
	function manageSchedule(sentObject, canOrSec,thisRowText)
	{
		$.ajax({
			type: "POST",
			url: 'academyServices/myServices/manageTrainingSchedule',
			data: "sentObj=" + JSON.stringify(sentObject),
			success: function(jqXHR, textStatus, errorThrown) {
				//console.log(textStatus, errorThrown);
				if (jqXHR === "sesssionTimeOut") {
					//Session has Expired,redirect to first page
					swal("Sorry session timed out", " left me too long ")
					displaySessionTimeOut();
				} else {

					switch (canOrSec) {
					case 'Cancel':{
						displayToast(canOrSec+"ed : "+thisRowText[0].innerHTML);
						break;
					}
					case 'Reschedule':{
						swal('Rescheduled');
						//displayToast(canOrSec+"d : "+thisRowText[0].innerHTML);
						break;
					}
					default:{break;}

					}
					allTrain.ajax.reload();
				}
			}
		});
	}
	$('#reqForTrain').hide();
	var clicked = false;
	getAllTrainings = $("#Training").autocomplete({
		minLength: 2,
		source: function(req, response) {
			$.ajax({
				url: "academyServices/myServices/getAllTrainings",
				dataType: "json",
				success: function(data) {
					var re = $.ui.autocomplete.escapeRegex(req.term);
					var matcher = new RegExp("^" + re, "i");
					response($.grep(data, function(item) {
						return matcher.test(item.Name);
					}));
				}
			});
		},
		select: function(event, ui) {
			event.preventDefault();
			$('#Training').val(ui.item.Name);
			$('#trainingId').val(ui.item.TrainId);
		}
	});
	if($("#Training").length!=0)
	{
		getAllTrainings.data("ui-autocomplete")._renderItem = function(ul, item)
		{
			return $("<li></li>").append(item.TrainId + "-" + item.Name).appendTo(ul);
		}
	}
	$('#Training').focus(function() {
		$('#reqForTrain').hide();
		$('#trainingId').val('');
	});
	$('#getRequests').click(function() {
		if ($('#trainingId').val() != '') {
			console.log($('#trainingId').val());
			if (clicked == true) {
				reqForTrain.ajax.url("academyServices/myServices/getRelaventRequests/" + $('#trainingId').val()).load(function() {
					$('#reqForTrain').show();
					$('#reqCount').text(reqForTrain.rows().data().length);
				});
			} else {
				reqForTrain = $('#reqForTrain').DataTable({
					"retrieve": true,
					"paging": false,
					"bInfo": false,
					"bSort": false,
					"aaSorting": [],
					"oLanguage": {
						"sZeroRecords": 'wait',
						"sEmptyTable": '<div class="alert alert-danger" role="alert"> <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>  <span class="sr-only">Error:</span>  No request for this training</div>'
					},
					"sAjaxSource": "academyServices/myServices/getRelaventRequests/" + $('#trainingId').val(),
					"sAjaxDataProp": "",
					"initComplete": function(settings, json) {
						$('#reqCount').text(reqForTrain.rows().data().length);
					},
					"aoColumns": [{
						"mData": "Category"
					}, {
						"mData": "Trainings"
					}, {
						"mData": "Coordinators"
					}, {
						"mData": "RequestedBy"
					}, {
						"mData": "RequestedOn"
					}]
				});
				$('#reqForTrain').show();
			}
			clicked = true;
		} else {
			displayToast('select trainings from the list');
		}
	});

	/*if('asd'=='')
	{
		createdTrainings.row.add({
			"category":$('#category').val(),
			"name": $('#training').val(),
			"coordinators":$('#coordinator').val()
		}).draw();
	//ajaxCall
		}*/
	$('#endDate,#startDate').datetimepicker({
		format: 'd-m-Y H:i:00',
		closeOnDateSelect: true
	});

	function validateField(value) {
		//alert(value.search(/[a-zA-z0-9]/));
		if (value !== "") {
			return true;
		}
		return false;
	}

	$('#customSearchFilter').keyup(function() {
		if (typeof allTrain !== 'undefined') {
			allTrain.search($(this).val()).draw();
		}
		if (typeof indexTable !== 'undefined') {
			indexTable.search($(this).val()).draw();
		}
		if (typeof createdTrainings !== 'undefined') {
			createdTrainings.api().search($(this).val()).draw();
		}
	});

	function displaySessionTimeOut() {
		swal({
			title: "Sorry session timed out!",
			html: "<h4 ><span class='label label-danger'>left me too long <span></h4>",
			type: "success",
			timer: 3000,
			showConfirmButton: false
		})
		window.location.href = "first.jsp";
	}

	function displayToast(whatToShow) {
		$('.error').text('');
		$('.error').text(whatToShow);
		$('.error').stop().fadeIn(400).delay(3000).fadeOut(400); //fade out
	}
});
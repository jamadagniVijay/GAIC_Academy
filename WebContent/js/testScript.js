
$(document).ready(function () {createdTrainings = $('#createdTrainings').DataTable({
		"paging": false,
		"bInfo":false,
		/*"bSort": false,
		"aaSorting": [],*/
		"sAjaxSource":"academyServices/myServices/getAllTrainings",
		"sAjaxDataProp": "",
		"columnDefs": [ {
			"aTargets": [3],
			"data": null
		} ],
		"aoColumns": [
		              {"mData": "Category"},
		              {"mData": "Name"},
		              {"mData": "Coordinators"},
		              {"mRender": function (data, type, full) 
		            	  {
		            	  return '<a href="" id="">Edit</a > | <a href="" class="deleteRow">Delete</a>'
		            	  /*academyServices/myServices/deleteThisTraining?trainId='+full['TrainId']+'" class="table-action-deletelink*/;
		            	  }
		              },
		              {"mData": "TrainId","visible":false}
		              ]
	});
	
	//click handling functions
	$('#indexPage,#createdTrainings').on('click', 'tbody td a' , function (e) {
		e.preventDefault();
	});
} );


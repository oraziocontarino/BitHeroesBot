var SET_COORDS_REQUEST = "SET_COORDS_REQUEST";
var SET_MISSION_REQUEST = "SET_MISSION_REQUEST";
var SET_RAID_REQUEST = "SET_RAID_REQUEST";
var configuration = {
	error: {
		coords: false,
		mission: false,
		raid: false
	},
	topLeft : {
		x : 0,
		y : 0
	},
	bottomRight : {
		x : 0,
		y : 0
	},
	selectedMission: "Z1D1",
	SelectedRaid: "R1"
}
function setCoordsMessage(){
	var payload = {}
	sendJavaMessage(SET_COORDS_REQUEST, payload);
}
function setCoordsMessageCallback(data){
	var data = JSON.parse(data);
	configuration.error.coords = data.error;
	configuration.topLeft = data.topLeft;
	configuration.bottomRight = data.bottomRight;
	$('.setTopLeft').val(configuration.topLeft.x+","+configuration.topLeft.y);
	$('.setBottomRight').val(configuration.bottomRight.x+", "+configuration.bottomRight.y);
	localStorage.setItem("configuration", JSON.stringify(configuration));
	setBusy(false);
}

function setMissionMessage(selectedMission){
	var payload = {
			selectedMission: selectedMission
	}
	sendJavaMessage(SET_MISSION_REQUEST, payload);
}
function setMissionMessageCallback(data){
	var data = JSON.parse(data);
	configuration.error.mission = data.error;
	configuration.selectedMission = data.selectedMission;  
	$('.selectedMission').val(configuration.selectedMission);
	setBusy(false);
}

function setRaidMessage(selectedRaid){
	var payload = {
			selectedRaid: selectedRaid
	}
	sendJavaMessage(SET_RAID_REQUEST, payload);
}

function setRaidMessageCallback(data){
	var data = JSON.parse(data);
	configuration.error.raid = data.error;
	configuration.selectedRaid = data.selectedRaid;  
	$('.selectedRaid').val(configuration.selectedRaid);
	setBusy(false);

	sendJavaMessage("dbg", configuration);
}

function sendJavaMessage(action, payload){
	var message = {
		action: action,
		payload: payload
	}
	alert(JSON.stringify(message));
}

function testCallFramJava(){
	sendJavaMessage("banana", {});
}

function setBusy(showLoader){
	if(showLoader){
		$("#loader").removeClass("hidden");
	}else{
		$("#loader").addClass("hidden");
	}
}
$(document).ready(function(){

	sendJavaMessage("banana", { "str" : localStorage.getItem("configuration") });
	$('.btn-expand-collapse').click(function(e) {
		$('.navbar-primary').toggleClass('collapsed');
		$('span', $(this)).toggleClass('hidden');
	});

	$('.navbar-primary-menu a').click(function(e) {
		$('.navbar-primary-menu a.active').removeClass('active');
		$(this).addClass('active');
		$('.main-content').addClass('hidden');
		$(".main-content."+$(this).attr("tab")).removeClass('hidden');
		//sendJavaMessage("test", "123stella");
	});

	$('.updateCoords').click(function(e) {
		setBusy(true);
		setTimeout(setCoordsMessage, 1000);
	});

	$('.updateMission').click(function(e) {
		var selectedMission = $('.setMission').val();
		if(selectedMission == ""){
			return;
		}
		setBusy(true);
		setTimeout(function(){ setMissionMessage(selectedMission); }, 1000);
	});
	
	$('.updateRaid').click(function(e) {
		var selectedRaid = $('.setRaid').val();
		if(selectedRaid == ""){
			return;
		}

		setBusy(true);
		setTimeout(function(){ setRaidMessage(selectedRaid); }, 1000);
	});
});
var SET_COORDS_REQUEST = "SET_COORDS_REQUEST";
var SET_MISSION_REQUEST = "SET_MISSION_REQUEST";
var SET_RAID_REQUEST = "SET_RAID_REQUEST";
var SET_BOT_START_REQUEST = "SET_BOT_START_REQUEST";
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
	selectedMission: {
		label: "Z1D1",
		id: "Z1D1"
	},
	selectedRaid: {
		label: "R1 - Astorath",
		id: "R1"
	}
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
	localStorage.setItem("configuration", JSON.stringify(configuration));
	loadConfig();
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
	localStorage.setItem("configuration", JSON.stringify(configuration));  
	loadConfig();
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
	localStorage.setItem("configuration", JSON.stringify(configuration));
	loadConfig();
	setBusy(false);
}

function setBotStartMessage(configuration){
	var payload = {
			configuration: configuration
	}
	sendJavaMessage(SET_BOT_START_REQUEST, configuration);
}

function setBotStartMessageCallback(data){
	//sendJavaMessage(SET_BOT_START_MESSAGE, configuration);
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

function loadConfig(){
	storedConfiguration = JSON.parse(localStorage.getItem("configuration"));
	try{
		setConfigData(storedConfiguration);
		localStorage.setItem("configuration", JSON.stringify(storedConfiguration));
		configuration = storedConfiguration;
	}catch(error){
		setConfigData(configuration);
		localStorage.setItem("configuration", JSON.stringify(configuration));
	}
	
}

function setConfigData(data){
	$('.setTopLeft').val(data.topLeft.x+", "+data.topLeft.y);
	$('.setBottomRight').val(data.bottomRight.x+", "+data.bottomRight.y);
	
	$('.selectedMission').attr("missionId", data.selectedMission.id);  
	$('.selectedMission').val(data.selectedMission.label);  
	
	$('.selectedRaid').attr("raidId", data.selectedRaid.id);
	$('.selectedRaid').val(data.selectedRaid.label);
}
$(document).ready(function(){
	setBusy(true);
	loadConfig();

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
		var selectedMission = {
				zone: $('.setMissionZone').val(),
				dungeon: $('.setMissionDungeon').val()
		};
		
		if(selectedMission.zone == "" || selectedMission.dungeon == ""){
			return;
		}
		setBusy(true);
		selectedMission = (selectedMission.zone+selectedMission.dungeon).toUpperCase();
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
	
	$('.startBot').click(function(e) {
		if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
			return;
		}
		
		//TODO: Disable side menu
		//TODO: implement status update
		//TODO: implement bot start
		//setBusy(true);
		setTimeout(function(){ setBotStartMessage(configuration); }, 1000);
	});
	

	setBusy(false);
});
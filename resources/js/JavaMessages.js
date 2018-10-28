var SET_COORDS_REQUEST = "SET_COORDS_REQUEST";
var SET_MISSION_REQUEST = "SET_MISSION_REQUEST";
var SET_RAID_REQUEST = "SET_RAID_REQUEST";
var SET_START_BOT_REQUEST = "SET_START_BOT_REQUEST";
var SET_STOP_BOT_REQUEST = "SET_STOP_BOT_REQUEST";
var SET_GET_LOGS_REQUEST = "SET_GET_LOGS_REQUEST";

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

function setStartBotMessage(configuration){
	var payload = {
			configuration: configuration
	}
	sendJavaMessage(SET_START_BOT_REQUEST, configuration);
}

function setStartBotMessageCallback(data){
	//Enable stop button
	$(".launcher.stopBot").removeClass("hidden");
	if(!$(".launcher.stopBot").hasClass("hidden")){
		$(".launcher.startBot").addClass("hidden");
	}
	setBusy(false);
	//sendJavaMessage(SET_BOT_START_MESSAGE, configuration);
}

function setStopBotMessage(configuration){
	var payload = {
			configuration: configuration
	}
	sendJavaMessage(SET_STOP_BOT_REQUEST, configuration);
}

function setStopBotMessageCallback(data){
	//Enable start button
	$(".launcher.startBot").removeClass("hidden");
	if(!$(".launcher.stopBot").hasClass("hidden")){
		$(".launcher.stopBot").addClass("hidden");
	}
	setBusy(false);
	//sendJavaMessage(SET_STOP_BOT_REQUEST, configuration);
}


function setGetLogsMessage(){
	var payload = {}
	sendJavaMessage(SET_GET_LOGS_REQUEST, configuration);
}

function setGetLogsMessageCallback(data){
	data = JSON.parse(data);
	//data = {"CURRENT_ACTION":"RAID","CURRENT_STATUS":"RUNNING","NEXT_ACTION":"MISSION"};
	//sendJavaMessage("banana", data);
	$(".currentStatus").text(data.CURRENT_STATUS);
	$(".currentAction").text(data.CURRENT_ACTION);
	$(".nextAction").text(data.NEXT_ACTION);
	if(data.CURRENT_STATUS == "IDLE"){
		$(".launcher.startBot").removeClass("hidden");
		if(!$(".launcher.stopBot").hasClass("hidden")){
			$(".launcher.stopBot").addClass("hidden");
		}
	}
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
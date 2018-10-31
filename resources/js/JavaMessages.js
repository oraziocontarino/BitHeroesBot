var SEND_COORDS_REQUEST = "SEND_COORDS_REQUEST";
var SEND_MISSION_REQUEST = "SEND_MISSION_REQUEST";
var SEND_RAID_REQUEST = "SEND_RAID_REQUEST";
var SEND_START_BOT_REQUEST = "SEND_START_BOT_REQUEST";
var SEND_STOP_BOT_REQUEST = "SEND_STOP_BOT_REQUEST";
var SEND_GET_LOGS_REQUEST = "SEND_GET_LOGS_REQUEST";
var SEND_GET_DEFAULT_REQUEST = "SEND_GET_DEFAULT_REQUEST";
var SEND_TEST = "SEND_TEST";

var REQUESTS = {
	SEND_COORDS_REQUEST: null,
	SEND_MISSION_REQUEST: null,
	SEND_RAID_REQUEST: null,
	SEND_START_BOT_REQUEST: null,
	SEND_STOP_BOT_REQUEST: null,
	SEND_GET_LOGS_REQUEST: null,
	SEND_GET_DEFAULT_REQUEST: null,
	SEND_TEST: null,
}


function setCoordsMessage(){
	var payload = {}
	sendJavaMessage(SEND_COORDS_REQUEST, payload);
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
	sendJavaMessage(SEND_MISSION_REQUEST, payload);
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
	sendJavaMessage(SEND_RAID_REQUEST, payload);
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
	sendJavaMessage(SEND_START_BOT_REQUEST, configuration);
}

function setStartBotMessageCallback(data){
	//Enable stop button
	$(".launcher.stopBot").removeClass("hidden");
	if(!$(".launcher.stopBot").hasClass("hidden")){
		$(".launcher.startBot").addClass("hidden");
	}
	setBusy(false);
	sendJavaMessage(SEND_BOT_START_MESSAGE, configuration);
}

function setStopBotMessage(configuration){
	var payload = {
			configuration: configuration
	}
	sendJavaMessage(SEND_STOP_BOT_REQUEST, configuration);
}

function setStopBotMessageCallback(data){
	//Enable start button
	$(".launcher.startBot").removeClass("hidden");
	if(!$(".launcher.stopBot").hasClass("hidden")){
		$(".launcher.stopBot").addClass("hidden");
	}
	setBusy(false);
	//sendJavaMessage(SEND_STOP_BOT_REQUEST, configuration);
}


function setGetLogsMessage(){
	var payload = {}
	sendJavaMessage(SEND_GET_LOGS_REQUEST, configuration);
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

function setGetDefaultConfigurationMessage(data){
	
}
function setGetDefaultConfigurationMessageCallback(data){
	
}

function setBusy(showLoader){
	if(showLoader){
		$("#loader").removeClass("hidden");
	}else{
		$("#loader").addClass("hidden");
	}
}



function testPromise(isCallback) {
	var deferred = null;
	if(isCallback){
		deferred = REQUESTS.SEND_TEST;
		//TODO: HANDLE CALLBACK ACTION
		
	}else{
		deferred = {
			promise: null,
			resolve: null,
			reject: null
		};
	}

	deferred.promise = new Promise((resolve, reject) => {
		deferred.resolve = resolve;
		deferred.reject = reject;
	});

	return deferred;
}

/*
 function defer() {
	var deferred = {
		promise: null,
		resolve: null,
		reject: null
	};

	deferred.promise = new Promise((resolve, reject) => {
		deferred.resolve = resolve;
		deferred.reject = reject;
	});

	return deferred;
}

this.treeBuilt = defer();

// Many, many lines below…

this.treeBuilt.resolve();
 * 
 * */




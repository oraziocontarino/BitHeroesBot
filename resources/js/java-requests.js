var ENDPOINTS = {
	"SET_COORDS": "SET_COORDS",
	"SET_MISSION": "SET_MISSION",
	"SET_RAID": "SET_RAID",
	"START_BOT": "START_BOT",
	"STOP_BOT": "STOP_BOT",
	"GET_LOGS": "GET_LOGS",
	"GET_DEFAULT_CONFIGURATION": "GET_DEFAULT_CONFIGURATION"	
}

var REQUESTS = {
	"SET_COORDS": null,
	"SET_MISSION": null,
	"SET_RAID": null,
	"START_BOT": null,
	"STOP_BOT": null,
	"GET_LOGS": null,
	"GET_DEFAULT_CONFIGURATION": null,
}

function setCoords(){
	var payload = {}
	return getPromise(ENDPOINTS.SET_COORDS, payload);
}

function setMission(selectedMission){
	var payload = {
			configuration: configuration,
			selectedMission: selectedMission
	}
	return getPromise(ENDPOINTS.SET_MISSION, payload);
}

function setRaid(selectedRaid){
	var payload = {
			configuration: configuration,
			selectedRaid: selectedRaid
	}
	return getPromise(ENDPOINTS.SET_RAID, payload);
}

function startBot(configuration){
	var payload = {
			configuration: configuration
	}
	return getPromise(ENDPOINTS.START_BOT, payload);
}

function stopBot(){
	var payload = {}
	return getPromise(ENDPOINTS.STOP_BOT, payload);
}

function getLogs(){
	var payload = {}
	return getPromise(ENDPOINTS.GET_LOGS, payload);
}

function getDefaultConfiguration(){
	var payload = {}
	return getPromise(ENDPOINTS.GET_DEFAULT_CONFIGURATION, payload);
}

function setBusy(showLoader){
	if(showLoader){
		$("#loader").removeClass("hidden");
	}else{
		$("#loader").addClass("hidden");
	}
}

function getPromise(action, payload) {
	var deferred = {
		promise: null,
		resolve: null,
		reject: null
	};
	deferred.promise = new Promise((resolve, reject) => {
		deferred.resolve = resolve;
		deferred.reject = reject;
		sendJavaMessage(action, payload);
	});
	REQUESTS[action] = deferred;
	return deferred;
}

function resolvePromise(action, payload){
	var timer = setInterval(function(){
		if(REQUESTS[action] != null){
			clearInterval(timer);
			REQUESTS[action].resolve(payload);
		}
	}, 200);
}

function sendJavaMessage(action, payload){
	var message = {
		action: action,
		payload: payload
	}
	alert(JSON.stringify(message));
}


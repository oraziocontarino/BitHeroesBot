var ENDPOINTS = {
	"SET_COORDS": "/setCoords",
	"START_BOT": "/startBot",
	"STOP_BOT": "/stopBot",
	"GET_LOGS": "/getLogs",
	"GET_DEFAULT_CONFIGURATION": "/getDefaultConfiguration",
	"IS_ALIVE": "/isAlive",
	"STOP_SERVER": "/stopServer",
	"TEST": "/test"
}


function test(){
	var payload = {
			configuration: configuration
	}
	return sendRequest(ENDPOINTS.TEST, payload);
}

function stopServer(){
	var payload = {
			configuration: configuration
	}
	return sendRequest(ENDPOINTS.STOP_SERVER, payload);
}

function isAlive(){
	var payload = {}
	return sendRequest(ENDPOINTS.IS_ALIVE, payload);
}

function setCoords(){
	var payload = {}
	return sendRequest(ENDPOINTS.SET_COORDS, payload);
}

function startBot(configuration){
	var payload = {
			configuration: configuration
	}
	return sendRequest(ENDPOINTS.START_BOT, payload);
}

function stopBot(){
	var payload = {}
	return sendRequest(ENDPOINTS.STOP_BOT, payload);
}

function getLogs(){
	var payload = {}
	return sendRequest(ENDPOINTS.GET_LOGS, payload);
}

function getDefaultConfiguration(){
	var payload = {}
	return sendRequest(ENDPOINTS.GET_DEFAULT_CONFIGURATION, payload);
}

function buildJavaMessage(action, payload){
	var message = {
		action: action,
		payload: payload
	}
	return JSON.stringify(message);
}

function sendRequest(action, payload) {
	var message = buildJavaMessage(action, payload);
	return new Promise(function(resolve, reject){
		$.ajax({
			type: "POST",
			url: "http://localhost:12345"+action,
			data: message,
			contentType: "application/json",
			dataType: "text",
			success: (data) => resolve(data),
			error: (data) => reject(data)
		});
	});
}

function setBusy(showLoader){
	if(showLoader){
		$("#loader").removeClass("hidden");
	}else{
		$("#loader").addClass("hidden");
	}
}


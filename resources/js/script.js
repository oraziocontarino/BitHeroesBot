var defaultConfiguration = configuration = {
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
	},
	selectedActions: {
		mission : true,
		raid: true
	}
}

function updateConfiguration(storedConfiguration){
	try{
		setConfigData(storedConfiguration);
		localStorage.setItem("configuration", JSON.stringify(storedConfiguration));
		configuration = storedConfiguration;
	}catch(error){
		setConfigData(configuration);
		localStorage.setItem("configuration", JSON.stringify(configuration));
	}	
}

function loadConfig(){
	storedConfiguration = JSON.parse(localStorage.getItem("configuration"));
	updateConfiguration(storedConfiguration);
}

function updateCheckedActions(key, value){
	storedConfiguration = JSON.parse(localStorage.getItem("configuration"));
	storedConfiguration.selectedActions[key]=value;
	updateConfiguration(storedConfiguration);
}

function setConfigData(data){
	//TAB - Main
	$.each(data.selectedActions, function(key){
		$('.bot-action-checkbox.'+key+"-checked").removeClass("hidden");
	});
	
	//TAB - Advanced setting
	$('.setTopLeft').val(data.topLeft.x+", "+data.topLeft.y);
	$('.setBottomRight').val(data.bottomRight.x+", "+data.bottomRight.y);
	
	$('.selectedMission').attr("missionId", data.selectedMission.id);  
	$('.selectedMission').val(data.selectedMission.label);  
	
	$('.selectedRaid').attr("raidId", data.selectedRaid.id);
	$('.selectedRaid').val(data.selectedRaid.label);
	
}

function showBitHeroesBotPanelWarning(message){
	$(".bit-heroes-bot-warning .alert-message").text(message);
	$(".bit-heroes-bot-warning").removeClass("hidden");
}

function hideBitHeroesBotPanelWarning(){
	$(".bit-heroes-bot-warning").removeClass("hidden");
	$(".bit-heroes-bot-warning").addClass("hidden");
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
			//TODO: implement setXxxWarning, eg: showBitHeroesBotPanelWarning("banana");
			return;
		}
		setBusy(true);
		selectedMission = (selectedMission.zone+selectedMission.dungeon).toUpperCase();
		setTimeout(function(){ setMissionMessage(selectedMission); }, 1000);
	});
	
	$('.updateRaid').click(function(e) {
		var selectedRaid = $('.setRaid').val();
		if(selectedRaid == ""){
			//TODO: implement setXxxWarning, eg: showBitHeroesBotPanelWarning("banana");
			return;
		}

		setBusy(true);
		setTimeout(function(){ setRaidMessage(selectedRaid); }, 1000);
	});

	$('.startBot').click(function(e) {
		if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
			showBitHeroesBotPanelWarning("Error occurred while reading configuration! Please try again.");
			return;
		}
		
		//TODO: Disable side menu
		setBusy(true);
		setTimeout(function(){ setStartBotMessage(configuration); }, 1000);
	});

	$('.stopBot').click(function(e) {
		if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
			showBitHeroesBotPanelWarning("Error occurred while reading configuration! Please try again.");
			return;
		}
		
		//TODO: Disable side menu
		setBusy(true);
		setTimeout(function(){ setStopBotMessage(configuration); }, 1000);
	});

	$('.bot-action-checkbox').click(function(e) {
		//setBusy(true);
		var key = $(this).attr('value');
		var value = $(this).prop('checked');
		updateCheckedActions(key, value);
		//setTimeout(function(){ setStopBotMessage(configuration); }, 1000);
	});
	
	$('.clearConfiguration').click(function(e) {
		setBusy(true);
		//TODO: STOP BOT BEFORE UPDATE!
		updateConfiguration(defaultConfiguration);
		setTimeout(function(){ setStopBotMessage(configuration); }, 1000);
		//TODO: convert all setXXX into promise!
		console.log(configuration);
		//location.reload();
		//
	});
	
	//setInterval(setGetLogsMessage, 1000);
	setBusy(false);
	//TODO: implement clear-config button
});


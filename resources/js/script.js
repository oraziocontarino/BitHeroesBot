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
		setBusy(true);
		setTimeout(function(){ setStartBotMessage(configuration); }, 1000);
	});
	
	$('.stopBot').click(function(e) {
		if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
			return;
		}
		
		//TODO: Disable side menu
		//TODO: implement status update
		//TODO: implement bot start
		//setBusy(true);
		setBusy(true);
		setTimeout(function(){ setStopBotMessage(configuration); }, 1000);
	});
	
	setInterval(setGetLogsMessage, 1000);
	setBusy(false);
});
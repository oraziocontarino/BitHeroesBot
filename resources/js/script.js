var configuration = defaultConfiguration = null;

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
	if(data.stack.mission == true){
		$('.bot-action-checkbox.mission-checked').removeClass("hidden");
	}else{
		$('.bot-action-checkbox.mission-unchecked').removeClass("hidden");
	}
	if(data.stack.raid == true){
		$('.bot-action-checkbox.raid-checked').removeClass("hidden");
	}else{
		$('.bot-action-checkbox.raid-unchecked').removeClass("hidden");
	}
	
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

function stopBotTask(){
	if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
		showBitHeroesBotPanelWarning("Error occurred while reading configuration! Please try again.");
		return;
	}
	
	//TODO: Disable side menu
	setBusy(true);
	stopBot().promise.then(function(data){
		//Enable start button
		$(".launcher.startBot").removeClass("hidden");
		if(!$(".launcher.stopBot").hasClass("hidden")){
			$(".launcher.stopBot").addClass("hidden");
		}
		setBusy(false);
	});
}
$(document).ready(function(){
	setBusy(true);
	//localStorage.removeItem("configuration");
	getDefaultConfiguration().promise.then(function(data){
		configuration = defaultConfiguration = JSON.parse(data);
		if(localStorage.getItem("configuration") == null){
			localStorage.setItem("configuration", data);
		}
		loadConfig();
		setBusy(false);
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
		setTimeout(function(){
			setCoords().promise.then(function(data){
				var data = JSON.parse(data);
				configuration.error.coords = data.error;
				configuration.topLeft = data.topLeft;
				configuration.bottomRight = data.bottomRight;
				localStorage.setItem("configuration", JSON.stringify(configuration));
				loadConfig();
				setBusy(false);
			});
		}, 1000);
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
		selectedMission = (selectedMission.zone+selectedMission.dungeon).toUpperCase();
		configuration.selectedMission.id=selectedMission;
		configuration.selectedMission.label=selectedMission;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});
	
	$('.updateRaid').click(function(e) {
		var selectedRaid = $('.setRaid').val().toUpperCase();
		if(selectedRaid == ""){
			//TODO: implement setXxxWarning, eg: showBitHeroesBotPanelWarning("banana");
			return;
		}

		configuration.selectedRaid.id=selectedRaid;
		configuration.selectedRaid.label=selectedRaid;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});

	$('.startBot').click(function(e) {
		if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
			showBitHeroesBotPanelWarning("Error occurred while reading configuration! Please try again.");
			return;
		}
		
		//TODO: Disable side menu
		sendJavaMessage("test", JSON.stringify(configuration));
		localStorage.setItem("configuration", JSON.stringify(configuration));
		setBusy(true);
		startBot(configuration).promise.then(function(data){
			//Enable stop button
			$(".launcher.stopBot").removeClass("hidden");
			if(!$(".launcher.stopBot").hasClass("hidden")){
				$(".launcher.startBot").addClass("hidden");
			}
			setBusy(false);
		});

	});

	$('.stopBot').click(function(e) {
		stopBotTask();
	});

	$('.bot-action-checkbox').click(function(e) {
		//setBusy(true);
		var stack = { missionSelector: null, raidSelector: null};
		stack.missionSelector = $('.bot-action-checkbox.mission-checked').hasClass('hidden') ? '.bot-action-checkbox.mission-unchecked' : '.bot-action-checkbox.mission-checked';
		stack.raidSelector = $('.bot-action-checkbox.raid-checked').hasClass('hidden') ? '.bot-action-checkbox.raid-unchecked' : '.bot-action-checkbox.raid-checked'; 
		configuration.stack.mission = $(stack.missionSelector).prop('checked');
		configuration.stack.raid = $(stack.raidSelector).prop('checked');
		localStorage.setItem("configuration", JSON.stringify(configuration));
	});
	
	$('.clearConfiguration').click(function(e) {
		setBusy(true);
		stopBot(configuration).promise.then(function(data){
			//Enable start button
			$(".launcher.startBot").removeClass("hidden");
			if(!$(".launcher.stopBot").hasClass("hidden")){
				$(".launcher.stopBot").addClass("hidden");
			}
			updateConfiguration(defaultConfiguration);
			location.reload();
			setBusy(false);
		});
	});
	
	
	setInterval(function(){
		return;
		getLogs().promise.then(function(data){
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
		});
	}, 1000);
});


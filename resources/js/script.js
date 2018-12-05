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

function setConfigData(data){
	//TAB - Main
	console.log(JSON.stringify(data));
	$('.checkbox.bot-action .mission').bootstrapToggle(data.stack.mission == true ? 'on' : 'off');
	$('.checkbox.bot-action .mission-label').text("Mission - "+data.selectedMission.label);
	
	$('.checkbox.bot-action .raid').bootstrapToggle(data.stack.raid == true ? 'on' : 'off');
	$('.checkbox.bot-action .raid-label').text("Raid - "+data.selectedRaid.label);

	$('.checkbox.bot-action .trial').bootstrapToggle(data.stack.trial == true ? 'on' : 'off');
	$('.checkbox.bot-action .gauntlet').bootstrapToggle(data.stack.gauntlet == true ? 'on' : 'off');

	$('.roundDelay').val(data.delays.round);
	$('.selectedRoundDelay').val(data.delays.round);
	
	//TAB - Advanced setting
	$('.setTopLeft').val(data.topLeft.x+", "+data.topLeft.y);
	$('.setBottomRight').val(data.bottomRight.x+", "+data.bottomRight.y);
	
	var missionData = data.selectedMission.id.split("D");
	$('.setMissionZone').val(missionData[0]);
	$('.setMissionDungeon').val("D"+missionData[1]);
	$('.selectedMission').attr("missionId", data.selectedMission.id);  
	$('.selectedMission').val(data.selectedMission.label);  
	
	$('.setRaid').val(data.selectedRaid.id);
	$('.selectedRaid').attr("raidId", data.selectedRaid.id);
	$('.selectedRaid').val(data.selectedRaid.label);

	$('.trial-settings .cost').val(data.trial.cost)
	$('.trial-settings .selectedCost').val(data.trial.cost);

	$('.trial-settings .difficulty').val(data.trial.difficulty);
	$('.trial-settings .selectedDifficulty').val(data.trial.difficulty);
	
	$('.gauntlet-settings .cost').val(data.gauntlet.cost)
	$('.gauntlet-settings .selectedCost').val(data.gauntlet.cost);

	$('.gauntlet-settings .difficulty').val(data.gauntlet.difficulty);
	$('.gauntlet-settings .selectedDifficulty').val(data.gauntlet.difficulty);
	
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

	setBusy(true);
	stopBot().promise.then(function(data){
		$('.lock-if-bot-running').addClass("hidden");
		//Enable start button
		$(".launcher.startBot").removeClass("hidden");
		if(!$(".launcher.stopBot").hasClass("hidden")){
			$(".launcher.stopBot").addClass("hidden");
		}
		setBusy(false);
	});
}
$(document).ready(function(){
	console.log("test js bridge");
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
		$('.navbar-primary-menu a.selected').removeClass('selected');
		$(this).addClass('selected');
		$('.main-content').addClass('hidden');
		$(".main-content."+$(this).attr("tab")).removeClass('hidden');
	});
	$('.advanced-settings ul li').click(function(e) {
		$('.advanced-settings ul li.active').removeClass('active');
		$(this).addClass('active');
		$('.advanced-settings .panel.sub-menu').addClass('hidden');
		$(".advanced-settings .panel.sub-menu."+$(this).attr("tab")).removeClass('hidden');
	});
	
	$("input[type=number]").on("change", function(e){
		$(this).val($(this).val());
	});
	$('#test_btn').click(function(e){
		setBusy(true);
		setTimeout(function(){
			test().promise.then(function(data){
				setBusy(false);
			});
		}, 1000);
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
			return;
		}
		selectedMission = (selectedMission.zone+selectedMission.dungeon).toUpperCase();
		configuration.selectedMission.id=selectedMission;
		configuration.selectedMission.label=selectedMission;
		configuration.stack.mission = true;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});
	
	$('.updateRaid').click(function(e) {
		var selectedRaid = $('.setRaid').val().toUpperCase();
		if(selectedRaid == ""){
			return;
		}

		configuration.selectedRaid.id=selectedRaid;
		configuration.selectedRaid.label=selectedRaid;
		configuration.stack.raid = true;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});
	
	$('.updateGauntlet').click(function(e) {
		var selectedCost = $('.gauntlet-settings .cost').val().toUpperCase();
		var selectedDifficulty = $('.gauntlet-settings .difficulty').val().toUpperCase();
		if(selectedCost == "" || selectedDifficulty == ""){
			return;
		}
		$('.gauntlet-settings .selectedCost').val(selectedCost.toUpperCase());
		$('.gauntlet-settings .selectedDifficulty').val(selectedDifficulty.toUpperCase());

		configuration.gauntlet.difficulty = selectedDifficulty;
		configuration.gauntlet.cost = selectedCost;
		configuration.stack.gauntlet = true;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});	
	$('.updateTrial').click(function(e) {
		var selectedCost = $('.trial-settings .cost').val().toUpperCase();
		var selectedDifficulty = $('.trial-settings .difficulty').val().toUpperCase();
		if(selectedCost == "" || selectedDifficulty == ""){
			return;
		}
		$('.trial-settings .selectedCost').val(selectedCost.toUpperCase());
		$('.trial-settings .selectedDifficulty').val(selectedDifficulty.toUpperCase());

		configuration.trial.difficulty = selectedDifficulty;
		configuration.trial.cost = selectedCost;
		configuration.stack.trial = true;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});
	
	$('.updateDelay').click(function(e) {
		var roundDelay = $('.roundDelay').val();
		if(roundDelay == ""){
			return;
		}
		$('.selectedRoundDelay').val(roundDelay);

		configuration.delays.round = roundDelay;
		localStorage.setItem("configuration", JSON.stringify(configuration));
		loadConfig();
	});
	
	$('.startBot').click(function(e) {
		if(configuration.error.coords || configuration.error.mission || configuration.error.raid){
			showBitHeroesBotPanelWarning("Error occurred while reading configuration! Please try again.");
			return;
		}
		
		localStorage.setItem("configuration", JSON.stringify(configuration));
		setBusy(true);
		startBot(configuration).promise.then(function(data){
			$('.lock-if-bot-running').removeClass("hidden");
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

	$('.checkbox.bot-action .raid').change(function() {
		configuration.stack.raid = $(this).prop('checked');
		localStorage.setItem("configuration", JSON.stringify(configuration));
		console.log("stack mission updated!");
	});

	$('.checkbox.bot-action .mission').change(function() {
		configuration.stack.mission = $(this).prop('checked');
		localStorage.setItem("configuration", JSON.stringify(configuration));
		console.log("stack mission updated!");
	});

	$('.checkbox.bot-action .trial').change(function() {
		configuration.stack.trial = $(this).prop('checked');
		localStorage.setItem("configuration", JSON.stringify(configuration));
		console.log("stack trial updated!");
	});
	
	$('.checkbox.bot-action .gauntlet').change(function() {
		configuration.stack.gauntlet = $(this).prop('checked');
		localStorage.setItem("configuration", JSON.stringify(configuration));
		console.log("stack gauntlet updated!");
	});
	
	$('.clearConfiguration').click(function(e) {
		setBusy(true);
		getDefaultConfiguration().promise.then(function(data){
			configuration = defaultConfiguration = JSON.parse(data);
			localStorage.setItem("configuration", data);
			loadConfig();
			setBusy(false);
		});
	});
	
	
	setInterval(function(){
		return;
		getLogs().promise.then(function(data){
			data = JSON.parse(data);
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


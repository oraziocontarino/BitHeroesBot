package be.events;

import java.awt.AWTException;

import org.json.JSONObject;

public class Gauntlet extends TrialAndGauntlet {

	public Gauntlet() throws AWTException {
		super();
	}
	@Override
	protected void updateEnabledStatus(JSONObject configuration) {
		this.enabled = configuration.getJSONObject("stack").getBoolean("trial");
	}
	@Override
	protected void updateCost(JSONObject configuration) {
		this.selectedCost = configuration.getJSONObject("gauntlet").getInt("cost");
	}
	@Override
	protected void updateDifficulty(JSONObject configuration) {
		this.selectedDifficulty = configuration.getJSONObject("gauntlet").getInt("difficulty");
	}
}

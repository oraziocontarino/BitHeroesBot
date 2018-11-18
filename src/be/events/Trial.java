package be.events;

import java.awt.AWTException;

import org.json.JSONObject;

public class Trial extends TrialAndGauntlet{

	public Trial() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void updateEnabledStatus(JSONObject configuration) {
		this.enabled = configuration.getJSONObject("stack").getBoolean("trial");
	}
	@Override
	protected void updateCost(JSONObject configuration) {
		this.selectedCost = configuration.getJSONObject("trial").getInt("cost");
	}
	@Override
	protected void updateDifficulty(JSONObject configuration) {
		this.selectedDifficulty = configuration.getJSONObject("trial").getInt("difficulty");
	}
}

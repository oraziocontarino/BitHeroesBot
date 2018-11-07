package gui.javagx;

import javafx.concurrent.Task;

@SuppressWarnings("restriction")
public class StopBotTask extends Task<Boolean> {
	public StopBotTask() {}

	@Override
	protected Boolean call() throws Exception { return true; }
}
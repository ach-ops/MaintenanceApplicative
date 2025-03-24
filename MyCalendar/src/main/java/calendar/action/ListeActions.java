package calendar.action;

import java.util.List;

public class ListeActions<T> {
	private final List<Action<T>> actions;

	public ListeActions(List<Action<T>> actions) {
		this.actions = actions;
	}

	public Action<T> get(int index) {
		return actions.get(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < actions.size(); i++) {
			sb.append(i + 1).append(" - ").append(actions.get(i).description()).append("\n");
		}
		return sb.toString();
	}
	public int size() {
		return actions.size();
	}

}


package calendar.action;

public interface Action<T> {
	T run();
	String description();
}

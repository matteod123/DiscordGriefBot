package de.corruptedbytes.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class Registry<T> {

	private final List<T> registered = new ArrayList<>();

	public void register(T... objects) {
		this.registered.addAll(Arrays.asList(objects));
	}

	public void unregister(T... objects) {
		this.registered.removeAll(Arrays.asList((Object[]) objects));
	}

	public T getBy(Predicate<? super T> predicate) {
		return this.registered.stream().filter(predicate).findFirst().orElse(null);
	}

	public final List<T> getRegistered() {
		return this.registered;
	}

}

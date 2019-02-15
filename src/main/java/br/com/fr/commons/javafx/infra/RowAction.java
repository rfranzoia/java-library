package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.scene.image.Image;

public class RowAction<T extends Serializable> {

	private Image icon;
	private Consumer<T> action;
	private String hint;
	private Predicate<T> validator;

	public RowAction() {
	}
	
	public RowAction(Image icon, Consumer<T> action, String hint, Predicate<T> validator) {
		this.icon = icon;
		this.action = action;
		this.hint = hint;
		this.validator = validator;
	}
	
	public RowAction(Image icon, Consumer<T> action, String hint) {
		this.icon = icon;
		this.action = action;
		this.hint = hint;
	}
	

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public Consumer<T> getAction() {
		return action;
	}

	public void setAction(Consumer<T> action) {
		this.action = action;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public Predicate<T> getValidator() {
		return validator;
	}

	public void setValidator(Predicate<T> validator) {
		this.validator = validator;
	}
	
}

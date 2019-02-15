package br.com.fr.commons.javafx.infra;

import java.io.Serializable;
import java.util.function.Function;

public class ColumnInfo<T extends Serializable> {

	private String title;
	private String fieldName;
	private double size;
	private Function<Object, String> formatter;

	public ColumnInfo(String title, String fieldName, double size, Function<Object, String> formatter) {
		super();
		this.title = title;
		this.fieldName = fieldName;
		this.size = size;
		this.formatter = formatter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Function<Object, String> getFormatter() {
		return formatter;
	}

	public void setFormatter(Function<Object, String> formatter) {
		this.formatter = formatter;
	}

}

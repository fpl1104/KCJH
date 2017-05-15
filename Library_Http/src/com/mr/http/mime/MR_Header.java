package com.mr.http.mime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MR_Header implements Iterable<MR_MinimalField> {
	private final List<MR_MinimalField> fields;
	private final Map<String, List<MR_MinimalField>> fieldMap;

	public MR_Header() {
		this.fields = new LinkedList<MR_MinimalField>();
		this.fieldMap = new HashMap<String, List<MR_MinimalField>>();
	}

	public void addField(MR_MinimalField field) {
		if (field == null) {
			return;
		}
		String key = field.getName().toLowerCase(Locale.US);
		List<MR_MinimalField> values = (List<MR_MinimalField>) this.fieldMap.get(key);
		if (values == null) {
			values = new LinkedList<MR_MinimalField>();
			this.fieldMap.put(key, values);
		}
		values.add(field);
		this.fields.add(field);
	}

	public List<MR_MinimalField> getFields() {
		return new ArrayList<MR_MinimalField>(this.fields);
	}

	public MR_MinimalField getField(String name) {
		if (name == null) {
			return null;
		}
		String key = name.toLowerCase(Locale.US);
		List<?> list = (List<?>) this.fieldMap.get(key);
		if ((list != null) && (!(list.isEmpty()))) {
			return ((MR_MinimalField) list.get(0));
		}
		return null;
	}

	public List<MR_MinimalField> getFields(String name) {
		if (name == null) {
			return null;
		}
		String key = name.toLowerCase(Locale.US);
		List<MR_MinimalField> list = (List<MR_MinimalField>) this.fieldMap.get(key);
		if ((list == null) || (list.isEmpty())) {
			return Collections.emptyList();
		}
		return new ArrayList<MR_MinimalField>(list);
	}

	public int removeFields(String name) {
		if (name == null) {
			return 0;
		}
		String key = name.toLowerCase(Locale.US);
		List<?> removed = (List<?>) this.fieldMap.remove(key);
		if ((removed == null) || (removed.isEmpty())) {
			return 0;
		}
		this.fields.removeAll(removed);
		return removed.size();
	}

	public void setField(MR_MinimalField field) {
		if (field == null) {
			return;
		}
		String key = field.getName().toLowerCase(Locale.US);
		List<MR_MinimalField> list = (List<MR_MinimalField>) this.fieldMap.get(key);
		if ((list == null) || (list.isEmpty())) {
			addField(field);
			return;
		}
		list.clear();
		list.add(field);
		int firstOccurrence = -1;
		int index = 0;
		for (Iterator<MR_MinimalField> it = this.fields.iterator(); it.hasNext(); ++index) {
			MR_MinimalField f = (MR_MinimalField) it.next();
			if (f.getName().equalsIgnoreCase(field.getName())) {
				it.remove();
				if (firstOccurrence == -1) {
					firstOccurrence = index;
				}
			}
		}
		this.fields.add(firstOccurrence, field);
	}

	public Iterator<MR_MinimalField> iterator() {
		return Collections.unmodifiableList(this.fields).iterator();
	}

	public String toString() {
		return this.fields.toString();
	}
}
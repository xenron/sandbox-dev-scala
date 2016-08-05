/*
 *
 * Instant Play Framework Starter
 * Phone Book Example
 *   ______  ___                             ___                     ___ __
 *  /   _  \/  /__  ______  ______  ______  /  /__  ______  ______  /  /  /
 * /   ____/   _  \/   _  \/   _  \/   ___\/   _  \/   _  \/   _  \/      \
 * \__/    \__/___/\______/\__/___/\______/\______/\______/\______/\__/___/
 *
 * Published under MIT License. Copyright (c) 2013 Daniel Dietrich, Packt Publishing
 *
 */
package controllers;

import scala.collection.Seq;

import models.Entry;
import play.data.Form;
import play.mvc.*;

import static play.data.Form.form;

public class Entries extends Controller {

	final static Form<Entry> entryForm = form(Entry.class);

	public static Result list(String filter) {
		Seq<Entry> entries = models.Entries.findByName(filter);
		return ok(views.html.index.render(entries));
	}

	public static Result remove(long id) {
		models.Entries.delete(id);
		return redirect(routes.Entries.list(""));
	}

	public static Result add() {
		Form<Entry> form = entryForm.fill(new Entry());
		return ok(views.html.edit.render(form, "Add entry"));
	}

	public static Result edit(long id) {
		Entry entry = models.Entries.findById(id);
		if (entry != null) {
			Form<Entry> form = entryForm.fill(entry);
			return ok(views.html.edit.render(form, "Edit entry"));
		} else {
			return redirect(routes.Entries.list(""));
		}
	}

	public static Result save() {
		Entry entry = entryForm.bindFromRequest(request()).get();
		models.Entries.save(entry);
		return redirect(routes.Entries.list(""));
	}


}

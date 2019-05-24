package br.ufpa.progep.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.PersistenceException;

import br.ufpa.progep.ejbs.AuthorEJB;
import br.ufpa.progep.entities.Author;

@FacesConverter(value = "authorConverter")
public class AuthorConverter implements Converter<Author> {
	
	@EJB
	private AuthorEJB authorEJB;

	@Override
	public Author getAsObject(FacesContext facesContext, UIComponent uiComponent, String idString) {
		try {
			Integer id = Integer.parseInt(idString);
			Author author = authorEJB.findAuthor(id);
			return author;
		} catch(PersistenceException exception) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Author author) {
		try {
			String id = author.getId().toString();
			return id;
		} catch(PersistenceException exception) {
			return null;
		}
	}

	public AuthorEJB getAuthorEJB() {
		return authorEJB;
	}

	public void setAuthorEJB(AuthorEJB authorEJB) {
		this.authorEJB = authorEJB;
	}

}

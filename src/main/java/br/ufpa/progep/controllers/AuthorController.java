package br.ufpa.progep.controllers;

//import java.util.ArrayList;
import java.util.List;

//import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.ufpa.progep.ejbs.AuthorEJB;
import br.ufpa.progep.entities.Author;
import br.ufpa.progep.util.FacesUtil;

/*
 * This class is responsible for managing administration processes
 * of the class "author", persisting, removing, editing and listing all
 * entities saved in database.
 * 
 * The Named annotation means that this class is an Managed Bean using Context
 * and Dependency Injection (CDI) and this will be managed by the server.
 * 
 * The RequestScoped annotation means that the server has to call this class
 * only if it requested.
 * */
@Named(value = "authorController")
@RequestScoped
public class AuthorController {

	/*
	 * Object responsible for executing business rules. This attribute
	 * is managed by the server (Wildfly 16.0), using the annotation EJB
	 * for injection.
	 * */
	@EJB
	private AuthorEJB authorEJB;
	
	/*
	 * Object that represents the author to be persisted, edited or removed.
	 * */
	private Author author;
	
	/*
	 * List of all authors saved in the database. Used for the search page.
	 * */
	private List<Author> authorsList;
	
	/*
	 * Object passed by '<f:param>' tag. Used to search the author that
	 * the user want to edit or remove, and if the 'param' returns a null author's id
	 * the attribute author is instantiated in the method 'inicializeAuthor()'.
	 * */
	private Integer id;
	//private String action;
	
	/*@PostConstruct
	public void initiateObjects() {
		initiateAuthor();
		//initiateAuthorsList();
	}*/

	/*
	 * Method responsible for persist the author's object in the database.
	 * And flag the user on web page that the method was successfully executed
	 * or not.
	 * */
	public String persist() {
		try {
			/*
			 * Method to persist the object in the database.
			 * */
			authorEJB.persist(author);
			
			/*
			 * Method to flag the user on web page that the method was successfully
			 * executed.
			 * */
			FacesUtil.addInfoMessage("Success", "Author's data succefully saved!");
		} catch (PersistenceException ex) {
			/*
			 * Method purely to flag the developer on console if the method was
			 * erroneously executed, and what gone wrong.
			 * */
			ex.printStackTrace();
			
			/*
			 * Method to flag the user on web page that the method was not successfully
			 * executed.
			 * */
			FacesUtil.addErrorMessage("Error", ex.getMessage());
		}
		
		/*
		 * Returns the user page to the search page with the new author already added to the list.
		 * */
		return "authorsList.xhtml?faces-redirect=true";
	}
	
	/*
	 * Method responsible for remove the author's object from the database.
	 * */
	public String remove() {
		try {
			/*
			 * Method to remove the object from the database.
			 * */
			authorEJB.remove(id);
			
			/*
			 * Method to flag the user on web page that the method was successfully
			 * executed.
			 * */
			FacesUtil.addInfoMessage("Success", "Author's data succefully removed!");
		} catch(PersistenceException ex) {
			/*
			 * Method purely to flag the developer on console if the method was
			 * erroneously executed, and what gone wrong.
			 * */
			ex.printStackTrace();
			
			/*
			 * Method to flag the user on web page that the method was not successfully
			 * executed.
			 * */
			FacesUtil.addErrorMessage("Error", ex.getMessage());
		}
		
		/*
		 * Returns the user page to the search page with the new author already added to the list.
		 * */
		return "authorsList.xhtml?faces-redirect=true";
	}
	
	/*
	 * Method responsible for persist the edited author's object.
	 * */
	public String edit() {
		try {
			/*
			 * Method to remove the object from the database.
			 * */
			authorEJB.edit(author, id);
			
			/*
			 * Method to flag the user on web page that the method was successfully
			 * executed.
			 * */
			FacesUtil.addInfoMessage("Success", "Author's data succefully edited!");
		} catch(PersistenceException ex) {
			/*
			 * Method purely to flag the developer on console if the method was
			 * erroneously executed, and what gone wrong.
			 * */
			ex.printStackTrace();
			
			/*
			 * Method to flag the user on web page that the method was not successfully
			 * executed.
			 * */
			FacesUtil.addErrorMessage("Error", ex.getMessage());
		}
		
		/*
		 * Returns the user page to the search page with the new author already added to the list.
		 * */
		return "authorsList.xhtml?faces-redirect=true";
	}
	
	/*
	 * Method responsible for search the author that have the id that was passed
	 * by the 'param' tag. It's executed every in the the web redirects to the add,
	 * edit and remove Author page.
	 * */
	public void loadRegister() {
		try {
			/*
			 * If is passed a value to id, it will find the author that has an id equal
			 * to the id passed.
			 * */
			if(id != null) {
				author = authorEJB.findAuthor(id);
				
			/*
			 * If it's passed no value to id (when the user wants to persist a new
			 * author in the database), it calls the initiateAuthor() method.
			 * */
			} else {
				initiateAuthor();
			}
		} catch(PersistenceException exception) {
			/*
			 * Method purely to flag the developer on console if the method was
			 * erroneously executed, and what gone wrong.
			 * */
			exception.printStackTrace();
		}
		
	}

	/*
	 * Method responsible for instantiated the author's object.
	 * */
	public void initiateAuthor() {
		author = new Author();
	}

	/*
	 * Method responsible for instantiated the author's list object.
	 * */
	/*public void initiateAuthorsList() {
		authorsList = new ArrayList<>();
	}*/
	
	/*
	 * Getters and Setters.
	 * */
	public AuthorEJB getAuthorEJB() {
		return authorEJB;
	}

	public void setAuthorEJB(AuthorEJB authorEJB) {
		this.authorEJB = authorEJB;
	}

	public Author getAuthor() {
		/*
		 * If the web page calls the author attribute and it has no value and
		 *  was not instantiated already, it calls the initiateAuthor() method.
		 * */
		if(author == null) {
			initiateAuthor();
		}
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	/*
	 * In this method, the class verify if the author's list is null, if is
	 * the object 'authorEJB' returns all authors saved in the database.
	 * */
	public List<Author> getAuthorsList() {
		if(authorsList == null) {
			authorsList = authorEJB.findAllAuthors();
		}
		return authorsList;
	}

	public void setAuthorsList(List<Author> authorsList) {
		this.authorsList = authorsList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}*/

}

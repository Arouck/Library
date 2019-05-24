package br.ufpa.progep.ejbs;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.ufpa.progep.entities.Author;

/*
 * This class is responsible for the execute the business rules of the Author
 * entity. This business rules are the methods: persist, edit, remove, findAll,
 * and find.
 * 
 * The Stateless annotation means that this class has no state, the server will
 * manage it this following way: Every time that it's necessary to be executed,
 * the server initialize the bean and when the operation is finished the bean is
 * finalized (It doesn't save previous status/states).
 * */
@Stateless
public class AuthorEJB {

	/*
	 * This attribute is used to perform operations directly with the database.
	 * The PersistenceContext annotation guarantees that the server will manage
	 * this attribute.
	 *  */
	@PersistenceContext(unitName = "EJB")
	private EntityManager em;

	/*
	 * Method to persist and author object, passed to it, in the database.
	 * If it has any error executing, will throw an PersistenceException
	 * to be treated in the Controller.
	 * */
	public void persist(Author author) throws PersistenceException {
		em.persist(author);
	}

	/*
	 * Method to remove and author object, passed to it, from the database.
	 * If it has any error executing, will throw an PersistenceException
	 * to be treated in the Controller.
	 * */
	public void remove(Integer index) throws PersistenceException {
		em.remove(em.find(Author.class, index));
	}

	/*
	 * Method to persist and edited author object, passed to it, in the database.
	 * If it has any error executing, will throw an PersistenceException
	 * to be treated in the Controller.
	 * */
	public void edit(Author author, Integer index) throws PersistenceException {
		Author author2 = em.find(Author.class, index);
		author2.setNome(author.getNome());
		em.merge(author2);
	}

	/*
	 * Method to find an author object in the database that has the id equal
	 * to the id number passed to the method.
	 * If it has any error executing, will throw an PersistenceException
	 * to be treated in the Controller.
	 * */
	public Author findAuthor(Integer index) throws PersistenceException {
		return em.find(Author.class, index);
	}

	/*
	 * Method to return all authors persisting in the database.
	 * If it has any error executing, will throw an PersistenceException
	 * to be treated in the Controller.
	 * */
	public List<Author> findAllAuthors() throws PersistenceException {
		/*
		 * It executes the query created in the Author class.
		 * */
		TypedQuery<Author> query = em.createNamedQuery("findAllAuthors", Author.class);
		return query.getResultList();
	}
}

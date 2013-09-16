package br.usp.ime.vision.dataset.tests.unit;

import junit.framework.TestCase;

import org.junit.Test;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.dao.interfaces.AlbumDAO;
import br.usp.ime.vision.dataset.dao.interfaces.UserDAO;

public class DAOFactoryTest extends TestCase {

	@Test
	public void testUserDAOCreation() {
		Object albumDAO = DAOFactory.getUserDao();
		assertEquals(true, albumDAO instanceof UserDAO);
	}

	@Test
	public void testAlbumDAOCreation() {
		Object albumDAO = DAOFactory.getAlbumDao();
		assertEquals(true, albumDAO instanceof AlbumDAO);
	}

}

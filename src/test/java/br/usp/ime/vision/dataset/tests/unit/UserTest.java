package br.usp.ime.vision.dataset.tests.unit;

import junit.framework.TestCase;

import org.junit.Test;

import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.validators.UsernameValidator;

public class UserTest extends TestCase {

	@Test
	public void testNullUsernameIsInvalid() {
		boolean expected = false;
		assertEquals(expected, UsernameValidator.validate(null));
	}

	@Test
	public void testVisionUser() {
		boolean expected = true;
		User user = new User();
		user.setEmail("user@vision.ime.usp.br");
		assertEquals(expected, user.isVisionUser());
	}

	@Test
	public void testNotVisionUser() {
		boolean expected = false;
		User user = new User();
		user.setEmail("user@ime.usp.br");
		assertEquals(expected, user.isVisionUser());
	}

}

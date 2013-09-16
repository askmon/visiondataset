package br.usp.ime.vision.dataset.tests.concordion;

import org.concordion.integration.junit3.ConcordionTestCase;

import br.usp.ime.vision.dataset.validators.UsernameValidator;

public class UserTest extends ConcordionTestCase {

	public boolean validUsername(String username) {
		return UsernameValidator.validate(username);
	}

}

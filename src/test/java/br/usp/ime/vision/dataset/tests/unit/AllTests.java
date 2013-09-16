package br.usp.ime.vision.dataset.tests.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlbumDAOImplTest.class, AttachmentDAOimplTest.class, AttachmentServiceTest.class,
        AttachmentUtilsTest.class, ConfigsTest.class, DAOFactoryTest.class, ImageServiceTest.class,
        UserTest.class })
public class AllTests {

}

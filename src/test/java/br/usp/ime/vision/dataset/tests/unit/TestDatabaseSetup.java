package br.usp.ime.vision.dataset.tests.unit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.impl.ConnectionFactoryMockup;

public final class TestDatabaseSetup {
	private static final Logger LOG = LoggerFactory.getLogger(TestDatabaseSetup.class);

    public static void setUp() throws SQLException, ConfigurationException {
        ConnectionFactoryMockup factory = new ConnectionFactoryMockup();
        Connection connection = factory.getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT pg_catalog.setval('tb_albums_id_seq', 1, true);"
                + "SELECT pg_catalog.setval('tb_annotations_id_seq', 5, true);"
                + "SELECT pg_catalog.setval('tb_image_attachments_id_seq', 32, true);"
                + "SELECT pg_catalog.setval('tb_images_id_seq', 6, true);"
                + "SELECT pg_catalog.setval('tb_tags_albums_id_seq', 1, true);"
                + "SELECT pg_catalog.setval('tb_tags_images_id_seq', 4, true);"
                + "SELECT pg_catalog.setval('tb_users_id_seq', 1, true);"
                + "INSERT INTO tb_users VALUES (1, 'rafael', '741CBAEB4DFC59E110E13A4CD2134538CCF04FB3', 'Rafael', 'rafaellg@vision.ime.usp.br', true, true, '2011-06-13 13:34:53.042-03', '2011-06-13 13:34:53.042-03', 100, true);"
                + "INSERT INTO tb_albums VALUES (1, 'Teste', 1, '2011-06-29 00:23:38.97-03', '2011-07-07 01:43:28.558-03', 10, 0, 0);"
                + "INSERT INTO tb_images VALUES (3, 'image/png', 1, 1, '2011-07-07 01:43:27.587-03');"
                + "INSERT INTO tb_images VALUES (4, 'image/png', 1, 1, '2011-07-07 01:43:28.286-03');"
                + "INSERT INTO tb_images VALUES (5, 'image/jpeg', 1, 1, '2011-07-07 01:43:28.422-03');"
                + "INSERT INTO tb_images VALUES (6, 'image/png', 1, 1, '2011-07-07 01:43:28.558-03');"
                + "INSERT INTO tb_image_attachments VALUES (15, 3, 'teste1'					,'2011-07-07 17:52:06.173-03');"
                + "INSERT INTO tb_image_attachments VALUES (16, 4, 'teste2'					,'2011-07-07 17:52:39.517-03');"
                + "INSERT INTO tb_image_attachments VALUES (19, 3, 'teste.png'				,'2011-07-12 19:23:46.03-03');"
                + "INSERT INTO tb_image_attachments VALUES (22, 3, 'marker_asdfsadfgdf.png'	,'2011-07-18 19:47:01.744-03');"
                + "INSERT INTO tb_image_attachments VALUES (23, 3, 'marker_asdf.png'		,'2011-07-18 19:57:05.516-03');"
                + "INSERT INTO tb_image_attachments VALUES (24, 3, 'asdgsdgdsg.jpg'			,'2011-07-25 13:15:43.208-03');"
                + "INSERT INTO tb_image_attachments VALUES (25, 3, 'asdgsdgdsg.jpg'			,'2011-07-25 13:17:41.805-03');"
                + "INSERT INTO tb_image_attachments VALUES (26, 3, 'features.json'			,'2011-07-25 18:01:50.23-03');"
                + "INSERT INTO tb_image_attachments VALUES (27, 3, 'features.json'			,'2012-01-21 18:05:07.486-03');"
                + "INSERT INTO tb_tags_albums VALUES (1, 'test', 1);"
                + "INSERT INTO tb_tags_images VALUES (3, 'asdf', 3);"
                + "INSERT INTO tb_tags_images VALUES (4, 'bdgdgbfg', 3);";

        Scanner s = new Scanner(sql);
        s.useDelimiter("(;(\r)?\n?)|(--\n)");
        while (s.hasNext()) {
            String st = s.next();
            try {
            	statement.execute(st + ";");	
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				LOG.error("erro executing "+ st, e);
			}
			
        }
        statement.close();

    }

    public static void tearDown() throws SQLException {
        ConnectionFactoryMockup factory = new ConnectionFactoryMockup();
        Connection connection = factory.getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete from tb_tags_images ;" + "delete from tb_tags_albums ;"
                + "delete from tb_annotations ;" + "delete from tb_image_attachments;"
                + "delete from tb_images;" + "delete from tb_permissions ;" + "delete from tb_albums;"
                + "delete from tb_users ;";
        Scanner s = new Scanner(sql);
        s.useDelimiter("(;(\r)?\n?)|(--\n)");
        while (s.hasNext()) {
            statement.execute(s.next());
        }
        statement.close();

    }

}

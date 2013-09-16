package br.usp.ime.vision.dataset.tiles;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.factory.BasicTilesContainerFactory;

/**
 * Container Factory for Tiles. Loads the <code>/WEB-INF/tiles-defs.xml</code>
 * configuration file.
 * 
 * @author Bruno Klava
 */
public class TilesContainerFactory extends BasicTilesContainerFactory {

    @Override
    protected List<URL> getSourceURLs(
            final TilesApplicationContext applicationContext,
            final TilesRequestContextFactory contextFactory) {

        final List<URL> urls = new ArrayList<URL>();
        try {
            urls.add(applicationContext.getResource("/WEB-INF/tiles-defs.xml"));
        } catch (final IOException e) {
            throw new DefinitionsFactoryException(
                    "Cannot load definition URLs", e);
        }
        return urls;

    }

}

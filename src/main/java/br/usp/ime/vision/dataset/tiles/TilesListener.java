package br.usp.ime.vision.dataset.tiles;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.AbstractTilesInitializer;
import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.AbstractTilesListener;

/**
 * Tiles Listener that uses {@link TilesContainerFactory} as factory.
 * 
 * @author Bruno Klava
 */
public class TilesListener extends AbstractTilesListener {

    @Override
    protected TilesInitializer createTilesInitializer() {
        return new AbstractTilesInitializer() {
            @Override
            protected AbstractTilesContainerFactory createContainerFactory(
                    final TilesApplicationContext context) {
                return new TilesContainerFactory();
            }
        };
    }

}

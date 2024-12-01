package misc;

import java.awt.image.BufferedImage;

/**
 * The misc.Tile class represents a single tile in the game world.
 * Tiles can have an image and a collision property to determine if they block movement.
 */
public class Tile {

    /** The image representing the tile. */
    public BufferedImage image;

    /** Flag indicating whether the tile has collision enabled. */
    public boolean collision;
}

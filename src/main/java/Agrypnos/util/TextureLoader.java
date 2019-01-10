package Agrypnos.util;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TextureLoader {
    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

    /**
     * @param textureString - String path to the texture you want to load relative to resources,
     * Example: "img/ui/missingtexture.png"
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided
     */
    public static Texture getTexture(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                return getTexture("img/Agrypnos/missingtexture.png");
            }
        }
        return textures.get(textureString);
    }

    /**
     * Creates and instance of the texture, applies a linear filter to it, and places it in the HashMap
     *
     * @param textureString - String path to the texture you want to load relative to resources,
     * Example: "img/ui/missingtexture.png"
     * @throws GdxRuntimeException
     */
    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        Texture texture =  new Texture(textureString);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textures.put(textureString, texture);
    }
}
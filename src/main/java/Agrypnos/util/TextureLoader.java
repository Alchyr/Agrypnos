package Agrypnos.util;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.helpers.ImageMaster;

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


    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        loadTexture(textureString, false);
    }

    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException {
        Texture texture =  new Texture(textureString);
        if (linearFilter)
        {
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        else
        {
            texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        }
        textures.put(textureString, texture);
    }
}
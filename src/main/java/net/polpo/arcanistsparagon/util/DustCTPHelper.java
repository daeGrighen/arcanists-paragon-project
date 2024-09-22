package net.polpo.arcanistsparagon.util;

import net.minecraft.particle.DustColorTransitionParticleEffect;
import org.joml.Vector3f;

public class DustCTPHelper extends DustColorTransitionParticleEffect {

        // Primary Colors
        public static final Vector3f RED = new Vector3f(1.0f, 0.0f, 0.0f);
        public static final Vector3f GREEN = new Vector3f(0.0f, 1.0f, 0.0f);
        public static final Vector3f BLUE = new Vector3f(0.0f, 0.0f, 1.0f);

        // Secondary Colors
        public static final Vector3f YELLOW = new Vector3f(1.0f, 1.0f, 0.0f);
        public static final Vector3f CYAN = new Vector3f(0.0f, 1.0f, 1.0f);
        public static final Vector3f MAGENTA = new Vector3f(1.0f, 0.0f, 1.0f);

        // Shades of Gray
        public static final Vector3f WHITE = new Vector3f(1.0f, 1.0f, 1.0f);
        public static final Vector3f LIGHT_GRAY = new Vector3f(0.75f, 0.75f, 0.75f);
        public static final Vector3f GRAY = new Vector3f(0.5f, 0.5f, 0.5f);
        public static final Vector3f DARK_GRAY = new Vector3f(0.25f, 0.25f, 0.25f);
        public static final Vector3f BLACK = new Vector3f(0.0f, 0.0f, 0.0f);

        // Extended Colors
        public static final Vector3f ORANGE = new Vector3f(1.0f, 0.65f, 0.0f);
        public static final Vector3f PINK = new Vector3f(1.0f, 0.75f, 0.8f);
        public static final Vector3f PURPLE = new Vector3f(0.5f, 0.0f, 0.5f);
        public static final Vector3f BROWN = new Vector3f(0.6f, 0.4f, 0.2f);
        public static final Vector3f LIME = new Vector3f(0.0f, 1.0f, 0.5f);
        public static final Vector3f VIOLET = new Vector3f(0.56f, 0.0f, 1.0f);

        // Pastel Colors
        public static final Vector3f PASTEL_BLUE = new Vector3f(0.68f, 0.85f, 0.9f);
        public static final Vector3f PASTEL_GREEN = new Vector3f(0.6f, 0.8f, 0.6f);
        public static final Vector3f PASTEL_PINK = new Vector3f(1.0f, 0.71f, 0.76f);
        public static final Vector3f PASTEL_YELLOW = new Vector3f(1.0f, 0.97f, 0.65f);

        // Additional Colors
        public static final Vector3f GOLD = new Vector3f(1.0f, 0.84f, 0.0f);
        public static final Vector3f SILVER = new Vector3f(0.75f, 0.75f, 0.75f);
        public static final Vector3f BRONZE = new Vector3f(0.8f, 0.5f, 0.2f);

        // Additional Shades of Red
        public static final Vector3f CRIMSON = new Vector3f(0.86f, 0.08f, 0.24f);
        public static final Vector3f DARK_RED = new Vector3f(0.55f, 0.0f, 0.0f);
        public static final Vector3f LIGHT_CORAL = new Vector3f(0.94f, 0.5f, 0.5f);
        public static final Vector3f INDIAN_RED = new Vector3f(0.8f, 0.36f, 0.36f);

        // Additional Shades of Green
        public static final Vector3f DARK_GREEN = new Vector3f(0.0f, 0.39f, 0.0f);
        public static final Vector3f FOREST_GREEN = new Vector3f(0.13f, 0.55f, 0.13f);
        public static final Vector3f SEA_GREEN = new Vector3f(0.18f, 0.55f, 0.34f);
        public static final Vector3f PALE_GREEN = new Vector3f(0.6f, 0.98f, 0.6f);

        // Additional Shades of Blue
        public static final Vector3f NAVY = new Vector3f(0.0f, 0.0f, 0.5f);
        public static final Vector3f MIDNIGHT_BLUE = new Vector3f(0.1f, 0.1f, 0.44f);
        public static final Vector3f SKY_BLUE = new Vector3f(0.53f, 0.81f, 0.92f);
        public static final Vector3f LIGHT_SKY_BLUE = new Vector3f(0.53f, 0.89f, 1.0f);

        // Browns and Earthy Colors
        public static final Vector3f SADDLE_BROWN = new Vector3f(0.55f, 0.27f, 0.07f);
        public static final Vector3f SIENNA = new Vector3f(0.63f, 0.32f, 0.18f);
        public static final Vector3f CHOCOLATE = new Vector3f(0.82f, 0.41f, 0.12f);
        public static final Vector3f TAN = new Vector3f(0.82f, 0.71f, 0.55f);

        // Additional Shades of Yellow
        public static final Vector3f LIGHT_YELLOW = new Vector3f(1.0f, 1.0f, 0.88f);
        public static final Vector3f LEMON_CHIFFON = new Vector3f(1.0f, 0.98f, 0.8f);
        public static final Vector3f GOLDENROD = new Vector3f(0.85f, 0.65f, 0.13f);
        public static final Vector3f KHAKI = new Vector3f(0.94f, 0.9f, 0.55f);

        // Purples and Violets
        public static final Vector3f LAVENDER = new Vector3f(0.9f, 0.9f, 0.98f);
        public static final Vector3f ORCHID = new Vector3f(0.85f, 0.44f, 0.84f);
        public static final Vector3f PLUM = new Vector3f(0.87f, 0.63f, 0.87f);
        public static final Vector3f DARK_ORCHID = new Vector3f(0.6f, 0.2f, 0.8f);

        // Pinks
        public static final Vector3f DEEP_PINK = new Vector3f(1.0f, 0.08f, 0.58f);
        public static final Vector3f HOT_PINK = new Vector3f(1.0f, 0.41f, 0.71f);
        public static final Vector3f LIGHT_PINK = new Vector3f(1.0f, 0.71f, 0.76f);
        public static final Vector3f SALMON = new Vector3f(0.98f, 0.5f, 0.45f);

        // Shades of Orange
        public static final Vector3f DARK_ORANGE = new Vector3f(1.0f, 0.55f, 0.0f);
        public static final Vector3f CORAL = new Vector3f(1.0f, 0.5f, 0.31f);
        public static final Vector3f TOMATO = new Vector3f(1.0f, 0.39f, 0.28f);
        public static final Vector3f PEACH_PUFF = new Vector3f(1.0f, 0.85f, 0.72f);

        // Other Interesting Colors
        public static final Vector3f MINT_CREAM = new Vector3f(0.96f, 1.0f, 0.98f);
        public static final Vector3f HONEYDEW = new Vector3f(0.94f, 1.0f, 0.94f);
        public static final Vector3f IVORY = new Vector3f(1.0f, 1.0f, 0.94f);
        public static final Vector3f SNOW = new Vector3f(1.0f, 0.98f, 0.98f);
        public static final Vector3f LIGHT_GOLDENROD = new Vector3f(0.98f, 0.98f, 0.82f);



    public DustCTPHelper(Vector3f fromColor, Vector3f toColor, float scale) {
        super(fromColor, toColor, scale);
    }
}

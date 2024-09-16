package net.polpo.arcanistsparagon.util;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.polpo.arcanistsparagon.ArcanistsParagon;

import java.util.Dictionary;
import java.util.Hashtable;

public class ArcanistsParagonParticleDecoder {
    public static int NOTE = 1;
    public static int PORTAL = 2;
    public static int HONEY = 3;
    public static int INK = 4;

    public Dictionary<Integer, DefaultParticleType> particleDict;

    // Constructor to initialize the dictionary
    public ArcanistsParagonParticleDecoder() {
        particleDict = new Hashtable<>();
        particleDict.put(NOTE, ParticleTypes.NOTE.getType());   // Ensure you're using the correct enum/class
        particleDict.put(PORTAL, ParticleTypes.PORTAL.getType());
        particleDict.put(HONEY, ParticleTypes.DRIPPING_HONEY.getType());
        particleDict.put(INK, ParticleTypes.SQUID_INK.getType());
    }

    public DefaultParticleType decode(int index) {
        DefaultParticleType type = particleDict.get(index);
        if (type != null) {
            ArcanistsParagon.LOGGER.info("decoded particle: " + type.toString());
        } else {
            ArcanistsParagon.LOGGER.info("Particle not found for index: " + index);
        }
        return type;
    }
}


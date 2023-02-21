package com.filipswiszcz.world;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;

public class Earth implements Generator {

    @Override
    public void generate(GenerationUnit unit) {
        final Point start = unit.absoluteStart();
        final Point size = unit.size();
        for (int x = 0; x < size.blockX(); x++) {
            for (int z = 0; z < size.blockZ(); z++) {
                for (int y = 0; y < Math.min(40 - start.blockY(), size.blockY()); y++) {
                    unit.modifier().setBlock(start.add(x, y, z), Block.GRASS);
                }
            }
        }
    }
    
}

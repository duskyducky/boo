package com.boo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;

public class BooClient implements ClientModInitializer {
    private static final Identifier JUMP_ID = Identifier.of("boo", "jump_boost");
    // ADD_MULTIPLIED_TOTAL with 0.2078 => jump strength = base * 1.2078,
    // which raises the jump peak by about 0.5 blocks (1.25 -> 1.75)
    private static final double BONUS = 0.2078;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            EntityAttributeInstance jump = client.player.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);
            if (jump != null && !jump.hasModifier(JUMP_ID)) {
                jump.addTemporaryModifier(new EntityAttributeModifier(
                        JUMP_ID, BONUS, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
        });
    }
}

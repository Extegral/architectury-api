/*
 * This file is part of architectury.
 * Copyright (C) 2020 shedaniel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.shedaniel.architectury.event.forge;

import me.shedaniel.architectury.event.Actor;
import me.shedaniel.architectury.event.Event;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.MinecraftForge;

import java.util.function.Consumer;

public class EventFactoryImpl {
    public static <T> Event<Consumer<T>> attachToForge(Event<Consumer<T>> event) {
        event.register(eventObj -> {
            if (!(eventObj instanceof net.minecraftforge.eventbus.api.Event)) {
                throw new ClassCastException(eventObj.getClass() + " is not an instance of forge Event!");
            }
            MinecraftForge.EVENT_BUS.post((net.minecraftforge.eventbus.api.Event) eventObj);
        });
        return event;
    }
    
    public static <T> Event<Actor<T>> attachToForgeActor(Event<Actor<T>> event) {
        event.register(eventObj -> {
            if (!(eventObj instanceof net.minecraftforge.eventbus.api.Event)) {
                throw new ClassCastException(eventObj.getClass() + " is not an instance of forge Event!");
            }
            MinecraftForge.EVENT_BUS.post((net.minecraftforge.eventbus.api.Event) eventObj);
            return InteractionResult.PASS;
        });
        return event;
    }
    
    public static <T> Event<Actor<T>> attachToForgeActorCancellable(Event<Actor<T>> event) {
        event.register(eventObj -> {
            if (!(eventObj instanceof net.minecraftforge.eventbus.api.Event)) {
                throw new ClassCastException(eventObj.getClass() + " is not an instance of forge Event!");
            }
            if (!((net.minecraftforge.eventbus.api.Event) eventObj).isCancelable()) {
                throw new ClassCastException(eventObj.getClass() + " is not cancellable Event!");
            }
            if (MinecraftForge.EVENT_BUS.post((net.minecraftforge.eventbus.api.Event) eventObj)) {
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });
        return event;
    }
}
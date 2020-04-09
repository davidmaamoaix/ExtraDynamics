package cn.davidma.extradynamics.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.IStateHolder;
import net.minecraft.util.Direction;
import net.minecraft.util.MapPopulator;

public class StateHelper {
	
	private static final Direction[] DIRECTIONS = Direction.values();
		
	public static Map<Direction, BooleanProperty> directionBooleanMap(String name) {
		return MapPopulator.createMap(
				Arrays.asList(DIRECTIONS),
				Arrays.asList(DIRECTIONS).stream().map(
						dir -> BooleanProperty.create(name + "_" + dir.name().toLowerCase())
				).collect(Collectors.toList())
		);
	}
	
	public static <T extends IStateHolder<T>, V extends Comparable<V>> T
			populateState(T state, List<IProperty<V>> properties, V value) {	
		
		for (IProperty<V> i: properties) {
			state = state.with(i, value);
		}
		
		return state;
	}
}

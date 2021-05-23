package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.entity.EntityType;

public class TypeEntityType extends TypeEnum<EntityType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeEntityType i = new TypeEntityType();
	public static TypeEntityType get() { return i; }
	public TypeEntityType()
	{
		super(EntityType.class);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Set<String> getNamesInner(EntityType value)
	{
		Set<String> ret = new MassiveSet<>(super.getNamesInner(value));
		
		if (value == EntityType.ZOMBIFIED_PIGLIN)
		{
			ret.add("pigzombie");
			ret.add("zombiepig");
			ret.add("zombiepiglin");
		}
		else if (value == EntityType.PIGLIN)
		{
			ret.add("piglin");
			ret.add("manpig");
			ret.add("pigman");
		}
		
		return ret;
	}

}

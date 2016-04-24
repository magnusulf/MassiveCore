package com.massivecraft.massivecore.item;

public class WriterItemStackMeta extends WriterAbstractItemStackMeta<Object, Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMeta i = new WriterItemStackMeta();
	public static WriterItemStackMeta get() { return i; }
	
	// -------------------------------------------- //
	// ACTIVE
	// -------------------------------------------- //
	
	@Override
	public void setActiveInner(boolean active)
	{
		if ( ! active) return;
		this.clearWriters();
		
		this.addWriters(
			// UNSPECIFIC
			WriterItemStackMetaName.class,
			WriterItemStackMetaLore.class,
			WriterItemStackMetaEnchants.class,
			WriterItemStackMetaRepaircost.class,
			
			// BOOK
			WriterItemStackMetaTitle.class,
			WriterItemStackMetaAuthor.class,
			WriterItemStackMetaPages.class,
			
			// LEATHER ARMOR
			WriterItemStackMetaColor.class,
			
			// MAP
			WriterItemStackMetaScaling.class,
			
			// POTION EFFECTS
			WriterItemStackMetaPotionEffects.class,
			
			// SKULL
			WriterItemStackMetaSkull17.class,
			WriterItemStackMetaSkull18.class,
			
			// FIREWORK EFFECT
			WriterItemStackMetaFireworkEffect.class,
			
			// FIREWORK
			WriterItemStackMetaFireworkEffects.class,
			WriterItemStackMetaFireworkFlight.class,
			
			// STORED ENCHANTS
			WriterItemStackMetaStoredEnchants.class,
			
			// UNBREAKABLE
			WriterItemStackMetaUnbreakable.class,
			
			// FLAGS
			WriterItemStackMetaFlags.class,
			
			// BANNER BASE
			WriterItemStackMetaBannerBase.class,
			// TODO: Shield?
			
			// BANNER PATTERNS
			WriterItemStackMetaBannerPatterns.class
			// TODO
			// TODO: Shield?
			
			// POTION
			// TODO			
		);
	}
	
}
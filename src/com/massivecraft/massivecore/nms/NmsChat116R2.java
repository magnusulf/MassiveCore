package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class NmsChat116R2 extends NmsChatAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static NmsChat116R2 i = new NmsChat116R2();
	public static NmsChat116R2 get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected Class<?> classChatMessageType;
	protected Object instanceChatMessageTypeGameInfo;
	protected Object instanceChatMessageTypeChatInfo;
	protected Class<?> classUUID;
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classChatSerializer = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");
		this.methodChatSerializer = ReflectionUtil.getMethod(this.classChatSerializer, "a", String.class);
		this.classEnumTitleAction = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");
		this.classUUID = UUID.class;
		this.classChatMessageType = PackageType.MINECRAFT_SERVER.getClass("ChatMessageType");
		this.instanceChatMessageTypeGameInfo = this.classChatMessageType.getEnumConstants()[2];
		this.instanceChatMessageTypeChatInfo = this.classChatMessageType.getEnumConstants()[0];
		// CHAT(0),
		// SYSTEM(1),
		// GAME_INFO(2);

		//setupCommon();
		for (Object object : this.classEnumTitleAction.getEnumConstants())
		{
			Enum<?> e = (Enum<?>) object;
			if (e.name().equalsIgnoreCase("TITLE")) this.enumEnumTitleActionMain = e;
			else if (e.name().equalsIgnoreCase("SUBTITLE")) this.enumEnumTitleActionSub = e;
			else if (e.name().equalsIgnoreCase("TIMES")) this.enumEnumTitleActionTimes = e;
		}

		this.classIChatBaseComponent = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent");

		// Get title packet and it's constructor
		this.classPacketPlayOutTitle = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle");
		this.constructorPacketPlayOutTitle = ReflectionUtil.getConstructor(this.classPacketPlayOutTitle, this.classEnumTitleAction, this.classIChatBaseComponent);
		this.constructorPacketPlayOutTitleTimes = ReflectionUtil.getConstructor(this.classPacketPlayOutTitle, this.classEnumTitleAction, this.classIChatBaseComponent, int.class, int.class, int.class);

		// Get Chat packet and it's constructor
		this.classPacketPlayOutChat = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat");
		this.constructorPacketPlayOutChat = ReflectionUtil.getConstructor(this.classPacketPlayOutChat, this.classIChatBaseComponent, this.classChatMessageType, this.classUUID);
		this.constructorPacketPlayOutChatType = ReflectionUtil.getConstructor(this.classPacketPlayOutChat, this.classIChatBaseComponent, this.classChatMessageType, this.classUUID);
	}
	
	// -------------------------------------------- //
	// CHAT
	// -------------------------------------------- //

	@Override
	public void sendChatMson(Object sendeeObject, Mson mson)
	{
		CommandSender sendee = IdUtil.getSender(sendeeObject);
		if (sendee == null) return;

		if (sendee instanceof Player)
		{
			Player player = (Player)sendee;
			String raw = mson.toRaw();
			Object component = toComponent(raw);
			Object packet = ReflectionUtil.invokeConstructor(this.constructorPacketPlayOutChat, component, this.instanceChatMessageTypeChatInfo, player.getUniqueId());//??? which typegameinfo?
			NmsBasics.get().sendPacket(player, packet);
		}
		else
		{
			String plain = mson.toPlain(true);
			this.sendChatPlain(sendee, plain);
		}
	}

	// -------------------------------------------- //
	// ACTIONBAR
	// -------------------------------------------- //
	@Override
	public void sendActionbarRaw(Object sendeeObject, String raw)
	{
		Player player = IdUtil.getPlayer(sendeeObject);
		if (player == null) return;

		Object component = toComponent(raw);
		Object packet = this.constructActionBarPacket(component, player.getUniqueId());
		NmsBasics.get().sendPacket(player, packet);
	}

	public <T> T constructActionBarPacket(Object component, Object uuid) {
		return ReflectionUtil.invokeConstructor(this.constructorPacketPlayOutChatType, component, this.instanceChatMessageTypeGameInfo, uuid);
	}
	
}

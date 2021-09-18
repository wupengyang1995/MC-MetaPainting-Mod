package net.metacraft.mod;


import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.metacraft.mod.painting.MetaDecorationItem;
import net.metacraft.mod.painting.MetaPaintingEntity;
import net.metacraft.mod.renderer.MapRenderer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class PaintingModInitializer implements net.fabricmc.api.ModInitializer {

	private static final String ENTITY_TYPE_ID = "entity_meta_painting";

	private static final String ITEM_NAMESPACE = "item_meta_painting";

	public static final EntityType<MetaPaintingEntity> ENTITY_TYPE_META_PAINTING = Registry.register(
			Registry.ENTITY_TYPE,
			ENTITY_TYPE_ID,
			EntityType.Builder.create(MetaPaintingEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.5F).maxTrackingRange(10).trackingTickInterval(2147483647).build(ENTITY_TYPE_ID));;

	public static final Item ITEM_META_PAINTING = Registry.register(Registry.ITEM, new Identifier("metapainting", "meta_painting"), new MetaDecorationItem(ENTITY_TYPE_META_PAINTING, (new Item.Settings()).maxCount(1).group(ItemGroup.DECORATIONS)));

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(CommandManager.literal("createMetaPainting")
					.requires(source -> source.hasPermissionLevel(1))
							.then(CommandManager.argument("url", StringArgumentType.greedyString())
									.executes(this::createMap)));
		});
	}

	private int createMap(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerCommandSource source = context.getSource();
		PlayerEntity player = source.getPlayer();
		String input = StringArgumentType.getString(context, "url");

		source.sendFeedback(new LiteralText("creating meta painting for url..."), false);
		BufferedImage image;
		try {
			if (isValid(input)) {
				URL url = new URL(input);
				URLConnection connection = url.openConnection();
				connection.setRequestProperty("User-Agent", "the meta painting mod");
				connection.connect();
				image = ImageIO.read(connection.getInputStream());
			} else {
				File file = new File(input);
				image = ImageIO.read(file);
			}
		} catch (IOException e) {
			source.sendFeedback(new LiteralText("That doesn't seem to be a valid image."), false);
			return 0;
		}

		if (image == null) {
			source.sendFeedback(new LiteralText("That doesn't seem to be a valid image."), false);
			return 0;
		}

		source.sendFeedback(new LiteralText("success!"), false);
		ItemStack stack = new ItemStack(ITEM_META_PAINTING);
		((MetaDecorationItem) ITEM_META_PAINTING).saveColor(stack, MapRenderer.render(image));
        if (!player.getInventory().insertStack(stack)) {
			player.dropItem(stack, false);
        }
		return 1;
	}



	private static boolean isValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

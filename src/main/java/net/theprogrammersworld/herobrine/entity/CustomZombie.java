package net.theprogrammersworld.herobrine.entity;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.World;
import net.theprogrammersworld.herobrine.Herobrine;

public class CustomZombie extends net.minecraft.world.entity.monster.EntityZombie implements CustomEntity {

	private MobType mobType = null;
	
	public CustomZombie(EntityTypes<? extends Entity> entitytypes, World world) {
		super(EntityTypes.ZOMBIE, world);
	}

	public CustomZombie(World world, Location loc, MobType mbt) {
		super(EntityTypes.ZOMBIE, world);
		this.mobType = mbt;
		if (mbt == MobType.ARTIFACT_GUARDIAN) {
			spawnArtifactGuardian(loc);
		} else if (mbt == MobType.HEROBRINE_WARRIOR) {
			spawnHerobrineWarrior(loc);
		}
	}

	private void spawnArtifactGuardian(Location loc) {

		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Herobrine.getPluginCore().getConfigDB().npc.getDouble("npc.Guardian.Speed"));
		this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(Herobrine.getPluginCore().getConfigDB().npc.getInt("npc.Guardian.HP"));
		this.setHealth(Herobrine.getPluginCore().getConfigDB().npc.getInt("npc.Guardian.HP"));

		this.setCustomName(new ChatComponentText("Artifact Guardian"));

		Zombie entityCast = (Zombie) this.getBukkitEntity();

		entityCast.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD, 1));
		entityCast.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
		entityCast.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE, 1));
		entityCast.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS, 1));
		entityCast.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS, 1));

		this.getBukkitEntity().teleport(loc);

	}

	private void spawnHerobrineWarrior(Location loc) {

		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Herobrine.getPluginCore().getConfigDB().npc.getDouble("npc.Warrior.Speed"));
		this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(Herobrine.getPluginCore().getConfigDB().npc.getInt("npc.Warrior.HP"));
		this.setHealth(Herobrine.getPluginCore().getConfigDB().npc.getInt("npc.Warrior.HP"));

		this.setCustomName(new ChatComponentText("Herobrine Warrior"));

		Zombie entityCast = (Zombie) this.getBukkitEntity();

		entityCast.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1));
		entityCast.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
		entityCast.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
		entityCast.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
		entityCast.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));

		this.getBukkitEntity().teleport(loc);

	}

	public CustomZombie(World world) {
		super(EntityTypes.ZOMBIE, world);
		mobType = null;
	}

	@Override
	public void Kill() {
		String mobS = "";
		if (mobType == MobType.ARTIFACT_GUARDIAN)
			mobS = "Guardian";
		else
			mobS = "Warrior";
		
		Object[] items = Herobrine.getPluginCore().getConfigDB().npc.getConfigurationSection("npc." + mobS + ".Drops")
				.getKeys(false).toArray();
		for (Object itemObj : items) {
			final String item = itemObj.toString();
			final int chance = new Random().nextInt(100);
			if (chance <= Herobrine.getPluginCore().getConfigDB().npc.getInt("npc." + mobS + ".Drops." + item + ".Chance")) {
				getBukkitEntity().getLocation().getWorld().dropItemNaturally(getBukkitEntity().getLocation(),
						new ItemStack(Material.matchMaterial(item), Herobrine.getPluginCore().getConfigDB().npc
								.getInt("npc." + mobS + ".Drops." + item + ".Count")));
			}
		}
		setHealth(0.0f);
	}

	@Override
	public MobType getMobType() {
		return this.mobType;
	}

}

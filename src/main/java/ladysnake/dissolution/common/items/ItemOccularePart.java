package ladysnake.dissolution.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemOccularePart extends Item implements ICustomLocation {

	private static int currentId;
	
	private final int id;

	public ItemOccularePart() {
		this(0);
	}

	public ItemOccularePart(int durability) {
		super();
		this.id = currentId++;
		this.setMaxDamage(durability);
	}

	public int getId() {
		return id;
	}

	@Override
	public ModelResourceLocation getModelLocation(Item item) {
		assert item.getRegistryName() != null;
		return new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":occularia_parts/" + item.getRegistryName().getResourcePath());
	}
}

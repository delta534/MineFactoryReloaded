package powercrystals.minefactoryreloaded.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import powercrystals.minefactoryreloaded.api.IUpgrade;

public class ItemUpgrade extends ItemFactory implements IUpgrade
{
	private static String[] _upgradeNames = { "lapis", "iron", "tin", "copper",
											  "bronze", "silver", "gold", "quartz",
											  "diamond", "platinum", "emerald", "cobble" };
	private static Icon[] _upgradeIcons = new Icon[_upgradeNames.length];
	
	public ItemUpgrade(int id)
	{
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMetaMax(_upgradeNames.length - 1);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		super.addInformation(stack, player, infoList, advancedTooltips);
		infoList.add(StatCollector.translateToLocal("tip.info.mfr.upgrade.radius") + 
				" " + getUpgradeLevel(UpgradeType.RADIUS, stack));
	}
	
	@Override
	public int getUpgradeLevel(UpgradeType type, ItemStack stack)
	{
		if (type != UpgradeType.RADIUS)
			return 0;
		
		int dmg = stack.getItemDamage();
		switch (dmg)
		{
		case 11:
			return -1;
		default:
			return dmg + 1;
		}
	}
	
	@Override
	public boolean isApplicableFor(UpgradeType type, ItemStack stack)
	{
		return type == UpgradeType.RADIUS;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack s)
	{
		int md = Math.min(s.getItemDamage(), _upgradeNames.length);
		return getUnlocalizedName() + "." + _upgradeNames[md];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister ir)
	{
		for(int i = 0; i < _upgradeIcons.length; i++)
		{
			_upgradeIcons[i] = ir.registerIcon("minefactoryreloaded:" + getUnlocalizedName() + "." + _upgradeNames[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta)
	{
		meta = Math.min(meta, _upgradeIcons.length);
		return _upgradeIcons[meta];
	}
}

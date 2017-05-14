package ladysnake.dissolution.common.entity;

import ladysnake.dissolution.common.entity.ai.EntityAIMinionAttack;
import ladysnake.dissolution.common.entity.ai.EntityAIMinionRangedAttack;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityMinionStray extends EntityMinionSkeleton {

	public EntityMinionStray(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIMinionRangedAttack(this, 1.0D, 20, 15.0F));
		this.tasks.addTask(3, new EntityAIMinionAttack(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		//this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.applyEntityAI();
	}
	
	@Override
	protected EntityTippedArrow getArrow(float p_190726_1_) {
		EntityTippedArrow ret = super.getArrow(p_190726_1_);
		ret.addEffect(new PotionEffect(MobEffects.SLOWNESS, 600));
		return ret;
	}

}

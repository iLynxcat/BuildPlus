package me.ilynxcat.mc.buildplus.experience

import org.bukkit.entity.Player

fun Player.deductExpForAction(action: ExperienceActionType) {
    val player = this
    val amount = action.deductionModifier
    
    if(!player.canAffordAction(action))
        throw NotEnoughExperienceError()

    if(player.exp == 0.0f) {
        player.level -= 1
        player.exp = 1.0f - amount
        return
    }

    var newExp = player.exp - amount

    if(newExp < 0f)
        newExp = 0f

    player.exp = newExp
}

fun Player.canAffordAction(action: ExperienceActionType): Boolean {
    return this.level > 0 || this.exp >= action.deductionModifier
}

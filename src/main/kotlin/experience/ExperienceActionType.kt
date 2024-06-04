package me.ilynxcat.mc.buildplus.experience

enum class ExperienceActionType(val deductionModifier: Float) {
    /** When player moves 1 block whilst flying in survival mode. */
    MOVE_WHILE_FLYING(0.002f),
    /** When player uses instant break on a block. */
    INSTANT_BREAK_BLOCK(0.0000f),
    /** When player is in build mode underwater and runs out of oxygen ("bubble bar" is empty). */
    BUBBLE_BAR_OVERRUN(0.00f)
}

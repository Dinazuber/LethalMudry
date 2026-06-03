package lethalmudry.Enemies

import ch.hevs.gdx2d.lethalmudry.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import lethalmudry.Counter

/**
 * Create a spider in the game
 * @param hp the total life of the spider
 * @param posX the X position of the spider
 * @param posY the Y position of the spider
 * @param damage the number of damage that does the spider to the player
 * @param width the width of the sprite
 * @param height the height of the sprite
 */
class Spider(hp: Float, posX: Float, posY: Float, width: Float, height: Float, texture: Texture)
  extends Enemies(hp, posX, posY, width, height, texture) {
  override def attack(healthBar: ProgressBar): Unit = {
    println(s"The spider just attacked you!")
    healthBar.setValue(healthBar.getValue - 10)
  }

  override def trackPlayer(player: Player): Unit = {

  }

  override def render(g: GdxGraphics): Unit = super.render(g)
}
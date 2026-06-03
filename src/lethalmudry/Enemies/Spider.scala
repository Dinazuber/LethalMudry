package lethalmudry.Enemies

import ch.hevs.gdx2d.lethalmudry.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar

/**
 * Create a spider in the game
 * @param hp the total life of the spider
 * @param posX the X position of the spider
 * @param posY the Y position of the spider
 * @param damage the number of damage that does the spider to the player
 * @param width the width of the sprite
 * @param height the height of the sprite
 */
class Spider(hp: Float, posX: Float, posY: Float, damage: Float, width: Float, height: Float, texture: Texture)
  extends Enemies(hp, posX, posY, damage, width, height, texture) {
  override def attack(healthBar: ProgressBar): Unit = {
    println(s"The spider just attacked you!")
    healthBar.setValue(healthBar.getValue - damage)
  }

  override def trackPlayer(player: Player): Unit = {

  }

  def render(g: GdxGraphics): Unit = {
    g.draw(texture, posX, posY, width, height)
  }
}
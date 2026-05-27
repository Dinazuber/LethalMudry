package objects

import ch.hevs.gdx2d.lethalmudry.{LethalMudry, Player}
import com.badlogic.gdx.graphics.Texture

class Bolt(x: Float, y: Float, texture: Texture, width: Float, height: Float) extends Object(x, y, width, height, texture){
  override def collect(player: Player, game: LethalMudry): Unit = {
    val currentHealth = game.healthBar.getValue
    game.healthBar.setValue(math.min(100f, currentHealth + 20f))
    println("Heal restored")
  }
}

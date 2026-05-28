package objects

import ch.hevs.gdx2d.lethalmudry.{LethalMudry, Player}
import com.badlogic.gdx.graphics.Texture

class Water(x: Float, y: Float, texture: Texture, width: Float, height: Float) extends Object(x, y, width, height, texture){
  override def collect(player: Player, game: LethalMudry): Unit = {
    val currentInventory = game.inventoryBar.getValue
    game.inventoryBar.setValue(currentInventory + 20f)
    println("Bolt collected")
  }
}

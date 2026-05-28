package objects

import ch.hevs.gdx2d.lethalmudry.{LethalMudry, Player}
import com.badlogic.gdx.graphics.Texture

class Bolt(x: Float, y: Float, texture: Texture, width: Float, height: Float) extends Object(x, y, width, height, texture){
  override def collect(player: Player, game: LethalMudry): Unit = {
    val currentInventory = game.inventoryBar.getValue
    println(s"the user has : ${currentInventory}")
    game.inventoryBar.setValue(currentInventory + 10f)
    println(s"new stock is : ${game.inventoryBar.getValue}")
    println("Bolt collected")
  }
}

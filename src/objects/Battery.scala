package objects

import ch.hevs.gdx2d.lethalmudry.{LethalMudry, Player}
import com.badlogic.gdx.graphics.Texture
import lethalmudry.Light

class Battery(x: Float, y:Float, texture: Texture, width: Float, height: Float) extends Object(x, y, width, height, texture) {
  override def collect(player: Player, game: LethalMudry): Unit = {
    var currentBattery = game.lightBar.getValue
    game.lightBar.setValue(math.min(1.0f, currentBattery + 0.25f))
    println("Battery recharged")
  }
}

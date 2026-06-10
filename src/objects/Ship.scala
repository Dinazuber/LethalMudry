package objects

import ch.hevs.gdx2d.lethalmudry.{LethalMudry, Player}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle


class Ship(posX: Float,
           posY: Float,
           width: Float,
           height: Float,
          texture: Texture) extends Object(posX,posY,width,height,texture) {

  override def collect(player: Player, game: LethalMudry): Unit = {
    game.quotaBar.setValue(game.quotaBar.getValue + game.inventoryBar.getValue)
    game.inventoryBar.setValue(0)
    println("Objects returned successfully")
  }

}
package objects

import ch.hevs.gdx2d.lethalmudry.{LethalMudry, Player}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

abstract class Object(posX: Float,
                      posY: Float,
                      width: Float,
                      height: Float,
                      texture: Texture) {

  val hitbox: Rectangle = new Rectangle(posX, posY, width, height)


  def collect(player: Player, game: LethalMudry): Unit = {

  }

  def render(g: GdxGraphics): Unit = {
    g.draw(texture, posX, posY, width, height)
  }

}

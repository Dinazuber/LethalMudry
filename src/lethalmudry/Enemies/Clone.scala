package lethalmudry.Enemies

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import lethalmudry.Counter

class Clone(hp: Float, posX: Float, posY: Float, width: Float, height: Float, texture: Texture)
  extends Enemies(hp, posX, posY, width, height, texture) {

  override def attack(healthBar: ProgressBar): Unit = {
    healthBar.setValue(healthBar.getValue - 10)
  }

  override def render(g: GdxGraphics): Unit = super.render(g)
}
package lethalmudry.Enemies

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import lethalmudry.Counter

class Wolf(hp: Float, posX: Float, posY: Float, width: Float, height: Float, texture: Texture)
  extends Enemies(hp, posX, posY, width, height, 1f, texture) {

  override def attack(healthBar: ProgressBar): Unit = {
    healthBar.setValue(healthBar.getValue - 25)
  }

  override def render(g: GdxGraphics): Unit = super.render(g)
}
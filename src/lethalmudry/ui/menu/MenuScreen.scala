package lethalmudry.ui.menu

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color

class MenuScreen extends RenderingScreen {
  override def onInit(): Unit = {}

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.DARK_GRAY)
    g.drawStringCentered((g.getScreenHeight / 2).toFloat, "Welcome to LethalMudry")
    g.drawStringCentered((g.getScreenHeight / 4).toFloat, "Press mouse left click to play")

  }

  override def dispose(): Unit = {
    PhysicsWorld.dispose()
    super.dispose()
  }
}
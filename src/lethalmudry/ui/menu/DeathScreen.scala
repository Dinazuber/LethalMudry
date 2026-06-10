package lethalmudry.ui.menu

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.{Color}

class DeathScreen extends RenderingScreen {

  protected var imgBackground: BitmapImage = _

  override def onInit(): Unit = {
    imgBackground = new BitmapImage("data/images/GameOver.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Reset the camera to the center of the screen
    val camera = g.getCamera
    camera.position.set(g.getScreenWidth / 2f, g.getScreenHeight / 2f, 0)
    camera.zoom = 1f
    camera.update()

    g.clear(Color.BLACK)
    g.setColor(Color.WHITE)
    g.drawPicture(g.getScreenWidth / 2, g.getScreenHeight / 2, imgBackground)
    g.drawStringCentered((g.getScreenHeight / 3).toFloat, "Press Enter to play")
  }

  override def dispose(): Unit = {
    PhysicsWorld.dispose()
    super.dispose()
  }
}
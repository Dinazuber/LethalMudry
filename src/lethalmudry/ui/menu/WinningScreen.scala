package lethalmudry.ui.menu

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color

class WinningScreen extends RenderingScreen {

  protected var imgBackground: BitmapImage = _

  override def onInit(): Unit = {
    imgBackground = new BitmapImage("data/images/WinningScreen.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.BLACK)
    g.drawPicture(g.getScreenWidth /2, g.getScreenHeight / 2, imgBackground)
  }

  override def dispose(): Unit = {
    PhysicsWorld.dispose()
    super.dispose()
  }
}
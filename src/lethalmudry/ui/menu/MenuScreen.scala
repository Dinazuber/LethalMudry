package lethalmudry.ui.menu

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.{Color}

class MenuScreen extends RenderingScreen {

  protected var imgBackground: BitmapImage = _

  override def onInit(): Unit = {
    imgBackground = new BitmapImage("data/images/backgroundMenu.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    onInit()
    g.clear(Color.BLACK)
    g.drawPicture((g.getScreenWidth / 4).toFloat, (g.getScreenHeight / 2).toFloat, imgBackground)
    g.drawPicture((g.getScreenWidth / 1.2).toFloat, (g.getScreenHeight / 2).toFloat, imgBackground)
    g.drawStringCentered((g.getScreenHeight / 2).toFloat, "Welcome to LethalMudry")
    g.drawStringCentered((g.getScreenHeight / 3).toFloat, "Press Enter to play")
  }

  override def dispose(): Unit = {
    PhysicsWorld.dispose()
    super.dispose()
  }
}
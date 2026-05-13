package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.components.bitmaps.{BitmapImage, Spritesheet}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import MovementLogic.MovementLogic
import GameAssets.GameAssets
import ch.hevs.gdx2d.hello.LevelManager.LevelManager
import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * Hello World demo in Scala
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */

object HelloWorldScala {

  def main(args: Array[String]): Unit = {
    new HelloWorldScala
  }
}

class HelloWorldScala extends PortableApplication(1920, 1080) {
  private var imgBitmap: BitmapImage = _
  private var imgBackground: BitmapImage = _
  private var ss: Spritesheet = _
  private val SPRITE_WIDTH = 128
  private val SPRITE_HEIGHT = 128
  val mvLogic: MovementLogic = new MovementLogic
  val assets: GameAssets = new GameAssets
  val levelManager = new LevelManager

  override def onInit(): Unit = {
    setTitle("LethalMudry")

    // Load a custom image (or from the lib "res/lib/icon64.png")
    imgBitmap = new BitmapImage("data/images/ISC_logo.png")
    assets.loadAll()
    assets.manager.finishLoading()
    val loadedMap = assets.getMap()
    levelManager.load(loadedMap)
    ss = new Spritesheet("data/images/lethalCompanyFull.png", SPRITE_WIDTH, SPRITE_HEIGHT)
  }

  /**
   * This method is called periodically by the engine
   *
   * @param g
   */

  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Clears the screen
    g.clear()
    //Draw the FPS
    g.drawFPS()
    //Camera
    val camera: OrthographicCamera = g.getCamera
    g.zoom(0.15f)
    levelManager.render(camera)
    // update MovementLogic
    mvLogic.update()
    // Display the current image of the animation
    g.draw(ss.sprites(mvLogic.line)(mvLogic.col), mvLogic.posX, mvLogic.posY)
  }
}
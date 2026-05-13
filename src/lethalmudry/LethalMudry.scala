package lethalmudry {

  import ch.hevs.gdx2d.components.bitmaps.{BitmapImage, Spritesheet}
  import ch.hevs.gdx2d.lib.GdxGraphics
  import ch.hevs.gdx2d.desktop.PortableApplication
  import com.badlogic.gdx.graphics.OrthographicCamera

  /**
   * LethalMudry - Main application
   *
   * @version 1.0
   */

  object LethalMudry {
    def main(args: Array[String]): Unit = {
      new LethalMudry
    }
  }

  class LethalMudry extends PortableApplication(1920, 1080) {
    private var imgBitmap: BitmapImage = _
    private val SPRITE_WIDTH = 128
    private val SPRITE_HEIGHT = 128
    private var ss: Spritesheet = _

    val mvLogic: MovementLogic = new MovementLogic
    val assets: GameAssets = new GameAssets
    val levelManager: LevelManager = new LevelManager

    override def onInit(): Unit = {
      setTitle("LethalMudry")

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
      g.clear()
      g.drawFPS()

      val camera: OrthographicCamera = g.getCamera
      g.zoom(0.15f)
      levelManager.render(camera)

      mvLogic.update()
      g.draw(ss.sprites(mvLogic.line)(mvLogic.col), mvLogic.posX, mvLogic.posY)
    }
  }
}
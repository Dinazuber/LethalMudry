package ch.hevs.gdx2d.hello

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation
import ch.hevs.gdx2d.components.bitmaps.{BitmapImage, Spritesheet}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication


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

class HelloWorldScala extends PortableApplication {
  private var imgBitmap: BitmapImage = _
  private var imgBackground: BitmapImage = _
  private var ss: Spritesheet = _
  private var FRAME_TIME = 0.15 //Duration of one frame
  private var SPRITE_WIDTH = 64
  private var SPRITE_HEIGHT = 64
  var dt: Float = 0

  var textureX: Int = 0
  var textureY: Int = 1

  override def onInit(): Unit = {
    setTitle("LethalMudry")

    // Load a custom image (or from the lib "res/lib/icon64.png")
    imgBitmap = new BitmapImage("data/images/ISC_logo.png")
    imgBackground = new BitmapImage("data/images/map.png")
    ss = new Spritesheet("data/images/sacha_walking.png", SPRITE_WIDTH, SPRITE_HEIGHT)
  }

  /**
   * Some animation related variables
   */
  private var direction: Int = 1
  private var currentTime: Float = 0
  private var currentFrame: Int = 0
  final private val ANIMATION_LENGTH: Float = 2f // Animation length (in seconds)
  final private val MIN_ANGLE: Float = -20
  final private val MAX_ANGLE: Float = 20
  var nFrames: Int = 4

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
    //Draw background
    g.drawPicture(getWindowWidth /2 , getWindowHeight /2, imgBackground)
    // Compute the angle of the image using an elastic interpolation
    dt += Gdx.graphics.getDeltaTime

    if(dt > FRAME_TIME){
      dt = 0
      currentFrame = (currentFrame + 1) % nFrames
    }

    // Display the current image of the animation
    g.draw(ss.sprites(textureY)(currentFrame), getWindowHeight/2, getWindowHeight/2)
  }

  /**
   * Compute time percentage for making a looping animation
   *
   * @return the current normalized time
   */
  private def computePercentage: Float = {
    if (direction == 1) {
      currentTime += Gdx.graphics.getDeltaTime
      if (currentTime > ANIMATION_LENGTH) {
        currentTime = ANIMATION_LENGTH
        direction *= -1
      }
    }
    else {
      currentTime -= Gdx.graphics.getDeltaTime
      if (currentTime < 0) {
        currentTime = 0
        direction *= -1
      }
    }
    currentTime / ANIMATION_LENGTH
  }
}

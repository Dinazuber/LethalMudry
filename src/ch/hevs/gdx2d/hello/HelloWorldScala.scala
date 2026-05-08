package ch.hevs.gdx2d.hello

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation
import ch.hevs.gdx2d.components.bitmaps.{BitmapImage, Spritesheet}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import com.badlogic.gdx.Input

import java.awt.Frame


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
  private var FRAME_TIME = 0.15f //Duration of one frame
  private var SPRITE_WIDTH = 128
  private var SPRITE_HEIGHT = 128
  var dt: Float = 0

  var textureX: Int = 0
  var textureY: Int = 1

  override def onInit(): Unit = {
    setTitle("LethalMudry")

    // Load a custom image (or from the lib "res/lib/icon64.png")
    imgBitmap = new BitmapImage("data/images/ISC_logo.png")
    imgBackground = new BitmapImage("data/images/map.png")
    ss = new Spritesheet("data/images/lethalcompany.png", SPRITE_WIDTH, SPRITE_HEIGHT)
  }

  /**
   * Some animation related variables
   */
  private var direction: Int = 1
  private var currentTime: Float = 0
  private var currentFrame: Int = 0
  private var isWalking: Boolean = false
  private var line = 0
  private var col = 0
  final private val ANIMATION_LENGTH: Float = 2f // Animation length (in seconds)
  final private val MIN_ANGLE: Float = -20
  final private val MAX_ANGLE: Float = 20
  var nFrames: Int = 4

  var posX: Float = 400.0f      // Position X de départ
  var posY: Float = 300.0f      // Position Y de départ
  val speed: Float = 200.0f     // Vitesse de déplacement (pixels/sec)
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

    val delta = Gdx.graphics.getDeltaTime
    val move = speed * delta
    isWalking = false
    var selectedIdx: Int = 0

    // BAS
    if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
      posY -= move
      line = 0
      col = 0
    }
    // HAUT
    else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
      posY += move
      line = 0
      col = 1
    }
    // GAUCHE
    else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
      posX -= move
      line = 1
      col = 0
    }
    // DROITE
    else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
      posX += move
      line = 1
      col = 1
    }
    if (isWalking){
      // Compute the angle of the image using an elastic interpolation
      dt += delta

      if(dt > FRAME_TIME){
        dt = 0
        currentFrame = (currentFrame + 1) % 2
      }
    }else{
      currentFrame = 0
      dt = 0
    }



    // Display the current image of the animation
    g.draw(ss.sprites(line)(col), posX, posY)
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

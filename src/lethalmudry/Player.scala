package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import lethalmudry.LevelManager

class Player(texture: Texture, startX: Float, startY: Float, level: LevelManager) {
  private val SPRITE_WIDTH  = 90
  private val SPRITE_HEIGHT = 121

  private val FRAMES: Array[Array[TextureRegion]] =
    TextureRegion.split(texture, SPRITE_WIDTH, SPRITE_HEIGHT)

  var x: Float = startX
  var y: Float = startY
  val speed: Float = 150f

  var stateTime: Float     = 0f
  val frameDuration: Float = 0.25f
  val nFrames: Int         = 2

  var currentRow: Int    = 0
  var currentCol: Int    = 0
  var isMoving: Boolean  = false

  val hitWidth   = 32f
  val hitHeight  = 32f
  val hitOffsetX = 32f
  val hitOffsetY = 0f

  def getWidth():  Float = SPRITE_WIDTH
  def getHeight(): Float = SPRITE_HEIGHT

  def update(deltaTime: Float, dx: Float, dy: Float): Unit = {
    if (dx != 0 || dy != 0) {
      isMoving  = true
      stateTime += deltaTime

      if      (dy < 0) { currentRow = 3 }
      else if (dy > 0) { currentRow = 0 }
      else if (dx < 0) { currentRow = 2 }
      else if (dx > 0) { currentRow = 1 }

      currentCol = (stateTime / frameDuration).toInt % nFrames

      val futurX = x + dx * speed * deltaTime
      val futurY = y + dy * speed * deltaTime

      if (!isHitboxColliding(futurX, y)) { x = futurX }
      if (!isHitboxColliding(x, futurY)) { y = futurY }

    } else {
      isMoving   = false
      stateTime  = 0f
      currentCol = 0
    }
  }

  def getPosX(posX: Float): Float = { x = posX; x }
  def getPosY(posY: Float): Float  = { y = posY; y }

  private def isHitboxColliding(testX: Float, testY: Float): Boolean = {
    val boxLeft   = testX + hitOffsetX
    val boxRight  = boxLeft  + hitWidth
    val boxBottom = testY + hitOffsetY
    val boxTop    = boxBottom + hitHeight

    level.collisions(boxLeft,  boxBottom) ||
      level.collisions(boxRight, boxBottom) ||
      level.collisions(boxLeft,  boxTop)    ||
      level.collisions(boxRight, boxTop)
  }

  def render(g: GdxGraphics): Unit = {
    if (currentRow < FRAMES.length && currentCol < FRAMES(currentRow).length) {
      val frame = FRAMES(currentRow)(currentCol)
      g.draw(frame, x, y)
    }
  }
}
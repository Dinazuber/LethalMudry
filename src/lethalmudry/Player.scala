package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import lethalmudry.LevelManager

class Player(texture: Texture, startX: Float, startY: Float, level: LevelManager) {
  private val SPRITE_WIDTH  = 90
  private val SPRITE_HEIGHT = 121
  private var headDirection = 90f //Regarde en haut par défaut

  private val FRAMES: Array[Array[TextureRegion]] =
    TextureRegion.split(texture, SPRITE_WIDTH, SPRITE_HEIGHT)

  var x: Float = startX
  var y: Float = startY
  val speed: Float = 175f

  var stateTime: Float     = 0f
  val frameDuration: Float = 0.25f
  val nFrames: Int         = 2

  var currentRow: Int    = 0
  var currentCol: Int    = 0
  var isMoving: Boolean  = false
  private var hp: Int = 150
  private var isAlive: Boolean = true

  val hitWidth   = 32f
  val hitHeight  = 32f
  val hitOffsetX = 32f
  val hitOffsetY = 0f

  def getWidth():  Float = SPRITE_WIDTH
  def getHeight(): Float = SPRITE_HEIGHT


  /**
   *
   * @return Renvoie la direction de la tête du joueur
   */
  def getHeadDirection(): Float = {
    headDirection
  }

  def getHealth(): Int = {
    hp
  }

  def setHealth(newHp: Int): Unit = {
    hp = newHp
  }

  def isPlayerAlive(): Boolean = {
    isAlive
  }

  def setPlayerAlive(stateAlive: Boolean): Unit = {
    isAlive = stateAlive
  }

  def update(deltaTime: Float, dx: Float, dy: Float): Unit = {
    if (dx != 0 || dy != 0) {
      isMoving  = true
      stateTime += deltaTime

      // Choix de la ligne de la spritesheet selon la direction
      // Adapte ces indices selon ta spritesheet lethalCompanyFull.png
      if      (dy < 0) {
        currentRow = 3 // bas (S)
        headDirection = 270f
      }
      else if (dy > 0) {
        currentRow = 0 // haut (W)
        headDirection = 90f
      }
      else if (dx < 0) {
        currentRow = 2 // gauche (A)
        headDirection = 180f
      }
      else if (dx > 0) {
        currentRow = 1 // droite (D)
        headDirection = 0f
      }

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

  def isHitboxColliding(testX: Float, testY: Float): Boolean = {
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
package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import lethalmudry.LevelManager

class Player(texture: Texture, startX: Float, startY: Float, level: LevelManager) {
  private val SPRITE_WIDTH  = 85
  private val SPRITE_HEIGHT = 120
  private var headDirection = 90f //Regarde en haut par défaut

  // Découpe la spritesheet en grille de TextureRegion
  private val FRAMES: Array[Array[TextureRegion]] =
    TextureRegion.split(texture, SPRITE_WIDTH, SPRITE_HEIGHT)

  var x: Float = startX
  var y: Float = startY
  val speed: Float = 150f

  var stateTime: Float  = 0f
  val frameDuration: Float = 0.25f  // en secondes (était en ms avant, corrigé)
  val nFrames: Int = 2

  // Direction initiale : ligne 0, col 0 (sécurisé)
  var currentRow: Int    = 0
  var currentCol: Int    = 0
  var isMoving: Boolean  = false

  val hitWidth   = 64f
  val hitHeight  = 64f
  val hitOffsetX = 32f
  val hitOffsetY = 0f

  /**
   *
   * @return Renvoie la largeur de la sprite
   */
  def getWidth(): Float = {
    SPRITE_WIDTH
  }

  /**
   *
   * @return Renvoie la hauteur de la sprite
   */
  def getHeight(): Float = {
    SPRITE_HEIGHT
  }


  /**
   *
   * @return Renvoie la direction de la tête du joueur
   */
  def getHeadDirection(): Float = {
    headDirection
  }

  def update(deltaTime: Float, dx: Float, dy: Float): Unit = {
    if (dx != 0 || dy != 0) {
      isMoving = true
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

      // Alterne entre les frames d'animation
      currentCol = (stateTime / frameDuration).toInt % nFrames

      // Calcul du mouvement avec collisions axe par axe
      val futurX = x + dx * speed * deltaTime
      val futurY = y + dy * speed * deltaTime

      if (!isHitboxColliding(futurX, y)) { x = futurX }
      if (!isHitboxColliding(x, futurY)) { y = futurY }

    } else {
      isMoving  = false
      stateTime = 0f
      currentCol = 0  // frame idle
    }
  }

  private def isHitboxColliding(testX: Float, testY: Float): Boolean = {
    val boxLeft   = testX + hitOffsetX
    val boxRight  = boxLeft + hitWidth
    val boxBottom = testY + hitOffsetY
    val boxTop    = boxBottom + hitHeight

    level.collisions(boxLeft,  boxBottom) ||
      level.collisions(boxRight, boxBottom) ||
      level.collisions(boxLeft,  boxTop)    ||
      level.collisions(boxRight, boxTop)
  }

  def render(g: GdxGraphics): Unit = {
    // Vérifie que les indices sont dans les bornes de la spritesheet
    if (currentRow < FRAMES.length && currentCol < FRAMES(currentRow).length) {
      val frame = FRAMES(currentRow)(currentCol)
      g.draw(frame, x, y)
    }
  }
}
package lethalmudry.Enemies
import ch.hevs.gdx2d.lethalmudry.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import lethalmudry.Counter

abstract class Enemies(hp: Float, posX: Float, posY: Float, width: Float, height: Float, texture: Texture) {
  val FRAME_WIDTH = texture.getWidth / 2 //2 column for the sprite
  val FRAME_HEIGHT = texture.getHeight / 4 //4 rows for the sprite

  val regions: Array[Array[TextureRegion]] = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT)

  var currentFrame: TextureRegion = regions(0)(0)
  val range: Float = 300.0f

  var timeOut: Counter = new Counter
  val hitbox: Rectangle = new Rectangle(posX, posY, width, height)


  def attack(healthBar: ProgressBar): Unit = {}

  def startCounterTimeOut(): Unit = {
    timeOut.startCounter()
  }

  def getTimeOut(): Long = {
    timeOut.getStartedTime()
  }

  def resetTime(): Unit = {
    timeOut.resetStartValue()
  }

  def inRange(player: Player): Boolean = {
    //Get diff of X and Y
    val diffX = player.x - hitbox.x
    val diffY = player.y - hitbox.y

    val distCarree = math.pow(diffX, 2) + math.pow(diffY, 2)

    val rangeCarree = math.pow(range, 2)

    return distCarree <= rangeCarree
  }

  def trackPlayer(player: Player): Unit = {
    val diffX = player.x - hitbox.x //diff of X of player and enemy
    val diffY = player.y - hitbox.y //diff of Y of player and enemy
    val speed: Float = 1.5f

    //Update X position
    var nextX = hitbox.x
    if (diffX > 0) nextX += speed
    else if (diffX < 0) nextX -= speed

    if(!player.isHitboxColliding(nextX, hitbox.y)){
      hitbox.x = nextX
    }

    // Update Y position
    var nextY = hitbox.y
    if (diffY > 0) nextY += speed
    else if (diffY < 0) nextY -= speed

    if(!player.isHitboxColliding(hitbox.x, nextY)){
      hitbox.y = nextY
    }

    // we get value absolute of X and Y
    if (math.abs(diffX) > math.abs(diffY)) {
      // horizontal
      if (diffX > 0) currentFrame = regions(1)(0) // Right
      else if (diffX < 0) currentFrame = regions(2)(0) // Left
    } else {
      // vertical
      if (diffY > 0) currentFrame = regions(3)(0) // top
      else if (diffY < 0) currentFrame = regions(0)(0) // bot
    }
  }

  def render(g: GdxGraphics): Unit = {
    g.draw(currentFrame, hitbox.x, hitbox.y, width, height)
  }

  def spawnInOut(startX: Int, startY: Int, spawn: Boolean): Unit = { }

}

package lethalmudry.Enemies
import ch.hevs.gdx2d.lethalmudry.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue.SpawnSide
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import lethalmudry.Counter

abstract class Enemies(hp: Float, posX: Float, posY: Float, width: Float, height: Float, texture: Texture) {
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

  def move(): Unit = { }

  def trackPlayer(player: Player): Unit = { }

  def render(g: GdxGraphics): Unit = {
    g.draw(texture, posX, posY, width, height)
  }

  def spawnInOut(startX: Int, startY: Int, spawn: Boolean): Unit = { }

}

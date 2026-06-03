package lethalmudry.Enemies
import ch.hevs.gdx2d.lethalmudry.Player
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue.SpawnSide
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar

abstract class Enemies(hp: Float, posX: Float, posY: Float, damage: Float, width: Float, height: Float, texture: Texture) {
  val hitbox: Rectangle = new Rectangle(posX, posY, width, height)
  def attack(healthBar: ProgressBar): Unit = {}

  def move(): Unit = { }

  def trackPlayer(player: Player): Unit = { }

  def spawnInOut(startX: Int, startY: Int, spawn: Boolean): Unit = { }

}

package lethalmudry.Enemies
import ch.hevs.gdx2d.lethalmudry.Player
import com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue.SpawnSide

case class Enemies() {
  var hp: Int = 0
  var speed: Int = 0
  var posX: Int = 0
  var posY: Int = 0
  var spawnIn: Boolean = true
  var isAlive: Boolean = true
  var damages: Int = 0
  var visionAngle: Int = 0

  def attack(player: Player): Unit = {
    val playerHp: Int = player.getHealth()
    // Attack the player
    if(playerHp >= 0){
      player.setHealth(playerHp - damages)
    }
    else{
      player.setPlayerAlive(false)
    }
  }

  def move(): Unit = {
    // Move across the map
    while (isAlive) {
      posX += util.Random.nextInt()
      posY += util.Random.nextInt()
    }
  }

  def trackPlayer(): Unit = {
    // Once player seen follows HIM
  }

  def spawnInOut(startX: Int, startY: Int, spawn: Boolean): Unit = {

    if(spawn){
      posX = startX // Depend du type de layout de la map TODO SWEEX
      posY = startY // Depend du type de layout de la map TODO SWEEX
    }
  }

}

package lethalmudry.Ennemies
import ch.hevs.gdx2d.lethalmudry.Player

case class Ennemies() {
  var hp: Int = 0
  var speed: Int = 0
  var posX: Int = 0
  var posY: Int = 0
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

}

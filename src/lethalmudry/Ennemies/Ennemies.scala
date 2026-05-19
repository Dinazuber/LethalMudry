package lethalmudry.Ennemies

trait Ennemies {
  var hp: Int = 0
  var speed: Int = 0
  var posX: Int = 0
  var posY: Int = 0
  var isAlive: Boolean = true
  var damages: Int = 0
  var visionAngle: Int = 0
  var playerHp: Int = 0
  def attack(): Unit = {
    // Attack the player
    ???

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

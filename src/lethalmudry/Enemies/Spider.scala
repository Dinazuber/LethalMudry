package lethalmudry.Enemies

class Spider extends Enemies {
  hp = 50
  speed = 125
  damages = 10
  spawnIn = true
  visionAngle = 50
  visionAngle = (Math.PI * visionAngle * 2).toInt
}
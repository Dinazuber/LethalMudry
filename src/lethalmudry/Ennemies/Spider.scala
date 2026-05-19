package lethalmudry.Ennemies

import ch.hevs.gdx2d.lethalmudry.Player

class Spider extends Ennemies {
  hp = 50
  speed = 125
  damages = 10
  visionAngle = 50
  visionAngle = (Math.PI * visionAngle * 2).toInt
}


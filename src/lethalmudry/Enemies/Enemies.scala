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

  def trackPlayer(player: Player): Unit = {
    println(s"${player.x}/${player.y}")
    var speed: Float = 1.0f //we set a speed for the enemie

    //We adjust the enemy position with the speed
    if(hitbox.x < player.x){
      hitbox.x += speed
      //If the enemy doesnt have animation
      try{
        currentFrame = regions(1)(0)
      }
      println(s"pos x : ${hitbox.x}")
    } else if(hitbox.x > player.x){
      hitbox.x -= speed
      try{
        currentFrame = regions(2)(0)
      }
    }


    //We do the same for the Y axe
    if(hitbox.y < player.y){
      hitbox.y += speed
      try{
        currentFrame = regions(3)(0)
      }
    } else if(hitbox.y > player.y){
      hitbox.y -= speed
      try{
        currentFrame = regions(0)(0)
      }
    }
  }

  def render(g: GdxGraphics): Unit = {
    g.draw(currentFrame, hitbox.x, hitbox.y, width, height)
  }

  def spawnInOut(startX: Int, startY: Int, spawn: Boolean): Unit = { }

}

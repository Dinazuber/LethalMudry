package ch.hevs.gdx2d.hello
package Player{

  import ch.hevs.gdx2d.lib.GdxGraphics
  import ch.hevs.gdx2d.hello.LevelManager.LevelManager
  import com.badlogic.gdx.graphics.Texture
  import com.badlogic.gdx.graphics.g2d.TextureRegion

  class Player(texture: Texture, startX: Float, startY: Float, level: LevelManager) {
    private val SPRITE_WIDTH = 128
    private val SPRITE_HEIGHT = 128
    private val FRAMES: Array[Array[TextureRegion]] = TextureRegion.split(texture,SPRITE_WIDTH,SPRITE_HEIGHT)

    var x: Float = startX
    var y: Float = startY
    val speed: Float = 150

    var stateTime: Float = 0f
    val frameDuration: Float = 250f
    val nFrames: Int = 2

    var currentDirection: Int = 10
    var isMoving: Boolean = false

    val hitWidth = 128
    val hitHeight = 128
    val hitOffsetX = 100f
    val hitOffsetY = 100f


    def update(deltaTime: Float, dx: Float, dy: Float): Unit = {

      if (dx != 0  || dy != 0){
        isMoving = true
        stateTime += deltaTime

        if (dx > 0){currentDirection = 11}       // right
        else if (dx < 0){ currentDirection = 9} // left
        else if (dy > 0){ currentDirection = 8} // up
        else if (dy < 0){ currentDirection = 10} // down

        var futurX = x + dx * speed * deltaTime
        var futurY =y + dy * speed * deltaTime

        if (!isHitboxColliding(futurX, y, level)) {
          x = futurX
        }

        if (!isHitboxColliding(x, futurY, level)) {
          y = futurY
        }

      }else{
        isMoving = false
        stateTime = 0f
      }
    }

    private def isHitboxColliding(testPosX: Float, testPosY: Float, level: LevelManager): Boolean = {
      val boxLeft = testPosX + hitOffsetX
      val boxRight = boxLeft + hitWidth
      val boxBottom = testPosY + hitOffsetY
      val boxTop = boxBottom + hitHeight

      val botLeftHasWall  = level.collisions(boxLeft, boxBottom)
      val botRightHasWall = level.collisions(boxRight, boxBottom)
      val topLeftHasWall  = level.collisions(boxLeft, boxTop)
      val topRightHasWall = level.collisions(boxRight, boxTop)

      botLeftHasWall ||  botRightHasWall || topLeftHasWall || topRightHasWall
    }

    def render(g: GdxGraphics): Unit = {
      val currentFrameIdx = if (isMoving){((stateTime / frameDuration) % nFrames).toInt}
      else{0}

      val frameToDraw = FRAMES(currentDirection)(currentFrameIdx)

      g.draw(frameToDraw, x, y)
    }
  }
}


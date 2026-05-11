import com.badlogic.gdx.Gdx

package MovementLogic {

  class MovementLogic {
    private var dt: Float = 0
    private val FRAME_TIME = 0.15f
    private var direction: Int = 1
    private var currentTime: Float = 0
    private var currentFrame: Int = 0
    private var isWalking: Boolean = false

    // Private backing fields
    private var _line: Int = 0
    private var _col: Int = 0
    private var _posX: Float = 400.0f
    private var _posY: Float = 300.0f

    // --- GETTERS AND SETTERS ---

    // Getter and Setter for line
    def line: Int = _line
    def line_=(newLine: Int): Unit = { _line = newLine }

    // Getter and Setter for col
    def col: Int = _col
    def col_=(newCol: Int): Unit = { _col = newCol }

    // Getter and Setter for posX
    def posX: Float = _posX
    def posX_=(newX: Float): Unit = { _posX = newX }

    // Getter and Setter for posY
    def posY: Float = _posY
    def posY_=(newY: Float): Unit = { _posY = newY }

    final private val ANIMATION_LENGTH: Float = 2f
    val speed: Float = 150.0f
    val toggle = Iterator.continually(List(0, 1)).flatten

    def update(): Unit = {
      val delta = Gdx.graphics.getDeltaTime
      val move = speed * delta
      isWalking = false

      if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
        posY -= move
        line = 0
        col = 0
        isWalking = true
      }
      else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
        posY += move
        line = 0
        col = 1
        isWalking = true
      }
      else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
        posX -= move
        line = 1
        col = toggle.next()
        isWalking = true
      }
      else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
        posX += move
        line = 1
        col = 1
        isWalking = true
      }

      if (isWalking) {
        dt += delta
        if (dt > FRAME_TIME) {
          dt = 0
          currentFrame = (currentFrame + 1) % 2
        }
      } else {
        currentFrame = 0
        dt = 0
      }
    }

    private def computePercentage: Float = {
      if (direction == 1) {
        currentTime += Gdx.graphics.getDeltaTime
        if (currentTime > ANIMATION_LENGTH) {
          currentTime = ANIMATION_LENGTH
          direction *= -1
        }
      } else {
        currentTime -= Gdx.graphics.getDeltaTime
        if (currentTime < 0) {
          currentTime = 0
          direction *= -1
        }
      }
      currentTime / ANIMATION_LENGTH
    }
  }
}
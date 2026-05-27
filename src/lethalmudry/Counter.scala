package lethalmudry

/**
 * Set up a counter to be used for the game
 */
class Counter {
  //The time of the start of the counter
  private var startValueCounter : Long = 0


  /**
   * Start the counter
   */
  def startCounter(): Unit = {
    startValueCounter = System.currentTimeMillis()
  }

  def getStartedTime(): Long = {
    startValueCounter
  }

  /**
   * reset the counter start value and start it again
   */
  def resetStartValue(): Unit = {
    startValueCounter = 0L
    startCounter()
  }
}

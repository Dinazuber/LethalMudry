package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.OrthographicCamera
import lethalmudry.LevelManager
import lethalmudry.Light
import lethalmudry.ui.menu.MenuScreen

/**
 * LethalMudry - Main application
 *
 * @version 1.0
 */

object LethalMudry {
  def main(args: Array[String]): Unit = {
    new LethalMudry
  }
}

class LethalMudry extends PortableApplication(1920, 1080) {
  val assets: GameAssets         = new GameAssets
  val levelManager: LevelManager = new LevelManager
  val menu: MenuScreen           = new MenuScreen
  var player: Player             = _
  var light: Light = _
  var firstRun: Boolean = true
  var menuOn: Boolean = true

  override def onInit(): Unit = {
    setTitle("LethalMudry")

    assets.loadAll()
    assets.manager.finishLoading()

    // Charger la map
    val loadedMap = assets.getMap()
    levelManager.load(loadedMap)

    // Créer le player au centre de la map
    val playerTexture = assets.getPlayerTexture()
    player = new Player(
      playerTexture,
      levelManager.mapPixelWidth / 2,
      levelManager.mapPixelHeight / 6,
      levelManager
    )
    light = new Light(player.x, player.y)
    light.generateLight()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if(menuOn){
      menu.onGraphicRender(g)
      if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
        menuOn = false
      }
      else{
        menuOn = true
      }
    }else{
      g.clear()

      // Inputs
      var dx = 0f
      var dy = 0f
      if (Gdx.input.isKeyPressed(Keys.D)) dx += 1f
      if (Gdx.input.isKeyPressed(Keys.A)) dx -= 1f
      if (Gdx.input.isKeyPressed(Keys.W)) dy += 1f
      if (Gdx.input.isKeyPressed(Keys.S)) dy -= 1f

      // Update
      val delta = Gdx.graphics.getDeltaTime
      player.update(delta, dx, dy)

      // Caméra centrée sur le player
      val camera: OrthographicCamera = g.getCamera
      camera.position.set(player.x + 64, player.y + 64, 0)
      g.zoom(0.25f)
      camera.update()

      // --- Rendu map puis player ---
      levelManager.render(camera)
      player.render(g)

      g.sbFlush()

      // --- Lumière du jeu ---
      //Met à jour le ray handler de la lumière
      light.updateRayHandler(camera)

      //Update la position de la lumière en fonction du joueur
      light.updatePosition(player)

      //Vérifie si le joueur fait click droit
      if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
        light.onClick()
      }

      g.drawFPS()
    }
  }
}
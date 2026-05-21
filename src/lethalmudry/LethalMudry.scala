package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.{TextureAtlas, TextureRegion}
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.utils.viewport.ScreenViewport
import lethalmudry.{Counter, LevelManager, Light}

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
  var player: Player             = _
  var light: Light = _
  var lastClickedTime: Long = 0L

  //Style light
  var atlas: TextureAtlas = _
  var skin: Skin = _
  var lightBar: ProgressBar = _

  var counterManager : Counter = new Counter

  //Stage to add elements on the window
  var stage: Stage = _

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
      levelManager.mapPixelHeight / 2,
      levelManager
    )
    light = new Light(player.x, player.y)
    light.generateLight()

    //Créer la barre de recharge de la lumière et ajouter les styles
    atlas = new TextureAtlas(Gdx.files.internal("data/styles/lightBar/barStyle.atlas"))
    skin = new Skin(Gdx.files.internal("data/styles/lightBar/barStyle.json"),atlas)
    lightBar = new ProgressBar(0f, 1f, 0.01f, false, skin)

    //Met a jour la bar de recharge de la lumière
    lightBar.setPosition(player.x, player.y)
    lightBar.setSize(290f, 200f)
    lightBar.setAnimateDuration(2)
    lightBar.setValue(1f) //La batterie de la lumière est pleine

    //Start one second counter (to decrease light battery
    counterManager.startCounter()

    //Init the stage and config it
    stage = new Stage()
    stage.addActor(lightBar)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    // --- Inputs ---
    var dx = 0f
    var dy = 0f
    if (Gdx.input.isKeyPressed(Keys.D)) dx += 1f
    if (Gdx.input.isKeyPressed(Keys.A)) dx -= 1f
    if (Gdx.input.isKeyPressed(Keys.W)) dy += 1f
    if (Gdx.input.isKeyPressed(Keys.S)) dy -= 1f

    // --- Update ---
    val delta = Gdx.graphics.getDeltaTime
    player.update(delta, dx, dy)

    // --- Caméra centrée sur le player ---
    val camera: OrthographicCamera = g.getCamera
    camera.position.set(player.x + 64, player.y + 64, 0)
    g.zoom(0.25f)
    camera.update()

    // --- Rendu map puis player ---
    levelManager.render(camera)
    player.render(g)

    //On vide le "sac" de dessins avant de passer à la lumière
    g.sbFlush()

    // --- Lumière du jeu ---
    //Met à jour le ray handler de la lumière
    light.updateRayHandler(camera)

    //On check la batterie, s'il en reste ou qu'il y en a qui a été ajouté, on available la light
    if(lightBar.getValue > 0){
      light.avaibleLight()
    }

    //Update la position de la lumière en fonction du joueur
    light.updatePosition(player)

    //Vérifie si le joueur fait click droit
    val currentTime = System.currentTimeMillis()
    if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
      if(light.isAvailable) {
        if (currentTime - lastClickedTime > 200) {
          light.onClick()
          lastClickedTime = currentTime
        }
      }
    }
    //Test to recharge batterie
    if(Gdx.input.isKeyPressed(Input.Keys.C)){
      lightBar.setValue(1f)
    }

    //Si la lumière est active et depuis plus d'une seconde, baisse sa vie
    if(light.lightStatus()){
      if(currentTime - counterManager.getStartedTime() >= 1000){
        counterManager.resetStartValue()
        if(light.isAvailable) {
          if (lightBar.getValue != 0) {
            println("BOOM, ça fait 1 seconde cheh")
            lightBar.setValue(lightBar.getValue - 0.01f)
          } else {
            light.disableLight()
          }
        }
      }
    } else {
      counterManager.resetStartValue()
    }

    //Update the camera of the stage
    stage.getViewport.setCamera(g.getCamera)

    //Update the progress bar position
    lightBar.setPosition(player.x - 170f, player.y - 160f)
    stage.act(delta)
    stage.draw()

    g.drawFPS()
  }
}
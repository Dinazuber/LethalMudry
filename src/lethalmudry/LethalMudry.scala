package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.utils.viewport.ScreenViewport
import lethalmudry.{Counter, LevelManager, Light}

/**
 * LethalMudry - Main application
 *
 * @version 1.0sa
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

  //Style Health Bar
  var healthAtlas: TextureAtlas = _
  var healthSkin: Skin = _
  var healthBar: ProgressBar = _

  //Style light
  var atlas: TextureAtlas = _
  var skin: Skin = _
  var lightBar: ProgressBar = _

  var counterManager : Counter = new Counter

  //Stage to add elements on the window
  var stage: Stage = _

  //Music player
  var music: MusicPlayer = _

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
      levelManager.mapPixelHeight / 10,
      levelManager
    )
    light = new Light(player.x, player.y)
    light.generateLight()

    //Créer la barre de recharge de la lumière et ajouter les styles
    atlas = new TextureAtlas(Gdx.files.internal("data/styles/lightBar/barStyle.atlas"))
    skin = new Skin(Gdx.files.internal("data/styles/lightBar/barStyle.json"),atlas)
    lightBar = new ProgressBar(0f, 1f, 0.01f, false, skin)

    //Met a jour la bar de recharge de la lumière
    lightBar.setPosition(20f, 50f)
    lightBar.setSize(500f, 50f)
    lightBar.setAnimateDuration(0.2f)
    lightBar.setValue(1f) //La batterie de la lumière est pleine

    //Start one second counter (to decrease light battery
    counterManager.startCounter()

    //Créer la barre de vie du personnage
    healthAtlas = new TextureAtlas(Gdx.files.internal("data/styles/healthBar/healthBar.atlas"))
    healthSkin = new Skin(Gdx.files.internal("data/styles/healthBar/healthBar.json"), healthAtlas)

    healthBar = new ProgressBar(0f, 100f, 1f, false, healthSkin, "health")
    healthBar.setSize(500f, 100f)
    healthBar.setAnimateDuration(0.2f)
    healthBar.setValue(100f)

    //Ajouter les positions des bars
    lightBar.setPosition(20f, Gdx.graphics.getHeight - 180f)
    healthBar.setPosition(20f, Gdx.graphics.getHeight - 130f)

    //Init the stage and config it
    stage = new Stage(new ScreenViewport())
    stage.addActor(lightBar)
    stage.addActor(healthBar)

    //Init the music player
    music = new MusicPlayer("data/music/lethalOST.mp3")
    music.play()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
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

    //Test to deal damage
    if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
      healthBar.setValue(healthBar.getValue - 10f)
    }

    //Heal player
    if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
      healthBar.setValue(healthBar.getValue + 10f)
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

    //Met a jour les éléments graphiques du jeu (progressbar)
    stage.getViewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight, true)
    stage.act(delta)
    stage.draw()

    g.drawFPS()
  }
}
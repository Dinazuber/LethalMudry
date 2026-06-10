package ch.hevs.gdx2d.lethalmudry

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.math.Rectangle
import lethalmudry.LevelManager
import lethalmudry.Light
import lethalmudry.ui.menu.MenuScreen
import lethalmudry.ui.menu.DeathScreen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.utils.viewport.ScreenViewport
import lethalmudry.Enemies.{DemonMudry, Enemies, Spider, Wolf}
import lethalmudry.{Counter, LevelManager, Light, PopUp}
import objects.{Battery, Bolt, Heal, Water}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

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
  val menu: MenuScreen           = new MenuScreen
  val gameOver: DeathScreen = new DeathScreen
  var player: Player             = _
  var playerHitBox: Rectangle = _
  var light: Light = _
  var lastClickedTime: Long = 0L
   var menuOn: Boolean = true //If the player is in the menu or not
  var deathOn: Boolean = false //if the player is dead or not

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

  //Barre d'inventaire
  var inventoryAtlas: TextureAtlas = _
  var inventorySkin : Skin = _
  var inventoryBar : ProgressBar = _

  //Game objects
  var objectsList: ArrayBuffer[objects.Object] = new ArrayBuffer[objects.Object]() //Needed to specify the package
  var battery: Battery = _
  var heal: Heal = _
  var bolt: Bolt = _

  //Game objects sprite
  var batteryTexture : Texture = _
  var healTexture : Texture = _
  var boltTexture : Texture = _
  var waterTexture : Texture = _

  //Get enemies textures
  var spiderTexture : Texture = _
  var mudryTexture : Texture = _
  var wolfTexture: Texture = _

  //Objects list
  var spawnableTiles: ArrayBuffer[(Int, Int)] = _

  //Game enemies
  var enemiesList : ArrayBuffer[Enemies] = new ArrayBuffer[Enemies]()
  var mudry: DemonMudry = _

  //Music player
  var music: MusicPlayer = _
  var ouch: MusicPlayer = _

  override def onInit(): Unit = {
    setTitle("LethalMudry")

    assets.loadAll()
    assets.manager.finishLoading()

    //Load all the sprite
    batteryTexture = assets.getBatteryTexture()
    healTexture = assets.getHealTexture()
    boltTexture = assets.getBoltTexture()
    waterTexture = assets.getWaterTexture()

    //Get enemies textures
    spiderTexture = assets.getSpiderTexture()
    mudryTexture = assets.getMudryTexture()
    wolfTexture = assets.getWolfTexture()

    // Charger la map
    val loadedMap = assets.getMap()
    levelManager.load(loadedMap)

    spawnableTiles = levelManager.getListSpawnable()

    //Get the spawnable tiles
    println(spawnableTiles.mkString(","))

    // Créer le player au centre de la map
    val playerTexture = assets.getPlayerTexture()
    player = new Player(
      playerTexture,
      levelManager.mapPixelWidth / 2,
      levelManager.mapPixelHeight / 10,
      levelManager
    )
    playerHitBox = new Rectangle(player.x, player.y, 64f, 64f)

    light = new Light(player.x, player.y)
    light.generateLight()

    spawnRandomObject(spawnableTiles)
    spawnEnemies(spawnableTiles)

    battery = new Battery(player.x + 250f, player.y + 50f, batteryTexture, 32f, 45f)
    heal = new Heal(player.x + 270f, player.y + 234f, healTexture, 32, 45f)
    bolt = new Bolt(player.x + 570f, player.y + 234f, boltTexture, 32, 45f)
    objectsList.append(battery)
    objectsList.append(heal)
    objectsList.append(bolt)

    //Show the first enemy
    mudry = new DemonMudry(20, player.x + 250f, player.y - 50f, 64f, 64f, mudryTexture)
    enemiesList.append(mudry)

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
    for(e <- enemiesList){
      e.startCounterTimeOut()
    }
    //Créer la barre de vie du personnage
    healthAtlas = new TextureAtlas(Gdx.files.internal("data/styles/healthBar/healthBar.atlas"))
    healthSkin = new Skin(Gdx.files.internal("data/styles/healthBar/healthBar.json"), healthAtlas)

    healthBar = new ProgressBar(0f, 100f, 1f, false, healthSkin, "health")
    healthBar.setSize(500f, 100f)
    healthBar.setAnimateDuration(0.2f)
    healthBar.setValue(100f)
    healthBar.getStyle.knobBefore.setMinWidth(0f)

    //Créer la barre d'inventaire du personnage
    inventoryAtlas = new TextureAtlas(Gdx.files.internal("data/styles/inventory/inventoryBar.atlas"))
    inventorySkin = new Skin(Gdx.files.internal("data/styles/inventory/inventoryBar.json"), inventoryAtlas)

    inventoryBar = new ProgressBar(0f, 100f, 1f, false, inventorySkin, "mana")
    inventoryBar.setSize(500f, 30f)
    inventoryBar.setAnimateDuration(0.2f)
    inventoryBar.setValue(0f)
    inventoryBar.getStyle.knobBefore.setMinWidth(0f)

    //Ajouter les positions des bars
    lightBar.setPosition(20f, Gdx.graphics.getHeight - 180f)
    healthBar.setPosition(20f, Gdx.graphics.getHeight - 130f)
    inventoryBar.setPosition((Gdx.graphics.getWidth - inventoryBar.getWidth) /2, 20f)

    //Init the stage and config it
    stage = new Stage(new ScreenViewport())
    stage.addActor(lightBar)
    stage.addActor(healthBar)
    stage.addActor(inventoryBar)

    //Init the music player
    music = new MusicPlayer("data/music/lethalOST.mp3")
    music.play()
    gameOver.onInit()
    menu.onInit()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if(healthBar.getValue <= 0) deathOn = true
    if(menuOn){
      menu.onGraphicRender(g)
      if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
        menuOn = false
      }
      else{
        menuOn = true
      }
    }else if(deathOn) {
      gameOver.onGraphicRender(g)
      if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
        deathOn = false

        //Reset player's position
        player.x = levelManager.mapPixelWidth / 2
        player.y = levelManager.mapPixelHeight / 10

        //Reset progress bar
        healthBar.setValue(100f)
        inventoryBar.setValue(0f)
        lightBar.setValue(100f)

        //Reset objects and enemies spawning
        objectsList.empty
        enemiesList.empty
        spawnRandomObject(spawnableTiles)
        spawnEnemies(spawnableTiles)
      } else {
        deathOn = true
      }
    } else {
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
      playerHitBox.setX(player.x)
      playerHitBox.setY(player.y)

      // Caméra centrée sur le player
      val camera: OrthographicCamera = g.getCamera
      camera.position.set(player.x + 64, player.y + 64, 0)
      //g.zoom(0.25f)
      camera.update()

      // --- Rendu map puis player ---
      levelManager.render(camera)
      player.render(g)

      for (o <- objectsList) {
        o.render(g)
      }

      for(e <- enemiesList){
        e.render(g)
      }

      //Show the first enemy
      for(e <- enemiesList){
        if(e.inRange(player)){
          e.trackPlayer(player)
        }
      }
      //spider.render(g) //and update the render

      //We check if player is in range or not
      if(mudry.inRange(player)){
        mudry.trackPlayer(player)
      }
      mudry.render(g)

      for(e <- enemiesList){
        if(playerHitBox.overlaps(e.hitbox)){
          var currentTime = System.currentTimeMillis()
          if(currentTime - e.getTimeOut() >= 1000){
            e.resetTime()
            println(s"a second past + the ${e.getClass.getSimpleName} attacked the player")
            e.attack(healthBar)
          }
        }
      }

      //   On vide le "sac" de dessins avant de passer à la lumière
        g.sbFlush()
      objectsList.filterInPlace({ o =>
        if (playerHitBox.overlaps(o.hitbox)) {
          if (!o.isInstanceOf[Heal] && !o.isInstanceOf[Battery]) {
            println(s"this objet is an ${o.getClass.getSimpleName}")
            //If the objet in collision is not a heal or a battery
            if (inventoryBar.getValue != 100f) {
              o.collect(player, this)
              new PopUp(s"You collected a ${o.getClass.getSimpleName}", stage)
              false
            } else {
              var notif = new PopUp("Votre inventaire est plein!", stage)
              true
            }
          } else {
            //It's a heal or a battery, so the player can take it
            o.collect(player, this)
            var notif = new PopUp(s"Vous avez ramasser un/e ${o.getClass.getSimpleName}", stage)
            false
          }
        } else {
          true
        }
      })

      // --- Lumière du jeu ---
      //Met à jour le ray handler de la lumière
      light.updateRayHandler(camera)

      //On check la batterie, s'il en reste ou qu'il y en a qui a été ajouté, on available la light
      if (lightBar.getValue > 0) {
        light.avaibleLight()
      }

      //Update la position de la lumière en fonction du joueur
      light.updatePosition(player)

      //Vérifie si le joueur fait click droit
      val currentTime = System.currentTimeMillis()
      if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
        if (light.isAvailable) {
          if (currentTime - lastClickedTime > 200) {
            light.onClick()
            lastClickedTime = currentTime
          }
        }
      }
      //Test to recharge batterie
      if (Gdx.input.isKeyPressed(Input.Keys.C)) {
        lightBar.setValue(1f)
      }

      //Test to deal damage
      if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
        healthBar.setValue(healthBar.getValue - 10f)
      }

      //Heal player
      if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
        healthBar.setValue(healthBar.getValue + 10f)
      }

      //Si la lumière est active et depuis plus d'une seconde, baisse sa vie
      if (light.lightStatus()) {
        if (currentTime - counterManager.getStartedTime() >= 1000) {
          counterManager.resetStartValue()
          if (light.isAvailable) {
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

  /**
   * Spawn randomly the objects of a list
   * @param validTiles
   */
  def spawnRandomObject(validTiles: ArrayBuffer[(Int, Int)]): Unit = {
    for(i <- 0 to 99){
      var shuffleSpawn = Random.shuffle(validTiles)
      var chances: Int = Random.nextInt(100)

      val (tileX, tileY) = shuffleSpawn(i)

      shuffleSpawn.remove(i)

      // L'axe X ne change pas (la gauche reste la gauche)
      val posX = (tileX * levelManager.getTileWidth()) + (levelManager.getTileWidth() / 2f)

      // L'axe Y est inversé : on part du haut et on descend
      val posY = ((levelManager.getTotalHeight() - 1 - tileY) * levelManager.getTileHeight()) + (levelManager.getTileHeight() / 2f)

      if(chances <= 60){
        var fithy = Random.nextInt(100)
        if(fithy <= 50){
          objectsList.append(new Bolt(posX, posY, boltTexture, 32, 45f))
        }else {
          objectsList.append(new Water(posX, posY, waterTexture, 32, 45f))
        }
      } else if(chances <= 80){
        objectsList.append(new Heal(posX, posY, healTexture, 32, 45f))
      } else {
        objectsList.append(new Battery(posX, posY, batteryTexture, 32, 45f))
      }

      println(s"the object (${objectsList(i).getClass.getSimpleName}) is at ${posX}/${posY}")
    }
  }

  def spawnEnemies(validTiles : ArrayBuffer[(Int, Int)]): Unit = {
    var shuffleSpawn = Random.shuffle(validTiles)
    val maxW = levelManager.getTotalWidth() * levelManager.getTileWidth()
    val maxH = levelManager.getTotalHeight() * levelManager.getTileHeight()

    for(i <- 0 to 29){
      var chances = Random.nextInt(100)
      var rdmValidTiles = Random.nextInt()
      var enemy: Enemies = null

      var (tileX, tileY) = shuffleSpawn(i)

      val posX = (tileX * levelManager.getTileWidth()) + (levelManager.getTileWidth() / 2f)
      val posY = ((levelManager.getTotalHeight() - 1 - tileY) * levelManager.getTileHeight()) + (levelManager.getTileHeight() / 2f)

      if(chances <= 50){
        enemy = new Wolf(100, posX, posY, 64, 64, wolfTexture)
      } else if(chances <= 100){
        enemy = new Spider(100, posX, posY, 64, 64, spiderTexture)
      }

      if(enemy != null){
        if(!levelManager.collisions(posX, posY)){
          enemy.setPosition(posX, posY)
          enemiesList.append(enemy)
          println(s"the enemy ${enemy.getClass.getSimpleName} spawn at ${posX}/${posY}")
        }
      }
    }
  }
}
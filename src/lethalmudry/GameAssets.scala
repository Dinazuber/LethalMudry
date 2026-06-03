package ch.hevs.gdx2d.lethalmudry {

  import com.badlogic.gdx.assets.AssetManager
  import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
  import com.badlogic.gdx.graphics.Texture
  import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
  import com.badlogic.gdx.utils.Disposable

  class GameAssets extends Disposable {
    val manager: AssetManager = new AssetManager()

    private val MAP_PATH = "data/images/TilesBigger/map.tmx"
    private val PLAYER_PATH = "data/images/sprite_sheet.png"
    private val BATTERY_PATH = "data/images/battery.png"
    private val HEAL_PATH = "data/images/heal.png"
    private val BOLT_PATH = "data/images/scrap/big_bolt.png"
    private val WATER_PATH = "data/images/scrap/water.png"
    private val SPIDER_PATH = "data/images/enemies/spider.png"

    /**
     * Load all the sprite of the game's items
     */
    def loadAll(): Unit = {
      manager.setLoader(classOf[TiledMap], new TmxMapLoader(new InternalFileHandleResolver))
      manager.load(MAP_PATH, classOf[TiledMap])
      manager.load(PLAYER_PATH, classOf[Texture])
      manager.load(BATTERY_PATH, classOf[Texture])
      manager.load(HEAL_PATH, classOf[Texture])
      manager.load(BOLT_PATH, classOf[Texture])
      manager.load(WATER_PATH, classOf[Texture])
      manager.load(SPIDER_PATH, classOf[Texture])
    }

    /**
     * Update the loading of the
     * @return return true when it's loaded?
     */
    def updateLoading(): Boolean = {
      manager.update()
    }

    /**
     * Get the sprite of the player
     * @return the Texture of the player
     */
    def getPlayerTexture(): Texture = {
      manager.get(PLAYER_PATH, classOf[Texture])
    }

    /**
     * Get the sprite of the map
     * @return the Texture of the map
     */
    def getMap(): TiledMap = {
      manager.get(MAP_PATH, classOf[TiledMap])
    }

    /**
     * Get the sprite of the battery
     * @return the Texture of the battery
     */
    def getBatteryTexture(): Texture = {
      manager.get(BATTERY_PATH, classOf[Texture])
    }

    /**
     * Get the sprite of an heal
     * @return the Texture of an heal
     */
    def getHealTexture(): Texture = {
      manager.get(HEAL_PATH, classOf[Texture])
    }

    /**
     * Get the sprite of the bolt
     * @return the Texture of the bolt
     */
    def getBoltTexture(): Texture = {
      manager.get(BOLT_PATH, classOf[Texture])
    }

    /**
     * Get the sprite of the bottle of water
     * @return the Texture of the bottle of water
     */
    def getWaterTexture(): Texture = {
      manager.get(WATER_PATH, classOf[Texture])
    }

    /**
     * Get the sprite of the spider
     * @return the Texture of the spider
     */
    def getSpiderTexture(): Texture = {
      manager.get(SPIDER_PATH, classOf[Texture])
    }

    override def dispose(): Unit = {
      manager.dispose()
    }
  }
}
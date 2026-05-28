package ch.hevs.gdx2d.lethalmudry

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.utils.Disposable

class GameAssets extends Disposable {
  val manager: AssetManager = new AssetManager()

  private val MAP_PATH    = "data/images/TilesBigger/maps.tmx"
  private val PLAYER_PATH = "data/images/sprite_sheet.png"
  private val BATTERY_PATH = "data/images/battery.png"
  private val HEAL_PATH = "data/images/heal.png"
  private val BOLT_PATH = "data/images/scrap/big_bolt.png"

  def loadAll(): Unit = {
    manager.setLoader(classOf[TiledMap], new TmxMapLoader(new InternalFileHandleResolver))
    manager.load(MAP_PATH, classOf[TiledMap])
    manager.load(PLAYER_PATH, classOf[Texture])
    manager.load(BATTERY_PATH, classOf[Texture])
    manager.load(HEAL_PATH, classOf[Texture])
    manager.load(BOLT_PATH, classOf[Texture])
  }

  def updateLoading(): Boolean = {
    manager.update()
  }

  def getPlayerTexture(): Texture = {
    manager.get(PLAYER_PATH, classOf[Texture])
  }

  def getMap(): TiledMap = {
    manager.get(MAP_PATH, classOf[TiledMap])
  }

  def getBatteryTexture() : Texture = {
    manager.get(BATTERY_PATH, classOf[Texture])
  }

  def getHealTexture(): Texture = {
    manager.get(HEAL_PATH, classOf[Texture])
  }

  def getBoltTexture(): Texture = {
    manager.get(BOLT_PATH, classOf[Texture])
  }

  override def dispose(): Unit = {
    manager.dispose()
  }
}
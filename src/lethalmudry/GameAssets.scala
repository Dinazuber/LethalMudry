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

  def loadAll(): Unit = {
    manager.setLoader(classOf[TiledMap], new TmxMapLoader(new InternalFileHandleResolver))
    manager.load(MAP_PATH, classOf[TiledMap])
    manager.load(PLAYER_PATH, classOf[Texture])
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

  override def dispose(): Unit = {
    manager.dispose()
  }
}
package ch.hevs.gdx2d.hello
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

package  LevelManager{
  class LevelManager {

    var map: TiledMap = _
    var mapRenderer: OrthogonalTiledMapRenderer = _

    var mapPixelWidth: Float = 0f
    var mapPixelHeight: Float = 0f

    def load(loadedMap: TiledMap): Unit = {
      this.map = loadedMap
      this.mapRenderer = new OrthogonalTiledMapRenderer(this.map)

      val prop = map.getProperties

      val mapWidthInTiles = prop.get("width", classOf[Int])
      val mapHeightInTiles = prop.get("height", classOf[Int])
      val tileWidth = prop.get("tilewidth", classOf[Int])
      val tileHeight = prop.get("tileheight", classOf[Int])

      mapPixelWidth = (mapWidthInTiles * tileWidth).toFloat
      mapPixelHeight = (mapHeightInTiles * tileHeight).toFloat
    }

    def render(camera: OrthographicCamera): Unit = {
      if (mapRenderer != null) {
        mapRenderer.setView(camera)
        mapRenderer.render()
      }
    }

    def collisions(xPixel: Float, yPixel: Float): Boolean = {
      if (map == null) return false

      val tileX = (xPixel / 32).toInt
      val tileY = (yPixel / 32).toInt

      val layer = map.getLayers.get("Walls")

      if (layer == null || !layer.isInstanceOf[TiledMapTileLayer]) {
        return false
      }

      val wallLayer = layer.asInstanceOf[TiledMapTileLayer]
      val cell = wallLayer.getCell(tileX, tileY)

      cell != null
    }

    def dispose(): Unit = {
      mapRenderer.dispose()
      map.dispose()
    }

    def worldToGrid(pixel: Float): Int = {
      (pixel / 32).toInt
    }
  }
}

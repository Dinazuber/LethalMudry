package lethalmudry {

  import com.badlogic.gdx.Gdx
  import com.badlogic.gdx.files.FileHandle
  import com.badlogic.gdx.graphics.OrthographicCamera
  import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
  import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}

  import java.util.logging.FileHandler

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

      val tileX = (xPixel / 164).toInt
      val tileY = (yPixel / 164).toInt

      val layer = map.getLayers.get("Outside")
      val layerDoors = map.getLayers.get("Doors")


      if (layer == null || !layer.isInstanceOf[TiledMapTileLayer]) {
        return false
      }

      if (layerDoors == null || !layer.isInstanceOf[TiledMapTileLayer]){
        val doorAudio : FileHandle = new FileHandle("data/audio/door.mp3")
        Gdx.audio.newSound(doorAudio)
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
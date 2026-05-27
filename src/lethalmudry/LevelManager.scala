package lethalmudry {

  import ch.hevs.gdx2d.lethalmudry.Player
  import com.badlogic.gdx.graphics.OrthographicCamera
  import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
  import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}

  class LevelManager {

    var map: TiledMap = _
    var mapRenderer: OrthogonalTiledMapRenderer = _

    var mapPixelWidth: Float = 0f
    var mapPixelHeight: Float = 0f

    private var tileWidth: Int = 0
    private var tileHeight: Int = 0
    private var wallLayer: TiledMapTileLayer = _

    def load(loadedMap: TiledMap): Unit = {
      this.map = loadedMap
      this.mapRenderer = new OrthogonalTiledMapRenderer(this.map)

      val prop = map.getProperties
      val mapWidthInTiles = prop.get("width", classOf[Int])
      val mapHeightInTiles = prop.get("height", classOf[Int])
      tileWidth = prop.get("tilewidth", classOf[Int])
      tileHeight = prop.get("tileheight", classOf[Int])

      mapPixelWidth = (mapWidthInTiles * tileWidth).toFloat
      mapPixelHeight = (mapHeightInTiles * tileHeight).toFloat

      val rawLayer = map.getLayers.get("Wall")
      wallLayer = rawLayer.asInstanceOf[TiledMapTileLayer]

    }

    def render(camera: OrthographicCamera): Unit = {
      if (mapRenderer != null) {
        mapRenderer.setView(camera)
        mapRenderer.render()
      }
    }

    def collisions(xPixel: Float, yPixel: Float): Boolean = {
      if (map == null || wallLayer == null) return false

      val tileX = (xPixel / tileWidth).toInt
      val tileY = (yPixel / tileHeight).toInt

      if (tileX < 0 || tileY < 0 ||
        tileX >= wallLayer.getWidth || tileY >= wallLayer.getHeight)
        return true

      wallLayer.getCell(tileX, tileY) != null
    }

    def dispose(): Unit = {
      mapRenderer.dispose()
      map.dispose()
    }
  }
}
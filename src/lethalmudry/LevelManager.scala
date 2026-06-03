package lethalmudry {

  import ch.hevs.gdx2d.lethalmudry.Player
  import com.badlogic.gdx.graphics.OrthographicCamera
  import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
  import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}

  import scala.collection.mutable.ArrayBuffer

  class LevelManager {

    var map: TiledMap = _
    var mapRenderer: OrthogonalTiledMapRenderer = _
    var layer: TiledMapTileLayer = _

    var mapPixelWidth: Float = 0f
    var mapPixelHeight: Float = 0f

    private var tileWidth: Int = 0
    private var tileHeight: Int = 0
    private var mapWidth : Int = 0
    private var mapHeight : Int = 0
    private var wallLayer: TiledMapTileLayer = _

    def load(loadedMap: TiledMap): Unit = {
      this.map = loadedMap
      this.mapRenderer = new OrthogonalTiledMapRenderer(this.map)
      layer = map.getLayers.get("Other").asInstanceOf[TiledMapTileLayer] //Get the layer of the spawnable tile

      val prop = map.getProperties
      mapWidth = prop.get("width", classOf[Int])
      mapHeight = prop.get("height", classOf[Int])
      tileWidth = prop.get("tilewidth", classOf[Int])
      tileHeight = prop.get("tileheight", classOf[Int])

      mapPixelWidth = (mapWidth * tileWidth).toFloat
      mapPixelHeight = (mapHeight * tileHeight).toFloat

      val rawLayer = map.getLayers.get("Wall")
      wallLayer = rawLayer.asInstanceOf[TiledMapTileLayer]

    }

    def getTotalHeight(): Int = {
      mapHeight
    }

    def getTileWidth(): Int = {
      tileWidth
    }

    def getTileHeight(): Int = {
      tileHeight
    }

    /**
     * Check if the cell of the map is spawnable
     * @param map the TiledMap of the map
     * @param x position X of the cell
     * @param y position Y of the cell
     * @return true if the cell is spawnable, else it returns false
     */
    def isValidTile(map: TiledMap, x: Int, y: Int, mapHeight: Int): Boolean = {
      val tiledY = mapHeight - 1 - y
      var cell = layer.getCell(x, tiledY) // Get the current cell

      if(cell != null && cell.getTile != null){
        val proprities = cell.getTile.getProperties
        proprities.containsKey("isSpawnable") && proprities.get("isSpawnable", classOf[Boolean]) // Check if cell has the propitie, and return it
      } else {
        false //we return false for the other cases
      }
    }

    def getListSpawnable(): ArrayBuffer[(Int, Int)] =  {
      val spawnableTiles = ArrayBuffer[(Int, Int)]()

      val prop = map.getProperties

      for(x <- 0 until mapWidth){
        for(y <- 0 until mapHeight){
          if(isValidTile(map, x, y, mapHeight)){
            spawnableTiles.append((x, y))
          }
        }
      }

      spawnableTiles
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
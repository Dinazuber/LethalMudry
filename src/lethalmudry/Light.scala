package lethalmudry

import box2dLight.ConeLight
import box2dLight.PointLight
import box2dLight.RayHandler
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lethalmudry.Player
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.GlobalSetter
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

import java.util
import java.util.Random

/**
 * Créer une lumière qui peut être utiliser par le joueur
 * @param x position x du joueur
 * @param y position y du joueur
 */
class Light(x: Float, y : Float) {
  var world: World = _
  var rayHandler: RayHandler = _

  var c1 : ConeLight = _

  var width: Int = 0
  var height: Int = 0

  /**
   * Génère la lumière de base
   */
  def generateLight(): Unit = {
    width = Gdx.graphics.getWidth
    height = Gdx.graphics.getHeight

    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0, 0))

    rayHandler = new RayHandler(world)

    c1 = new ConeLight(rayHandler, 300, Color.WHITE, 500f,
      x, y, 270, 40)

    rayHandler.setCulling(true)
    rayHandler.setShadows(true)
    rayHandler.setBlur(true)
    rayHandler.setAmbientLight(0.2f)
  }

  /**
   * Update la position de la lumière en fonctione du jeu
   * @param player le joueur
   */
  def updatePosition(player: Player) : Unit = {
    c1.setPosition(player.x + (player.getWidth() / 2), player.y + (player.getHeight() / 2))
  }

  /**
   * Met à jour le rayhandler de la lumière
   * @param camera La caméra utilisé dans le jeu
   */
  def updateRayHandler(camera: OrthographicCamera): Unit = {
    rayHandler.setCombinedMatrix(camera)
    rayHandler.updateAndRender()
  }

  /**
   * Active ou désactive la lumière en fonction d'un click droit
   */
  def onClick(): Unit = {
    println(s"The player just right clicked!")
    //Turn off or on the lights when the player click
    c1.setActive(!c1.isActive)
  }
}

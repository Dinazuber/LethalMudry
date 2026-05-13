package lethalmudry

import box2dLight.ConeLight
import box2dLight.PointLight
import box2dLight.RayHandler
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

import java.util
import java.util.Random


class Light {
  var world: World = _
  var rayHandler: RayHandler = _

  var c1 : ConeLight = _

  var width: Int = 0
  var height: Int = 0

  def generateLigth(): Unit = {
    width = Gdx.graphics.getWidth
    height = Gdx.graphics.getHeight

    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0, 0))

    rayHandler = new RayHandler(world)

    c1 = new ConeLight(rayHandler, 300, new Color(1, 1, 1, 0.95f), 14,
      0.2f * width * PhysicsConstants.PIXEL_TO_METERS,
      0.9f * height * PhysicsConstants.PIXEL_TO_METERS,
      270, 40)

    rayHandler.setCulling(true)
    rayHandler.setShadows(true)
    rayHandler.setBlur(true)
    rayHandler.setAmbientLight(0.4f)
  }

  def onClick(): Unit = {
    //Turn off the lights when the player click
    if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Buttons.RIGHT)){
      c1.setActive(!c1.isActive)
    }
  }
}

/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import Entities.FlightControl;
import Entities.Reaper;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class ExtendedApplication extends SimpleApplication implements AnalogListener,
ActionListener {

	private BulletAppState bulletAppState;
	private Spatial spaceCraft;
	private FlightControl flight;

	public static void main(String[] args) {
		ExtendedApplication app = new ExtendedApplication();
		AppSettings settings = new AppSettings(true);
		settings.setRenderer(AppSettings.LWJGL_OPENGL3);
		app.start();
	}


	private void setupKeys() {
		inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addListener(this, "Lefts");

	}

	@Override
	public void simpleInitApp() {

		bulletAppState = new BulletAppState();
		bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
		stateManager.attach(bulletAppState);
		rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
		flight=new Reaper();
		setupKeys();
		buildPlayer();

	}

	private void buildPlayer() {
		flyCam.setEnabled(false);
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onAction(String binding, boolean value, float tpf) {
		boolean test = false;
		if(test){	//Luftsteuerung
			if (binding.equals("LeftRotation")) {
				flight.setLinearVelocity(cam.getLeft().mult(25));
			} else if (binding.equals("rightRotation"))	{   
				flight.setLinearVelocity(cam.getLeft().negate().mult(25));
			} else if (binding.equals("upRotation"))   	{  
				flight.setLinearVelocity(cam.getUp().mult(25));
			} else if (binding.equals("downRotation")) 	{  
				flight.setLinearVelocity(cam.getUp().negate().mult(25));
			} else if (binding.equals("accelerate"))   	{        	
			} else if (binding.equals("decelerate"))   	{        	
			} else if (binding.equals("land")) 			{        	
			} else if (binding.equals("lift")) 			{
			} else if (binding.equals("primaryShoot")) 	{
			} else if (binding.equals("secondShoot"))  	{
			}
		}else{//Bodensteuerung
			if (binding.equals("turnLeft")) 			{
			} else if (binding.equals("turnRight"))		{        	
			} else if (binding.equals("lookUp")) 		{
			} else if (binding.equals("lookDown"))		{        	
			} else if (binding.equals("walkForward"))   {        	
			} else if (binding.equals("walkbackward")) 	{        	
			} else if (binding.equals("walkLeft"))   	{        	
			} else if (binding.equals("walkRight")) 	{        	
			} else if (binding.equals("jump")) 			{        	
			} else if (binding.equals("duck")) 			{        	
			} else if (binding.equals("layDown")) 		{        	
			}
		}


	}

	@Override
	public void simpleUpdate(float tpf) {
		System.out.println(spaceCraft.getLocalTranslation());
	}



}

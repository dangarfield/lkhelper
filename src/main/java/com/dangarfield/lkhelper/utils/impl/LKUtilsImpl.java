package com.dangarfield.lkhelper.utils.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dangarfield.lkhelper.data.PlayerCastleData;
import com.dangarfield.lkhelper.data.ResourcesData;
import com.dangarfield.lkhelper.data.TroopsData;
import com.dangarfield.lkhelper.utils.LKUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class LKUtilsImpl implements LKUtils {
	private static Logger LOG = LogManager.getLogger("LKUtils");
	
	public static void main(String[] args) throws Exception {
		LKUtilsImpl lkUtils = new LKUtilsImpl();
		
		int t = 100;
		int s = 200;
		int i = 300;
		int a = 403;
		LOG.info(t + " - " + s + " - " + i + " - " + a);
		int d = lkUtils.getDistanceToHabitat(t, s, i, a);
		LOG.info("Result = " + d);
		
		
    }
	
	@Override
	public int getDistanceToHabitat(final int homeX, final int homeY, final int targetX, final int targetY) {
		try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            
            FileReader reader = new FileReader("src/main/resources/distanceToHabitat.js");
            engine.eval(reader);
            reader.close();
            engine.eval("var distance = Object.distanceToHabitat("+homeX+","+homeY+","+targetX+","+targetY+");");
            
            return ((Double) engine.get("distance")).intValue();
        } catch (ScriptException e) {
            // Shouldn't happen unless somebody breaks the script
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            // Shouldn't happen unless somebody breaks the script
            throw new RuntimeException(e);
        } catch (IOException e) {
        	// Shouldn't happen unless somebody breaks the script
            throw new RuntimeException(e);
		}
	}
	
	@Override
	public void addResourceToPlayer(final PlayerCastleData playerCastle, final int resourceId, final int amount) throws Exception {
		switch (resourceId) {
	        case 1:
	        	playerCastle.getResources().setWood(amount);
	            break;
	        case 2:
	        	playerCastle.getResources().setStone(amount);
	        	break;
	        case 3:
	        	playerCastle.getResources().setOre(amount);
	        	break;
	        case 4:
	        	playerCastle.getResources().setPeople(amount);
	        	break;
	        case 5:
	        	playerCastle.getResources().setCopper(amount);
	        	break;
	        case 6:
	        	playerCastle.getResources().setSilver(amount);
    			break;
	        case 7:
	        	//none, we're not bothered about gold
    			break;
    		default: throw new Exception("Unknown resource type: " + resourceId);
	    }
	}

	@Override
	public void addTroopsToPlayer(final PlayerCastleData playerCastle, final Map<String, Long> troopMap) throws Exception {
		
		Long sourceHabitatId = troopMap.get("sourceHabitatId");
		
		for (String key : troopMap.keySet()) {
			switch (TroopMapKeys.valueOf("TROOPMAPKEYS_" + key.toUpperCase())) {
				case TROOPMAPKEYS_SOURCEHABITATID:
		            break;
		        case TROOPMAPKEYS_1:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addSpearman(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addSpearman(troopMap.get(key).intValue());
		        	}
		        	break;
		        case TROOPMAPKEYS_2:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addSwordsman(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addSwordsman(troopMap.get(key).intValue());
		        	}
		        	break;
		        case TROOPMAPKEYS_101:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addArcher(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addArcher(troopMap.get(key).intValue());
		        	}
		        	break;
		        case TROOPMAPKEYS_102:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addCrossbowman(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addCrossbowman(troopMap.get(key).intValue());
		        	}
		        	break;
		        case TROOPMAPKEYS_201:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addArmouredHorse(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addArmouredHorse(troopMap.get(key).intValue());
		        	}
					break;
		        case TROOPMAPKEYS_202:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addLancer(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addLancer(troopMap.get(key).intValue());
		        	}
					break;
		        case TROOPMAPKEYS_10001:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addCart(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addCart(troopMap.get(key).intValue());
		        	}
					break;
		        case TROOPMAPKEYS_10002:
		        	if (sourceHabitatId == playerCastle.getId()) {
		        		playerCastle.getTroops().addOxcart(troopMap.get(key).intValue());
		        	} else {
		        		playerCastle.getExternalDefendingtroops().addOxcart(troopMap.get(key).intValue());
		        	}
					break;
				default: throw new Exception("Unknown troop type: " + "TROOPMAPKEYS_" + key.toUpperCase());
			}
		}
	}
	private enum TroopMapKeys {

		TROOPMAPKEYS_SOURCEHABITATID,
		TROOPMAPKEYS_1,
		TROOPMAPKEYS_2,
		TROOPMAPKEYS_101,
		TROOPMAPKEYS_102,
		TROOPMAPKEYS_201,
		TROOPMAPKEYS_202,
		TROOPMAPKEYS_10001,
		TROOPMAPKEYS_10002
	}
	@Override
	public void resetResourceCount(PlayerCastleData playerCastle) {
		playerCastle.setResources(new ResourcesData());
	}

	@Override
	public void resetTroopCount(PlayerCastleData playerCastle) {
		playerCastle.setTroops(new TroopsData());
		playerCastle.setExternalDefendingtroops(new TroopsData());
	}
}

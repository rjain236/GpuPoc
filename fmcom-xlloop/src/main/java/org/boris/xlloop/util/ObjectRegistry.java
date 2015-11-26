/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.xlloop.util;

import java.util.HashMap;
import java.util.Map;

import org.boris.xlloop.SessionContext;

/**
 * Used to contain objects and provides mapping to a generated key.
 */
public class ObjectRegistry
{
    private Map<SessionContext,Map<String,Object>> map = new HashMap<>();
    private Map<SessionContext,Map<Object,String>> rev = new HashMap<>();
    private Map<SessionContext,Map<String,Integer>> countMap = new HashMap<>();

    private void initialiseSessionContext(SessionContext sessionContext){
    	Map<String,Object> subMap = new HashMap<String, Object>();
    	Map<Object,String> subRev = new HashMap<Object, String>();
    	Map<String,Integer> subCounMap = new HashMap<String, Integer>();
    	map.put(sessionContext, subMap);
    	rev.put(sessionContext, subRev);
    	countMap.put(sessionContext, subCounMap);
    }
    
    /**
     * Place an object in the registry.
     * 
     * @param obj.
     * 
     * @return String (the generated key).
     */
    public String put(Object obj,SessionContext sessionContext) {
    	if(!map.containsKey(sessionContext)||!rev.containsKey(sessionContext)||!countMap.containsKey(sessionContext))
    		initialiseSessionContext(sessionContext);
    	
    	if (rev.get(sessionContext).containsKey(obj)) {
            return (String) rev.get(sessionContext).get(obj);
        } else {
            String key = createKey(obj,sessionContext);
            map.get(sessionContext).put(key, obj);
            rev.get(sessionContext).put(obj, key);
            return key;
        }
    }

    /**
     * Get the keys.
     * 
     * @return String[].
     */
    public String[] getKeys() {
        return (String[]) map.keySet().toArray(new String[0]);
    }

    /**
     * Retrieve an object from the registry.
     * 
     * @param key.
     * 
     * @return Object.
     */
    public Object get(String key,SessionContext sessionContext) {
    	if(!map.containsKey(sessionContext))
    		return null;
    	return map.get(sessionContext).get(key);
    }

    /**
     * Remove an object from the registry.
     * 
     * @param key.
     */
    public void remove(String key,SessionContext sessionContext) {
        if(!map.containsKey(sessionContext))
        	return;
    	Object o = map.get(sessionContext).get(key);
        if (o != null) {
            map.get(sessionContext).remove(key);
            rev.get(sessionContext).remove(o);
        }
    }

    /**
     * Generate the key for the object.
     * 
     * @param obj.
     * 
     * @return String.
     */
    private String createKey(Object obj,SessionContext sessionContext) {
        StringBuilder sb = new StringBuilder();
        String simpleName = obj.getClass().getSimpleName();
    	if(!map.containsKey(sessionContext)||!rev.containsKey(sessionContext)||!countMap.containsKey(sessionContext))
    		initialiseSessionContext(sessionContext);
        Integer index = countMap.get(sessionContext).get(simpleName);
        if(index==null)index = 0;
        sb.append(simpleName);
        sb.append("@");
        sb.append(index);
        index++;
        countMap.get(sessionContext).put(simpleName, index);
        return sb.toString();
    }

    /**
     * Clear out the registry.
     */
    public void clear() {
        map.clear();
        rev.clear();
    }
    
    public void clear(SessionContext sessionContext) {
    	if(map.containsKey(sessionContext))map.remove(sessionContext);
    	if(rev.containsKey(sessionContext))rev.remove(sessionContext);
    	if(countMap.containsKey(sessionContext))countMap.remove(sessionContext);
    }

    /**
     * Get the size of the registry.
     * 
     * @return int.
     */
    public int size() {
        return map.size();
    }
}

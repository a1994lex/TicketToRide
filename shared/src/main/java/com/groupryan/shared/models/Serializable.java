package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * ${CLASS} in com.groupryan.shared.models
 *
 * @author Alex POwley
 * @version 1.0 2/9/2018.
 */

public interface Serializable {
    Object mapToObject(LinkedTreeMap map);
}

package com.ciengine.master.listeners.impl;

import com.ciengine.master.listeners.RuleBuilder;

import java.util.List;

/**
 * Created by evgenymekhanikov on 10.03.17.
 */

public interface Pipeline {
    List<RuleBuilder> prepare();
}

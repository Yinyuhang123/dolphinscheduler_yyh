/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.server.master.dispatch.host.assign;

import java.util.Collection;

/**
 * lower weight round robin
 */
public class LowerWeightRoundRobin extends AbstractSelector<HostWeight> {

    /**
     * Selects a HostWeight from a collection of HostWeight objects.
     * The selection is based on the current weight of each HostWeight.
     * The HostWeight with the smallest current weight is selected.
     *
     * @param sources A collection of HostWeight objects to select from.
     * @return The selected HostWeight with the smallest current weight.
     */
    @Override
    public HostWeight doSelect(Collection<HostWeight> sources) {
        double totalWeight = 0;
        double lowWeight = 0;
        HostWeight lowerNode = null;
        for (HostWeight hostWeight : sources) {
            totalWeight += hostWeight.getWeight();
            hostWeight.setCurrentWeight(hostWeight.getCurrentWeight() + hostWeight.getWeight());
            if (lowerNode == null || lowWeight > hostWeight.getCurrentWeight()) {
                lowerNode = hostWeight;
                lowWeight = hostWeight.getCurrentWeight();
            }
        }
        if (lowerNode != null) {
            lowerNode.setCurrentWeight(lowerNode.getCurrentWeight() + totalWeight);
        }
        return lowerNode;
    }

}

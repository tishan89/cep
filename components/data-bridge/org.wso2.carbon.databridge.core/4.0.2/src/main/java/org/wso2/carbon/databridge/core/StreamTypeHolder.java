/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.databridge.core;

import org.wso2.carbon.databridge.commons.AttributeType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event stream data type holder
 */
public class StreamTypeHolder {
    private String domainName;
    private Map<String, AttributeType[][]> dataTypeMap=  new ConcurrentHashMap<String, AttributeType[][]>();

    public StreamTypeHolder(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }


    public Map<String, AttributeType[][]> getDataTypeMap() {
        return dataTypeMap;
    }

    public void putDataType(String streamId, AttributeType[] meta,
                            AttributeType[] correlation, AttributeType[] payload) {
        this.dataTypeMap.put(streamId, new AttributeType[][]{meta,correlation,payload});
    }

    public AttributeType[][] getDataType(String streamId) {
        return dataTypeMap.get(streamId);
    }
}

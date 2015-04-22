/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.server.controller;


import java.util.List;

import org.apache.ambari.server.orm.entities.ClusterEntity;
import org.apache.ambari.server.orm.entities.ServiceConfigEntity;
import org.apache.ambari.server.orm.entities.StackEntity;
import org.apache.ambari.server.state.StackId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class ServiceConfigVersionResponse {
  @JsonProperty("cluster_name")
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private final String clusterName;

  @JsonProperty("service_name")
  private final String serviceName;

  @JsonProperty("service_config_version")
  private final Long version;

  @JsonProperty("createtime")
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private final Long createTime;

  @JsonProperty("group_id")
  private final Long groupId;

  @JsonProperty("group_name")
  private final String groupName;

  @JsonProperty("user")
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private final String userName;

  @JsonProperty("service_config_version_note")
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private final String note;

  @JsonProperty("stack_id")
  private String stackId;

  @JsonProperty("is_current")
  private Boolean isCurrent = Boolean.FALSE;

  @JsonProperty("is_cluster_compatible")
  private final Boolean isCompatibleWithCurrentStack;

  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private List<ConfigurationResponse> configurations;

  @JsonProperty("hosts")
  private final List<String> hosts;

  /**
   * Constructor.
   *
   * @param clusterName
   * @param serviceName
   * @param version
   * @param isCurrent
   * @param isCompatibleWithCurrentStack
   * @param configurations
   */
  public ServiceConfigVersionResponse(ServiceConfigEntity serviceConfigEntity,
      String configGroupName) {
    super();
    ClusterEntity clusterEntity = serviceConfigEntity.getClusterEntity();

    clusterName = clusterEntity.getClusterName();
    serviceName = serviceConfigEntity.getServiceName();
    version = serviceConfigEntity.getVersion();
    userName = serviceConfigEntity.getUser();
    createTime = serviceConfigEntity.getCreateTimestamp();
    note = serviceConfigEntity.getNote();
    groupId = (null != serviceConfigEntity.getGroupId() ? serviceConfigEntity.getGroupId(): -1L);
    groupName = configGroupName;
    hosts = serviceConfigEntity.getHostNames();

    StackEntity serviceConfigStackEntity = serviceConfigEntity.getStack();
    StackEntity clusterStackEntity = clusterEntity.getClusterStateEntity().getCurrentStack();

    isCompatibleWithCurrentStack = clusterStackEntity.equals(serviceConfigStackEntity);
    stackId = new StackId(serviceConfigStackEntity).getStackId();
  }

  public String getServiceName() {
    return serviceName;
  }

  public Long getVersion() {
    return version;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public String getUserName() {
    return userName;
  }
  public String getClusterName() {
    return clusterName;
  }

  public List<ConfigurationResponse> getConfigurations() {
    return configurations;
  }

  public void setConfigurations(List<ConfigurationResponse> configurations) {
    this.configurations = configurations;
  }

  public String getNote() {
    return note;
  }

  public List<String> getHosts() {
    return hosts;
  }

  public String getGroupName() {
    return groupName;
  }

  public Long getGroupId() {
    return groupId;
  }

  /**
   * Gets the Stack ID that this configuration is scoped for.
   *
   * @return
   */
  public String getStackId() {
    return stackId;
  }

  public Boolean getIsCurrent() {
    return isCurrent;
  }

  public void setIsCurrent(Boolean isCurrent) {
    this.isCurrent = isCurrent;
  }

  /**
   * Gets whether this service configuration is compatible with the cluster's
   * current stack version.
   *
   * @return {@code true} if compatible, {@code false} otherwise.
   */
  public Boolean isCompatibleWithCurrentStack() {
    return isCompatibleWithCurrentStack;
  }
}


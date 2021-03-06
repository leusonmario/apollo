package com.ctrip.framework.apollo.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ctrip.framework.apollo.common.entity.AppNamespace;
import com.ctrip.framework.apollo.biz.service.AppNamespaceService;
import com.ctrip.framework.apollo.common.utils.BeanUtils;
import com.ctrip.framework.apollo.core.dto.AppNamespaceDTO;

import java.util.List;

@RestController
public class AppNamespaceController {

  @Autowired
  private AppNamespaceService appNamespaceService;

  @RequestMapping("/apps/{appId}/appnamespace/{appnamespace}/unique")
  public boolean isAppNamespaceUnique(@PathVariable("appId") String appId,
      @PathVariable("appnamespace") String appnamespace) {
    return appNamespaceService.isAppNamespaceNameUnique(appId, appnamespace);
  }

  @RequestMapping("/appnamespaces/public")
  public List<AppNamespaceDTO> findPublicAppNamespaces(){
    List<AppNamespace> appNamespaces = appNamespaceService.findPublicAppNamespaces();
    return BeanUtils.batchTransform(AppNamespaceDTO.class, appNamespaces);
  }

  @RequestMapping(value = "/apps/{appId}/appnamespaces", method = RequestMethod.POST)
  public AppNamespaceDTO createOrUpdate( @RequestBody AppNamespaceDTO appNamespace){

    AppNamespace entity = BeanUtils.transfrom(AppNamespace.class, appNamespace);
    AppNamespace managedEntity = appNamespaceService.findOne(entity.getAppId(), entity.getName());

    if (managedEntity != null){
      BeanUtils.copyEntityProperties(entity, managedEntity);
      entity = appNamespaceService.update(managedEntity);
    }else {
      entity = appNamespaceService.createAppNamespace(entity, entity.getDataChangeCreatedBy());
    }

    return BeanUtils.transfrom(AppNamespaceDTO.class, entity);

  }

}

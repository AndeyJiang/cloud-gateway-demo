package com.andey.dao;


import com.andey.entity.TGateWayRouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jiangbin on 2018/10/15.
 */
@Component
public interface TGateWayRouteDefinitionMapper {

    List<TGateWayRouteDefinition>  findByName();
}

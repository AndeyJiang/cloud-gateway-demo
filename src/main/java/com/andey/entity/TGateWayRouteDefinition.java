package com.andey.entity;


import lombok.Data;
import java.util.Date;

/**
 * Created by jiangbin on 2018/10/12.
 */
@Data
public class TGateWayRouteDefinition{
    /**
     * Route的唯一id，不设置默认为uuid
     */
    private String id;
    /**
     * 路由功能描述
     */
    private String disribes;

    /**
     * 为一个数组，数组内每个元素内容包含name 和 args 结构如下：
     * [{"name":"Path",args:{"_genkey_0":"/baidu"}},{"name":"Method",args:{"_genkey_0":"POST"}}]
     * [PredicateDefinition{name='Path', args={pattern=/126, pathPattern=/126}}, PredicateDefinition{name='Method', args={method=GET}}]
     */
    private String predicates;

    /**
     * 为一个数组，结构和predicates一致
     */
    private String filters;

    /**
     * 下游的uri
     */
    private String uri;

    /**
     * 为数值
     */
    private int orders;

    /**
     * 上游服务
     */
    private String upStream;

    /**
     * 上游服务
     */
    private String downStream;
    /**
     * 插入路由信息时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 先满足基本功能，后续扩展再说
     */
}

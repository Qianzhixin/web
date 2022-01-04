package team.ifp.cbirc.blImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.ifp.cbirc._enum.Predicate;
import team.ifp.cbirc.bl.KGService;
import team.ifp.cbirc.dao.TripleRepository;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.po.Triple;
import team.ifp.cbirc.pojo.LinkPOJO;
import team.ifp.cbirc.pojo.NodePOJO;
import team.ifp.cbirc.pojo.TypePOJO;
import team.ifp.cbirc.vo.KGVO;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.VersionChangesVO;

import java.util.*;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */

@Service
public class KGServiceImpl implements KGService {

    @Autowired
    TripleRepository tripleRepository;

    @Autowired
    ExternalRegulationRepository externalRegulationRepository;

    private final String URI_PREFIX = "http://127.0.0.1:8080/cbirc/kg";

    /**
     * 获取id所指外规相关知识图谱信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> getKGInfo(Integer id) {
        Optional<ExternalRegulation> centerById = externalRegulationRepository.findById(id);
        if(!centerById.isPresent()) {
            ResponseVO.buildNotFound("所要创建图谱法规不存在");
        }

        List<Triple> sList = tripleRepository.findBySourceId(id);
        List<Triple> tList = tripleRepository.findByTargetId(id);

        //做统计节点信息准备工作
        Set<NodePOJO> nodesSet = new HashSet<>();
        List<LinkPOJO> links = new LinkedList<>();
        Map<String, TypePOJO> typesMap = new HashMap<>(); //uri:类型节点映射

        //生成文字类型
        String literalUri = buildTypeUri("literal");
        TypePOJO literalType = new TypePOJO(1, literalUri,"文字类型");
        typesMap.put(literalUri,literalType);

        //创建中心节点
        NodePOJO centerNode = externalRegulation2NodePOJO(centerById.get(), nodesSet);
        TypePOJO centerType = getTypeAndMerge(centerById.get(), typesMap);
        centerNode.setTypeId(centerType.getId());

        //生成centerNode作为主语产生的节点、连接、类型
        for(Triple triple:sList) {
            buildLinks(centerNode, triple.getTargetId(), triple.getTargetTitle(),triple.getPredicate(),true,nodesSet,links,typesMap);
        }

        //生成centerNode作为宾语产生的节点、连接、类型
        for (Triple triple: tList) {
            buildLinks(centerNode, triple.getSourceId(), triple.getSourceTitle(),triple.getPredicate(),false,nodesSet,links,typesMap);
        }

        //生成图谱
        KGVO kgvo = new KGVO();
        kgvo.setCenterId(id);
        kgvo.setNodes(new LinkedList<>(nodesSet));
        kgvo.setLinks(links);
        kgvo.setTypes(new LinkedList<>(typesMap.values()));

        return ResponseEntity.ok(ResponseVO.buildOK(kgvo));
    }

    /**
     * 获取某一法律版本变迁
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> versionChanges(Integer id) {
        Optional<ExternalRegulation> byId = externalRegulationRepository.findById(id);
        if(!byId.isPresent()) {
            ResponseVO.buildNotFound("所请求的法规不存在");
        }

        ExternalRegulation externalRegulation = byId.get();

        VersionChangesVO versionChangesVO = new VersionChangesVO();
        versionChangesVO.addChanges(externalRegulation.getReleaseDate(),"发布",null,null);
        versionChangesVO.addChanges(externalRegulation.getImplementationDate(),"实施",null,null);

        List<Triple> abolishList = tripleRepository.findByTargetIdAndPredicate(id, Predicate.TO_ABOLISH);
        for (Triple triple : abolishList) {
            Optional<ExternalRegulation> byId1 = externalRegulationRepository.findById(triple.getSourceId());
            if (!byId1.isPresent()) {
                ResponseVO.buildInternetServerError("服务器错误");
            }
            ExternalRegulation source = byId1.get();
            String titleAndNumber = "《" + source.getTitle() + "》" + "（" + source.getNumber() + "）";
            versionChangesVO.addChanges(source.getImplementationDate(),"使废止", source.getId(), titleAndNumber);
        }

        return ResponseEntity.ok(ResponseVO.buildOK(versionChangesVO));
    }







    private String buildNodeUri(String nodeName) {
        return URI_PREFIX + "/externalRegulation/" + nodeName;
    }

    private String buildTypeUri(String typeName) {
        return URI_PREFIX + "/type/" + typeName;
    }

    /**
     * 根据给定条件创建相关节点、连接、类型
     * 结果分别更新在 nodeSet、linksList、typesMap中
     * @param node1
     * @param id2
     * @param title2
     * @param predicate
     * @param direction
     * @param nodesSet
     * @param linksList
     * @param typesMap
     */
    private void buildLinks(NodePOJO node1, Integer id2, String title2, Predicate predicate, boolean direction,
                            Set<NodePOJO> nodesSet,List<LinkPOJO> linksList,Map<String,TypePOJO> typesMap) {
        NodePOJO node2;
        TypePOJO type2;

        //纯文本节点
        if(id2 == null) {
            node2 = literal2NodePOJO(title2,nodesSet);
            node2.setTypeId(1);
        }
        //实体节点
        else {
            Optional<ExternalRegulation> byId = externalRegulationRepository.findById(id2);
            if(!byId.isPresent()) ResponseVO.buildInternetServerError("服务器错误");
            node2 = externalRegulation2NodePOJO(byId.get(), nodesSet);
            type2 = getTypeAndMerge(byId.get(), typesMap);
            node2.setTypeId(type2.getId());
        }

        if(node2.getId() == 0) return;

        LinkPOJO link = new LinkPOJO();
        link.setLabel(predicate.getName());
        if(direction) {
            link.setSourceId(node1.getId());
            link.setTargetId(node2.getId());
        }
        else {
            link.setSourceId(node2.getId());
            link.setTargetId(node1.getId());
        }
        linksList.add(link);

        //如果是上位法反向推导出下位法
        if(predicate.equals(Predicate.HIGHER_LEVEL_LAW)) {
            LinkPOJO cLink = new LinkPOJO();
            cLink.setLabel(Predicate.LOWER_LEVEL_LAW.getName());
            if(!direction) {
                cLink.setSourceId(node1.getId());
                cLink.setTargetId(node2.getId());
            }
            else {
                cLink.setSourceId(node2.getId());
                cLink.setTargetId(node1.getId());
            }
            linksList.add(cLink);
        }

    }

    /**
     * 生成一个NodePOJO对象
     * @param storeId
     * @param title
     * @param nodesSet
     * @return
     */
    private NodePOJO buildNodePOJO(int storeId,String title,Set<NodePOJO> nodesSet) {
        NodePOJO node = new NodePOJO();
        node.setUri(buildNodeUri(String.valueOf(storeId)));

        //查看是否新增节点 并设置展必要信息
        if(nodesSet.add(node)) {
            node.setId(nodesSet.size());
            node.setStoreId(storeId);
            node.setLabel(title);
        }

        return node;
    }

    /**
     * 将一个法规存储对象转化为NodePOJO对象
     * @param externalRegulation
     * @return
     */
    private NodePOJO externalRegulation2NodePOJO(ExternalRegulation externalRegulation,Set<NodePOJO> nodesSet) {
        NodePOJO node = new NodePOJO();
        node.setUri(buildNodeUri(String.valueOf(externalRegulation.getId())));

        //查看是否新增节点 并设置展必要信息
        if(nodesSet.add(node)) {
            node.setId(nodesSet.size());
            node.setStoreId(externalRegulation.getId());
            node.setLabel(externalRegulation.getTitle());
        }

        return node;
    }

    /**
     * 将一个文字转化为NodePOJO对象
     * @param literal
     * @param nodesSet
     * @return
     */
    private NodePOJO literal2NodePOJO(String literal,Set<NodePOJO> nodesSet) {
        NodePOJO node = new NodePOJO();
        node.setUri(buildNodeUri(literal));

        //查看是否新增节点 并设置展必要信息
        if(nodesSet.add(node)) {
            node.setId(nodesSet.size());
            node.setStoreId(-1);
            node.setLabel(literal);
        }

        return node;
    }

    /**
     * 获取类型节点 并将其合并进类型集合
     * @param externalRegulation
     * @return
     */
    private TypePOJO getTypeAndMerge(ExternalRegulation externalRegulation,Map<String,TypePOJO> typesMap) {
        String uri = buildTypeUri(externalRegulation.getType());

        //在集合中是否已经含有当前类型节点
        TypePOJO typePOJO = typesMap.get(uri);

        //未找到类型节点 新建并插入
        if(typePOJO == null) {
            typePOJO = new TypePOJO(typesMap.size() + 1,uri,externalRegulation.getType());
            typesMap.put(uri,typePOJO);
        }

        return typePOJO;
    }

}

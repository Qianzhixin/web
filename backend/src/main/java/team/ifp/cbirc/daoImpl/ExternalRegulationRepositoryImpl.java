package team.ifp.cbirc.daoImpl;

import org.springframework.stereotype.Repository;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationDao;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;

import javax.persistence.*;
import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 */

@Repository
public class ExternalRegulationRepositoryImpl implements ExternalRegulationDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据 pojo、begin、len 进行法规搜索
     * @param pojo 搜索条件集合类
     * @return 如果是有意义的查询返回结果列别哦;如果是无意义查询(pojo 内条件全空、范围无意义,除了begin==0 && len==0 代表搜索全部记录)返回 null
     */
    @Override
    public List<ExternalRegulation> search(SearchRegulationPOJO pojo) {

        String hql = makeSearchHql(pojo);//获取查找sql

        //不是有意义的sql查询 返回null
        if(
                hql==null &&
                (pojo.getBegin()!=0 || pojo.getLen()!=0) &&
                (pojo.getBegin()<0 || pojo.getLen()<0)
        ) return null;

        //生成 Query 并注入变量
        TypedQuery<ExternalRegulation> query = makeQuery(pojo,hql);

        //设定搜索范围
        if(pojo.getBegin()!=0 || pojo.getLen()!=0) {
            query.setFirstResult(pojo.getBegin());
            query.setMaxResults(pojo.getLen());
        }

        return query.getResultList();
    }

    /**
     * 生成搜索hql
     * @param pojo 搜索条件集合类
     * @return 能够形成有意义查询则返回对应 hql;否则返回 null
     */
    private String makeSearchHql(SearchRegulationPOJO pojo) {
        StringBuilder sqlBuilder = new StringBuilder("from ExternalRegulation er ");

        //拼接搜索条件sql
        if(!spliceSearchCondition(sqlBuilder,pojo)) return null;

        return sqlBuilder.toString();
    }

    /**
     * 根据 pojo 拼接搜索条件(where/and)
     * @param pojo
     * @param hqlBuilder
     * @return 是否能够拼接有意义的查询
     */
    private boolean spliceSearchCondition(StringBuilder hqlBuilder, SearchRegulationPOJO pojo) {
        //当条件不为null时才加入判断
        //isFirst && !(isFirst = false)判断结果只有第一次为true,后续为false
        boolean isFirst = true;
        if(pojo.getTitle() != null){
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" title like :title ");
        }
        if(pojo.getNumber() != null){
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" number = :number ");
        }
        if(pojo.getEffectivenessLevel() != null) {
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" effectiveness_level = :effectiveness_level ");
        }
        if(pojo.getPublishingDepartment() != null) {
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" publishing_department = :publishing_department ");
        }
        if(pojo.getState() != null) {
            hqlBuilder.append(isFirst && !(isFirst = false) ? "where" : "and").append(" state = :state ");
        }
        if(pojo.getFromReleaseDate() != null) {
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" release_date >= :from_release_date ");
        }
        if(pojo.getToReleaseDate() != null) {
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" release_date <= :to_release_date ");
        }
        if(pojo.getFromImplementationDate() != null) {
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" implementation_date >= :from_implementation_date ");
        }
        if(pojo.getToImplementationDate() != null) {
            hqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" implementation_date <= :to_implementation_date ");
        }

        return !isFirst;
    }

    /**
     * 生成 hql 对应 Query 对象,并注入 po 内部变量
     * @param pojo
     * @param hql
     * @return
     */
    private TypedQuery<ExternalRegulation> makeQuery(SearchRegulationPOJO pojo, String hql) {
        TypedQuery<ExternalRegulation> query = entityManager.createQuery(hql,ExternalRegulation.class);

        if(pojo.getTitle() != null)query.setParameter("title",  "%" + pojo.getTitle() + "%");
        if(pojo.getNumber() != null)query.setParameter("number",pojo.getNumber());
        if(pojo.getEffectivenessLevel() != null)query.setParameter("effectiveness_level",pojo.getEffectivenessLevel());
        if(pojo.getPublishingDepartment() != null)query.setParameter("publishing_department",pojo.getPublishingDepartment());
        if(pojo.getFromReleaseDate() != null)query.setParameter("from_release_date",pojo.getFromReleaseDate());
        if(pojo.getToReleaseDate() != null)query.setParameter("to_release_date",pojo.getToReleaseDate());
        if(pojo.getFromImplementationDate() != null)query.setParameter("from_implementation_date",pojo.getFromImplementationDate());
        if(pojo.getToImplementationDate() != null)query.setParameter("to_implementation_date",pojo.getToImplementationDate());
        if(pojo.getState() != null)query.setParameter("state",pojo.getState());

        return query;
    }

}

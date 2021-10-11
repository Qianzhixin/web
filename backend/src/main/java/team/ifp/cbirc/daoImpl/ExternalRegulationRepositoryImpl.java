package team.ifp.cbirc.daoImpl;

import org.springframework.stereotype.Repository;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationDao;
import team.ifp.cbirc.entity.ExternalRegulation;
import team.ifp.cbirc.po.SearchRegulationPO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
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
     * 根据 po、begin、len 进行法规搜索
     * @param po 搜索条件集合类
     * @return 如果是有意义的查询返回结果列别哦;如果是无意义查询(po 内条件全空、范围无意义,除了begin==0 && len==0 代表搜索全部记录)返回 null
     */
    @Override
    public List<ExternalRegulation> search(SearchRegulationPO po) {

        String sql = makeSearchSql(po);//获取查找sql

        if(sql == null) return null;//不是有意义的sql查询 返回null

        Query query = makeQuery(po,sql);//生成 Query 并注入变量

        List rows = query.getResultList();//查询结果

        List<ExternalRegulation> resultList = new ArrayList<>();//结果列表

        for (Object obj: rows) {
            Object[] row = (Object[]) obj;
            resultList.add(row2ExternalRegulation(row));
        }

        return resultList;
    }

    /**
     * 生成搜索sql
     * @param po 搜索条件集合类
     * @return sql 能够形成有意义查询则返回对应 sql;否则返回 null
     */
    private String makeSearchSql(SearchRegulationPO po) {
        StringBuilder sqlBuilder = new StringBuilder("select * from external_regulation ");

        //拼接搜索条件sql
        if(!spliceSearchCondition(sqlBuilder,po)) return null;
        //拼接搜索范围sql
        if(!spliceSearchRange(sqlBuilder,po.getBegin(),po.getLen())) return null;

        return sqlBuilder.toString();
    }

    /**
     * 生成 sql 对应 Query 对象,并注入 po 内部变量
     * @param po
     * @param sql
     * @return
     */
    private Query makeQuery(SearchRegulationPO po,String sql) {
        Query query = entityManager.createNativeQuery(sql);

        if(po.getTitle() != null)query.setParameter("title",po.getTitle());
        if(po.getNumber() != null)query.setParameter("number",po.getNumber());
        if(po.getEffectivenessLevel() != null)query.setParameter("effectiveness_level",po.getEffectivenessLevel());
        if(po.getPublishingDepartment() != null)query.setParameter("publishing_department",po.getPublishingDepartment());
        if(po.getReleaseDate() != null)query.setParameter("release_date",po.getReleaseDate());
        if(po.getImplementationDate() != null)query.setParameter("implementation_date",po.getImplementationDate());
        if(po.getState() != null)query.setParameter("state",po.getState().equals(RegulationState.UNPUBLISHED)?0:1);

        return query;
    }

    /**
     * 根据 po 拼接搜索条件(where/and)
     * @param po
     * @param sqlBuilder
     * @return 是否能够拼接有意义的查询
     */
    private boolean spliceSearchCondition(StringBuilder sqlBuilder,SearchRegulationPO po) {
        //当条件不为null时才加入判断
        //isFirst && !(isFirst = false)判断结果只有第一次为true,后续为false
        boolean isFirst = true;
        if(po.getTitle() != null){
            sqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" title = :title ");
        }
        if(po.getNumber() != null){
            sqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" number = :number ");
        }
        if(po.getEffectivenessLevel() != null) {
            sqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" effectiveness_level = :effectiveness_level ");
        }
        if(po.getPublishingDepartment() != null) {
            sqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" publishing_department = :publishing_department ");
        }
        if(po.getReleaseDate() != null) {
            sqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" release_date = :release_date ");
        }
        if(po.getImplementationDate() != null) {
            sqlBuilder.append((isFirst && !(isFirst = false)) ? "where" : "and").append(" implementation_date = :implementation_date ");
        }
        if(po.getState() != null) {
            sqlBuilder.append(isFirst && !(isFirst = false) ? "where" : "and").append(" state = :state ");
        }

        return !isFirst;
    }

    /**
     * 拼接搜索范围
     * begin==0 && len==0 代表搜索全部记录
     * @param sqlBuilder
     * @param begin
     * @param len
     * @return 是否能够拼接有意义的查询
     */
    private boolean spliceSearchRange(StringBuilder sqlBuilder,int begin,int len) {
        if(begin==0 && len==0) return true;
        else if(begin<0 || len<0) return false;

        sqlBuilder.append("limit ").append(begin).append(",").append(len);

        return true;
    }

    /**
     * 将数据库查询出的行转换为ExternalRegulation
     * @param cells
     * @return
     */
    private ExternalRegulation row2ExternalRegulation(Object[] cells) {
        ExternalRegulation externalRegulation = new ExternalRegulation();
        int i = 0;
        externalRegulation.setId((int) cells[i++]);
        externalRegulation.setTitle((String) cells[i++]);
        externalRegulation.setNumber((String) cells[i++]);
        externalRegulation.setType((String) cells[i++]);
        externalRegulation.setPublishingDepartment((String) cells[i++]);
        externalRegulation.setEffectivenessLevel((int) cells[i++]);
        externalRegulation.setReleaseDate((Date) cells[i++]);
        externalRegulation.setImplementationDate((Date) cells[i++]);
        externalRegulation.setInterpretationDepartment((String) cells[i++]);
        externalRegulation.setInputPerson((String) cells[i++]);
        externalRegulation.setInputDate((Date) cells[i++]);
        externalRegulation.setTextPath((String) cells[i++]);
        externalRegulation.setState((((int)cells[i++])==0)?RegulationState.UNPUBLISHED:RegulationState.PUBLISHED);
        return externalRegulation;
    }

}

package cn.noteblog.pagination.dialect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.type.SimpleTypeRegistry;

import cn.noteblog.pagination.domain.PageBounds;


public class Dialect {
	
	private Map<String,Object> newParameterObject = new HashMap<String,Object>(); 
	private List<ParameterMapping> parameterMappings;
	private Object parameterObject;
	private MappedStatement mappedStatement;
	protected PageBounds<?> pageBounds;
	private Executor executor;
	protected BoundSql boundSql;
	
	@SuppressWarnings("unused")
	private Dialect(){}
	
	public Dialect (Object parameterObject,MappedStatement mappedStatement,PageBounds pageBounds,Executor executor){
		this.parameterObject = parameterObject;
		this.mappedStatement = mappedStatement;
		this.pageBounds = pageBounds;
		this.executor = executor;
		buildParameterObject(parameterObject,mappedStatement);
	}
	
	/* 将参数组装进parameterObject*/
	@SuppressWarnings("unchecked")
	public void buildParameterObject (Object parameterObject,MappedStatement mappedStatement){
		boundSql = mappedStatement.getBoundSql(parameterObject);
		parameterMappings = new ArrayList(boundSql.getParameterMappings());
		if(parameterObject instanceof Map){
			newParameterObject.putAll((Map)parameterObject);
		}else if(parameterObject !=null){
			//判断是否属于基本类型而不是自定义类型
			Class clazz = parameterObject.getClass();
			if(clazz.isPrimitive() || clazz.isArray()||SimpleTypeRegistry.isSimpleType(clazz)
					||Enum.class.isAssignableFrom(clazz)||Collection.class.isAssignableFrom(clazz)){
				for(ParameterMapping parameterMapping:parameterMappings){
					newParameterObject.put(parameterMapping.getProperty(), parameterObject);
				}
			}else{
				//如果是实体类
				MetaObject metaObject = mappedStatement.getConfiguration().newMetaObject(parameterObject);
				ObjectWrapper objectWrapper = metaObject.getObjectWrapper();
				for(ParameterMapping parameterMapping:parameterMappings){
					PropertyTokenizer popt = new PropertyTokenizer(parameterMapping.getProperty());
					String prop = parameterMapping.getProperty();
					Object obj = objectWrapper.get(popt);
					newParameterObject.put(prop, obj);
				}
			}
		}
	}
	/*此方法子类必须重写，返回带limit的sql语句*/
	public String getPageSql () {
		return null;
	}
	
	/*设置参数*/
	@SuppressWarnings("unchecked")
	protected void setPageParameter(String name,Object value,Class type){
		ParameterMapping parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(), name, type).build();
		parameterMappings.add(parameterMapping);
		newParameterObject.put(name, value);
		
	}
	/*重新构建MappedStatement对象*/
	public MappedStatement buildMappedStatement(){
		StaticSqlSource newSqlSource = new StaticSqlSource(mappedStatement.getConfiguration(), getPageSql(), getParameterMappings());
		MappedStatement.Builder builder=  new MappedStatement.Builder(mappedStatement.getConfiguration(),
				mappedStatement.getId(),newSqlSource,mappedStatement.getSqlCommandType());
		builder.resultMaps(mappedStatement.getResultMaps()).resultSetType(mappedStatement.getResultSetType())
				.statementType(mappedStatement.getStatementType());
		return builder.build();
	}

	//查询记录条数
	public void getCount() throws SQLException {
		
			String countSql ="SELECT COUNT(1) FROM ("+boundSql.getSql()+") AS totals";
			//通过boundSql得到对应的参数映射
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),countSql,parameterMappings,parameterObject);
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);
			
			
			Connection connection = executor.getTransaction().getConnection();
			PreparedStatement ps = connection.prepareStatement(countSql);
			parameterHandler.setParameters(ps);
			
			ResultSet rs = ps.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			pageBounds.setTotalCount(totalCount);

		}
	
	public Map<String, Object> getNewParameterObject() {
		return newParameterObject;
	}
	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}
	
	

}

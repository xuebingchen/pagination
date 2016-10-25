package cn.noteblog.pagination.dialect;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;

import cn.noteblog.pagination.domain.PageBounds;

public class MySqlDialect extends Dialect{

	public MySqlDialect(Object parameterObject,MappedStatement mappedStatement,PageBounds pageBounds,Executor executor) {
		super(parameterObject, mappedStatement, pageBounds, executor);
	}

	public String getPageSql () {
		StringBuffer sqlBuffer = new StringBuffer(boundSql.getSql().trim());
		if(sqlBuffer.lastIndexOf(";") ==sqlBuffer.length()-1){
			sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		}
		if(pageBounds.getOffset() != 0){
			StringBuffer buffer = new StringBuffer(sqlBuffer.length()+20).append(sqlBuffer);
			buffer.append(" limit ?,?");
			setPageParameter(" _offset ",pageBounds.getLimit(),Integer.class);
			setPageParameter("_limit",pageBounds.getOffset(),Integer.class);
			return buffer.toString();
		}else{
			StringBuffer buffer = new StringBuffer(sqlBuffer.length()+20).append(sqlBuffer);
			buffer.append(" limit ?");
			setPageParameter("_limit",0,Integer.class);
			return buffer.toString();
		}
	}
}

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;

import cn.noteblog.pagination.domain.Blog;
import cn.noteblog.pagination.domain.PageBounds;



public class PagitionTest {
	


	
	@Test
	public void pageTest(){
		try{
	
		String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        DefaultSqlSessionFactory sqlSessionFactory = (DefaultSqlSessionFactory) new SqlSessionFactoryBuilder().build(inputStream);
        DefaultSqlSession session = (DefaultSqlSession) sqlSessionFactory.openSession();
        
        PageBounds bounds = new PageBounds();
        bounds.setTotalCount(12);
        bounds.setPageCount(13);
        bounds.setOrderBy("order by id desc");
        HashMap<String, Object> params = new HashMap<String, Object>();
        Blog blog = new Blog();
        blog.setUserId(3);
//        List<Object> list = session.selectList("cn.noteblog.pagination.domain.Blog.getList");
//        System.out.println(list.size());
        List<Object> list = session.selectList("db.table.blog.findByBlog", blog, bounds);
        System.out.println(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}

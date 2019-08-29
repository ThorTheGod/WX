package com.noir.demo.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class SessionFactoryConfiguration {  //事务提交类：将dataSource与mybatis绑定
    @Value("${mybatis_config_file}")
    private String mybatisConfigFilePath;//设置mybatis-config的路径

    @Value("${mapper_path}")
    private String mapperPath;  //mapper用于将数据库请求转换为sql语句，此处设置mapper类的路径（全部扫描）

    @Value("${entity_package}")
    private String entityPackage;//实体类所在的包路径，全部扫描

    @Autowired  //通过找寻名为dataSource的bean自动注入到此处的dataSource
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")//交于spring管理创建
    public SqlSessionFactoryBean createSqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //装载mybatis-config的扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //拼接mapper文件的扫描路径，其中前者值为classpath（即resources）根路径
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapperPath;
        //加载mapper文件的路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResource(packageSearchPath));
        //加载dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        //指定实体类包的扫描路径,以映射实体类；setTypeAlisesPackage专用于接收String类型
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        return sqlSessionFactoryBean;
    }

}

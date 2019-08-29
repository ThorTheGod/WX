package com.noir.demo.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

//数据源配置类，为数据库连接服务
@Configuration
@MapperScan("com.noir.demo.dao")//配置mybatis—mapper的扫描路径
public class DataSourceConfiguration {
    //从资源包的application文件中读取基础配置值
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    //通过bean标签让spring的ioc创建bean
    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //设置数据源属性值
        dataSource.setDriverClass(jdbcDriver);//驱动
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        //关闭连接后不自动提交，便于事务对其控制
        dataSource.setAutoCommitOnClose(false);

        return dataSource;
    }
}

package com.zccp.commons.durid;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mybatis配置,包含分页配置
 *
 * @author chenm 2018-04-16 下午12:03
 * @reviewer
 */
@Configuration
@ConditionalOnProperty(prefix = "druid.datasource.primary", value = "enabled", havingValue = "true", matchIfMissing = false)
@MapperScanner(basePackages = {"${druid.datasource.primary.base.package}"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DruidDatasourceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DruidDatasourceConfiguration.class);

    @Value("${druid.datasource.primary.base.package}")
    private String dataSourcePackage;
    @Autowired
    @Qualifier(DynamicDataSourceRegister.DEFAULT_DS)
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        String typeAliasesPackage = dataSourcePackage + ".dataobject";
        String path = "classpath*:" + dataSourcePackage + ".mapper/*.xml";
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("reasonable", "false");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageHelper.setProperties(props);

        factoryBean.setPlugins(new Interceptor[]{pageHelper});
        //mybatis.typeAliasesPackage：指定domain类的基包，即指定其在*Mapper.xml文件中可以使用简名来代替全类名（看后边的UserMapper.xml介绍）
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        //mybatis.mapperLocations：指定*Mapper.xml的位置 如果不加会报org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.blog.mapper.MessageMapper.findMessageInfo异常
        //因为找不到*Mapper.xml，也就无法映射mapper中的接口方法。
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(path));
        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public String getDataSourcePackage() {
        return dataSourcePackage;
    }

    public void setDataSourcePackage(String dataSourcePackage) {
        this.dataSourcePackage = dataSourcePackage;
    }
}

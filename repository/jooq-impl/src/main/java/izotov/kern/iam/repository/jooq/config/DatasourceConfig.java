package izotov.kern.iam.repository.jooq.config;


import izotov.kern.iam.repository.jooq.poperties.JDBCProperties;

public interface DatasourceConfig {
    
    void setJdbcProperties(JDBCProperties jdbcProperties);
    
    JDBCProperties getJdbcProperties();
}

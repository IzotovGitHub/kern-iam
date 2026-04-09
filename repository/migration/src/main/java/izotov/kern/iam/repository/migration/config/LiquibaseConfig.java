package izotov.kern.iam.repository.migration.config;


import izotov.kern.iam.repository.migration.properties.LiquibaseProperties;

public interface LiquibaseConfig {
    
    void setLiquibaseProperties(LiquibaseProperties liquibaseProperties);
    LiquibaseProperties getLiquibaseProperties();
}

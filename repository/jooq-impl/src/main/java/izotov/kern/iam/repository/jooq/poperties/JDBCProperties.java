package izotov.kern.iam.repository.jooq.poperties;

public interface JDBCProperties {
    
    void setUrl(String url);
    
    void setUsername(String username);
    
    void setPassword(String password);
    
    void setDriverClassName(String driverClassName);
    
    void setHikari(Hikari hikari);
    
    String getUrl();
    
    String getUsername();
    
    String getPassword();
    
    String getDriverClassName();
    
    Hikari getHikari();
    
    interface Hikari {
        
        void setMaximumPoolSize(Integer maximumPoolSize);
        
        Integer getMaximumPoolSize();
    }
}

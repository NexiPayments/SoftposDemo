package it.nexi.softpos_evo.domain.enums;

/**
 * 
 * @author Nexi Payments 
 */
public enum DomainEnum {
    
    /**
     *
     */
    STAGE(1,"STAGING"),

    /**
     *
     */
    PROD(2,"PRODUCTION");
  
    private final Integer id;
    private final String environment;

    private DomainEnum(Integer id, String environment) {
      this.id = id;
      this.environment = environment;
    }
    
    /**
     *
     * @return
     */
    public Integer getId() {
      return id;
    }

    /**
     *
     * @return
     */
    public String getEnvironment() {
      return environment;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public static String getEnvironmentById(Integer id) {
        if (id.equals(DomainEnum.STAGE.getId())) {
            return DomainEnum.STAGE.getEnvironment();
        } else if (id.equals(DomainEnum.PROD.getId())) {
            return DomainEnum.PROD.getEnvironment();
        }
        return "";
    }
  
}


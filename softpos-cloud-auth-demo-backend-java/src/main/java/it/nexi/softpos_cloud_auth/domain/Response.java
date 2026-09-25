package it.nexi.softpos_cloud_auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Nexi Payments 
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    
    private T ResponseBody;
    private String Error;
    
}

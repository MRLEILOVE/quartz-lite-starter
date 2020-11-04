package com.leigq.quartzlite.autoconfigure.configure;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Orika 配置.
 *
 * @author leigq
 * <br/>
 * Orika 的使用看这里：
 * <ul>
 *     <li>
 *         <a href='https://www.jianshu.com/p/271cf6976a3d'>Orika的使用姿势</a>
 *     </li>
 *     <li>
 *         <a href='https://www.cnblogs.com/albert1024/articles/8434741.html'>Orika JavaBean映射工具探秘</a>
 *     </li>
 * </ul>
 */
@Configuration
public class OrikaAutoConfig {

    /**
     * 注入 DefaultMapperFactory，方便在使用的地方直接注入
     *
     * @return the mapper factory
     */
    @Bean
    @ConditionalOnMissingBean(MapperFactory.class)
    public MapperFactory getMapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

}

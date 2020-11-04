package com.leigq.quartzlite.autoconfigure.configure;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisPlus 配置
 * <p>
 *
 * @author LeiGQ <br>
 * @date 2019-06-14 10:40 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Configuration
@MapperScan("com.leigq.quartzlite.starter.domain.mapper")
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class MyBatisPlusAutoConfig {

    /**
     * 分页插件
     *
     * @author ：LeiGQ <br>
     * @date ：2019-06-14 10:43 <br>
     * <p>
     */
    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链 作用！阻止恶意的全表更新删除
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    /**
     * 乐观锁
     *
     * @author ：LeiGQ <br>
     * @date ：2019-06-14 10:55 <br>
     * <p>
     */
    @Bean
    @ConditionalOnMissingBean(OptimisticLockerInterceptor.class)
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}

package com.leigq.quartzlite.starter.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leigq.quartzlite.starter.domain.entity.SysTaskLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysTaskLogMapper extends BaseMapper<SysTaskLog> {
}
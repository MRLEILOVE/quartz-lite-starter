package com.leigq.quartzlite.starter.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leigq.quartzlite.starter.bean.vo.SysTaskListVO;
import com.leigq.quartzlite.starter.domain.entity.SysTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysTaskMapper extends BaseMapper<SysTask> {
	/**
	 * 获取任务列表
	 *
	 * @param page the page
	 * @return the page
	 */
	IPage<SysTaskListVO> taskList(@Param("page") Page<SysTaskListVO> page);
}
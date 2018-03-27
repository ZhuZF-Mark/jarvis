package com.jarvis.user.mapper;

import com.jarvis.user.Dto.ModuleFunctionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/20.
 */
@Mapper
public interface SystemModuleMapper {
    /**
     * 查询所有模块和功能点
     * @return list
     */
    List<ModuleFunctionDto> findAllModuleFunction();
}

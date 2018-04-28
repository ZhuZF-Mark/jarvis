package com.jarvis.user.mapper;

import com.jarvis.user.dto.FunctionAccessDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/22.
 */
@Mapper
public interface FunctionAccessMapper {
    List<FunctionAccessDto> findUserModuleFunctionAccess(@Param("userId") Long userId, @Param("moduleId")Long moduleId);
}

package com.wan.tool.mapper.idm;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.wan.tool.model.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年05月15日 19:51:40
 */
@DS("idm")
@Mapper
public interface IdmMapper {

    public List<Company> getCompanies(@Param("companyNames") List<String> companyNames,
                                      @Param("hierarchyCodes") List<String> hierarchyCodes);

}

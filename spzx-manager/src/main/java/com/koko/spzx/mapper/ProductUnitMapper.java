package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductUnitMapper {
    List<ProductUnit> findAllProductUnits();

}

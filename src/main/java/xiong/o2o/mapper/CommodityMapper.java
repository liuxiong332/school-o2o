package xiong.o2o.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import xiong.o2o.entity.Commodity;

import java.util.List;

public interface CommodityMapper extends BaseMapper<Commodity> {

    @Select("select * from commodity")
    List<Commodity> selectAll();

}

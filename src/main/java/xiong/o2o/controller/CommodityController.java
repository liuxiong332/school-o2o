package xiong.o2o.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xiong.o2o.mapper.CommodityMapper;
import xiong.o2o.service.CommodityService;
import xiong.o2o.util.OutputResult;
import xiong.o2o.vo.CommodityVO;

import java.util.List;

@RestController
public class CommodityController {
    Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    CommodityService commodityService;

    @GetMapping("/commodities")
    OutputResult<List<CommodityVO>> getAllCommodities() {
        return new OutputResult(commodityService.getAllCommodities());
    }

    @GetMapping("/commodities/{id}")
    OutputResult<List<CommodityVO>> getAllCommodities(@PathVariable Long id) {
        return new OutputResult(commodityService.getCommodity(id));
    }

    @PostMapping("/commodities")
    OutputResult createCommodity(@RequestBody CommodityVO commodityVO) {
        commodityService.createCommodity(commodityVO);
        return new OutputResult(null);
    }

    @PutMapping("/commodities/{id}")
    OutputResult updateCommodity(@RequestBody CommodityVO commodityVO, @PathVariable Long id) {
        commodityVO.setId(id);
        commodityService.updateCommodity(commodityVO);
        return new OutputResult(null);
    }
}
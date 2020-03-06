import React, { useState, useCallback, useEffect } from 'react';
import { App, View, Page, List, ListInput, ListButton, Button, Link, Row, Col } from 'framework7-react';
import { request } from "./common";

// home.jsx
export default (props) => {
  console.log("now props:", props);
  let [seckillPrice, setSeckillPrice] = useState();
  let [fromTime, setFromTime] = useState("");
  let [toTime, setToTime] = useState(0);
  
  const handlePrice = useCallback(event => {
    setSeckillPrice(event.target.value);
  });
  const handleFrom = useCallback(event => {
    setFromTime(new Date(event.target.value).getTime());
  });
  const handleTo = useCallback(event => {
    setToTime(new Date(event.target.value).getTime());
  });

  // useEffect(() => {
  //   if (props.id) {
  //     request(`/commodities/${props.id}`, { method: "GET" }).then(item => {
  //       setName(item.name);
  //       setDescription(item.description);
  //       setPrice(item.price);
  //       setQuantity(item.quantity);
  //     });
  //   }
  // }, [props.id]);
  const handleEdit = useCallback(() => {
    let body = {
      commodityId: props.commodityId, seckillPrice, fromTime, toTime,
    };
    
    request(`/seckills/`, { method: "POST", body }).then(() => {
      props.$f7router.back({ force: true, });
    });
  }, [props.commodityId, seckillPrice, fromTime, toTime]);

  // const handleAddPromote = useCallback(() => {
  //   props.$f7router.navigate(`/commodities/${props.id}/seckills/create`);
  // }, [props.id]);

  return (
    <Page name="seckillEdit">
      <List noHairlinesMd>
        
        <ListInput
          label="秒杀价格"
          type="text"
          placeholder="0.0"
          onChange={handlePrice}
          clearButton
        />

        <ListInput
          label="开始时间"
          type="datetime-local"
          onChange={handleFrom}
          placeholder="开始时间"
        />

        <ListInput
          label="结束时间"
          type="datetime-local"
          placeholder="结束时间"
          onChange={handleTo}
        />
        
      </List>

      <Row tag="p">
        <Col tag="span">
          <Button fill onClick={handleEdit}>{"创建"}</Button>
        </Col>
      </Row>
      
     
      <Row tag="p">
        <Col tag="span">
          <Button fill onClick={() => props.$f7router.back()}>
            返回
          </Button>
        </Col>
      </Row>
      
    </Page>
  );
};
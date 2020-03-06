import React, { useState, useCallback, useEffect } from 'react';
import { App, View, Page, List, ListInput, ListButton, Button, Link, Row, Col } from 'framework7-react';
import { request } from "./common";

// home.jsx
export default (props) => {
  console.log("now props:", props);
  let [name, setName] = useState("");
  let [description, setDescription] = useState("");
  let [price, setPrice] = useState(0);
  let [quantity, setQuantity] = useState(1);
  let [promoteId, setPromoteId] = useState(null);

  const handleName = useCallback(event => {
    setName(event.target.value);
  });
  const handleDescription = useCallback(event => {
    setDescription(event.target.value);
  });
  const handlePrice = useCallback(event => {
    setPrice(Number(event.target.value));
  });
  const handleQuantity = useCallback(event => {
    setQuantity(parseInt(event.target.value));
  });

  useEffect(() => {
    if (props.id) {
      request(`/commodities/${props.id}`, { method: "GET" }).then(item => {
        setName(item.name);
        setDescription(item.description);
        setPrice(item.price);
        setQuantity(item.quantity);
        setPromoteId(item.promoteId);
      });
    }
  }, [props.id]);
  const handleEdit = useCallback(() => {
    let body = {
      name, description, price, quantity,
    };
    if (props.id) {
      request(`/commodities/${props.id}`, { method: "PUT", body });
    } else {
      request(`/commodities`, { method: "POST", body });
    }
  }, [props.id, name, description, price, quantity]);

  const handleAddPromote = useCallback(() => {
    if (promoteId != null) {
      request(`/seckills/${promoteId}`, { method: "DELETE" }).then(() => {
        setPromoteId(null);
      });
    } else {
      props.$f7router.navigate(`/commodities/${props.id}/seckills/create`);
    }
  }, [props.id, promoteId]);

  return (
    <Page name="commodityEdit">
      <List noHairlinesMd>
        
        <ListInput
          label="Name"
          type="text"
          placeholder="Your name"
          value={name}
          onChange={handleName}
          clearButton
        />

        <ListInput
          label="Description"
          type="text"
          placeholder="Your Description"
          value={description}
          onChange={handleDescription}
          clearButton
        />

        <ListInput
          label="Price"
          type="number"
          placeholder="Your Price"
          value={price}
          onChange={handlePrice}
          clearButton
        />

        <ListInput
          label="Quantity"
          type="number"
          placeholder="Your Quantity"
          value={quantity}
          onChange={handleQuantity}
          clearButton
        />
        
      </List>

      {props.id && (
        <Row tag="p">
          <Col tag="span">
            <Button fill onClick={handleAddPromote}>
              {promoteId == null ? "添加秒杀活动" : "删除秒杀活动"}
            </Button>
          </Col>
        </Row>
      )}

      <Row tag="p">
        <Col tag="span">
          <Button fill onClick={handleEdit}>{props.id ? "编辑" : "创建" }</Button>
        </Col>
      </Row>
      
      {/* <Button fill onClick={() => this.$f7router.back()}>
        返回
      </Button> */}
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
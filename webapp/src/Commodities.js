import React, { useState, useEffect, useCallback } from 'react';
import { App, View, Page, List, ListInput, Icon, ListItem, BlockTitle, Button, Link } from 'framework7-react';
import { request } from "./common";

export default (props) => {
  let [itemList, setItemList] = useState([]);

  useEffect(() => {
    request(`/commodities`).then(data => setItemList(data));
  }, []);

  // const clickCreate = useCallback(() => {
  //   console.log("views:", this.$f7.views); 
  //   this.$f7router.navigate("/commodities/create")
  // }, []);

  return (
    <Page name="commodities">
      <BlockTitle>所有商品</BlockTitle>
      <List>
        {itemList.map(item => (
          <ListItem key={item.id} link={`/commodities/edit/${item.id}`} header={item.name} title={item.description} after="Edit">
            <Icon slot="media" icon="demo-list-icon"></Icon>
          </ListItem>
        ))}
      </List>
      {/* <Button fill onClick={clickCreate}>创建</Button> */}
      <Button fill onClick={() => props.$f7router.navigate('/commodities/create')}>
        创建
      </Button>
    </Page>
  );
}
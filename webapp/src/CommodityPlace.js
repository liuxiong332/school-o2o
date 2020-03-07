import React, { useState, useCallback, useEffect } from 'react';
import { 
  App, View, Page, List, ListItem, ListButton, Button, Navbar, Link, 
  Row, Col, BlockTitle, ListInput, Block, Icon, Sheet 
} from 'framework7-react';
import { request } from "./common";

function getCommodity(id, setItemModel) {
  request(`/commodities/${id}`, { method: "GET" }).then(item => {
    setItemModel(item);
  });
}
// home.jsx
export default (props) => {
  let [itemModel, setItemModel] = useState({});
  let [popupOpened, setPopupOpened] = useState(false);
 
  useEffect(() => {
    if (props.id) {
      getCommodity(props.id, setItemModel);
    }
  }, [props.id]);
  
  const handlePlace = useCallback(() => {
    let token = localStorage.getItem("token");
    if (localStorage.getItem("token") == null) {
      return props.$f7router.navigate("/login");
    }
    request(`/commodities/${props.id}/placeOrder`, { method: "POST", headers: { token } }).then(() => {
      getCommodity(props.id, setItemModel);
      // alert("下单成功！");
      setPopupOpened(true);
      setTimeout(() => setPopupOpened(false), 1000);
    }).catch(err => {
      if (err.code === 401) {
        props.$f7router.navigate("/login");
      } else {
        console.error(err);
      }
    });
  }, [props.id]);

  return (
    <Page name="commodityEdit">
      <List noHairlinesMd>
        
        <ListItem title="商品名字" after={itemModel.name}>
        </ListItem>

        <ListItem title="商品描述" after={itemModel.description}>
        </ListItem>
        
        <ListItem title="价格" after={itemModel.price + ""}>
        </ListItem>
         
        <ListItem title="库存" after={itemModel.quantity + ""}>
        </ListItem>
      </List>

      {itemModel.promoteId && (
        <React.Fragment>
          <BlockTitle>当前商品正在参加秒杀活动</BlockTitle>

          <List>
            <ListItem title="秒杀价格" after={itemModel.promotePrice}>
            </ListItem>
          
            <ListItem title="开始时间" after={new Date(itemModel.startTime).toLocaleString("zh-CN")}>
            </ListItem>
          
            <ListItem title="结束时间" after={new Date(itemModel.endTime).toLocaleString("zh-CN")}>
            </ListItem>
          </List>
        </React.Fragment>
      )}
      
      <Block>

        <Row tag="p">
          <Col tag="span">
            <Button fill onClick={handlePlace}>{"下单"} <Icon icon="icon-home"></Icon></Button>
          </Col>
        </Row>
        <Row tag="p">
          <Col tag="span">
            <Button fill onClick={() => props.$f7router.navigate(`/commodities/edit/${props.id}`)}>{"编辑"}</Button>
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
      </Block>
      
      <Sheet 
        opened={popupOpened} 
      >
        <Block>
          {/* <BlockTitle>下单成功</BlockTitle>  */}
          <Button color="green">下单成功</Button>
        </Block>
      </Sheet>
    </Page>
  );
};
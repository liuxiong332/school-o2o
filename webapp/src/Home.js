import React from 'react';
import { App, View, Page, List, ListInput, ListButton } from 'framework7-react';
import { request } from "./common";

async function handleSendCode() {
  request("/sendVerifyCode?email=lx@xx", { method: "POST" });
}

// home.jsx
export default () => (
  <Page name="home">
    <List noHairlinesMd>
      <ListInput
        label="E-mail"
        type="email"
        placeholder="Your e-mail"
        clearButton
      />

      <ListInput
        label="Email verification code"
        type="text"
        placeholder="verification code"
        clearButton
      />
      <ListButton title="Send Verification Code" onClick={handleSendCode}/>

      <ListInput
        label="Name"
        type="text"
        placeholder="Your name"
        clearButton
      />

      <ListInput
        label="Enter Password"
        type="password"
        placeholder="Your password"
        clearButton
      />

      <ListInput
        label="Enter Password again"
        type="password"
        placeholder="Your password"
        clearButton
      />

      
    </List>
  </Page>
);
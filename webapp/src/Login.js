import React, { useState, useCallback } from 'react';
import { App, View, Page, List, ListInput, ListButton, Button } from 'framework7-react';
import { request } from "./common";

// home.jsx
export default () => {
  let [username, setUsername] = useState("");
  let [password, setPassword] = useState("");
 
  const handleUsername = useCallback(event => {
    setUsername(event.target.value);
  });
  const handlePassword = useCallback(event => {
    setPassword(event.target.value);
  });
   
  const handleLogin = useCallback(() => {
    let body = {
      username, password
    };
    request(`/login`, { method: "POST", body });
  });
  return (
    <Page name="home">
      <List noHairlinesMd>
        
        <ListInput
          label="Name"
          type="text"
          placeholder="Your name"
          value={username}
          onChange={handleUsername}
          clearButton
        />

        <ListInput
          label="Enter Password"
          type="password"
          placeholder="Your password"
          value={password}
          onChange={handlePassword}
          clearButton
        />
        
      </List>

      <Button fill onClick={handleLogin}>Login</Button>
    </Page>
  );
};